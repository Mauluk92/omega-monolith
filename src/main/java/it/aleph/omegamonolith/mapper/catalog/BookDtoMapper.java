package it.aleph.omegamonolith.mapper.catalog;

import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.catalog.book.CreateBookDto;
import it.aleph.omegamonolith.model.catalog.Author;
import it.aleph.omegamonolith.model.catalog.Book;
import it.aleph.omegamonolith.model.catalog.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
public interface BookDtoMapper {
    Book toEntity(CreateBookDto createBookDto);
    BookDto toDto(Book entity);

    CreateBookDto toCreateDto(Book entity);
    CreateBookDto toCreateDto(BookDto bookDto);
    void updateBook(@MappingTarget Book toUpdate, BookDto updated);
    List<BookDto> toBookDtoList(List<Book> entityList);

    Book toEntity(BookDto bookDto);

    BookDto toDto(CreateBookDto createBookDto);
    default Map<String, Object> toMap(BookDto bookDto){
        Map<String, Object> map = new HashMap<>();
        map.put("cutterNumber", bookDto.getCutterNumber());
        map.put("id", bookDto.getId());
        map.put("availability", bookDto.getAvailability());
        map.put("isbn", bookDto.getIsbn());
        map.put("description", bookDto.getDescription());
        map.put("title", bookDto.getTitle());
        map.put("pubDate", bookDto.getPubDate());
        map.put("pubHouse", bookDto.getPubHouse());
        map.put("deweyDecimalCode", bookDto.getDeweyDecimalCode());
        map.put("authorList", bookDto.getAuthorList());
        map.put("tagList", bookDto.getTagList());
        return map;
    }
    default Book toEntity(Map<String, Object> resource){
        Book book = new Book();
        book.setId((Long) resource.get("id"));
        book.setAvailability((Boolean) resource.get("availability"));
        book.setIsbn((String) resource.get("isbn"));
        book.setDescription((String) resource.get("description"));
        book.setTitle((String) resource.get("title"));
        book.setPubDate(Date.from(Instant.ofEpochMilli((Long) resource.get("pubDate"))));
        book.setPubHouse((String) resource.get("pubHouse"));
        book.setDeweyDecimalCode((String) resource.get("deweyDecimalCode"));
        book.setAuthorList((List<Author>) resource.get("authorList"));
        book.setTagList((List<Tag>) resource.get("tagList"));
        return book;
    }
}
