package it.aleph.omegamonolith.controller.catalog.tag;

import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/tags")
public interface TagsController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<TagDto> getAllTags(@RequestParam(defaultValue = "10", name = "pageSize") Integer pageSize,
                            @RequestParam(defaultValue = "0", name = "pageNum") Integer pageNum,
                            @RequestParam(required = false, name = "tag") String tag);
}
