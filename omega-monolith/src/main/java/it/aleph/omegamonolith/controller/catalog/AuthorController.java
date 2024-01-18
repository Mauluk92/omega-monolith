package it.aleph.omegamonolith.controller.catalog;

import it.aleph.omegamonolith.dto.catalog.AuthorDto;
import it.aleph.omegamonolith.service.catalog.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AuthorController {

    private final AuthorService authorService;


    @PostMapping("/author")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto addAuthor(@RequestBody @Valid AuthorDto createAuthorDto){
        return authorService.addAuthor(createAuthorDto);
    }

    @GetMapping("/author/{id}")
    public AuthorDto getAuthorById(@PathVariable Long id){
        return authorService.getAuthorById(id);
    }

    @DeleteMapping("/author/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAuthorById(@PathVariable Long id){
        authorService.removeAuthorById(id);
    }

    @PutMapping("/author/{id}")
    public AuthorDto updateAuthorById(@PathVariable Long id, @RequestBody @Valid AuthorDto updateAuthorDto){
        return authorService.updateAuthorById(id, updateAuthorDto);
    }

    @GetMapping("/authors")
    public List<AuthorDto> searchAuthors(@RequestParam(defaultValue = "0") Integer pageNum,
                                         @RequestParam(defaultValue = "10")Integer pageSize,
                                         @RequestParam(required = false) String name){
        return authorService.searchAuthors(pageSize, pageNum, name);
    }
}
