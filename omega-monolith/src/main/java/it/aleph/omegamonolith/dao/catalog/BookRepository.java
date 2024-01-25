package it.aleph.omegamonolith.dao.catalog;

import it.aleph.omegamonolith.model.catalog.Book;
import org.mapstruct.Named;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {


    @Query(value = "SELECT * FROM book WHERE book.cutter_number LIKE CONCAT(?, '%') " +
            "AND book.title = ? ORDER BY LENGTH(book.cutter_number) DESC LIMIT 1", nativeQuery = true)
    Book findBookWithCutterNumber(String cutterNumber, String title);
}
