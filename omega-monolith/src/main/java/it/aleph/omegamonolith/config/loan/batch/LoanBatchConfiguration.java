package it.aleph.omegamonolith.config.loan.batch;

import it.aleph.omegamonolith.dao.loan.LoanRepository;
import it.aleph.omegamonolith.model.loan.Loan;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;

public class LoanBatchConfiguration {


    @Bean
    public ItemReader<Loan> repositoryReader(PagingAndSortingRepository<Loan, Long> pagingAndSortingRepository){
        RepositoryItemReader<Loan> reader = new RepositoryItemReader<>();
        reader.setSaveState(false);
        reader.setPageSize(10);
        reader.setRepository(pagingAndSortingRepository);
        reader.setMethodName("findAllAcceptableLoans");
        reader.setSort(new HashMap<>());
        return reader;
    }

    @Bean
    public ItemWriter<Loan> repositoryWriter(LoanRepository loanRepository){
        RepositoryItemWriter<Loan> writer = new RepositoryItemWriter<>();
        writer.setRepository(loanRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean(name="updateStep")
    public Step updateStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                            ItemReader<Loan> itemReader,
                            ItemProcessor<Loan, Loan> itemProcessor,
                            ItemWriter<Loan> itemWriter){
        return new StepBuilder("readingLoanStep", jobRepository)
                .<Loan, Loan>chunk(10, platformTransactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Job updateLoanStatusJob(JobRepository jobRepository,
                                   JobExecutionListener jobExecutionListener,
                                   @Qualifier("updateStep") Step updateStep){
        return new JobBuilder("updateLoanStatusJob", jobRepository)
                .start(updateStep)
                .listener(jobExecutionListener).build();
    }
}
