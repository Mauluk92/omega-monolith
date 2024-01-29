package it.aleph.omegamonolith.config.resource.batch.step;

import it.aleph.omegamonolith.config.kafka.KafkaConfiguration;
import it.aleph.omegamonolith.dao.resource.RequestedResourceRepository;
import it.aleph.omegamonolith.dto.resource.request.RequestedResourceOperationDto;
import it.aleph.omegamonolith.dto.resource.response.ResourceOperationDto;
import it.aleph.omegamonolith.model.resource.request.RequestedResourceOperation;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

@Import({KafkaConfiguration.class})
public class PersistStepRequestBatchConfiguration {

    private final static String TOPIC_NAME = "report-book-events";

    @Bean
    public ItemReader<ResourceOperationDto> resourceReader(@Qualifier("consumerProp") Properties props){
        return new KafkaItemReader<>(props, TOPIC_NAME, 1);
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
                            ItemReader<RequestedResourceOperationDto> resourceReader,
                            ItemProcessor<RequestedResourceOperationDto, RequestedResourceOperation> resourceProcessor,
                            ItemWriter<RequestedResourceOperation> resourceWriter){
        return new StepBuilder("persistRequestStep", jobRepository)
                .<RequestedResourceOperationDto, RequestedResourceOperation>chunk(10, transactionManager)
                .reader(resourceReader)
                .processor(resourceProcessor)
                .writer(resourceWriter)
                .build();
    }

}
