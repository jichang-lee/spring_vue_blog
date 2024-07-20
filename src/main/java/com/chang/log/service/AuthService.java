package com.chang.log.service;

import com.chang.log.domain.User;
import com.chang.log.repository.UserRepository;
import com.chang.log.request.user.LoginRequest;
import com.chang.log.response.UserTokenInfo;
import com.chang.log.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
                .orElseThrow(()-> new UsernameNotFoundException("이메일이 존재하지 않습니다."));

        //암호화 된 패스워드를 디코딩한 값과 입력한 패스워드가 다를 때
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("계정 정보가 일치하지 않습니다.");
        }

        UserTokenInfo userTokenInfo = modelMapper.map(user, UserTokenInfo.class);

        return jwtUtil.createAccessToken(userTokenInfo);

    }
}
