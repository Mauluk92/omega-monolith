package it.aleph.omegamonolith.config.resource.batch.step;

import it.aleph.omegamonolith.config.kafka.KafkaConfiguration;
import it.aleph.omegamonolith.dto.resource.response.ResourceOperationDto;
import it.aleph.omegamonolith.model.resource.request.RequestedResourceOperation;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Import({KafkaConfiguration.class})
public class ResourceKafkaResponseStep {


    @Bean
    public ItemProcessor<RequestedResourceOperation, ResourceOperationDto> responseProcessor(ItemProcessor<RequestedResourceOperation, ResourceOperationDto> processor){
        return processor;
    }

    @Bean
    public ItemWriter<ResourceOperationDto> kafkaWriter(KafkaTemplate<String, ResourceOperationDto> kafkaTemplate){
        KafkaItemWriter<String, ResourceOperationDto> kafkaItemWriter = new KafkaItemWriter<>();
        kafkaItemWriter.setKafkaTemplate(kafkaTemplate);
        kafkaItemWriter.setItemKeyMapper(b -> b.getResource().toString());
        return kafkaItemWriter;
    }

    @Bean
    public Step responseKafka(JobRepository jobRepository, PlatformTransactionManager manager,
                              @Qualifier("kafkaWriter") ItemWriter<ResourceOperationDto> writer,
                              @Qualifier("responseProcessor") ItemProcessor<RequestedResourceOperation, ResourceOperationDto> processor,
                              @Qualifier("requestedResourceReader")ItemReader<RequestedResourceOperation> reader){
        return new StepBuilder("responseKafka", jobRepository).<RequestedResourceOperation, ResourceOperationDto>chunk(10, manager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .allowStartIfComplete(true)
                .build();

    }
}
