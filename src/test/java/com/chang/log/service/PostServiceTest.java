package com.chang.log.service;


import com.chang.log.domain.Post;
import com.chang.log.domain.User;
import com.chang.log.repository.post.PostRepository;
import com.chang.log.repository.UserRepository;
import com.chang.log.request.post.PostCreate;
import com.chang.log.response.PostResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    void bulkInsert() {
        User user = userRepository.findById(1L).orElse(null);

        for(int i = 0 ; i < 10; i++) {
            Post post = Post.builder()
                .title("post title" + i)
                .content("post content" + i)
                .user(user)
                .build();
            postRepository.save(post);
        }
    }


//    @Test
//    @DisplayName("게시글 작성")
//    void postCreate() throws IOException {
//        //given
//        User user = User.builder()
//                .name("이지창")
//                .email("jichang@naver.com")
//                .password("1234")
//                .build();
//        userRepository.save(user);
//
//        PostCreate post = PostCreate.builder()
//                .title("게시글 제목")
//                .content("게시글 내용")
//                .writer(user.getName())
//                .user(user)
//                .build();
//
//        //when
//        postService.postWrite(post,user.getId());
//
//        //then
//        assertEquals(1,postRepository.count());
//        assertEquals("게시글 제목",post.getTitle());
//        assertEquals("게시글 내용",post.getContent());
//        assertEquals("이지창",post.getWriter());
//
//    }

//    @Test
//    @DisplayName("게시글 리스트")
//    void postList(){
////    List<Post> postList = new ArrayList<>();
////        for(int i = 1; i <= 30; i++) {
////            Post posts = Post.builder()
////                    .title("내용" + i)
////                    .content("내용" + i)
////                    .writer("작성자" + i)
////                    .build();
////            postList.add(posts);
////        }
////        postRepository.saveAll(postList);
//        // 람다식
//       List<Post> posts = IntStream.rangeClosed(1, 30)
//                .mapToObj(i ->{
//                    return Post.builder()
//                            .title("제목" + i)
//                            .content("내용" + i)
//                            .writer("작성자" + i)
//                            .build();
//                }).collect(Collectors.toList());
//       postRepository.saveAll(posts);
//
//        List<PostResponse> postResponses = postService.postList(1 , 10);
//
//        assertEquals(10L, postResponses.size());
//
//    }
}