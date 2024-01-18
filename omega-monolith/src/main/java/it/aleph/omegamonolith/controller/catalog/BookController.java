package it.aleph.omegamonolith.controller.catalog;

import it.aleph.omegamonolith.dto.catalog.AssociateBookDto;
import it.aleph.omegamonolith.dto.catalog.BookDto;
import it.aleph.omegamonolith.dto.catalog.CreateBookDto;
import it.aleph.omegamonolith.service.catalog.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/book")
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto addBook(@RequestBody @Valid CreateBookDto createBookDto){
        return bookService.addBook(createBookDto);
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable("id") Long id){
        return bookService.getBookById(id);
    }

    @PatchMapping("/{id}")
    public BookDto updateBookStatus(@PathVariable("id") Long id,@RequestBody Boolean status){
        return bookService.updateBookStatus(id, status);
    }
    @PatchMapping("/associate/{id}")
    public BookDto associateBook(@PathVariable("id") Long id, @RequestBody @Valid AssociateBookDto associateBookDto){
        return bookService.associateBook(id, associateBookDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookById(@PathVariable("id") Long id){
        bookService.removeBookById(id);
    }

    @PutMapping("/{id}")
    public BookDto updateBookById(@PathVariable("id") Long id, @RequestBody @Valid BookDto updateBookDto){
        return bookService.updateBook(id, updateBookDto);
    }


}
