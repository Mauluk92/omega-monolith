package it.aleph.omegamonolith.service.catalog.impl;

import it.aleph.omegamonolith.callnumber.calculator.CallNumberCalculator;
import it.aleph.omegamonolith.callnumber.calculator.CallNumberResult;
import it.aleph.omegamonolith.dao.catalog.BookRepository;
import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.catalog.book.CreateBookDto;
import it.aleph.omegamonolith.dto.catalog.book.SearchBooksDto;
import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import it.aleph.omegamonolith.exception.NotFoundException;
import it.aleph.omegamonolith.mapper.catalog.BookDtoMapper;
import it.aleph.omegamonolith.model.catalog.Book;
import it.aleph.omegamonolith.service.catalog.AuthorService;
import it.aleph.omegamonolith.service.catalog.BookService;
import it.aleph.omegamonolith.service.catalog.TagService;
import it.aleph.omegamonolith.service.resource.RequestedResourceService;
import it.aleph.omegamonolith.specification.catalog.BookSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final RequestedResourceService requestedResourceService;
    private final TagService tagService;
    private final BookDtoMapper bookDtoMapper;
    private final CallNumberCalculator calculator;
    private final List<ObjectFactory<BookSpecificationBuilder>> specificationFactory;

    /**
     * The process of creating a book is the following:
     * First, all authors and tags are found according to the idList provided by CreateBookDto POJO
     * If ANY of the given id does not correspond to an entity, an exception is raised,
     * otherwise a call number can be calculated via CutterNumberCalculator.
     * If the book is already registered (it has the same ISBN and the same ADDRESS of the introduced book),
     * an exception is raised. Note: an address corresponds to the concatenation of dewey decimal code, cutter number and
     * publication date of the book.
     * At the end of this process, the book is saved.
     * @param createBookDto a dto with the purpose of issuing a creation of a new book inside the library
     * @return the saved Book
     */
    @Override
    public BookDto addBook(CreateBookDto createBookDto) {
        List<AuthorDto> authorList = authorService.findAllByIdList(createBookDto.getAuthorIdList());
        List<TagDto> tagList = tagService.findAllByIdList(createBookDto.getTagIdList());
        BookDto bookDto = bookDtoMapper.toDto(createBookDto);
        bookDto.setAuthorList(authorList);
        bookDto.setTagList(tagList);
        CallNumberResult result = calculator.calculate(bookDto);
        bookDto.setCutterNumber(result.getCutterNumber());
        Book book = bookDtoMapper.toEntity(bookDto);
        bookRepository.save(book);
        return bookDto;
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
        listBookDto.forEach(requestedResourceService::insertOperation);
    }

    private Specification<Book> buildSpecification(SearchBooksDto searchBooksDto){
        List<BookSpecificationBuilder> specificationBuilderList = specificationFactory.stream().map(ObjectFactory::getObject).toList();
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
