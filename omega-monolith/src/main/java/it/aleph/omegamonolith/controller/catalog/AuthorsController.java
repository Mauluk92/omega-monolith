package it.aleph.omegamonolith.controller.catalog;

import it.aleph.omegamonolith.dto.catalog.AuthorDto;
import it.aleph.omegamonolith.service.catalog.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authors")
public class AuthorsController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public List<AuthorDto> searchAuthors(@RequestParam(defaultValue = "0") Integer pageNum,
                                         @RequestParam(defaultValue = "10")Integer pageSize,
                                         @RequestParam(required = false) String name){
        return authorService.searchAuthors(pageSize, pageNum, name);
    }

}
