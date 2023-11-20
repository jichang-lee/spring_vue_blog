package com.chang.log.service;

import com.chang.log.domain.User;
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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        User user = userRepository.findAll().iterator().next();

        //then
        Assertions.assertEquals(1,userRepository.count());
        Assertions.assertEquals("jichang@naver.com",user.getEmail());
//        Assertions.assertEquals("1234",user.getPassword());
        Assertions.assertTrue(passwordEncoder.matches("1234", user.getPassword()));
        Assertions.assertEquals("이지창",user.getName());

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
        userService.userEdit(user.getId(), userEditor);

        //then

        User findUser = userRepository.findById(user.getId())
                .orElseThrow(UserNotFound :: new);

        Assertions.assertEquals("삼지창",findUser.getName());
        Assertions.assertEquals("jichang@naver.com",findUser.getEmail());
        Assertions.assertTrue(passwordEncoder.matches("1234", findUser.getPassword()));
    }

    @Test
    @DisplayName("회원 전체 조회")
    void userList(){

        //given
        List<User> users = IntStream.range(0,10)
                .mapToObj(i -> User.builder()
                        .name("이지창" + i)
                        .email("jichang@email" + i)
                        .password("123" +i)
                        .build())
                .collect(Collectors.toList());
        userRepository.saveAll(users);

        //when
        userService.userList();

        //then
        Assertions.assertEquals(10,userRepository.count());
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
        userService.userDelete(user.getId());

        //then
        Assertions.assertEquals(0,userRepository.count());
    }
}
