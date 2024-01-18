package it.aleph.omegamonolith.service.catalog;

import it.aleph.omegamonolith.dto.catalog.TagDto;

import java.util.List;

public interface TagService {

    TagDto addTag(TagDto createTagDto);
    TagDto getTagById(Long id);

    void removeTagById(Long id);
    TagDto updateTagById(Long id, TagDto updateTagDto);
    List<TagDto> getAllTags(Integer pageNum, Integer pageSize, String tag);
}
