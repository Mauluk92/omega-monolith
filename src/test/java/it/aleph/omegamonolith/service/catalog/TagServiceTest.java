package it.aleph.omegamonolith.service.catalog;

import it.aleph.omegamonolith.dao.catalog.TagRepository;
import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import it.aleph.omegamonolith.mapper.catalog.TagDtoMapper;
import it.aleph.omegamonolith.model.catalog.Tag;
import it.aleph.omegamonolith.providers.service.catalog.author.RemoveAuthorServiceProvider;
import it.aleph.omegamonolith.providers.service.catalog.tag.*;
import it.aleph.omegamonolith.service.catalog.impl.TagServiceImpl;
import it.aleph.omegamonolith.specification.catalog.impl.TagByNameSpecificationImpl;
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
@ContextConfiguration(classes = {TagServiceImpl.class, TagByNameSpecificationImpl.class})
public class TagServiceTest {

    @Autowired
    private TagService tagService;
    @MockBean
    private TagDtoMapper tagDtoMapper;
    @MockBean
    private TagRepository tagRepository;

    @ParameterizedTest
    @ArgumentsSource(GetTagServiceProvider.class)
    public void getTagTest(long id, Tag tagFound, TagDto response){
        Mockito.when(tagRepository.findById(id)).thenReturn(Optional.of(tagFound));
        Mockito.when(tagDtoMapper.toDto(tagFound)).thenReturn(response);

        Assertions.assertEquals(response, tagService.getTagById(id));
    }

    @ParameterizedTest
    @ArgumentsSource(AddTagServiceProvider.class)
    public void addTagTest(TagDto request, Tag mappedTag, Tag savedTag, TagDto response){
        Mockito.when(tagDtoMapper.toEntity(request)).thenReturn(mappedTag);
        Mockito.when(tagRepository.save(mappedTag)).thenReturn(savedTag);
        Mockito.when(tagDtoMapper.toDto(savedTag)).thenReturn(response);

        Assertions.assertEquals(response, tagService.addTag(request));
    }

    @ParameterizedTest
    @ArgumentsSource(UpdateTagServiceProvider.class)
    public void updateTagTest(long id, TagDto request, Tag tagObtained, TagDto response){
        Mockito.when(tagRepository.findById(id)).thenReturn(Optional.of(tagObtained));
        Mockito.when(tagDtoMapper.toDto(tagObtained)).thenReturn(response);

        Assertions.assertEquals(response, tagService.updateTagById(id, request));
    }

    @ParameterizedTest
    @ArgumentsSource(RemoveTagServiceProvider.class)
    public void removeTagById(long id, Tag tagObtained){
        Mockito.when(tagRepository.findById(id)).thenReturn(Optional.of(tagObtained));

        Assertions.assertDoesNotThrow(() -> tagService.removeTagById(id));
    }

    @ParameterizedTest
    @ArgumentsSource(SearchTagServiceProvider.class)
    public void searchTagByNameTest(List<Tag> tagsFound, List<TagDto> mappedTags, Integer pageSize, Integer pageNum, String tagName){
        Mockito.when(tagRepository.findAll(Mockito.<Specification<Tag>>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(tagsFound));
        Mockito.when(tagDtoMapper.toDtoList(tagsFound)).thenReturn(mappedTags);

        Assertions.assertEquals(mappedTags, tagService.getAllTags(pageNum, pageSize, tagName));
    }

    @ParameterizedTest
    @ArgumentsSource(GetAllTagServiceProvider.class)
    public void getAllTagsByIdTest(List<Long> idList, List<Tag> tagFoundList, List<TagDto> tagListMapped){
        Mockito.when(tagRepository.findAllById(idList)).thenReturn(tagFoundList);
        Mockito.when(tagDtoMapper.toDtoList(tagFoundList)).thenReturn(tagListMapped);

        Assertions.assertEquals(tagListMapped, tagService.findAllByIdList(idList));
    }

}
