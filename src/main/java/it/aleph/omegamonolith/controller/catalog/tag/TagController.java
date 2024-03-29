package it.aleph.omegamonolith.controller.catalog.tag;

import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/tag")
public interface TagController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TagDto addTag(@RequestBody @Valid TagDto createTagDto);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TagDto getTabById(@PathVariable(name = "id") Long id);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTagById(@PathVariable(name = "id") Long id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TagDto updateTagById(@PathVariable(name = "id") Long id, @RequestBody @Valid TagDto updateTagDto);
}
