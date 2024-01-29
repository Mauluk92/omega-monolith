package it.aleph.omegamonolith.controller.catalog.book;

import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/books")
public interface BooksController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<BookDto> filteredBookSearch(@RequestParam(defaultValue = "0", name="pageNum") Integer pageNum,
                                     @RequestParam(defaultValue = "10", name="pageSize") Integer pageSize,
                                     @RequestParam(required = false, name="authorId") Long authorId,
                                     @RequestParam(required = false, name="tagId") Long tagId,
                                     @RequestParam(required = false, name="title") String title,
                                     @RequestParam(required = false, name="address") String address);


    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    void addBooks(@RequestBody List<BookDto> listBook);

}
