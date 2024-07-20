package com.chang.log.util;

import com.chang.log.response.UserTokenInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    private final Key key;
    private final long accessTokenExpTime;

    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration_time}") long accessTokenExpTime) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
    }

    /**
     * JWT 생성
     *
     * @param userTokenInfo
     * @return JWT String
     */

    public String createAccessToken(UserTokenInfo userTokenInfo) {
        return createToken(userTokenInfo,accessTokenExpTime);
    }

    public String createToken(UserTokenInfo userTokenInfo, long accessTokenExpTime) {
        Claims claims = Jwts.claims();
        claims.put("id", userTokenInfo.getId());
        claims.put("emaiul", userTokenInfo.getEmail());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(accessTokenExpTime);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant())) //발급 시각
                .setExpiration(Date.from(tokenValidity.toInstant())) //민료 시각
                .signWith(key, SignatureAlgorithm.HS256) // 키와 알고리즘으로 jwt 서명
                .compact(); //jwt 를 String 으로 return
    }

    /**
     * JWT token userId 추출
     *
     * @param token
     * @return token userId
     */
    public Long getUserId(String token) {
        return parseClaims(token).get("id", Long.class);
    }


    /**
     * JWT 검증
     * @param token
     * @return isValidate
     */

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SignatureException | MalformedJwtException e) {
            log.info("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty");
        }
        return false;
    }


    /**
     * JWT Claims 추
     *
     * @param accessToken
     * @return JWT Claims
     */
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }


}
