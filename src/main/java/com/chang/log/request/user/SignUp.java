package com.chang.log.request.user;

import lombok.Builder;
import lombok.Data;

@Data
public class SignUp {

    private String name;
    private String email;
    private String password;

    @Builder
    public SignUp(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
