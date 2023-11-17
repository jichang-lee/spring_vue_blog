package com.chang.log.service;

import com.chang.log.domain.User;
import com.chang.log.repository.UserRepository;
import com.chang.log.request.user.SignUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;



    @AfterEach
    void clean(){
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("회원가입 성공")
    void signUpSuccess(){

        //given
        SignUp signUp = SignUp.builder()
                .email("jichang@naver.com")
                .password("1234")
                .name("이지창")
                .build();

        //when
        userService.signUp(signUp);

        User user = userRepository.findAll().iterator().next();

        //then
        Assertions.assertEquals(1,userRepository.count());
        Assertions.assertEquals("jichang@naver.com",user.getEmail());
        Assertions.assertEquals("1234",user.getPassword());
        Assertions.assertEquals("이지창",user.getName());

    }
}
