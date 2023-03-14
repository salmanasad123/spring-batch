package com.example.springbatch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

// this is interceptor that runs before and after job execution
@Component
public class FirstJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {

        System.out.println("Before job execution " + jobExecution.getJobInstance().getJobName());

        // job execution context is at job level
        jobExecution.getExecutionContext().put("myKey", "myValue");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        System.out.println("After job execution " + jobExecution.getJobInstance().getJobName());
    }
}
