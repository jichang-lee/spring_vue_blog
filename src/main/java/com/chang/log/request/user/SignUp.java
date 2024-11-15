package com.chang.log.request.user;

import com.chang.log.domain.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class SignUp {

    private String name;
    private String email;
    private String password;
//    private Image image;


    @Builder
    public SignUp(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
//        this.image = image;
    }


}
