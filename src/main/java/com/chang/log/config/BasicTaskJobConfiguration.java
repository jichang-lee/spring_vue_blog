package com.chang.log.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.chang.log.controller.batch.ManagerTask;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class BasicTaskJobConfiguration {

	@Autowired
	PlatformTransactionManager transactionManager;

	@Bean
	public Tasklet managerTasklet() {
		return new ManagerTask();
	}

	@Bean
	public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		log.info("---------------init step ---------------");
		return new StepBuilder("myStep",jobRepository)
			.tasklet(managerTasklet(),transactionManager)
			.build();
	}

	@Bean
	public Job myJob(Step step, JobRepository jobRepository) {
		log.info("---------------init job ---------------");
		return new JobBuilder("myJob",jobRepository)
			.incrementer(new RunIdIncrementer())
			.start(step)
			.build();
	}
}
