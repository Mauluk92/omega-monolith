package it.aleph.omegamonolith.providers.controller.catalog.book;

import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.catalog.book.CreateBookDto;
import it.aleph.omegamonolith.instancio.model.AliceInWonderlandModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class AddBookControllerProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        BookDto bookDtoSaved = Instancio.of(AliceInWonderlandModel.bookDtoModel()).create();
        CreateBookDto createBookDto =Instancio.of(AliceInWonderlandModel.createBookDtoModel()).create();
        return Stream.of(Arguments.of(createBookDto, bookDtoSaved));
    }
}
