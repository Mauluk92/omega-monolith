package it.aleph.omegamonolith.controller.catalog;

import it.aleph.omegamonolith.dto.catalog.TagDto;
import it.aleph.omegamonolith.service.catalog.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TagController {


    private final TagService tagService;
    @PostMapping("/tag")
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto addTag(@RequestBody @Valid TagDto createTagDto){
        return tagService.addTag(createTagDto);
    }

    @GetMapping("/tag/{id}")
    public TagDto getTabById(@PathVariable Long id){
        return tagService.getTagById(id);
    }

    @DeleteMapping("/tag/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTagById(@PathVariable Long id){ tagService.removeTagById(id);}

    @PutMapping("/tag/{id}")
    public TagDto updateTagById(@PathVariable Long id, @RequestBody @Valid TagDto updateTagDto){
        return tagService.updateTagById(id, updateTagDto);
    }

    @GetMapping("/tags")
    public List<TagDto> getAllTags(@RequestParam(defaultValue = "10") Integer pageSize,
                                   @RequestParam(defaultValue = "0") Integer pageNum,
                                   @RequestParam(required = false) String tag){
        return tagService.getAllTags(pageNum, pageSize, tag);
    }
}
