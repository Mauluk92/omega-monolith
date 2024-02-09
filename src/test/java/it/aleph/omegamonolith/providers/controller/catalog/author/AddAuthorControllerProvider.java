package it.aleph.omegamonolith.providers.controller.catalog.author;

import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.instancio.model.LewisCarrollModel;
import org.instancio.Binding;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class AddAuthorControllerProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        AuthorDto authorDtoWithoutId = Instancio.of(LewisCarrollModel.authorDtoModel())
                .ignore(Binding.fieldBinding(AuthorDto.class, "id"))
                .create();
        AuthorDto authorDtoSaved = Instancio.of(LewisCarrollModel.authorDtoModel())
                .create();
        return Stream.of(Arguments.of(authorDtoWithoutId, authorDtoSaved));
    }
}
