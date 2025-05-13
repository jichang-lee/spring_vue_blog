package com.chang.log.controller.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ManagerScheduled {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	@Scheduled(cron = "0/10 * * * * *")
	public void greeting() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
			.addString("myJob", System.currentTimeMillis() + "")
			.toJobParameters();
		log.info("---------------myJob Scheduler Start ---------------");
		jobLauncher.run(job,jobParameters);
	}
}
