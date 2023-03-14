package com.example.springbatch.controller;

import com.example.springbatch.request.JobParamsRequest;
import com.example.springbatch.service.JobService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/job")
public class JobController {

    @Autowired
    JobLauncher jobLauncher;

    @Qualifier("firstJob")
    @Autowired
    Job firstJob;

    @Qualifier("secondJob")
    @Autowired
    Job secondJob;

    @Autowired
    JobService jobService;

    @Autowired
    JobOperator jobOperator;  // to stop job we need job operator


    @GetMapping("/start/{jobName}")
    public String startJob(@PathVariable("jobName") String jobName,
                           @RequestBody List<JobParamsRequest> jobParamsRequestList) {

        // launch the job through rest api.
        jobService.startJob(jobName, jobParamsRequestList);

        return "Job started..";
    }

    // stop the job using job execution id, we can get it from database.
    @GetMapping("/stop/{executionId}")
    public String stopJob(@PathVariable("executionId") long executionId) throws
            NoSuchJobExecutionException, JobExecutionNotRunningException {

        jobOperator.stop(executionId);

        return "Job stopped";
    }
}
