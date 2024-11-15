package com.chang.log.service;

import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chang.log.domain.User;
import com.chang.log.repository.UserRepository;
import com.chang.log.request.user.LoginRequest;
import com.chang.log.response.UserTokenInfo;
import com.chang.log.util.JwtUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;

	@Transactional
	public String login(LoginRequest req) {
		String email = req.getEmail();
		String password = req.getPassword();
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new NoSuchElementException(email+" 회원 이메일을 찾을 수 없습니다."));

		if(!passwordEncoder.matches(password,user.getPassword())) {
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		}

		UserTokenInfo memberInfo = modelMapper.map(user, UserTokenInfo.class);

		return jwtUtil.createAccessToken(memberInfo);
	}
}
