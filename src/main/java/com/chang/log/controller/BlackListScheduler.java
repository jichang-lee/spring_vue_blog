package com.chang.log.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.chang.log.config.RedisConfig;
import com.chang.log.service.UserService;
import com.chang.log.util.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlackListScheduler {

	private final UserService userService;
	private final RedisUtil redisUtil;

	private final static String REDIS_KEY = "blackList";
	private final static String REDIS_VALUE = "true";


	// @Scheduled(cron = "5 * * * * *",zone = "Asia/Seoul")
	public void userBlackListAdd() {
		String customKey = REDIS_KEY + "2024-03-04";

		redisUtil.save(customKey,REDIS_VALUE,100);
		userService.addUserBlackList();

	}

	// @Scheduled(cron = "10 * * * * *",zone = "Asia/Seoul")
	public void userBlackListAdd_2() {
		String customKey = REDIS_KEY + "2024-03-04";

		if(redisUtil.exist(customKey)) {
			log.error("userBlackListAdd_2 Scheduler Error !");
			return;
		}
		redisUtil.save(customKey,REDIS_VALUE,100);
		userService.addUserBlackList();

	}
}
