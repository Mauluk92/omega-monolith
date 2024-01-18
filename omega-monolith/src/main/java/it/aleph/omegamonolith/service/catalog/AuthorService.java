package it.aleph.omegamonolith.service.catalog;

import it.aleph.omegamonolith.dto.catalog.AuthorDto;

import java.util.List;

public interface AuthorService {

    AuthorDto addAuthor(AuthorDto createAuthorDto);
    AuthorDto getAuthorById(Long id);
    void removeAuthorById(Long id);
    AuthorDto updateAuthorById(Long id, AuthorDto updated);
    List<AuthorDto> searchAuthors(Integer pageSize, Integer pageNum, String name);
}
