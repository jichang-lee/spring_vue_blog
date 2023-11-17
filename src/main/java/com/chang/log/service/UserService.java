package com.chang.log.service;

import com.chang.log.domain.User;
import com.chang.log.repository.UserRepository;
import com.chang.log.request.user.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public void signUp(SignUp signUp) {

        //1. byEmail -> 중복 이메일 검증
        //2. passwordEncoder

        User user = User.builder()
                .email(signUp.getEmail())
                .password(signUp.getPassword())
                .name(signUp.getName())
                .build();

        userRepository.save(user);
    }


}
