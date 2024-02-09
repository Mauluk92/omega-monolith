package it.aleph.omegamonolith.controller.catalog.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.aleph.omegamonolith.controller.advice.OmegaControllerAdvice;
import it.aleph.omegamonolith.controller.catalog.book.impl.BookControllerImpl;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.catalog.book.CreateBookDto;
import it.aleph.omegamonolith.exception.NotFoundException;
import it.aleph.omegamonolith.providers.controller.catalog.book.AddBookControllerProvider;
import it.aleph.omegamonolith.providers.controller.catalog.book.GetBookControllerProvider;
import it.aleph.omegamonolith.providers.controller.catalog.book.UpdateBookControllerProvider;
import it.aleph.omegamonolith.providers.controller.catalog.book.UpdateStatusBookControllerProvider;
import it.aleph.omegamonolith.service.catalog.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
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
        {
                BookControllerImpl.class,
                ObjectMapper.class,
                OmegaControllerAdvice.class
        }
)
public class BookControllerTest {

    @MockBean
    private BookService bookService;
    @Autowired
    private OmegaControllerAdvice omegaControllerAdvice;
    @Autowired
    private BookController bookController;
    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(bookController)
                .setControllerAdvice(omegaControllerAdvice)
                .build();
    }

    @ParameterizedTest
    @ArgumentsSource(AddBookControllerProvider.class)
    public void addBookTest(CreateBookDto request, BookDto response) throws Exception{
        Mockito.when(bookService.addBook(Mockito.any(CreateBookDto.class))).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(response)));

    }

    @ParameterizedTest
    @ArgumentsSource(GetBookControllerProvider.class)
    public void getSingleBook(long id, BookDto response) throws Exception {
        Mockito.when(bookService.getBookById(id)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.get("/book/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .string(objectMapper
                                .writeValueAsString(response)));
    }

    @Test
    public void getNonExistentBook() throws Exception {
        NotFoundException notFoundException = NotFoundException.builder().idListNotFound(List.of(1L)).message("Not found book").build();
        Mockito.when(bookService.getBookById(1L)).thenThrow(notFoundException);
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/book/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @ParameterizedTest
    @ArgumentsSource(UpdateStatusBookControllerProvider.class)
    public void updateBookStatus(long id, BookDto bookDto) throws Exception {
        Mockito.when(bookService.updateBookStatus(id, true)).thenReturn(bookDto);
        mockMvc.perform(MockMvcRequestBuilders.patch("/book/{id}", id)
                        .content(objectMapper.writeValueAsString(Boolean.TRUE))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(bookDto)));
    }

    @ParameterizedTest
    @ArgumentsSource(UpdateBookControllerProvider.class)
    public void updateBookTest(BookDto toBeUpdated, BookDto updated) throws Exception {
        Mockito.when(bookService.updateBook(Mockito.anyLong(), Mockito.any(BookDto.class))).thenReturn(updated);
        mockMvc.perform(MockMvcRequestBuilders.put("/book/{id}", toBeUpdated.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(updated)));
    }

    @Test
    public void removeNonExistentBookTest() throws Exception {
        NotFoundException notFoundException = NotFoundException.builder().idListNotFound(List.of(1L)).message("Not found book").build();
        Mockito.doThrow(notFoundException).when(bookService).removeBookById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/book/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
