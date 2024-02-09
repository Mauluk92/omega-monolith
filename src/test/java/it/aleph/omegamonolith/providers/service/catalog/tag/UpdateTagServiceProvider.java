package it.aleph.omegamonolith.providers.service.catalog.tag;

import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import it.aleph.omegamonolith.instancio.model.FantasyTagModel;
import it.aleph.omegamonolith.model.catalog.Tag;
import org.instancio.Binding;
import org.instancio.Instancio;
import org.instancio.Model;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UpdateTagServiceProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        TagDto request = Instancio.of(FantasyTagModel.tagDtoModel())
                .ignore(Binding.fieldBinding(TagDto.class, "id"))
                .supply(Binding.fieldBinding(TagDto.class, "description"), () -> "updatedTag")
                .create();
        Tag obtainedTag = Instancio.of(FantasyTagModel.tagModel()).create();
        Long tagId = obtainedTag.getId();
        TagDto response = Instancio.of(FantasyTagModel.tagDtoModel())
                .supply(Binding.fieldBinding(TagDto.class, "description"), () -> "updatedTag")
                .create();
        Arguments arguments = Arguments.of(tagId, request, obtainedTag, response);

        return Stream.of(arguments);
    }
}
