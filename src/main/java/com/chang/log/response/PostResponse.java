package com.chang.log.response;

import com.chang.log.domain.Post;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PostResponse {

    private String title;
    private String content;
    private String writer;
    private int view;

    private Page<Post> posts;

}
