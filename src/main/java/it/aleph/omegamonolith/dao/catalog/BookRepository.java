package it.aleph.omegamonolith.dao.catalog;

import it.aleph.omegamonolith.model.catalog.Book;
import org.mapstruct.Named;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {


    @Query(value = "SELECT * FROM book WHERE book.cutter_number = ? " +
            "AND book.isbn= ? " +
            "AND book.title = ?  LIMIT 1", nativeQuery = true)
    Book findBookWithCutterNumber(String cutterNumber, String isbn, String title);
}
