package it.aleph.omegamonolith.providers.controller.catalog.tag;

import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import it.aleph.omegamonolith.instancio.model.FantasyTagModel;
import org.instancio.Binding;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class AddTagControllerProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        TagDto request = Instancio.of(FantasyTagModel.tagDtoModel())
                .ignore(Binding.fieldBinding(TagDto.class, "id"))
                .create();
        TagDto response = Instancio.of(FantasyTagModel.tagDtoModel()).create();
        return Stream.of(Arguments.of(request, response));
    }
}
