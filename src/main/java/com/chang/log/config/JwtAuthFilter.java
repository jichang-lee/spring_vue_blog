package com.chang.log.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chang.log.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

	private final CustomUserDetailsService customUserDetailsService;
	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		String refr = request.getHeader("Refresh-Token");
		log.info("header auth = {}",authHeader);
		log.info("header reft={}",refr);

		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			log.info("token = {}",token);
			//JWT 유효성 검증
			if(jwtUtil.validateToken(token)) {
				Long userId = jwtUtil.getUserId(token);

				//유저와 토큰 일치 시 userDetails 생성
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId.toString());

				if(userDetails != null) {
					//userDetails, password, Role -> 접근권한 인증 Token 생성
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

					//현재 Request 의 Security Context 에 접근권한 설정
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}

			}

		}
		filterChain.doFilter(request,response);
	}
}
