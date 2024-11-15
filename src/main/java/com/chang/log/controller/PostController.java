package com.chang.log.controller;

import com.chang.log.domain.Post;
import com.chang.log.request.post.PostCreate;
import com.chang.log.response.PostResponse;
import com.chang.log.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/write")
    public void postWrite(@RequestParam String postData,
                          @RequestParam(required = false) List<MultipartFile> files) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        PostCreate postCreate = objectMapper.readValue(postData, PostCreate.class);
        postService.postWrite(postCreate,1L,files);
    }

    @GetMapping("/list")
    public PostResponse getList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String writer) {
        Page<Post> posts = postService.getPosts(page, size, title, content, writer);
        PostResponse postResponse = new PostResponse();
        postResponse.setPosts(posts);
        return postResponse;
    }

}
