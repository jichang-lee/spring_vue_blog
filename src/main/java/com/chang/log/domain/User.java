package com.chang.log.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private LocalDate createAt;

    @OneToOne
    @JoinColumn
    private Image image;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public User( String name, String email, String password, Image image) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createAt = LocalDate.now();
        this.image = image;
    }


    public void edit(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createAt = LocalDate.now();
    }
}
