package com.chang.log.request.user;

import com.chang.log.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
public class profileUpload {

    private String url;
    private User user;


    @Builder
    public profileUpload(String url , User user) {
        this.url = url;
        this.user = user;
    }
}
