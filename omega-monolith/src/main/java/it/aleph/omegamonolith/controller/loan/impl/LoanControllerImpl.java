package it.aleph.omegamonolith.controller.loan.impl;

import it.aleph.omegamonolith.controller.loan.LoanController;
import it.aleph.omegamonolith.dto.loan.LoanDto;
import it.aleph.omegamonolith.dto.loan.LoanStatusDto;
import it.aleph.omegamonolith.service.loan.LoanService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoanControllerImpl implements LoanController {

    private final LoanService loanService;
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
}
