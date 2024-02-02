package it.aleph.omegamonolith.service.catalog.impl;

import it.aleph.omegamonolith.callnumber.calculator.CallNumberCalculator;
import it.aleph.omegamonolith.callnumber.calculator.CallNumberResult;
import it.aleph.omegamonolith.dao.catalog.AuthorRepository;
import it.aleph.omegamonolith.dao.catalog.BookRepository;
import it.aleph.omegamonolith.dao.catalog.TagRepository;
import it.aleph.omegamonolith.dto.catalog.book.AssociateBookDto;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.catalog.book.CreateBookDto;
import it.aleph.omegamonolith.dto.catalog.book.SearchBooksDto;
import it.aleph.omegamonolith.dto.resource.request.RequestedResourceOperationDto;
import it.aleph.omegamonolith.dto.resource.request.RequestedTypeOperationDto;
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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final BookDtoMapper bookDtoMapper;
    private final ListableBeanFactory beanFactory;
    private final CallNumberCalculator calculator;
    private final KafkaTemplate<String, RequestedResourceOperationDto> kafkaTemplate;

    @Override
    public CreateBookDto addBook(CreateBookDto createBookDto) {
        Book book = bookDtoMapper.toEntity(createBookDto);
        BookDto bookDto = bookDtoMapper.toDto(book);
        CallNumberResult result = calculator.calculate(bookDto);
        book.setCutterNumber(result.getCutterNumber());
        return bookDtoMapper.toCreateDto(bookRepository.save(book));
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
    public List<BookDto> filteredBookSearch(Integer pageSize, Integer pageNum, Long authorId, Long tagId, String title, String address) {
        SearchBooksDto searchBooksDto = SearchBooksDto
                .builder()
                .authorId(authorId)
                .tagId(tagId)
                .title(title)
                .address(address)
                .build();
        Sort sort = Sort.by("title");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Book> page = bookRepository.findAll(buildSpecification(searchBooksDto), pageable);
        return bookDtoMapper.toBookDtoList(page.toList());
    }

    @Override
    public void addBooks(List<BookDto> listBookDto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        listBookDto.stream().map(b -> {
            RequestedResourceOperationDto request = new RequestedResourceOperationDto();
            request.setRequestedTypeOperationDto(RequestedTypeOperationDto.INSERTION);
            request.setResourceType("book");
            request.setAssociatedUser(jwt.getClaim("preferred_username"));
            b.setAuthorList(new ArrayList<>());
            b.setTagList(new ArrayList<>());
            request.setValid(true);
            request.setExecuted(false);

            CallNumberResult callNumberResult = calculator.calculate(b);

            request.setAddress(callNumberResult.getCallNumber());
            b.setCutterNumber(callNumberResult.getCutterNumber());

            request.setResource(bookDtoMapper.toMap(b));
            return request;
        }).forEach(r -> kafkaTemplate.send("report-book-events", r));
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

}
