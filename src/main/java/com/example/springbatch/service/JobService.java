package com.example.springbatch.service;

import com.example.springbatch.request.JobParamsRequest;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobService {

    @Qualifier("firstJob")
    @Autowired
    Job firstJob;

    @Qualifier("secondJob")
    @Autowired
    Job secondJob;

    @Autowired
    JobLauncher jobLauncher;

    @Async
    public void startJob(String jobName, List<JobParamsRequest> jobParamsRequestList) {

        // setting job parameters that are reflected in the db in params table
        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime", new JobParameter(System.currentTimeMillis()));

        jobParamsRequestList.stream()
                .forEach((JobParamsRequest jobParamsRequest)-> {
                    params.put(jobParamsRequest.getParamKey(),
                            new JobParameter(jobParamsRequest.getParamValue()));
                });

        JobParameters jobParameters = new JobParameters(params);

        JobExecution jobExecution = null;

        try {
            if (jobName.equals(firstJob.getName())) {

                jobExecution = jobLauncher.run(firstJob, jobParameters);

            } else if (jobName.equals(secondJob.getName())) {

                jobExecution = jobLauncher.run(secondJob, jobParameters);
            }
            System.out.println("Job Execution ID: " + jobExecution.getId());
        } catch (Exception e) {
            System.out.println("Exception while running job");
        }
    }
}

