package com.chang.log.exception;

public class PostNotFound extends MasterException{

    private static final String MESSAGE = "해당 게시글을 찾을 수 없습니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
