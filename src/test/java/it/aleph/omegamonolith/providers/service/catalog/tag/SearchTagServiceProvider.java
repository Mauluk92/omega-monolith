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

public class SearchTagServiceProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        Tag tagFound = Instancio.of(FantasyTagModel.tagModel()).create();
        TagDto tagDto = Instancio.of(FantasyTagModel.tagDtoModel()).create();
        Integer pageSize = 10;
        Integer pageNum = 0;
        String tagName = tagFound.getName();

        Arguments arguments = Arguments.of(List.of(tagFound), List.of(tagDto), pageSize, pageNum, tagName);

        return Stream.of(arguments);
    }
}
