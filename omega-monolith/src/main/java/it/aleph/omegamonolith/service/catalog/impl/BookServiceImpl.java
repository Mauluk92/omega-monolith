package it.aleph.omegamonolith.service.catalog.impl;

import it.aleph.omegamonolith.cutter.model.CutterNumberFact;
import it.aleph.omegamonolith.dao.catalog.AuthorRepository;
import it.aleph.omegamonolith.dao.catalog.BookRepository;
import it.aleph.omegamonolith.dao.catalog.TagRepository;
import it.aleph.omegamonolith.dto.catalog.book.AssociateBookDto;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.catalog.book.CreateBookDto;
import it.aleph.omegamonolith.dto.catalog.book.SearchBooksDto;
import it.aleph.omegamonolith.dto.resource.request.RequestedResourceOperationDto;
import it.aleph.omegamonolith.dto.resource.request.RequestedTypeOperationDto;
import it.aleph.omegamonolith.exception.CutterProcessingException;
import it.aleph.omegamonolith.exception.NotFoundException;
import it.aleph.omegamonolith.mapper.catalog.BookCutterFactMapping;
import it.aleph.omegamonolith.mapper.catalog.BookDtoMapper;
import it.aleph.omegamonolith.model.catalog.Author;
import it.aleph.omegamonolith.model.catalog.Book;
import it.aleph.omegamonolith.model.catalog.Tag;
import it.aleph.omegamonolith.service.catalog.BookService;
import it.aleph.omegamonolith.specification.catalog.BookSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final BookDtoMapper bookDtoMapper;
    private final Map<String, Map<String, String>> globalCutterTable;
    private final KieContainer kieContainer;
    private final BookCutterFactMapping bookCutterFactMapping;
    private final ListableBeanFactory beanFactory;
    private final KafkaTemplate<String, RequestedResourceOperationDto> kafkaTemplate;

    @Override
    public CreateBookDto addBook(CreateBookDto createBookDto) {
        CutterNumberFact fact = bookCutterFactMapping.toFact(createBookDto);
        KieSession kieSession = kieContainer.newKieSession();
        String accessPointLetter = fact.getAccessPoint().substring(0,1).toLowerCase();
        Map<String, String> globalMatcher = globalCutterTable.get(accessPointLetter);


        fact.setCutterNumberMapping(globalMatcher);
        fact.setCutterNumberExpansionMapping(globalCutterTable.get("expansion"));


        FactHandle factHandle = kieSession.insert(fact);
        kieSession.fireAllRules();


        Book bookWithCutterNumber = bookRepository.findBookWithCutterNumber(fact.getCutterNumber(), createBookDto.getTitle());
        fact.setBookWithSameCutterNumber(bookWithCutterNumber);

        kieSession.update(factHandle, fact);
        kieSession.fireAllRules();
        kieSession.dispose();


        if(Objects.nonNull(fact.getCutterError())){
            String message = fact.getCutterError().getMessage();
            String cause = fact.getCutterError().getCause();
            throw CutterProcessingException.builder().message(message).messageCauseList(List.of(cause)).build();
        }
        Book book = bookDtoMapper.toEntity(createBookDto);
        bookCutterFactMapping.updateEntity(book, fact);
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
        listBookDto.stream().map(b -> {
            RequestedResourceOperationDto request = new RequestedResourceOperationDto();
            request.setRequestedTypeOperationDto(RequestedTypeOperationDto.INSERTION);
            request.setResourceType("book");
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
