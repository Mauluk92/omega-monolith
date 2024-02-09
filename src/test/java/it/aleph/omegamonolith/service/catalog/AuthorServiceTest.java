package it.aleph.omegamonolith.service.catalog;

import it.aleph.omegamonolith.dao.catalog.AuthorRepository;
import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.mapper.catalog.AuthorDtoMapper;
import it.aleph.omegamonolith.model.catalog.Author;
import it.aleph.omegamonolith.providers.service.catalog.author.*;
import it.aleph.omegamonolith.service.catalog.impl.AuthorServiceImpl;
import it.aleph.omegamonolith.specification.catalog.impl.AuthorByNameSpecificationImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes =  {
        AuthorServiceImpl.class,
        AuthorByNameSpecificationImpl.class
})
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;
    @MockBean
    private AuthorDtoMapper authorDtoMapper;
    @MockBean
    private AuthorRepository authorRepository;

    @ParameterizedTest
    @ArgumentsSource(GetAuthorServiceProvider.class)
    public void getAuthorByIdTest(long id, Author authorFound, AuthorDto response){
        Mockito.when(authorRepository.findById(id)).thenReturn(Optional.of(authorFound));
        Mockito.when(authorDtoMapper.toDto(authorFound)).thenReturn(response);

        Assertions.assertEquals(response, authorService.getAuthorById(id));
    }

    @ParameterizedTest
    @ArgumentsSource(AddAuthorServiceProvider.class)
    public void addAuthorTest(AuthorDto request, Author mappedAuthor, Author authorSaved, AuthorDto response){
        Mockito.when(authorDtoMapper.toEntity(request)).thenReturn(mappedAuthor);
        Mockito.when(authorRepository.save(mappedAuthor)).thenReturn(authorSaved);
        Mockito.when(authorDtoMapper.toDto(authorSaved)).thenReturn(response);

        Assertions.assertEquals(response, authorService.addAuthor(request));
    }

    @ParameterizedTest
    @ArgumentsSource(UpdateAuthorServiceProvider.class)
    public void updateAuthorTest(long id, AuthorDto request, Author authorObtained, AuthorDto response){
        Mockito.when(authorRepository.findById(id)).thenReturn(Optional.of(authorObtained));
        Mockito.when(authorDtoMapper.toDto(authorObtained)).thenReturn(response);

        Assertions.assertEquals(response, authorService.updateAuthorById(id, request));
    }

    @ParameterizedTest
    @ArgumentsSource(RemoveAuthorServiceProvider.class)
    public void removeAuthorTest(long id, Author authorObtained){
        Mockito.when(authorRepository.findById(id)).thenReturn(Optional.of(authorObtained));

        Assertions.assertDoesNotThrow(() -> authorService.removeAuthorById(id));
    }

    @ParameterizedTest
    @ArgumentsSource(SearchAuthorServiceProvider.class)
    public void searchAuthorTest(List<Author> authorListFound, List<AuthorDto> authorListMapped, int pageSize, int pageNum, String name){
        Mockito.when(authorRepository.findAll(Mockito.<Specification<Author>>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(authorListFound));
        Mockito.when(authorDtoMapper.toDtoList(authorListFound)).thenReturn(authorListMapped);

        Assertions.assertEquals(authorListMapped, authorService.searchAuthors(pageSize, pageNum, name));
    }

    @ParameterizedTest
    @ArgumentsSource(GetAllAuthorServiceProvider.class)
    public void getAllAuthorById(List<Long> authorIdList, List<Author> authorFoundList, List<AuthorDto> authorDtoList){
        Mockito.when(authorRepository.findAllById(authorIdList)).thenReturn(authorFoundList);
        Mockito.when(authorDtoMapper.toDtoList(authorFoundList)).thenReturn(authorDtoList);

        Assertions.assertEquals(authorDtoList, authorService.findAllByIdList(authorIdList));
    }


}
