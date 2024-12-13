package com.chang.log.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

	private final RedisTemplate<String,Object> redisTemplate;

	public void saveToken(String key,String value, long duration) {
		redisTemplate.opsForValue().set(key,value,duration, TimeUnit.MILLISECONDS);
	}

	public String getToken(String key) {
		return (String) redisTemplate.opsForValue().get(key);
	}

	public void deleteToken(String key) {
		redisTemplate.delete(key);
	}
}
