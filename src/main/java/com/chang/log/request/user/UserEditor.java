package com.chang.log.request.user;

import lombok.Builder;
import lombok.Data;

@Data
public class UserEditor {

    private String name;
    private String email;
    private String password;

    @Builder
    public UserEditor(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


}
