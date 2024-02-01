package it.aleph.omegamonolith.config.resource.batch.step;

import it.aleph.omegamonolith.dao.resource.RequestedResourceRepository;
import it.aleph.omegamonolith.dto.resource.request.RequestedResourceOperationDto;
import it.aleph.omegamonolith.model.resource.request.RequestedResourceOperation;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

public class ConsistencyStepRequest {


    @Bean
    public ItemReader<RequestedResourceOperation> requestedResourceReader(RequestedResourceRepository repository){
        RepositoryItemReader<RequestedResourceOperation> reader = new RepositoryItemReader<>();
        reader.setRepository(repository);
        reader.setMethodName("findAllByNotExecuted");
        reader.setPageSize(10);
        reader.setSort(Map.of());
        return reader;
    }

    @Bean
    public ItemWriter<RequestedResourceOperation> requestedResourceOperationDtoItemWriter(@Qualifier("requestResourceWriter") ItemWriter<RequestedResourceOperation> writer){
        return writer;
    }

    @Bean
    public Step consistencyStep(JobRepository jobRepository,
                                PlatformTransactionManager manager,
                                @Qualifier("requestedResourceReader") ItemReader<RequestedResourceOperation> reader,
                                @Qualifier("requestedResourceOperationDtoItemWriter") ItemWriter<RequestedResourceOperation> writer){
        return new StepBuilder("consistencyStep", jobRepository)
                .<RequestedResourceOperation, RequestedResourceOperation>chunk(10, manager)
                .reader(reader)
                .writer(writer)
                .allowStartIfComplete(true)
                .build();
    }
}
