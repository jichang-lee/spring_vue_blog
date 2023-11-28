package com.chang.log.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @OneToOne
    @JoinColumn
    private User user;

    @Builder
    public Image(Long id, String url, User user) {
        this.id = id;
        this.url = url;
        this.user = user;
    }
}
