package it.aleph.omegamonolith.config.resource.batch.step;

import it.aleph.omegamonolith.config.kafka.KafkaConfiguration;
import it.aleph.omegamonolith.dto.resource.response.ResourceOperationDto;
import it.aleph.omegamonolith.model.resource.response.ResourceOperation;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

@Import(KafkaConfiguration.class)
public class ExecutionStep {


    @Bean
    public ItemReader<ResourceOperationDto> resourceOperationReader(@Qualifier("consumerProp")Properties prop){
        return new KafkaItemReader<>(prop, "report-book-response", 0);
    }

    @Bean
    public ItemProcessor<ResourceOperationDto, ResourceOperation> resourceProcessorExecution(ItemProcessor<ResourceOperationDto, ResourceOperation> processor){
        return processor;
    }

    @Bean
    public ItemWriter<ResourceOperation> executeWriter(ItemWriter<ResourceOperation> writer){
        return writer;
    }


    @Bean
    public Step executionStep(JobRepository jobRepository, PlatformTransactionManager manager,
                              @Qualifier("resourceOperationReader") ItemReader<ResourceOperationDto> reader,
                              @Qualifier("resourceProcessorExecution") ItemProcessor<ResourceOperationDto, ResourceOperation> processor,
                              @Qualifier("executeWriter") ItemWriter<ResourceOperation> writer){
        return new StepBuilder("executionStep", jobRepository).<ResourceOperationDto, ResourceOperation>chunk(10, manager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .allowStartIfComplete(true)
                .build();
    }
}
