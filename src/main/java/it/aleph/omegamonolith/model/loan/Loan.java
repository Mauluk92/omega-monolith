package it.aleph.omegamonolith.model.loan;

import it.aleph.omegamonolith.model.catalog.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="loan")
public class Loan {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name="start_date", nullable = false)
    private Date startDate;
    @Column(name="end_date", nullable = false)
    private Date endDate;
    @Column(name="status", nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

    @ManyToOne(cascade= CascadeType.MERGE, optional = false)
    @JoinColumn(name="book_id", referencedColumnName = "id")
    private Book associatedBook;
    @Column(name="username", nullable = false)
    private String associatedUser;

    @Column(name="issued_timestamp", nullable = false)
    private Instant issuedTimestamp;
}
