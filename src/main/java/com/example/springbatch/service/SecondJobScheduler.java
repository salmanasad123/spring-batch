package com.example.springbatch.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

// we will schedule our job to run every 1 minute using this class
@Service
public class SecondJobScheduler {

    @Autowired
    JobLauncher jobLauncher;

    @Qualifier("secondJob")
    @Autowired
    Job secondJob;

    // cron expression to run the job every one minute
    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void secondJobScheduler() {

        // setting job parameters that are reflected in the db in params table
        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime", new JobParameter(System.currentTimeMillis()));

        JobParameters jobParameters = new JobParameters(params);

        try {
            JobExecution jobExecution = jobLauncher.run(secondJob, jobParameters);
            System.out.println("Job Execution ID: " + jobExecution.getId());
        } catch (Exception e) {
            System.out.println("Exception while running job");
        }

    }

}
