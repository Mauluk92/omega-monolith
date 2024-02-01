package it.aleph.omegamonolith.dto.loan;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.aleph.omegamonolith.constraint.loan.annotations.LoanDateConstraint;
import it.aleph.omegamonolith.constraint.loan.annotations.LoanNoPastDateConstraint;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@LoanDateConstraint
public class LoanDto {

    @NotNull
    @LoanNoPastDateConstraint
    private Date startDate;
    @NotNull
    private Date endDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LoanStatusDto loanStatus;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BookDto associatedBook;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String associatedUser;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long bookId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant issuedTimestamp;
}
