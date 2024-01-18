package it.aleph.omegamonolith.mapper.catalog;

import it.aleph.omegamonolith.dto.catalog.BookDto;
import it.aleph.omegamonolith.dto.catalog.CreateBookDto;
import it.aleph.omegamonolith.model.catalog.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
public interface BookDtoMapper {
    Book toEntity(CreateBookDto createBookDto);
    BookDto toDto(Book entity);
    void updateBook(@MappingTarget Book toUpdate, BookDto updated);
    List<BookDto> toBookDtoList(List<Book> entityList);
}
