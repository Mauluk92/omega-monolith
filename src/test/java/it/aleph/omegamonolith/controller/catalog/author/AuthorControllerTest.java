package it.aleph.omegamonolith.controller.catalog.author;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.aleph.omegamonolith.controller.advice.OmegaControllerAdvice;
import it.aleph.omegamonolith.controller.catalog.author.impl.AuthorControllerImpl;
import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.exception.NotFoundException;
import it.aleph.omegamonolith.providers.controller.catalog.author.AddAuthorControllerProvider;
import it.aleph.omegamonolith.providers.controller.catalog.author.GetAuthorControllerProvider;
import it.aleph.omegamonolith.providers.controller.catalog.author.UpdateAuthorControllerProvider;
import it.aleph.omegamonolith.service.catalog.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes =
        {AuthorControllerImpl.class,
                ObjectMapper.class,
                OmegaControllerAdvice.class}
)
public class AuthorControllerTest {

    @MockBean
    private AuthorService authorService;
    @Autowired
    private OmegaControllerAdvice omegaControllerAdvice;
    @Autowired
    private AuthorController authorController;
    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(authorController)
                .setControllerAdvice(omegaControllerAdvice)
                .build();
    }

    @ParameterizedTest
    @ArgumentsSource(GetAuthorControllerProvider.class)
    public void getAuthorByIdTest(long id, AuthorDto authorDto) throws Exception {
        Mockito.when(authorService.getAuthorById(id)).thenReturn(authorDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(authorDto)));
    }

    @Test
    public void getNonExistentAuthor() throws Exception{
        NotFoundException notFoundException = NotFoundException.builder().idListNotFound(List.of(1L)).message("Not found author").build();
        Mockito.when(authorService.getAuthorById(1L)).thenThrow(notFoundException);
        mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status()
                        .isNotFound());
    }

    @ParameterizedTest
    @ArgumentsSource(AddAuthorControllerProvider.class)
    public void addAuthorTest(AuthorDto authorDto, AuthorDto authorDtoCreated) throws Exception {
        Mockito.when(authorService.addAuthor(Mockito.any(AuthorDto.class))).thenReturn(authorDtoCreated);
        mockMvc.perform(MockMvcRequestBuilders.post("/author").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(authorDtoCreated)));

    }
    @ParameterizedTest
    @ArgumentsSource(UpdateAuthorControllerProvider.class)
    public void updateAuthorTest(long id, AuthorDto authorDto) throws Exception {
        Mockito.when(authorService.updateAuthorById(Mockito.anyLong(), Mockito.any(AuthorDto.class))).thenReturn(authorDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/author/{id}", id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorDto)))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(authorDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void removeNonExistentAuthor() throws Exception {
        NotFoundException notFoundException = NotFoundException.builder().idListNotFound(List.of(1L)).message("Not found author").build();
        Mockito.doThrow(notFoundException).when(authorService).removeAuthorById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/author/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
