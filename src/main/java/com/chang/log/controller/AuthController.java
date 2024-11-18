package com.chang.log.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chang.log.request.user.LoginRequest;
import com.chang.log.service.AuthService;

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
	public ResponseEntity<String> getMemberProfile(
		@Valid @RequestBody LoginRequest loginRequest
	) {
		String token = authService.login(loginRequest);
		log.info("toekn ={}",token);
		return ResponseEntity.status(HttpStatus.OK)
			.header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
			.body(token);
	}

	// @PostMapping("/logout")
	// public ResponseEntity<Void> logout(@RequestHeader (HttpHeaders.AUTHORIZATION) String token) {
	// 	String jwt = token.replace("Bearer", "");
	//
	// }
}
