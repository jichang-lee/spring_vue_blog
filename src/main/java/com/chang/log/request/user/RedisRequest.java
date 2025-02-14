package com.chang.log.request.user;

import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;

@RedisHash(value = "memberToken")
@AllArgsConstructor
@Data
public class RedisRequest {

	private String email;
	private String refreshToken;
}
