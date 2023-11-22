package com.chang.log.controller.user;

import com.chang.log.domain.User;
import com.chang.log.repository.UserRepository;
import com.chang.log.request.user.SignUp;
import com.chang.log.request.user.UserEditor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void clean(){
        userRepository.deleteAll();
    }
    
    @Test
    @DisplayName("회원가입")
    void signUpSuccess() throws Exception {
        SignUp signUp = SignUp.builder()
                .email("jichang@naver.com")
                .password("1234")
                .name("이지창")
                .build();

        String json = objectMapper.writeValueAsString(signUp);

        mockMvc.perform(post("/user/signup")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("회원수정")
    void userEditSuccess() throws Exception{

        //given
        User user = User.builder()
                .name("이지창")
                .email("jichang@naver.com")
                .password("1234")
                .build();

        userRepository.save(user);

        UserEditor userEditor = UserEditor.builder()
                .name("삼지창")
                .email("jichang@naver.com")
                .password("1234")
                .build();
        String json = objectMapper.writeValueAsString(userEditor);

        //expected
        mockMvc.perform(patch("/user/edit/{userId}",user.getId())
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("회원 단건 조회 (마이페이지)")
    void findUser() throws Exception{
        //given
        User user = User.builder()
                .name("이지창")
                .email("jichang@naver.com")
                .password("1234")
                .build();

        userRepository.save(user);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/user/myPage/{userId}",user.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("이지창"))
                .andExpect(jsonPath("$.email").value("jichang@naver.com"))
                .andExpect(jsonPath("$.password").value(user.getPassword()))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 탈퇴")
    void userDelete() throws Exception {
        //given
        User user = User.builder()
                .name("이지창")
                .email("jicahng@naver.com")
                .password("1234")
                .build();
        userRepository.save(user);

        //expected
        mockMvc.perform(delete("/user/delete/{userId}",user.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}