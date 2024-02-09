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

public class GetTagServiceProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        TagDto response = Instancio.of(FantasyTagModel.tagDtoModel())
                .ignore(Binding.fieldBinding(TagDto.class, "id"))
                .create();
        Tag tagFound = Instancio.of(FantasyTagModel.tagModel()).create();
        Long id = tagFound.getId();
        return Stream.of(Arguments.of(id, tagFound, response));
    }
}
