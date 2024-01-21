package it.aleph.omegamonolith.controller.catalog.author.impl;

import it.aleph.omegamonolith.controller.catalog.author.AuthorController;
import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.service.catalog.AuthorService;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class AuthorControllerImpl implements AuthorController {

    private final AuthorService authorService;

    @Override
    public AuthorDto addAuthor(AuthorDto createAuthorDto){
        return authorService.addAuthor(createAuthorDto);
    }

    @Override
    public AuthorDto getAuthorById(Long id){
        return authorService.getAuthorById(id);
    }
    @Override
    public void removeAuthorById(Long id){
        authorService.removeAuthorById(id);
    }

    @Override
    public AuthorDto updateAuthorById(Long id, AuthorDto updateAuthorDto){
        return authorService.updateAuthorById(id, updateAuthorDto);
    }
}
