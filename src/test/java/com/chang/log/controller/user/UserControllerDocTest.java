package com.chang.log.controller.user;

import com.chang.log.repository.UserRepository;
import com.chang.log.request.user.SignUp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https",uriHost = "v1.jichang.com",uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
class UserControllerDocTest {

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
                .andDo(MockMvcRestDocumentation.document("user/user-signup",
                        PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("name").description("성명"),
                        PayloadDocumentation.fieldWithPath("email").description("이메일"),
                        PayloadDocumentation.fieldWithPath("password").description("비밀번호").optional()
                        )
                ));

    }


}