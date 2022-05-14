package com.site.rfs.batchprocesslargefile.config;

import com.site.rfs.batchprocesslargefile.domain.Client;
import com.site.rfs.batchprocesslargefile.mappers.JsonLineMapper;
import com.site.rfs.batchprocesslargefile.processors.ClientProcessor;
import com.site.rfs.batchprocesslargefile.writers.ClientItemWriter;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step(){
        return stepBuilderFactory.get("json-step").<Client,Client>chunk(2)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job runjob(){
        return jobBuilderFactory.get("jobSaveFile")
                .flow(step())
                .end().build();
    }


    @Bean
    public FlatFileItemReader<Client> reader() {
        FlatFileItemReader<Client> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("/process/data-processing/clients.txt"));
        itemReader.setName("clientsData");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(new JsonLineMapper<>(Client.class));
        return itemReader;
    }

    @Bean
    public ClientProcessor processor() {
        return new ClientProcessor();
    }

    @Bean
    public ClientItemWriter writer() {
        return new ClientItemWriter();
    }


}
