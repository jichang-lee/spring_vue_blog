package com.chang.log.service;

import com.chang.log.domain.Post;
import com.chang.log.domain.User;
import com.chang.log.exception.PostNotFound;
import com.chang.log.exception.UserNotFound;
import com.chang.log.repository.post.PostRepository;
import com.chang.log.repository.UserRepository;
import com.chang.log.request.post.PostCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void postWrite(PostCreate postCreate,Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .writer(user.getName())
                .user(user)
                .build();

        postRepository.save(post);

    }





    public void postDelete(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }

}
