package it.aleph.omegamonolith.service.catalog;

import it.aleph.omegamonolith.callnumber.calculator.CallNumberCalculator;
import it.aleph.omegamonolith.callnumber.calculator.CallNumberResult;
import it.aleph.omegamonolith.dao.catalog.BookRepository;
import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.catalog.book.CreateBookDto;
import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import it.aleph.omegamonolith.mapper.catalog.BookDtoMapper;
import it.aleph.omegamonolith.model.catalog.Book;
import it.aleph.omegamonolith.providers.service.catalog.book.*;
import it.aleph.omegamonolith.service.catalog.impl.BookServiceImpl;
import it.aleph.omegamonolith.service.resource.RequestedResourceService;
import it.aleph.omegamonolith.specification.catalog.impl.BookByTitleSpecificationImpl;
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
@ContextConfiguration(classes = {
        BookServiceImpl.class,
        BookByTitleSpecificationImpl.class
})
public class BookServiceTest {

    @Autowired
    private BookService bookService;
    @MockBean
    private BookDtoMapper bookDtoMapper;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private TagService tagService;
    @MockBean
    private RequestedResourceService requestedResourceService;
    @MockBean
    private CallNumberCalculator callNumberCalculator;


    @ParameterizedTest
    @ArgumentsSource(GetBookServiceProvider.class)
    public void getBookTest(long id, Book book, BookDto response){
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        Mockito.when(bookDtoMapper.toDto(book)).thenReturn(response);

        Assertions.assertEquals(response, bookService.getBookById(id));
    }

    @ParameterizedTest
    @ArgumentsSource(AddBookServiceProvider.class)
    public void addBookTest(CreateBookDto request,
                            BookDto mappedRequest,
                            List<AuthorDto> authorList,
                            List<TagDto> tagList,
                            CallNumberResult callNumberResult,
                            Book mappedEntityRequest,
                            Book bookSaved){
        Mockito.when(bookDtoMapper.toDto(request)).thenReturn(mappedRequest);
        Mockito.when(authorService.findAllByIdList(request.getAuthorIdList())).thenReturn(authorList);
        Mockito.when(tagService.findAllByIdList(request.getTagIdList())).thenReturn(tagList);
        Mockito.when(callNumberCalculator.calculate(mappedRequest)).thenReturn(callNumberResult);
        Mockito.when(bookDtoMapper.toEntity(mappedRequest)).thenReturn(mappedEntityRequest);
        Mockito.when(bookRepository.save(mappedEntityRequest)).thenReturn(bookSaved);

        Assertions.assertEquals(mappedRequest, bookService.addBook(request));
    }

    @ParameterizedTest
    @ArgumentsSource(UpdateBookServiceProvider.class)
    public void updateBookTest(Long bookId, BookDto request, Book obtainedBook, BookDto response){
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(obtainedBook));
        Mockito.when(bookDtoMapper.toDto(obtainedBook)).thenReturn(response);

        Assertions.assertEquals(response, bookService.updateBook(bookId, request));
    }

    @ParameterizedTest
    @ArgumentsSource(RemoveBookServiceProvider.class)
    public void removeBookByIdTest(Long bookId, Book obtainedBook){
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(obtainedBook));

        Assertions.assertDoesNotThrow(() -> bookService.removeBookById(bookId));
    }

    @ParameterizedTest
    @ArgumentsSource(SearchBookServiceProvider.class)
    public void searchBookTest(List<Book> bookFoundList,
                               List<BookDto> mappedBookFoundList,
                               Integer pageSize,
                               Integer pageNum,
                               String title){
        Mockito.when(bookRepository.findAll(Mockito.<Specification<Book>>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(bookFoundList));
        Mockito.when(bookDtoMapper.toBookDtoList(bookFoundList)).thenReturn(mappedBookFoundList);

        Assertions.assertEquals(mappedBookFoundList, bookService.filteredBookSearch(pageSize, pageNum, null, null, title, null));
    }


}
