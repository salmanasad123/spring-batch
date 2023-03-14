package com.example.springbatch.config;

import com.example.springbatch.listener.FirstJobListener;
import com.example.springbatch.listener.FirstStepListener;
import com.example.springbatch.processor.FirstItemProcessor;
import com.example.springbatch.reader.FirstItemReader;
import com.example.springbatch.service.SecondTasklet;
import com.example.springbatch.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// Configuration file for jobs and steps

@Configuration
public class SampleJob {

    // class to create jobs
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private SecondTasklet secondTasklet;

    @Autowired
    private FirstJobListener firstJobListener;

    @Autowired
    private FirstStepListener firstStepListener;

    @Autowired
    private FirstItemReader firstItemReader;

    @Autowired
    private FirstItemProcessor firstItemProcessor;

    @Autowired
    private FirstItemWriter firstItemWriter;


    // this method will return job
    // first step is defined using start and for subsequent steps we use .next() method
    @Bean
    public Job firstJob() {
        return jobBuilderFactory.get("first_job")
                .incrementer(new RunIdIncrementer())  // this will auto increment the program args so our job instance is unique everytime
                .listener(firstJobListener)  // setting up the job listener
                .start(firstStep()) // job step
                .next(secondStep()) // job step
                .build();
    }

    public Step firstStep() {
        return stepBuilderFactory
                .get("first_step")
                .listener(firstStepListener)
                .tasklet(firstTask())
                .build();
    }

    //  .get("second_step") --> name of the step
    public Step secondStep() {
        return stepBuilderFactory
                .get("second_step")
                .tasklet(secondTasklet)
                .build();
    }

    public Tasklet firstTask() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is first tasklet step");
                return RepeatStatus.FINISHED;
            }
        };
    }

   /* public Tasklet secondTask() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is second tasklet step");
                return RepeatStatus.FINISHED;
            }
        };
    }*/

    // .<Integer, Long>chunk(3) --> input and output data type we need to provide
    public Step firstChunkStep(){
        return stepBuilderFactory.get("first_chunk_step")
                .<Integer, Long>chunk(3)
                .reader(firstItemReader)
                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

    @Bean
    public Job secondJob() {
        return jobBuilderFactory.get("second_job")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep()) // step for our job
                .build();
    }
}
