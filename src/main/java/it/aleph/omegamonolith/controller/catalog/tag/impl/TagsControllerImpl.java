package it.aleph.omegamonolith.controller.catalog.tag.impl;

import it.aleph.omegamonolith.controller.catalog.tag.TagsController;
import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import it.aleph.omegamonolith.service.catalog.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TagsControllerImpl implements TagsController {

    private final TagService tagService;


    public List<TagDto> getAllTags( Integer pageSize,
                                    Integer pageNum,
                                    String tag){
        return tagService.getAllTags(pageNum, pageSize, tag);
    }
}
