package it.aleph.omegamonolith.providers.service.catalog.tag;

import it.aleph.omegamonolith.instancio.model.FantasyTagModel;
import it.aleph.omegamonolith.model.catalog.Tag;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class RemoveTagServiceProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        Tag tagObtained = Instancio.of(FantasyTagModel.tagModel()).create();
        Long tagId = tagObtained.getId();
        Arguments arguments = Arguments.of(tagId, tagObtained);
        return Stream.of(arguments);
    }
}
