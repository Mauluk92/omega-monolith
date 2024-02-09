package it.aleph.omegamonolith.providers.service.catalog.author;

import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.instancio.model.LewisCarrollModel;
import it.aleph.omegamonolith.model.catalog.Author;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

public class GetAllAuthorServiceProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        Author authorFound = Instancio.of(LewisCarrollModel.authorModel()).create();
        AuthorDto mappedAuthor = Instancio.of(LewisCarrollModel.authorDtoModel()).create();
        Long idAuthor = authorFound.getId();

        Arguments arguments = Arguments.of(List.of(idAuthor), List.of(authorFound), List.of(mappedAuthor));

        return Stream.of(arguments);
    }
}
