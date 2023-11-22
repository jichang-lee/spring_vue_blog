package com.chang.log.request.post;

import com.chang.log.domain.Comment;
import com.chang.log.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostCreate {

    private String title;
    private String content;
    private String writer;
    private int views;
    private LocalDate createAt;
    private User user;


    @Builder
    public PostCreate(String title, String content, String writer,User user) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.views = 0;
        this.createAt = LocalDate.now();
        this.user = user;
    }
}
