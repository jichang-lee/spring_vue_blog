package com.chang.log.config;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.chang.log.domain.User;
import com.chang.log.repository.UserRepository;
import com.chang.log.response.UserTokenInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		User user = userRepository.findById(Long.parseLong(id))
			.orElseThrow(() -> new UsernameNotFoundException("해당하는 유저가 없습니다."));

		UserTokenInfo dto = modelMapper.map(user, UserTokenInfo.class);

		return new CustomUserDetails(dto);
	}
}
