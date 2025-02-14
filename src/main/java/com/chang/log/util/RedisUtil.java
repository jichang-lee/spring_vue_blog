package com.chang.log.util;

import java.util.concurrent.TimeUnit;

import org.hibernate.annotations.Comment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisUtil {

	private final RedisTemplate<String,String> redisTemplate;

	public void save(String key, String value,long expTime) {
		redisTemplate.opsForValue().set(key,value,expTime, TimeUnit.SECONDS);
	}

	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public void delete(String key) {
		redisTemplate.delete(key);
	}
}
