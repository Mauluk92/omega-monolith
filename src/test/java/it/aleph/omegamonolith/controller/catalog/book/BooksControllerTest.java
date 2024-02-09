package it.aleph.omegamonolith.controller.catalog.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.aleph.omegamonolith.controller.advice.OmegaControllerAdvice;
import it.aleph.omegamonolith.controller.catalog.book.impl.BooksControllerImpl;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.providers.controller.catalog.book.SearchBooksControllerProvider;
import it.aleph.omegamonolith.service.catalog.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes =
        {BooksControllerImpl.class,
                ObjectMapper.class,
                OmegaControllerAdvice.class}
)
public class BooksControllerTest {

    @MockBean
    private BookService bookService;
    @Autowired
    private OmegaControllerAdvice omegaControllerAdvice;
    @Autowired
    private BooksControllerImpl booksController;
    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(booksController)
                .setControllerAdvice(omegaControllerAdvice)
                .build();
    }


    @ParameterizedTest
    @ArgumentsSource(SearchBooksControllerProvider.class)
    public void getBookList(List<BookDto> bookDtoList) throws Exception {
        Mockito.when(bookService.filteredBookSearch(10, 0, null, null, null, null))
                .thenReturn(bookDtoList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(bookDtoList)));
    }
}
