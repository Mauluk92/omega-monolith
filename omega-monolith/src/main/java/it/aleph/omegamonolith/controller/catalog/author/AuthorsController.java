package it.aleph.omegamonolith.controller.catalog.author;

import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public interface AuthorsController {


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<AuthorDto> searchAuthors(@RequestParam(defaultValue = "0") Integer pageNum,
                                  @RequestParam(defaultValue = "10")Integer pageSize,
                                  @RequestParam(required = false) String name);
}
