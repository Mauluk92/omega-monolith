package it.aleph.omegamonolith.dto.loan;

import it.aleph.omegamonolith.dto.catalog.BookDto;
import it.aleph.omegamonolith.dto.user.UserDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LoanDto {
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @NotNull
    private LoanStatusDto loanStatus;
    @NotNull
    private BookDto associatedBook;
    @NotNull
    private UserDto associatedUser;
}
