package it.aleph.omegamonolith.mapper.catalog;

import it.aleph.omegamonolith.cutter.model.CutterNumberFact;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.catalog.book.CreateBookDto;
import it.aleph.omegamonolith.model.catalog.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
public interface BookCutterFactMapping {
    @Mapping( target="accessPoint", expression = "java(book.getTitle().replace(\"\\s\", \"\").toLowerCase())")
    CutterNumberFact toFact(CreateBookDto book);

    @Mapping( target="accessPoint", expression = "java(book.getTitle().replace(\"\\s\", \"\").toLowerCase())")
    CutterNumberFact toFact(BookDto book);

    @Mapping(target= "title", ignore = true)
    void updateEntity(@MappingTarget Book book, CutterNumberFact cutterNumberFact);

}
