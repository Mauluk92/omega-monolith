package it.aleph.omegamonolith.controller.loan.impl;

import it.aleph.omegamonolith.controller.loan.LoanController;
import it.aleph.omegamonolith.dto.loan.LoanDto;
import it.aleph.omegamonolith.dto.loan.LoanStatusDto;
import it.aleph.omegamonolith.service.loan.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoanControllerImpl implements LoanController {

    private final LoanService loanService;
    private final JobLauncher jobLauncher;
    private final Job job;
    @Override
    public LoanDto getLoanById(Long id) {
        return loanService.getLoanById(id);
    }

    @Override
    public LoanDto issueLoan(LoanDto loanDto) {
        return loanService.issueLoan(loanDto);
    }

    @Override
    public LoanDto updateLoanStatus(Long id, LoanStatusDto loanStatusDto) {
        return loanService.updateLoanStatus(id, loanStatusDto);
    }

    @Override
    public void removeLoan(Long id) {
        loanService.removeLoan(id);
    }

    @Override
    public void startJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(job, new JobParameters());
    }
}
