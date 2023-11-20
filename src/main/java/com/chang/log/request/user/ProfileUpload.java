package com.chang.log.request.user;

import com.chang.log.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
public class ProfileUpload {

    private String url;
    private User user;


    @Builder
    public ProfileUpload(String url , User user) {
        this.url = url;
        this.user = user;
    }
}
