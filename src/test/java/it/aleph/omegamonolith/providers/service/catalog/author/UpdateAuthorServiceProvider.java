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

public class UpdateAuthorServiceProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        AuthorDto request = Instancio.of(LewisCarrollModel.authorDtoModel())
                .ignore(Binding.fieldBinding(AuthorDto.class, "id"))
                .supply(Binding.fieldBinding(AuthorDto.class, "biography"), () -> "Updated!")
                .create();
        Author authorObtained = Instancio.of(LewisCarrollModel.authorModel()).create();
        Long idAuthor = authorObtained.getId();
        AuthorDto response = Instancio.of(LewisCarrollModel.authorDtoModel())
                .supply(Binding.fieldBinding(AuthorDto.class, "biography"), () -> "Updated!")
                .create();

        Arguments arguments = Arguments.of(idAuthor, request, authorObtained, response);

        return Stream.of(arguments);
    }
}
