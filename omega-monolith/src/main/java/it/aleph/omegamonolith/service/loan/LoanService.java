package it.aleph.omegamonolith.service.loan;

import it.aleph.omegamonolith.dto.loan.LoanDto;
import it.aleph.omegamonolith.dto.loan.LoanStatusDto;

public interface LoanService {
    LoanDto getLoanById(Long id);
    LoanDto issueLoan(LoanDto loanDto, Long bookId, Long userId);
    LoanDto updateLoanStatus(Long id, LoanStatusDto loanStatusDto);
    void removeLoan(Long id);
}
