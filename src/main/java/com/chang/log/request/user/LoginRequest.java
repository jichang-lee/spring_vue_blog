package com.chang.log.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;

}
