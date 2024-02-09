package it.aleph.omegamonolith.providers.controller.catalog.book;

import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.instancio.model.AliceInWonderlandModel;
import org.instancio.Binding;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UpdateBookControllerProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        BookDto bookDtoUpdated = Instancio.of(AliceInWonderlandModel.bookDtoModel())
                .ignore(Binding.fieldBinding(BookDto.class, "id"))
                .create();
        Long bookId = bookDtoUpdated.getId();
        return Stream.of(Arguments.of(bookId, bookDtoUpdated));
    }
}
