package com.chang.log.service;

import com.chang.log.domain.Post;
import com.chang.log.domain.User;
import com.chang.log.exception.PostNotFound;
import com.chang.log.exception.UserNotFound;
import com.chang.log.repository.post.PostRepository;
import com.chang.log.repository.UserRepository;
import com.chang.log.request.post.PostCreate;
import com.chang.log.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void postWrite(PostCreate postCreate, Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .writer(user.getName())
                .user(user)
                .build();

        postRepository.save(post);

//        if (file != null) {
//            String fileName = file.getOriginalFilename();
//
//        }

    }

//    public List<PostResponse> postList() {
//       return postRepository.findAll().stream().map(post -> {
//            PostResponse posts = new PostResponse();
//            posts.setTitle(post.getTitle());
//            posts.setContent(post.getContent());
//            posts.setWriter(post.getWriter());
//            return posts;
//        }).collect(Collectors.toList());
//    }
    public List<PostResponse> postList(int page , int size) {
        Page<Post> posts = postRepository.findAll(PageRequest.of(page,size));

        return posts.stream().map(post -> {
            PostResponse postResponse = new PostResponse();
            postResponse.setTitle(post.getTitle());
            postResponse.setContent(post.getContent());
            postResponse.setWriter(post.getWriter());
            return postResponse;
        }).collect(Collectors.toList());
    }





    public void postDelete(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }

}
