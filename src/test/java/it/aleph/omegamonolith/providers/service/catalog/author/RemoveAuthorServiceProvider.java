package it.aleph.omegamonolith.providers.service.catalog.author;

import it.aleph.omegamonolith.instancio.model.LewisCarrollModel;
import it.aleph.omegamonolith.model.catalog.Author;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class RemoveAuthorServiceProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        Author authorObtained = Instancio.of(LewisCarrollModel.authorModel()).create();
        Long idAuthor = authorObtained.getId();

        Arguments arguments = Arguments.of(idAuthor, authorObtained);

        return Stream.of(arguments);
    }
}
