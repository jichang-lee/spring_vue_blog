package com.chang.log.response;

import lombok.Data;

@Data
public class PostResponse {

    private String title;
    private String content;
    private String writer;
    private int view;

}
