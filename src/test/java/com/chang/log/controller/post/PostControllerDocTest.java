package com.chang.log.controller.post;

import com.chang.log.config.MasterMockUser;
import com.chang.log.domain.User;
import com.chang.log.repository.PostRepository;
import com.chang.log.repository.UserRepository;
import com.chang.log.request.post.PostCreate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https",uriHost = "v1.jichang.com",uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @MasterMockUser
    @DisplayName("게시글 작성")
    void postWrite() throws Exception {
        User user = User.builder()
                .name("이지창")
                .email("jichang@naver.com")
                .password("1234")
                .build();
        userRepository.save(user);

        PostCreate postCreate = PostCreate.builder()
                .title("글 제목")
                .content("글 내용")
                .writer(user.getName())
                .build();
        String json = objectMapper.writeValueAsString(postCreate);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/post/write")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/post/post-create",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").description("게시글 제목"),
                                PayloadDocumentation.fieldWithPath("content").description("게시글 내용"),
                                PayloadDocumentation.fieldWithPath("writer").description("작성자"),
                                PayloadDocumentation.fieldWithPath("user").description("회원정보"),
                                PayloadDocumentation.fieldWithPath("createAt").description("게시글 작성일")
                        )));

    }


}
