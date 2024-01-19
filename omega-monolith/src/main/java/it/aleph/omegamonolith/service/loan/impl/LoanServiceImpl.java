package it.aleph.omegamonolith.service.loan.impl;

import it.aleph.omegamonolith.dao.loan.LoanRepository;
import it.aleph.omegamonolith.dto.catalog.BookDto;
import it.aleph.omegamonolith.dto.loan.LoanDto;
import it.aleph.omegamonolith.dto.loan.LoanStatusDto;
import it.aleph.omegamonolith.dto.user.UserDto;
import it.aleph.omegamonolith.exception.NotFoundException;
import it.aleph.omegamonolith.mapper.loan.LoanDtoMapper;
import it.aleph.omegamonolith.model.loan.Loan;
import it.aleph.omegamonolith.service.catalog.BookService;
import it.aleph.omegamonolith.service.loan.LoanService;
import it.aleph.omegamonolith.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanDtoMapper loanDtoMapper;
    private final BookService bookService;
    private final UserService userService;

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
    public LoanDto issueLoan(LoanDto loanDto, Long bookId, Long userId) {
        BookDto bookDto = bookService.getBookById(bookId);
        UserDto userDto = userService.findUserById(userId);
        loanDto.setAssociatedBook(bookDto);
        loanDto.setAssociatedUser(userDto);
        return loanDtoMapper.toDto(loanRepository.save(loanDtoMapper.toEntity(loanDto)));
    }

    @Override
    public LoanDto updateLoanStatus(Long id, LoanStatusDto loanStatusDto) {
        Loan loan = loanDtoMapper.toEntity(getLoanById(id));
        loan.setStatus(loanDtoMapper.toEntity(loanStatusDto));
        return loanDtoMapper.toDto(loanRepository.save(loan));
    }

    @Override
    public void removeLoan(Long id) {
        loanRepository.deleteById(id);
    }
}
