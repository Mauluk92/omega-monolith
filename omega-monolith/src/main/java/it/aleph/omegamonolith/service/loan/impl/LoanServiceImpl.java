package it.aleph.omegamonolith.service.loan.impl;

import it.aleph.omegamonolith.dao.loan.LoanRepository;
import it.aleph.omegamonolith.dto.loan.LoanDto;
import it.aleph.omegamonolith.dto.loan.LoanStatusDto;
import it.aleph.omegamonolith.exception.NotFoundException;
import it.aleph.omegamonolith.mapper.loan.LoanDtoMapper;
import it.aleph.omegamonolith.model.loan.Loan;
import it.aleph.omegamonolith.service.catalog.BookService;
import it.aleph.omegamonolith.service.loan.LoanService;
import it.aleph.omegamonolith.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanDtoMapper loanDtoMapper;
    private final UserService userService;
    private final BookService bookService;

    @Override
    public LoanDto getLoanById(Long id) {
        return loanDtoMapper.toDto(loanRepository.findById(id).orElseThrow(() ->
                NotFoundException.builder()
                        .idListNotFound(List.of(id))
                        .message("The loan identified by: " + id + "was not found")
                        .build())
        );
    }

    @Override
    public LoanDto issueLoan(LoanDto loanDto) {
        loanDto.setIssuedTimestamp(Instant.now());
        loanDto.setLoanStatus(LoanStatusDto.ISSUED);
        loanDto.setAssociatedUser(userService.findUserById(loanDto.getUserId()));
        loanDto.setAssociatedBook(bookService.getBookById(loanDto.getBookId()));
        return loanDto;
    }

    @Override
    public LoanDto updateLoanStatus(Long id, LoanStatusDto loanStatusDto) {
        Loan loan = loanDtoMapper.toEntity(getLoanById(id));
        loan.setLoanStatus(loanDtoMapper.toEntity(loanStatusDto));
        return loanDtoMapper.toDto(loanRepository.save(loan));
    }

    @Override
    public void removeLoan(Long id) {
        loanRepository.deleteById(id);
    }
}
