package it.aleph.omegamonolith.mapper.catalog;

import it.aleph.omegamonolith.dto.catalog.TagDto;
import it.aleph.omegamonolith.model.catalog.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
public interface TagDtoMapper {

    Tag toEntity(TagDto createTagDto);
    TagDto toDto(Tag entity);
    void update(@MappingTarget Tag toUpdate, TagDto updated);
    List<TagDto> toDtoList(List<Tag> tagList);
}
