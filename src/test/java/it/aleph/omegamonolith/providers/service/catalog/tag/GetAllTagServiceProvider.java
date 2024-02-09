package it.aleph.omegamonolith.providers.service.catalog.tag;

import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import it.aleph.omegamonolith.instancio.model.FantasyTagModel;
import it.aleph.omegamonolith.model.catalog.Tag;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

public class GetAllTagServiceProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        Tag tagFound = Instancio.of(FantasyTagModel.tagModel()).create();
        TagDto tagMapped = Instancio.of(FantasyTagModel.tagDtoModel()).create();
        Long idTag = tagFound.getId();

        Arguments arguments = Arguments.of(List.of(idTag), List.of(tagFound), List.of(tagMapped));

        return Stream.of(arguments);

    }
}
