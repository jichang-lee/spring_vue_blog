package com.chang.log.service;

import com.chang.log.domain.Post;
import com.chang.log.domain.PostPhotoPath;
import com.chang.log.domain.User;
import com.chang.log.exception.PostNotFound;
import com.chang.log.exception.UserNotFound;
import com.chang.log.repository.post.PostPhotoPathRepository;
import com.chang.log.repository.post.PostRepository;
import com.chang.log.repository.UserRepository;
import com.chang.log.repository.post.PostRepositoryCustom;
import com.chang.log.request.post.PostCreate;
import com.chang.log.request.post.PostSearch;
import com.chang.log.response.FileResponse;
import com.chang.log.response.PagingResponse;
import com.chang.log.response.PostResponse;
import com.chang.log.util.FileUtil;
import com.chang.log.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostRepositoryCustom postRepositoryCustom;
    private final PostPhotoPathRepository postPhotoPathRepository;
    private final FileUtil FileUtil;
    private final JwtUtil jwtUtil;


    public void postWrite(PostCreate postCreate, Long userId,List<MultipartFile> files) throws IOException {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .writer(user.getName())
                .user(user)
                .build();

        postRepository.save(post);
        //게시판을 저장할 떄 파일이 있다면
        if (!files.isEmpty()) {
            for (MultipartFile file : files) {
                FileResponse fileResponse = FileUtil.getFile(file);
                PostPhotoPath postPhoto = PostPhotoPath.builder()
                        .path(fileResponse.getFilePath())
                        .fileName(fileResponse.getOriginalFilename())
                        .newFilename(fileResponse.getStoredFilename())
                        .size(fileResponse.getSize())
                        .build();
                postPhotoPathRepository.save(postPhoto);
            }
        }
    }


    public PagingResponse<PostResponse> getPosts(PostSearch postSearch) {
        // log.info(jwtUtil.getUserId());
        Page<Post> postPage = postRepositoryCustom.getList(postSearch);
        return new PagingResponse<>(postPage, PostResponse.class);
    }


    public void postDelete(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }

}
