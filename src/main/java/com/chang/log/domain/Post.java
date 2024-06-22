package com.chang.log.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String writer;
    private int views;

    private LocalDate createAt;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<PostPhotoPath> postPhotoPaths = new ArrayList<>();

    @Builder
    public Post(String title, String content, String writer,User user) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.views = 0;
        this.createAt = LocalDate.now();
        this.user = user;
    }
}
