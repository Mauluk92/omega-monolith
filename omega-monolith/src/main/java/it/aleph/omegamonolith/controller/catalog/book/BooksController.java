package it.aleph.omegamonolith.controller.catalog.book;

import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

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
}
