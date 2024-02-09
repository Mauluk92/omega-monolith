package it.aleph.omegamonolith.providers.controller.catalog.tag;

import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import it.aleph.omegamonolith.instancio.model.FantasyTagModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UpdateTagControllerProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        TagDto tagDto = Instancio.of(FantasyTagModel.tagDtoModel()).create();
        Long id = tagDto.getId();
        return Stream.of(Arguments.of(id, tagDto));
    }
}
