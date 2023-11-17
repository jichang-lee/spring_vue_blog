package com.chang.log.request.user;

import lombok.Builder;
import lombok.Data;

@Data
public class UserEdit {

    private String email;
    private String password;
    private String name;


    @Builder
    public UserEdit(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
