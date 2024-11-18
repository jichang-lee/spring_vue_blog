package com.chang.log.response;

import java.time.LocalDateTime;

import com.chang.log.domain.Post;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    // private final LocalDateTime regDate;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        // this.regDate = post.();
    }

}
