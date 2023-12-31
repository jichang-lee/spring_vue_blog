package com.chang.log.exception;

public class AlreadyExistsEmail extends MasterException{

    private static final String MESSAGE = "이미 가입된 이메일입니다.";
    public AlreadyExistsEmail() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
