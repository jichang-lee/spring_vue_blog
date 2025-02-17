package com.chang.log.controller;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chang.log.request.user.LoginRequest;
import com.chang.log.response.UserResponse;
import com.chang.log.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;


	@PostMapping("/login")
	public ResponseEntity<Map<String,String>> getMemberProfile(
		@Valid @RequestBody LoginRequest loginRequest
	) {
		Map<String, String> tokens = authService.login(loginRequest);

		log.info("toekn ={}",tokens);
		return ResponseEntity.status(HttpStatus.OK)
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + tokens.get("accessToken"))
			.body(tokens);
	}

	@PostMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.isAuthenticated()) {
			new SecurityContextLogoutHandler().logout(request,response,authentication);
		}
	}

	@PostMapping("/userInfo")
	public UserResponse getUserInfo(@RequestHeader("Authorization") String token) {
		String accessToken = token.substring(7);

		return authService.getUserInfo(accessToken);
	}


	// @PostMapping("/reissue")
	// public ResponseEntity<Void> logout(@RequestHeader () {
	// 	String jwt = token.replace("Bearer", "");
	//
	// }


}
