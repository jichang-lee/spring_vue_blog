package com.chang.log.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.chang.log.domain.User;
import com.chang.log.repository.UserRepository;
import com.chang.log.request.user.LoginRequest;

@SpringBootTest
public class AuthServiceTest {

	@Autowired
	private AuthService authService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUser() {
		User user = User.builder()
			.name("이지창")
			.email("jccc23@naver.com")
			.password(passwordEncoder.encode("1234"))
			.build();
		userRepository.save(user);
	}

	@AfterEach
	void clean(){
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("로그인 성공 했을 떄")
	void loginSuccess() {
		//given
		LoginRequest loginInput = LoginRequest.builder()
			.email("jccc23@naver.com")
			.password("1234")
			.build();

		//when
		Map<String, String> tokens = authService.login(loginInput);

		//then
		assertNotNull(tokens.get("accessToken"),"AccessToken 이 null 일 수 없음");
		assertNotNull(tokens.get("refreshToken"),"RefreshToken 은 null 이 될 수 없다");
	}

	@Test
	@DisplayName("로그인 실패_계정이 존재 하지 않을 떄")
	void loginFail_withoutAccount() {
		//given
		LoginRequest loginInput = LoginRequest.builder()
			.email("exist@email.com")
			.password(passwordEncoder.encode("1234"))
			.build();

		//when & then
		assertThrows(NoSuchElementException.class, () ->{
			authService.login(loginInput);
		});
	}

	@Test
	@DisplayName("로그인 실패_계정 비밀번호 틀렸을 때")
	void loginFail_password() {
		//given
		LoginRequest loginInput = LoginRequest.builder()
			.email("jccc23@naver.com")
			.password(passwordEncoder.encode("부대볶음 먹고싶다"))
			.build();

		//when & then
		assertThrows(BadCredentialsException.class, () -> {
			authService.login(loginInput);
		});

	}
}
