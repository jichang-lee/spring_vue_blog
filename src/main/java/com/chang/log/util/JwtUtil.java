package com.chang.log.util;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.chang.log.enums.RoleType;
import com.chang.log.exception.HeaderTokenNotFound;
import com.chang.log.exception.RefreshTokenNotFound;
import com.chang.log.response.UserTokenInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {

	private final Key key;
	private final long accessTokenExpTime;
	private final long refreshTokenExpTime;

	@Autowired
	private final RedisUtil redisUtil;

	public JwtUtil(@Value("${jwt.secret}") String secretKey,
		@Value("${jwt.expiration_time}") long accessTokenExpTime,
		@Value("${jwt.refresh_expiration_time}") long refreshTokenExpTime,
		RedisUtil redisUtil
	) {
		this.redisUtil = redisUtil;
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.accessTokenExpTime = accessTokenExpTime;
		this.refreshTokenExpTime = refreshTokenExpTime;
	}

	public String createAccessToken(UserTokenInfo member) {
		return createToken(member, accessTokenExpTime);
	}

	public String createRefreshAccessToken(UserTokenInfo member) {
		String refreshToken = createToken(member, refreshTokenExpTime);
		redisUtil.save(member.getEmail(),refreshToken,refreshTokenExpTime);

		return refreshToken;
	}


	private String createToken(UserTokenInfo member, long accessTokenExpTime) {
		//토큰에 담을 회원 정보
		Claims claims = Jwts.claims();
		claims.put("userId", member.getUserId());
		claims.put("email", member.getEmail());
		claims.put("role", member.getRole());

		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime tokenValidity = now.plusSeconds(accessTokenExpTime);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(Date.from(now.toInstant()))
			.setExpiration(Date.from(tokenValidity.toInstant()))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	/*
		token 에서 user PK get
	 */
	public Long getUserId(String token) {
		return parseClaims(token).get("userId", Long.class);
	}

	public String getUserEmail(String token) {
		return parseClaims(token).get("email", String.class);
	}

	public RoleType getUserRole(String token) {
		return parseClaims(token).get("role", RoleType.class);
	}

	public String refreshAccessToken(String refreshToken) {
		Long userId = getUserId(refreshToken);
		String userEmail = getUserEmail(refreshToken);
		RoleType userRole = getUserRole(refreshToken);

		String storeRefreshToken = redisUtil.get(userEmail);

		if(storeRefreshToken == null || !storeRefreshToken.equals(refreshToken)) {
			throw new RefreshTokenNotFound();
		}

		UserTokenInfo user = new UserTokenInfo(userId, userEmail, userRole);
		return createAccessToken(user);
	}

	public String resolveToken(HttpServletRequest req) {
		String header = req.getHeader("Authorization");
		if(header != null && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		throw new HeaderTokenNotFound();
	}

	/*
		JWT 검증
	 */
	public boolean validateToken(String token) {
		try {
			log.info("key={}",key);
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.error("Invalid JWT Token", e);
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty.", e);
		}
		return false;
	}

	/*
		JWT Claims 추출
	 */
	public Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

}
