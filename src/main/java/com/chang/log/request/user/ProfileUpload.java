package com.chang.log.request.user;

import com.chang.log.domain.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileUpload {

    private String url;
    private User user;
    private MultipartFile imageFile;


    @Builder
    public ProfileUpload(String url , User user ,MultipartFile imageFile) {
        this.url = url;
        this.user = user;
        this.imageFile = imageFile;
    }
}
