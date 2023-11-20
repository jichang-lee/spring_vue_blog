package com.chang.log.request.user;

import com.chang.log.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserResponse {

    private String name;
    private String email;
    private String password;

    @Builder
    public UserResponse(User user) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

}
