package it.aleph.omegamonolith.model.loan;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private LoanStatus status;
}
