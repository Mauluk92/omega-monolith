package it.aleph.omegamonolith.service.catalog.impl;

import it.aleph.omegamonolith.dao.catalog.AuthorRepository;
import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.dto.catalog.author.SearchAuthorsDto;
import it.aleph.omegamonolith.exception.NotFoundException;
import it.aleph.omegamonolith.mapper.catalog.AuthorDtoMapper;
import it.aleph.omegamonolith.model.catalog.Author;
import it.aleph.omegamonolith.service.catalog.AuthorService;
import it.aleph.omegamonolith.specification.catalog.AuthorSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {


    private final AuthorRepository authorRepository;
    private final AuthorDtoMapper authorDtoMapper;
    private final List<ObjectFactory<AuthorSpecificationBuilder>> beanFactory;



    @Override
    public AuthorDto addAuthor(AuthorDto createAuthorDto) {
        Author entity = authorDtoMapper.toEntity(createAuthorDto);
        return authorDtoMapper.toDto(authorRepository.save(entity));
    }

    @Override
    public AuthorDto getAuthorById(Long id) {
        Author authorObtained = accessResource(id);
        return authorDtoMapper.toDto(authorObtained);
    }

    @Override
    public void removeAuthorById(Long id) {
        Author authorObtained = accessResource(id);
        authorRepository.delete(authorObtained);
    }

    @Override
    public AuthorDto updateAuthorById(Long id, AuthorDto updated) {
        Author authorObtained = accessResource(id);
        authorDtoMapper.update(authorObtained, updated);
        authorRepository.save(authorObtained);
        return authorDtoMapper.toDto(authorObtained);
    }

    @Override
    public List<AuthorDto> searchAuthors(Integer pageSize, Integer pageNum, String name) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("name"));
        SearchAuthorsDto searchAuthorsDto = SearchAuthorsDto.builder().name(name).build();
        Page<Author> pageAuthors = authorRepository.findAll(buildSpecification(searchAuthorsDto), pageable);
        return authorDtoMapper.toDtoList(pageAuthors.toList());
    }

    @Override
    public List<AuthorDto> findAllByIdList(List<Long> idAuthorList){
        List<Author> authorList = authorRepository.findAllById(idAuthorList);
        if(authorList.size() != idAuthorList.size()){
            List<Long> idFoundList = authorList.stream().map(Author::getId).toList();
            List<Long> idNotFoundList = idAuthorList.stream().filter(idFoundList::contains).toList();
            throw buildNotFoundException(idNotFoundList);
        }
        return authorDtoMapper.toDtoList(authorList);
    }

    private Specification<Author> buildSpecification(SearchAuthorsDto searchAuthorsDto){
        List<AuthorSpecificationBuilder> specificationBuilderList = beanFactory.stream().map(ObjectFactory::getObject).toList();
        return specificationBuilderList.stream()
                .map(specificationBuilder ->
                        specificationBuilder.setFilter(searchAuthorsDto).build())
                .reduce(Specification::and)
                .orElse(null);
    }

    private Author accessResource(Long id){
        return authorRepository.findById(id).orElseThrow(() -> buildNotFoundException(List.of(id)));
    }

    private RuntimeException buildNotFoundException(List<Long> idList){
        return NotFoundException.builder().idListNotFound(idList).message("The following id was not found: ").build();
    }
}
