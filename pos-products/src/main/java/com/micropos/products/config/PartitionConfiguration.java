package com.micropos.products.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.micropos.common.model.Product;
import com.micropos.products.batch.JsonFileReader;
import com.micropos.products.batch.ProductProcessor;
import com.micropos.products.batch.ProductWriter;
import com.micropos.products.repository.ProductRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class PartitionConfiguration {

    @Autowired
    public ProductRepository productRepository;

    @Bean
    public Job partitioningJob(JobBuilderFactory jobBuilderFactory, Step masterStep) throws Exception {
        return jobBuilderFactory
                .get("partitioningJob")
                .incrementer(new RunIdIncrementer())
                .flow(masterStep)
                .end()
                .build();
    }

    @Bean
    public Step masterStep(StepBuilderFactory stepBuilderFactory, Step slaveStep, Partitioner partitioner) throws Exception {
        return stepBuilderFactory
                .get("masterStep")
                .partitioner(slaveStep)
                .partitioner("partition", partitioner)
                .gridSize(10)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public Partitioner partitioner() throws Exception {
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        partitioner.setResources(resolver.getResources("data/*.json"));
        return partitioner;
    }

    @Bean
    public Step slaveStep(StepBuilderFactory stepBuilderFactory,
                          ItemReader<JsonNode> itemReader,
                          ItemProcessor<JsonNode, Product> itemProcessor,
                          ItemWriter<Product> itemWriter) throws Exception {
        return stepBuilderFactory
                .get("slaveStep")
                .<JsonNode, Product>chunk(10)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<JsonNode> itemReader(@Value("#{stepExecutionContext['fileName']}") String file) {
        return new JsonFileReader(file);
    }

    @Bean
    public ItemProcessor<JsonNode, Product> itemProcessor() {
        return new ProductProcessor();
    }

    @Bean
    public ItemWriter<Product> itemWriter() {
        return new ProductWriter(productRepository);
    }

}
