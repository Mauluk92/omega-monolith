package it.aleph.omegamonolith.providers.service.catalog.book;

import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.instancio.model.AliceInWonderlandModel;
import it.aleph.omegamonolith.model.catalog.Book;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

public class SearchBookServiceProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        Book bookFound = Instancio.of(AliceInWonderlandModel.bookModel()).create();
        BookDto bookDto = Instancio.of(AliceInWonderlandModel.bookDtoModel()).create();
        Integer pageSize = 10;
        Integer pageNum = 0;
        String title = "Alice in Wonderland";

        Arguments arguments = Arguments.of(List.of(bookFound), List.of(bookDto), pageSize, pageNum, title);

        return Stream.of(arguments);
    }
}
