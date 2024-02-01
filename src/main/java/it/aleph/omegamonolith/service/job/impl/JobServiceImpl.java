package it.aleph.omegamonolith.service.job.impl;

import it.aleph.omegamonolith.exception.JobException;
import it.aleph.omegamonolith.service.job.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final ListableBeanFactory beanFactory;

    @Override
    public void startJob(String jobName) {
        JobLauncher jobLauncher = beanFactory.getBean(JobLauncher.class);
        JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
        Collection<Job> jobCollection = beanFactory.getBeansOfType(Job.class).values();
        jobCollection.stream().filter(j -> j.getName().equals(jobName)).forEach(j -> {
            try {
                jobLauncher.run(j, jobParameters);
            } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobRestartException |
                     JobParametersInvalidException e) {
                throw JobException.builder().message("An error occurred during Job Start!").errorMessages(List.of(e.getMessage())).build();
            }
        });
    }
}
