package it.aleph.omegamonolith.controller.catalog;

import it.aleph.omegamonolith.dto.catalog.TagDto;
import it.aleph.omegamonolith.service.catalog.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagsController {

    private final TagService tagService;

    @GetMapping
    public List<TagDto> getAllTags(@RequestParam(defaultValue = "10") Integer pageSize,
                                   @RequestParam(defaultValue = "0") Integer pageNum,
                                   @RequestParam(required = false) String tag){
        return tagService.getAllTags(pageNum, pageSize, tag);
    }
}
