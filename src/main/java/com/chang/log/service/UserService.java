package com.chang.log.service;

import com.chang.log.domain.User;
import com.chang.log.exception.AlreadyExistsEmail;
import com.chang.log.repository.UserRepository;
import com.chang.log.request.user.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUp signUp) {

        //1. byEmail -> 중복 이메일 검증
        Optional<User> byEmail = userRepository.findByEmail(signUp.getEmail());
        if(byEmail.isPresent()){
            throw new AlreadyExistsEmail();
        }

        //2. passwordEncoder
        String encrypt = passwordEncoder.encode(signUp.getPassword());

        User user = User.builder()
                .email(signUp.getEmail())
                .password(encrypt)
                .name(signUp.getName())
                .build();

        userRepository.save(user);
    }


}
