package com.example.springbatch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {

        System.out.println("Before Step " + stepExecution.getStepName());
        // job execution context
        System.out.println("Before Step " + stepExecution.getJobExecution().getExecutionContext());
        System.out.println("Before Step " + stepExecution.getExecutionContext()); // step execution context
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        System.out.println("After Step " + stepExecution.getStepName());
        System.out.println("After Step " + stepExecution.getJobExecution().getExecutionContext());
        System.out.println("After Step " + stepExecution.getExecutionContext()); // step execution context
        return null;
    }
}
