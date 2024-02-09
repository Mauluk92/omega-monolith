package it.aleph.omegamonolith.providers.service.catalog.book;

import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.instancio.model.AliceInWonderlandModel;
import it.aleph.omegamonolith.model.catalog.Book;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class GetBookServiceProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        Book bookFound = Instancio.of(AliceInWonderlandModel.bookModel()).create();
        BookDto response = Instancio.of(AliceInWonderlandModel.bookDtoModel()).create();
        Long idBook = bookFound.getId();

        Arguments arguments = Arguments.of(idBook, bookFound, response);

        return Stream.of(arguments);
    }
}
