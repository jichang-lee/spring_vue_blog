package com.chang.log.exception;

public class UserNotFound extends MasterException{

    private static final String MESSAGE = "해당 회원을 찾을 수 없습니다.";

    public UserNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
