package it.aleph.omegamonolith.controller.catalog.book.impl;

import it.aleph.omegamonolith.controller.catalog.book.BookController;
import it.aleph.omegamonolith.dto.catalog.book.AssociateBookDto;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.catalog.book.CreateBookDto;
import it.aleph.omegamonolith.keycloak.service.AuthenticationService;
import it.aleph.omegamonolith.service.catalog.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class BookControllerImpl implements BookController {

    private final BookService bookService;


    @Override
    public BookDto addBook(CreateBookDto createBookDto){
        return bookService.addBook(createBookDto);
    }

    @Override
    public BookDto getBookById(Long id){
        return bookService.getBookById(id);
    }

    @Override
    public BookDto updateBookStatus(Long id, Boolean status){
        return bookService.updateBookStatus(id, status);
    }

    @Override
    public void removeBookById(Long id){
        bookService.removeBookById(id);
    }

    @Override
    public BookDto updateBookById(Long id, BookDto updateBookDto){
        return bookService.updateBook(id, updateBookDto);
    }
}
