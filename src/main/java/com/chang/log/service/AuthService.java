package com.chang.log.service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chang.log.domain.User;
import com.chang.log.exception.UserNotFound;
import com.chang.log.repository.UserRepository;
import com.chang.log.request.user.LoginRequest;
import com.chang.log.response.UserResponse;
import com.chang.log.response.UserTokenInfo;
import com.chang.log.util.JwtUtil;
import com.chang.log.util.RedisUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
	private final RedisService redisService;

	@Transactional
	public Map<String,String> login(LoginRequest req) {
		String email = req.getEmail();
		var password = req.getPassword();
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new NoSuchElementException(email + " 회원 이메일을 찾을 수 없습니다."));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		}

		UserTokenInfo memberInfo = modelMapper.map(user, UserTokenInfo.class);

		String refreshAccessToken = jwtUtil.createRefreshAccessToken(memberInfo);
		String accessToken = jwtUtil.createAccessToken(memberInfo);


		// redisService.saveToken(refreshTokenRedisKey,refreshAccessToken, jwtUtil.getRefreshTokenExpTime());

		HashMap<String, String> token = new HashMap<>();
		// 관리자에서 로그인 유저를 제어하기 위해 accessToken put
		token.put("accessToken",accessToken);
		token.put("refreshToken",refreshAccessToken);
		return token;
	}

	public UserResponse getUserInfo(String accessToken) {
		if (accessToken == null) {
			throw new NoSuchElementException("accessToken 값이 존재하지않습니다.");
		}
		Long userId = jwtUtil.getUserId(accessToken);
		if (userId == null) {
			throw new IllegalArgumentException("토큰에 대한 회원이 존재하지않습니다.");
		}

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new NoSuchElementException(userId + " 회원을 찾을 수 없습니다"));

		return new UserResponse(user.getName(),
			user.getEmail());
	}

	public void deleteRefreshToken(HttpServletRequest request) {
		if(request.getHeader("Authorization") != null) {
			String token = jwtUtil.resolveToken(request);
			String userEmail = jwtUtil.getUserEmail(token);

			User user = userRepository.findByEmail(userEmail)
				.orElseThrow(UserNotFound::new);

			redisUtil.delete(user.getEmail());
		}

	}


	/*
		redis
	 */



}
