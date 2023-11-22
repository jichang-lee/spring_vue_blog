package com.chang.log.controller;

import com.chang.log.request.post.PostCreate;
import com.chang.log.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/write")
    public void postWrite(@RequestBody PostCreate postCreate){
        postService.postWrite(postCreate,1L); //<- userPrincipal
    }
}
