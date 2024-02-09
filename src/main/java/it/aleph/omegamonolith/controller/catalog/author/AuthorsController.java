package it.aleph.omegamonolith.controller.catalog.author;

import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/authors")
public interface AuthorsController {


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<AuthorDto> searchAuthors(@RequestParam(defaultValue = "0", name = "pageNum") Integer pageNum,
                                  @RequestParam(defaultValue = "10", name = "pageSize")Integer pageSize,
                                  @RequestParam(required = false, name = "name") String name);
}
