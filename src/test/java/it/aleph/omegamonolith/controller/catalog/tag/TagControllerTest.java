package it.aleph.omegamonolith.controller.catalog.tag;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.aleph.omegamonolith.controller.advice.OmegaControllerAdvice;
import it.aleph.omegamonolith.controller.catalog.tag.impl.TagControllerImpl;
import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import it.aleph.omegamonolith.exception.NotFoundException;
import it.aleph.omegamonolith.providers.controller.catalog.tag.AddTagControllerProvider;
import it.aleph.omegamonolith.providers.controller.catalog.tag.GetTagControllerProvider;
import it.aleph.omegamonolith.providers.controller.catalog.tag.UpdateTagControllerProvider;
import it.aleph.omegamonolith.service.catalog.TagService;
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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes =
        {TagControllerImpl.class,
                ObjectMapper.class,
                OmegaControllerAdvice.class}
)
public class TagControllerTest {

    @MockBean
    private TagService tagService;
    @Autowired
    private OmegaControllerAdvice omegaControllerAdvice;
    @Autowired
    private TagController tagController;
    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(tagController)
                .setControllerAdvice(omegaControllerAdvice)
                .build();
    }

    @ParameterizedTest
    @ArgumentsSource(GetTagControllerProvider.class)
    public void getTagById(long id, TagDto tagDto) throws Exception {
        Mockito.when(tagService.getTagById(id)).thenReturn(tagDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/tag/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(tagDto)));

    }

    @Test
    public void getNonExistentTag() throws Exception {
        NotFoundException notFoundException = NotFoundException.builder().idListNotFound(List.of(1L)).message("Not found tag").build();
        Mockito.when(tagService.getTagById(1L)).thenThrow(notFoundException);
        mockMvc.perform(MockMvcRequestBuilders.get("/tag/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @ParameterizedTest
    @ArgumentsSource(AddTagControllerProvider.class)
    public void createTagTest(TagDto tagDto, TagDto tagDtoCreated) throws Exception {
        Mockito.when(tagService.addTag(Mockito.any(TagDto.class))).thenReturn(tagDtoCreated);
        mockMvc.perform(MockMvcRequestBuilders.post("/tag")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(tagDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(tagDtoCreated)));
    }

    @ParameterizedTest
    @ArgumentsSource(UpdateTagControllerProvider.class)
    public void updateTagTest(long id, TagDto updated) throws Exception {
        Mockito.when(tagService.updateTagById(Mockito.anyLong(), Mockito.any(TagDto.class))).thenReturn(updated);
        mockMvc.perform(MockMvcRequestBuilders.put("/tag/{id}", id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(updated)));
    }

    @Test
    public void removeTagTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/tag/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void removeNonExistentTag() throws Exception {
        NotFoundException notFoundException = NotFoundException.builder().idListNotFound(List.of(1L)).message("Not found tag").build();
        Mockito.doThrow(notFoundException).when(tagService).removeTagById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/tag/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}
