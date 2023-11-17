package com.chang.log.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(
        indexes = {
                @Index(name = "IDX_COMMENT_POST_Id" , columnList = "post_id")
        })
public class Comment {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String author;
        private String password;
        private String content;

        @ManyToOne
        @JoinColumn
        private Post post;

}
