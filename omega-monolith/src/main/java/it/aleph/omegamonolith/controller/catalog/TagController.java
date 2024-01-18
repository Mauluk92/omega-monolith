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
@RequestMapping("/tag")
public class TagController {


    private final TagService tagService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto addTag(@RequestBody @Valid TagDto createTagDto){
        return tagService.addTag(createTagDto);
    }

    @GetMapping("/{id}")
    public TagDto getTabById(@PathVariable Long id){
        return tagService.getTagById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTagById(@PathVariable Long id){ tagService.removeTagById(id);}

    @PutMapping("/{id}")
    public TagDto updateTagById(@PathVariable Long id, @RequestBody @Valid TagDto updateTagDto){
        return tagService.updateTagById(id, updateTagDto);
    }


}
