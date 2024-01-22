package it.aleph.omegamonolith.config.loan.batch;

import jakarta.annotation.Nonnull;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class UpdateLoanJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        if(jobExecution.getStatus().isRunning()){
            System.out.println(jobExecution.getJobInstance().getJobName() + "IS RUNNING");
        }else if(jobExecution.getStatus() == BatchStatus.FAILED){
            System.out.println("JOB FAILED");
        }

    }

    @Override
    public void afterJob(@Nonnull JobExecution jobExecution) {
        JobExecutionListener.super.afterJob(jobExecution);
    }
}
