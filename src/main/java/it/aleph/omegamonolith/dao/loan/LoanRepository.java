package it.aleph.omegamonolith.dao.loan;

import it.aleph.omegamonolith.model.loan.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>, JpaSpecificationExecutor<Loan>, PagingAndSortingRepository<Loan, Long> {


    @Query(nativeQuery = true, value="SELECT * FROM loan AS l WHERE l.status NOT IN ('REJECTED', 'EXPIRED', 'CLOSED') ORDER BY l.issued_timestamp DESC")
    Page<Loan> findAllAcceptableLoans(Pageable pageable);

    @Query(nativeQuery = true, value="SELECT * FROM loan AS l WHERE (l.book_id = ? AND ((l.start_date <= ? <= end_date))) LIMIT 1")
    Loan findOverlappingLoans(Long bookId, Date startDate, Date endDate);
}
