package it.aleph.omegamonolith.controller.catalog.tag.impl;

import it.aleph.omegamonolith.controller.catalog.tag.TagController;
import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import it.aleph.omegamonolith.service.catalog.TagService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TagControllerImpl implements TagController {


    private final TagService tagService;

    @Override
    public TagDto addTag(TagDto createTagDto){
        return tagService.addTag(createTagDto);
    }

    @Override
    public TagDto getTabById(Long id){
        return tagService.getTagById(id);
    }

    @Override
    public void deleteTagById(Long id){ tagService.removeTagById(id);}

    @Override
    public TagDto updateTagById(Long id, TagDto updateTagDto){
        return tagService.updateTagById(id, updateTagDto);
    }


}
