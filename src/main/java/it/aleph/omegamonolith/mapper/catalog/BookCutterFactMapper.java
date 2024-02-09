package it.aleph.omegamonolith.mapper.catalog;

import it.aleph.omegamonolith.cutter.model.CutterNumberFact;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
public interface BookCutterFactMapper {
    @Mapping( target="accessPoint", expression = "java(book.getTitle().replace(\"\\s\", \"\").toLowerCase())")
    CutterNumberFact toFact(BookDto book);

}
