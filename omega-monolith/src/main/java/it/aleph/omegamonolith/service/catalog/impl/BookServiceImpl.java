package it.aleph.omegamonolith.service.catalog.impl;

import it.aleph.omegamonolith.cutter.calculator.CutterCalculator;
import it.aleph.omegamonolith.cutter.calculator.impl.BasicCutterCalculator;
import it.aleph.omegamonolith.cutter.mapping.CutterTableMappingBuilder;
import it.aleph.omegamonolith.cutter.mapping.impl.CutterTableMappingBuilderImpl;
import it.aleph.omegamonolith.dao.catalog.AuthorRepository;
import it.aleph.omegamonolith.dao.catalog.BookRepository;
import it.aleph.omegamonolith.dao.catalog.TagRepository;
import it.aleph.omegamonolith.dto.catalog.AssociateBookDto;
import it.aleph.omegamonolith.dto.catalog.BookDto;
import it.aleph.omegamonolith.dto.catalog.CreateBookDto;
import it.aleph.omegamonolith.dto.catalog.SearchBooksDto;
import it.aleph.omegamonolith.exception.NotFoundException;
import it.aleph.omegamonolith.mapper.catalog.BookDtoMapper;
import it.aleph.omegamonolith.model.catalog.Author;
import it.aleph.omegamonolith.model.catalog.Book;
import it.aleph.omegamonolith.model.catalog.Tag;
import it.aleph.omegamonolith.service.catalog.BookService;
import it.aleph.omegamonolith.specification.catalog.BookSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final BookDtoMapper bookDtoMapper;
    private final ListableBeanFactory beanFactory;

    @Override
    public BookDto addBook(CreateBookDto createBookDto) {
        Book book = bookDtoMapper.toEntity(createBookDto);
        return bookDtoMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto updateBook(Long id, BookDto updateBookDto) {
        Book obtainedBook = accessResource(id);
        bookDtoMapper.updateBook(obtainedBook, updateBookDto);
        bookRepository.save(obtainedBook);
        return bookDtoMapper.toDto(obtainedBook);
    }

    @Override
    public BookDto updateBookStatus(Long id, Boolean status) {
        Book obtainedBook = accessResource(id);
        obtainedBook.setAvailability(status);
        bookRepository.save(obtainedBook);
        return bookDtoMapper.toDto(obtainedBook);
    }

    @Override
    public BookDto associateBook(Long id, AssociateBookDto associateBookDto) {
        Book obtainedBook = accessResource(id);
        List<Author> authors = authorRepository.findAllById(associateBookDto.getAuthorIdList());
        List<Tag> tags = tagRepository.findAllById(associateBookDto.getTagIdList());
        obtainedBook.setAuthorList(authors);
        obtainedBook.setTagList(tags);
        bookRepository.save(obtainedBook);
        return bookDtoMapper.toDto(obtainedBook);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book obtainedBook = accessResource(id);
        return bookDtoMapper.toDto(obtainedBook);
    }

    @Override
    public void removeBookById(Long id) {
        Book obtainedBook = accessResource(id);
        bookRepository.delete(obtainedBook);
    }

    @Override
    public List<BookDto> filteredBookSearch(Integer pageSize, Integer pageNum, Long authorId, Long tagId, String title) {
        SearchBooksDto searchBooksDto = SearchBooksDto
                .builder()
                .authorId(authorId)
                .tagId(tagId)
                .title(title)
                .build();
        Sort sort = Sort.by("title");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Book> page = bookRepository.findAll(buildSpecification(searchBooksDto), pageable);
        return bookDtoMapper.toBookDtoList(page.toList());
    }

    private Specification<Book> buildSpecification(SearchBooksDto searchBooksDto){
        List<BookSpecificationBuilder> specificationBuilderList = beanFactory.getBeansOfType(BookSpecificationBuilder.class).values().stream().toList();
        return specificationBuilderList.stream()
                .map(specificationBuilder -> specificationBuilder
                        .setFilter(searchBooksDto)
                        .build()).reduce(Specification::and).orElse(Specification.where(null));
    }

    private Book accessResource(Long id){
        return bookRepository.findById(id).orElseThrow(() ->buildNotFoundException(List.of(id)));
    }

    private RuntimeException buildNotFoundException(List<Long> idList){
        return NotFoundException.builder().idListNotFound(idList).message("The following id was not found: " + idList).build();
    }
    private CutterCalculator buildCutterCalculator(){
        CutterTableMappingBuilder builderVowels = new CutterTableMappingBuilderImpl();
        Map<Character , Character> mappingVowels = builderVowels
                .singleCharacter('b','2')
                .singleCharacter('d', '3')
                .range('l','m', '4')
                .singleCharacter('n', '5')
                .singleCharacter('p', '6')
                .singleCharacter('r', '7')
                .range('s', 't', '8')
                .range('u', 'y', '9')
                .build();
        Map<Character, Map<Character, Character>> globalMapping = new HashMap<>();
        globalMapping.put('a', mappingVowels);
        globalMapping.put('e', mappingVowels);
        globalMapping.put('i', mappingVowels);
        globalMapping.put('o', mappingVowels);
        globalMapping.put('u', mappingVowels);
        BasicCutterCalculator basicCalculator = new BasicCutterCalculator();
        basicCalculator.setCutterTableMapping(globalMapping);
        return basicCalculator;
    }

}
