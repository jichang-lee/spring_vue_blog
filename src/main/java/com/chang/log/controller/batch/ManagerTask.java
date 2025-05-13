package com.chang.log.controller.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ManagerTask implements Tasklet, InitializingBean {
	@Override
	public RepeatStatus execute(StepContribution contribution,
		ChunkContext chunkContext) throws Exception {
		//비즈니스 구현
		log.info("---------------Execute ---------------");
		log.info("ManagerTask: {}, {}",contribution, chunkContext);
		return RepeatStatus.FINISHED;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("---------------afterPropertiesSet ---------------");
	}
}
