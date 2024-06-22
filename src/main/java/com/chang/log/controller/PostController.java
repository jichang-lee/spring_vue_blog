package com.chang.log.controller;

import com.chang.log.config.UserPrincipal;
import com.chang.log.request.post.PostCreate;
import com.chang.log.response.PostResponse;
import com.chang.log.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/write")
    public void postWrite(@RequestBody PostCreate postCreate){
        postService.postWrite(postCreate,1L);
    }

    @GetMapping("/list")
    public List<PostResponse> getList(@RequestParam Integer page
                                    , @RequestParam Integer size){
       return postService.postList(page,size);
    }

}
