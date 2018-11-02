package com.xzy.springbootapi.admin.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 该方法仅仅用来测试每分钟执行
 */
public class MinuteJob implements Job, Serializable {
    Logger logger =LoggerFactory.getLogger(MinuteJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("JobName: {}", context.getJobDetail().getKey().getName());
	}
}