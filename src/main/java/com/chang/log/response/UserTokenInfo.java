package com.chang.log.response;

import com.chang.log.enums.RoleType;

import lombok.Data;

@Data
public class UserTokenInfo {

    private Long userId;
    private String email;
    private String password;
    private RoleType role;
}
