package it.aleph.omegamonolith.controller.catalog.tag;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.aleph.omegamonolith.controller.advice.OmegaControllerAdvice;
import it.aleph.omegamonolith.controller.catalog.tag.impl.TagsControllerImpl;
import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import it.aleph.omegamonolith.providers.controller.catalog.tag.SearchTagsControllerProvider;
import it.aleph.omegamonolith.service.catalog.TagService;
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
        {TagsControllerImpl.class,
                ObjectMapper.class,
                OmegaControllerAdvice.class}
)
public class TagsControllerTest {

    @MockBean
    private TagService tagService;
    @Autowired
    private OmegaControllerAdvice omegaControllerAdvice;
    @Autowired
    private TagsController tagsController;
    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(tagsController)
                .setControllerAdvice(omegaControllerAdvice)
                .build();
    }

    @ParameterizedTest
    @ArgumentsSource(SearchTagsControllerProvider.class)
    public void getTagsTest(List<TagDto> tagDtoList) throws Exception{
        Mockito.when(tagService.getAllTags(0, 10, null)).thenReturn(tagDtoList);
        mockMvc.perform(MockMvcRequestBuilders.get("/tags"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(tagDtoList)));
    }

}
