package com.chang.log.service;


import com.chang.log.domain.User;
import com.chang.log.repository.post.PostRepository;
import com.chang.log.repository.UserRepository;
import com.chang.log.request.post.PostCreate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;



    @AfterEach
    void clean(){
        userRepository.deleteAll();
        postRepository.deleteAll();
    }


    @Test
    @DisplayName("게시글 작성")
    void postCreate(){
        //given
        User user = User.builder()
                .name("이지창")
                .email("jichang@naver.com")
                .password("1234")
                .build();
        userRepository.save(user);

        PostCreate post = PostCreate.builder()
                .title("게시글 제목")
                .content("게시글 내용")
                .writer(user.getName())
                .user(user)
                .build();

        //when
        postService.postWrite(post,user.getId());

        //then
        assertEquals(1,postRepository.count());
        assertEquals("게시글 제목",post.getTitle());
        assertEquals("게시글 내용",post.getContent());
        assertEquals("이지창",post.getWriter());

    }
}