package it.aleph.omegamonolith.config.resource.batch.step;

import it.aleph.omegamonolith.config.kafka.KafkaConfiguration;
import it.aleph.omegamonolith.dao.resource.RequestedResourceRepository;
import it.aleph.omegamonolith.dto.resource.request.RequestedResourceOperationDto;
import it.aleph.omegamonolith.dto.resource.response.ResourceOperationDto;
import it.aleph.omegamonolith.model.resource.request.RequestedResourceOperation;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

@Import({KafkaConfiguration.class})
public class PersistStepRequestBatchConfiguration {

    private final static String TOPIC_NAME = "report-book-events";

    @Bean
    public ItemReader<RequestedResourceOperationDto> resourceReader(@Qualifier("consumerProp") Properties prop){
        KafkaItemReader<String, RequestedResourceOperationDto> kafka = new KafkaItemReader<>(prop, "report-book-events",0);
        return kafka;
    }

    @Bean
    public ItemWriter<RequestedResourceOperation> resourceWriter(RequestedResourceRepository repository){
        RepositoryItemWriter<RequestedResourceOperation> repositoryWriter = new RepositoryItemWriter<>();
        repositoryWriter.setRepository(repository);
        repositoryWriter.setMethodName("save");
        return repositoryWriter;
    }

    @Bean
    public ItemProcessor<RequestedResourceOperationDto, RequestedResourceOperation> resourceProcessor(
            ItemProcessor<RequestedResourceOperationDto, RequestedResourceOperation> processor){
        return processor;

    }

    @Bean
    public Step persistStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                            @Qualifier("resourceReader") ItemReader<RequestedResourceOperationDto> resourceReader,
                            @Qualifier("resourceProcessor") ItemProcessor<RequestedResourceOperationDto, RequestedResourceOperation> resourceProcessor,
                            @Qualifier("resourceWriter") ItemWriter<RequestedResourceOperation> resourceWriter){
        return new StepBuilder("persistRequestStep", jobRepository)
                .<RequestedResourceOperationDto, RequestedResourceOperation>chunk(10, transactionManager)
                .reader(resourceReader)
                .processor(resourceProcessor)
                .writer(resourceWriter)
                .allowStartIfComplete(true)
                .build();
    }

}
