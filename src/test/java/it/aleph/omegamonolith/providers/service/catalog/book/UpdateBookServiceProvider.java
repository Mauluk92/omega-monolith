package it.aleph.omegamonolith.providers.service.catalog.book;

import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.instancio.model.AliceInWonderlandModel;
import it.aleph.omegamonolith.model.catalog.Book;
import org.instancio.Binding;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UpdateBookServiceProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        BookDto request = Instancio.of(AliceInWonderlandModel.bookDtoModel())
                .supply(Binding.fieldBinding(BookDto.class, "description"), () -> "Updated!")
                .ignore(Binding.fieldBinding(BookDto.class, "id"))
                .create();
        Book obtainedBook = Instancio.of(AliceInWonderlandModel.bookModel()).create();
        BookDto response = Instancio.of(AliceInWonderlandModel.bookDtoModel())
                .supply(Binding.fieldBinding(BookDto.class, "description"), () -> "Updated!")
                .create();
        Long bookId = obtainedBook.getId();

        Arguments arguments = Arguments.of(bookId, request, obtainedBook, response);
        return Stream.of(arguments);
    }
}
