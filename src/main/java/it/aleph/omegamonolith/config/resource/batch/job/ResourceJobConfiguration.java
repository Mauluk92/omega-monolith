package it.aleph.omegamonolith.config.resource.batch.job;

import it.aleph.omegamonolith.config.resource.batch.step.ConsistencyStepRequest;
import it.aleph.omegamonolith.config.resource.batch.step.ExecutionStep;
import it.aleph.omegamonolith.config.resource.batch.step.PersistStepRequestBatchConfiguration;
import it.aleph.omegamonolith.config.resource.batch.step.ResourceKafkaResponseStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Import({PersistStepRequestBatchConfiguration.class,
        ConsistencyStepRequest.class,
        ResourceKafkaResponseStep.class,
        ExecutionStep.class})
public class ResourceJobConfiguration {

    @Bean
    public Job resourceJobExecution(@Qualifier("persistStep")Step firstStep,
                           @Qualifier("consistencyStep") Step secondStep,
                           @Qualifier("responseKafka") Step thirdStep,
                           @Qualifier("executionStep") Step fourthStep,
                           JobRepository jobRepository){
        return new JobBuilder("resourceJobExecution", jobRepository)
                .start(firstStep)
                .next(secondStep)
                .next(thirdStep)
                .next(fourthStep)
                .build();

    }
}
