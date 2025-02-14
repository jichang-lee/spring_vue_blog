package com.chang.log.exception;

public class HeaderTokenNotFound extends MasterException {

	private static final String MESSAGE = "헤더에 토큰 정보를 찾을 수 없습니다.";
	@Override
	public int getStatusCode() {
		return 401;
	}

	public HeaderTokenNotFound() {
		super(MESSAGE);
	}
}
