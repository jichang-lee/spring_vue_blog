package com.chang.log.response;

import com.chang.log.enums.RoleType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserTokenInfo {

    private Long userId;
    private String email;
    private String password;
    private RoleType role;

    public UserTokenInfo(Long userId, String email, RoleType role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
    }
}
