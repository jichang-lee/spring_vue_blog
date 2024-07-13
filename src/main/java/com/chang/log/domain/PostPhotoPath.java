package com.chang.log.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class PostPhotoPath {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;
    private String url;
    private Integer size;
    private String fileName;
    private String newFilename;

    @ManyToOne
    @JoinColumn
    private Post post;

    @Builder
    public PostPhotoPath(String path, String url, Integer size, String fileName, String newFilename, Post post) {
        this.path = path;
        this.url = url;
        this.size = size;
        this.fileName = fileName;
        this.newFilename = newFilename;
        this.post = post;
    }
}
