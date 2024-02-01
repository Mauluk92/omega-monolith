package it.aleph.omegamonolith.controller.loan;

import it.aleph.omegamonolith.dto.loan.LoanDto;
import it.aleph.omegamonolith.dto.loan.LoanStatusDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/loan")
public interface LoanController {
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    LoanDto getLoanById(@PathVariable(name="id") Long id);
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    LoanDto issueLoan(@RequestBody @Valid LoanDto loanDto);
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    LoanDto updateLoanStatus(@PathVariable(name="id") Long id,
                             @RequestBody @Valid @NotNull LoanStatusDto loanStatusDto);

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeLoan(@PathVariable(name="id") Long id);
}
