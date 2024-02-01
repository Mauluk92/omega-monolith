package it.aleph.omegamonolith.controller.catalog.book.impl;

import it.aleph.omegamonolith.controller.catalog.book.BooksController;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.keycloak.service.AuthenticationService;
import it.aleph.omegamonolith.service.catalog.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BooksControllerImpl implements BooksController {

    private final BookService bookService;
    private final AuthenticationService authenticationService;
    @Override
    public List<BookDto> filteredBookSearch(Integer pageNum,
                                            Integer pageSize,
                                            Long authorId,
                                            Long tagId,
                                            String title,
                                            String address){
        return bookService.filteredBookSearch(pageSize, pageNum, authorId, tagId, title, address);
    }

    @Override
    public void addBooks(List<BookDto> listBook) {
        bookService.addBooks(listBook);
    }


}
