package it.aleph.omegamonolith.providers.service.catalog.author;

import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.instancio.model.LewisCarrollModel;
import it.aleph.omegamonolith.model.catalog.Author;
import org.instancio.Binding;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class AddAuthorServiceProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        AuthorDto request = Instancio.of(LewisCarrollModel.authorDtoModel())
                .ignore(Binding.fieldBinding(AuthorDto.class, "id"))
                .create();
        Author mappedAuthor = Instancio.of(LewisCarrollModel.authorModel())
                .ignore(Binding.fieldBinding(AuthorDto.class, "id"))
                .create();
        Author savedAuthor = Instancio.of(LewisCarrollModel.authorModel()).create();
        AuthorDto response = Instancio.of(LewisCarrollModel.authorDtoModel()).create();

        Arguments arguments = Arguments.of(request, mappedAuthor, savedAuthor, response);

        return Stream.of(arguments);
    }
}
