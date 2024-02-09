package it.aleph.omegamonolith.controller.catalog.author;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.aleph.omegamonolith.controller.advice.OmegaControllerAdvice;
import it.aleph.omegamonolith.controller.catalog.author.impl.AuthorsControllerImpl;
import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.providers.controller.catalog.author.SearchAuthorsControllerProvider;
import it.aleph.omegamonolith.service.catalog.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes =
        {AuthorsControllerImpl.class,
                ObjectMapper.class,
                OmegaControllerAdvice.class}
)
public class AuthorsControllerTest {

    @MockBean
    private AuthorService authorService;
    @Autowired
    private OmegaControllerAdvice omegaControllerAdvice;
    @Autowired
    private AuthorsControllerImpl authorsController;
    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(authorsController)
                .setControllerAdvice(omegaControllerAdvice)
                .build();
    }

    @ParameterizedTest
    @ArgumentsSource(SearchAuthorsControllerProvider.class)
    public void findAuthorsFilteredTest(List<AuthorDto> authorDtoList) throws Exception {
        Mockito.when(authorService.searchAuthors(10,0,null)).thenReturn(authorDtoList);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(authorDtoList)));
    }


}
