package it.aleph.omegamonolith.providers.service.catalog.tag;

import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import it.aleph.omegamonolith.instancio.model.FantasyTagModel;
import it.aleph.omegamonolith.model.catalog.Tag;
import org.instancio.Binding;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class AddTagServiceProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        TagDto request = Instancio.of(FantasyTagModel.tagDtoModel()).ignore(Binding.fieldBinding(TagDto.class, "id")).create();
        Tag mappedTag = Instancio.of(FantasyTagModel.tagModel()).ignore(Binding.fieldBinding(TagDto.class, "id")).create();
        Tag savedTag = Instancio.of(FantasyTagModel.tagModel()).create();
        TagDto response = Instancio.of(FantasyTagModel.tagDtoModel()).create();
        Arguments arguments = Arguments.of(request, mappedTag, savedTag, response);
        return Stream.of(arguments);
    }
}
