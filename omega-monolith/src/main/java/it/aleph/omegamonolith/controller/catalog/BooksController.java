package it.aleph.omegamonolith.controller.catalog;

import it.aleph.omegamonolith.dto.catalog.BookDto;
import it.aleph.omegamonolith.service.catalog.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> filteredBookSearch(@RequestParam(defaultValue = "0") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(required = false) Long authorId,
                                            @RequestParam(required = false) Long tagId,
                                            @RequestParam(required = false) String title){
        return bookService.filteredBookSearch(pageSize, pageNum, authorId, tagId, title);
    }
}
