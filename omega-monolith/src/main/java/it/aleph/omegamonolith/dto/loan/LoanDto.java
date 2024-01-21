package it.aleph.omegamonolith.dto.loan;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.aleph.omegamonolith.constraint.loan.annotations.LoanDateConstraint;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.user.UserDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@LoanDateConstraint
public class LoanDto {
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LoanStatusDto loanStatus;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BookDto associatedBook;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDto associatedUser;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long bookId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant issuedTimestamp;
}
