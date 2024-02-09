package it.aleph.omegamonolith.instancio.model;

import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import it.aleph.omegamonolith.instancio.generator.tag.TagDescriptionGenerator;
import it.aleph.omegamonolith.instancio.generator.tag.TagIdGenerator;
import it.aleph.omegamonolith.instancio.generator.tag.TagNameGenerator;
import it.aleph.omegamonolith.model.catalog.Tag;
import org.instancio.Binding;
import org.instancio.Instancio;
import org.instancio.Model;

public class FantasyTagModel {

    public static Model<TagDto> tagDtoModel(){
        return Instancio.of(TagDto.class)
                .supply(Binding.fieldBinding(TagDto.class, "id"), new TagIdGenerator())
                .supply(Binding.fieldBinding(TagDto.class, "description"), new TagDescriptionGenerator())
                .supply(Binding.fieldBinding(TagDto.class, "name"),new TagNameGenerator())
                .toModel();
    }

    public static Model<Tag> tagModel() {
        return Instancio.of(Tag.class)
                .supply(Binding.fieldBinding(Tag.class, "id"), new TagIdGenerator())
                .supply(Binding.fieldBinding(Tag.class, "description"), new TagDescriptionGenerator())
                .supply(Binding.fieldBinding(Tag.class, "name"), new TagNameGenerator())
                .toModel();
    }

}
