package com.chang.log.domain;


import jakarta.persistence.*;
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
    private String fileName;
    private String newFilename;

    @ManyToOne
    @JoinColumn
    private Post post;


}
