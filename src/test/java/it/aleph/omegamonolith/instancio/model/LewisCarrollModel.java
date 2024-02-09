package it.aleph.omegamonolith.instancio.model;

import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.instancio.generator.author.AuthorIdGenerator;
import it.aleph.omegamonolith.instancio.generator.author.BiographyGenerator;
import it.aleph.omegamonolith.instancio.generator.author.NameGenerator;
import it.aleph.omegamonolith.model.catalog.Author;
import org.instancio.Binding;
import org.instancio.Instancio;
import org.instancio.Model;

public class LewisCarrollModel {

    public static Model<AuthorDto> authorDtoModel(){
        return Instancio.of(AuthorDto.class)
                .supply(Binding.fieldBinding(AuthorDto.class, "id"), new AuthorIdGenerator())
                .supply(Binding.fieldBinding(AuthorDto.class, "name"), new NameGenerator())
                .supply(Binding.fieldBinding(AuthorDto.class, "biography"), new BiographyGenerator())
                .toModel();
    }

    public static Model<Author> authorModel(){
        return Instancio.of(Author.class)
                .supply(Binding.fieldBinding(Author.class, "id"), new AuthorIdGenerator())
                .supply(Binding.fieldBinding(Author.class, "name"), new NameGenerator())
                .supply(Binding.fieldBinding(Author.class, "biography"), new BiographyGenerator())
                .toModel();
    }
}
