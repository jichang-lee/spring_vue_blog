package com.chang.log.controller;

import com.chang.log.config.CustomUserDetails;
import com.chang.log.domain.Post;
import com.chang.log.request.post.PostCreate;
import com.chang.log.request.post.PostSearch;
import com.chang.log.response.PagingResponse;
import com.chang.log.response.PostResponse;
import com.chang.log.response.UserTokenInfo;
import com.chang.log.service.PostService;
import com.chang.log.service.UserService;
import com.chang.log.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/post")
@Slf4j
public class PostController {

    private final PostService postService;
    private final JwtUtil jwtUtil;

    @PostMapping("/write")
    public void postWrite(@RequestParam String postData,
                          @RequestParam(required = false) List<MultipartFile> files) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // @todo 개선
        if(files == null || files.isEmpty()) {
            files = new ArrayList<>();
        }
        PostCreate postCreate = objectMapper.readValue(postData, PostCreate.class);
        postService.postWrite(postCreate,1L,files);
    }

    @GetMapping("/list")
    public PagingResponse<PostResponse> getList(
            @ModelAttribute PostSearch postSearch
        ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();

        log.info("User ID: {}", userDetails.getUsername());
        // principal 값을 로그로 출력
        return postService.getPosts(postSearch);
    }

}
