package com.chang.log.request.user;

import lombok.Data;

@Data
public class ReFreshRequest {

	private String email;
	private String refreshToken;
}
