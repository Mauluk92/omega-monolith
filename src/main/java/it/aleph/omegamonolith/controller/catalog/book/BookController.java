package it.aleph.omegamonolith.controller.catalog.book;

import it.aleph.omegamonolith.dto.catalog.book.AssociateBookDto;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.catalog.book.CreateBookDto;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/book")
public interface BookController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BookDto addBook(@RequestBody @Valid CreateBookDto createBookDto);
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    BookDto getBookById(@PathVariable(name="id") Long id);
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    BookDto updateBookStatus(@PathVariable("id") Long id,@RequestBody @Valid @NotNull Boolean status);
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeBookById(@PathVariable("id") Long id);
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    BookDto updateBookById(@PathVariable("id") Long id, @RequestBody @Valid BookDto updateBookDto);
}
