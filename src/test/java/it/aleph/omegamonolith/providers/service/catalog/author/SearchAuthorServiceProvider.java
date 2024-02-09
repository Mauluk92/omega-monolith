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

public class SearchAuthorServiceProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        Author authorFound = Instancio.of(LewisCarrollModel.authorModel()).create();
        AuthorDto authorDto = Instancio.of(LewisCarrollModel.authorDtoModel()).create();
        Integer pageSize = 10;
        Integer pageNum = 0;
        String name = "Lewis Carroll";

        Arguments arguments = Arguments.of(List.of(authorFound), List.of(authorDto), pageSize, pageNum, name);

        return Stream.of(arguments);
    }
}
