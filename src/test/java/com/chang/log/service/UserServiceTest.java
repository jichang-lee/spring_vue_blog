package com.chang.log.service;

import com.chang.log.domain.User;
import com.chang.log.exception.AlreadyExistsEmail;
import com.chang.log.exception.UserNotFound;
import com.chang.log.repository.UserRepository;
import com.chang.log.request.user.SignUp;
import com.chang.log.request.user.UserEditor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


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

        User user = userRepository.findByEmail("jichang@naver.com")
                .orElseThrow(AlreadyExistsEmail::new);

        //then
        assertEquals(1,userRepository.count());
        assertEquals("jichang@naver.com",user.getEmail());
//        Assertions.assertEquals("1234",user.getPassword());
        assertTrue(passwordEncoder.matches("1234", user.getPassword()));
        assertEquals("이지창",user.getName());

    }

    @Test
    @DisplayName("회원수정")
    void userEdit(){
        //given
        User user = User.builder()
                .name("이지창")
                .email("jichang@naver.com")
                .password("1234")
                .build();
        userRepository.save(user);

        UserEditor userEditor = UserEditor.builder()
                .name("삼지창")
                .email(user.getEmail())
                .password("1234")
                .build();
        //when
        userService.userEdit(user.getUserId(), userEditor);

        //then

        User findUser = userRepository.findById(user.getUserId())
                .orElseThrow(UserNotFound :: new);

        assertEquals("삼지창",findUser.getName());
        assertEquals("jichang@naver.com",findUser.getEmail());
        assertTrue(passwordEncoder.matches("1234", findUser.getPassword()));
    }

    @Test
    @DisplayName("회원 단건 조회 (마이페이지)")
    void findUser(){

        //given
        User user = User.builder()
                .name("이지창")
                .email("jichang@naver.com")
                .password("1234")
                .build();

        userRepository.save(user);

        //when
        userService.findUser(user.getUserId());

        //then
        assertEquals(1,userRepository.count());
        assertEquals("이지창",user.getName());
        assertEquals("jichang@naver.com",user.getEmail());

    }


    @Test
    @DisplayName("회원 탈퇴")
    void userDelete(){

        //given
        User user = User.builder()
                .name("이지창")
                .email("jichang@naver.com")
                .password("1234")
                .build();

        userRepository.save(user);

        //when
        userService.userDelete(user.getUserId());

        //then
        assertEquals(0,userRepository.count());
    }
}
