package com.chang.log.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    private String email;

    private String password;

    private LocalDate createAt;

    @ColumnDefault("'Y'")
    private String blackUserYn;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<UserBlackList> userBlackLists;



//    @OneToOne
//    @JoinColumn
//    private Image image;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public User( String name, String email, String password, String blackUserYn) {
        this.name = name;
        this.email = email;
        this.password = password;
		this.blackUserYn = blackUserYn;
		this.createAt = LocalDate.now();
//        this.image = image;
    }


    public void edit(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createAt = LocalDate.now();
    }
}
