package it.aleph.omegamonolith.controller.catalog.author;

import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/author")
public interface AuthorController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AuthorDto addAuthor(@RequestBody @Valid AuthorDto createAuthorDto);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    AuthorDto getAuthorById(@PathVariable("id") Long id);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeAuthorById(@PathVariable("id") Long id);
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    AuthorDto updateAuthorById(@PathVariable("id") Long id,
                               @RequestBody @Valid AuthorDto updateAuthorDto);


}
