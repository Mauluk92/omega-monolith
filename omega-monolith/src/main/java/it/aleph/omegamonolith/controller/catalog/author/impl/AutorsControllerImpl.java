package it.aleph.omegamonolith.controller.catalog.author.impl;

import it.aleph.omegamonolith.controller.catalog.author.AuthorsController;
import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.service.catalog.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AutorsControllerImpl implements AuthorsController {

    private final AuthorService authorService;
    public List<AuthorDto> searchAuthors(Integer pageNum,
                                         Integer pageSize,
                                         String name){
        return authorService.searchAuthors(pageSize, pageNum, name);
    }

}
