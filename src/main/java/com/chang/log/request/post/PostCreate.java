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
    private LocalDate createAt;
    private User user;
    private String filePath;
    private String fileName;
    private String newFileName;
    private String fileUrl;

//    @Builder
//    public PostCreate(String title, String content, String writer) {
//        this.title = title;
//        this.content = content;
//        this.writer = writer;
//    }

    @Builder
    public PostCreate(String title, String content, String writer,User user, String filePath, String fileUrl) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createAt = LocalDate.now();
        this.user = user;
        this.filePath = filePath;
        this.fileUrl = fileUrl;
    }
}
