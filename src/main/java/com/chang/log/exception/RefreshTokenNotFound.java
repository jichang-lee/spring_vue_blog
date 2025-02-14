package com.chang.log.exception;

import java.util.Map;

public class RefreshTokenNotFound extends MasterException {

	private static final String MESSAGE = "refreshToken 을 찾을 수 없습니다.";
	@Override
	public int getStatusCode() {
		return 401;
	}

	public RefreshTokenNotFound() {
		super(MESSAGE);
	}
}
