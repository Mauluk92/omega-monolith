package it.aleph.omegamonolith.controller.catalog.book.impl;

import it.aleph.omegamonolith.controller.catalog.book.BooksController;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.service.catalog.BookService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BooksControllerImpl implements BooksController {

    private final BookService bookService;
    @Override
    public List<BookDto> filteredBookSearch(Integer pageNum,
                                            Integer pageSize,
                                            Long authorId,
                                            Long tagId,
                                            String title,
                                            String address){
        return bookService.filteredBookSearch(pageSize, pageNum, authorId, tagId, title);
    }
}
