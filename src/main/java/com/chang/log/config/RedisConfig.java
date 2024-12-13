package com.chang.log.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	//실제로는 password 까지

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
		// config.setPassword();

		return new LettuceConnectionFactory(config);
	}

	@Bean
	public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);

		// 일반적인 키 벨류의 경우 시리얼라이저
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

		// 일반적인 키 벨류의 경우 시리얼라이저
		// redisTemplate.setKeySerializer(new StringRedisSerializer());
		// redisTemplate.setValueSerializer(new StringRedisSerializer());

		// hash 사용할 경우
		// redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		// redisTemplate.setHashValueSerializer(new StringRedisSerializer());
		//
		// redisTemplate.setDefaultSerializer(new StringRedisSerializer());

		return redisTemplate;
	}

}
