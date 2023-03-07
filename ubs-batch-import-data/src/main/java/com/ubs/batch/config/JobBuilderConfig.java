package com.ubs.batch.config;

import com.ubs.batch.domain.job.ItemProcessed;
import com.ubs.batch.domain.job.ItemRead;
import com.ubs.batch.job.processor.StockItemProcessor;
import com.ubs.batch.job.reader.StockItemReader;
import com.ubs.batch.job.writer.StockItemWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobBuilderConfig {

    private static final String JOB_NAME = "importDataJob";
    private static final String STEP_NAME = "importDataStep";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job importDataJob(Step importDataStep) {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(importDataStep)
                .build();
    }

    @Bean
    public Step importDataStep(StockItemReader itemReader,
                               StockItemProcessor itemProcessor,
                               StockItemWriter itemWriter) {
        return stepBuilderFactory.get(STEP_NAME)
                .<ItemRead, ItemProcessed>chunk(1)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }
}
