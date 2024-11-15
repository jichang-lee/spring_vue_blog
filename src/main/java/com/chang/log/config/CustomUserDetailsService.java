package com.chang.log.config;

import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chang.log.domain.User;
import com.chang.log.enums.RoleType;
import com.chang.log.repository.UserRepository;
import com.chang.log.response.UserTokenInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	// private final ModelMapper modelMapper;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		log.info("userId = {}",userId);
		User user = userRepository.findById(Long.parseLong(userId))
			.orElseThrow(() -> new NoSuchElementException(userId + " 해당 회원을 찾을 수 없습니다"));
		UserTokenInfo info = new UserTokenInfo();
		info.setUserId(user.getUserId());
		info.setEmail(user.getEmail());
		info.setPassword(user.getPassword());
		info.setRole(RoleType.ADMIN);

		// UserTokenInfo info = modelMapper.map(user, UserTokenInfo.class);

		return new CustomUserDetails(info);
	}
}
