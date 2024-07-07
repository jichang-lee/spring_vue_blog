package com.chang.log.exception;

import java.util.Map;

public class AuthInterceptorException extends MasterException{

    private static final String MESSAGE = "인증에 실패하였습니다.";

    @Override
    public int getStatusCode() {
        return 401;
    }

    public AuthInterceptorException() {
        super(MESSAGE);
    }
}
