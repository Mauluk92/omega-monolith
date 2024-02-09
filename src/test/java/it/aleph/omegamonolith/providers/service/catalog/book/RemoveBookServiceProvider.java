package it.aleph.omegamonolith.providers.service.catalog.book;

import it.aleph.omegamonolith.instancio.model.AliceInWonderlandModel;
import it.aleph.omegamonolith.model.catalog.Book;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class RemoveBookServiceProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        Book obtainedBook = Instancio.of(AliceInWonderlandModel.bookModel()).create();
        Long bookId = obtainedBook.getId();

        Arguments arguments = Arguments.of(bookId, obtainedBook);
        return Stream.of(arguments);
    }
}
