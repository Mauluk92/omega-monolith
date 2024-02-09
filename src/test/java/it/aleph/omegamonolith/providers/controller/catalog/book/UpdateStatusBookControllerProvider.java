package it.aleph.omegamonolith.providers.controller.catalog.book;

import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.instancio.model.AliceInWonderlandModel;
import it.aleph.omegamonolith.model.catalog.Book;
import org.instancio.Binding;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UpdateStatusBookControllerProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        BookDto bookDto = Instancio.of(AliceInWonderlandModel.bookDtoModel())
                .supply(Binding.fieldBinding(BookDto.class, "availability"), () -> Boolean.FALSE).create();
        Long bookId = bookDto.getId();;
        return Stream.of(Arguments.of(bookId, bookDto));
    }
}
