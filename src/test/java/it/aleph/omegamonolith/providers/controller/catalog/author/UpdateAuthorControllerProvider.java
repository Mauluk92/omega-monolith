package it.aleph.omegamonolith.providers.controller.catalog.author;

import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.instancio.model.LewisCarrollModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UpdateAuthorControllerProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        AuthorDto authorDtoUpdated = Instancio.of(LewisCarrollModel.authorDtoModel()).create();
        Long id = authorDtoUpdated.getId();
        return Stream.of(Arguments.of(id, authorDtoUpdated));
    }
}
