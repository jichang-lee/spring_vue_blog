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
    @DisplayName("회원 조회")
    void userList() throws Exception{
        //given
        List<User> userList = IntStream.range(0,10)
                .mapToObj(i -> User.builder()
                        .name("지창" + i)
                        .email("email@jicahng" + i)
                        .password("1234" + i)
                        .build()
                ).collect(Collectors.toList());

        userRepository.saveAll(userList);


        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list")
                .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
//                .andExpect(jsonPath("$[0].id",Matchers.notNullValue()))
//                .andExpect(jsonPath("$[0].name").value("지창0"))
//                .andExpect(jsonPath("$[0]"))
//                .andExpect(jsonPath("$[0]"))
                .andDo(MockMvcResultHandlers.print());

        //회원 삭제되는 문제 ********
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