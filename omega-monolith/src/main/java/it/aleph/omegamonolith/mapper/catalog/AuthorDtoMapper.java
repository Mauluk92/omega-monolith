package it.aleph.omegamonolith.mapper.catalog;

import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.model.catalog.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
public interface AuthorDtoMapper {


    Author toEntity(AuthorDto authorDto);
    AuthorDto toDto(Author entity);
    void update(@MappingTarget Author toUpdate, AuthorDto updated);

    List<AuthorDto> toDtoList(List<Author> authorList);


}
