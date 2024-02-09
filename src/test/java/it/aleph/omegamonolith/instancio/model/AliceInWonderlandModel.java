package it.aleph.omegamonolith.instancio.model;

import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.catalog.book.CreateBookDto;
import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import it.aleph.omegamonolith.instancio.generator.book.*;
import it.aleph.omegamonolith.model.catalog.Author;
import it.aleph.omegamonolith.model.catalog.Book;
import it.aleph.omegamonolith.model.catalog.Tag;
import org.instancio.*;

import java.util.Date;
import java.util.List;

public class AliceInWonderlandModel {


    public static Model<BookDto> bookDtoModel() {
        AuthorDto authorDto = Instancio.of(LewisCarrollModel.authorDtoModel()).create();
        TagDto tagDto = Instancio.of(FantasyTagModel.tagDtoModel()).create();
        return Instancio.of(BookDto.class)
                .supply(Binding.fieldBinding(BookDto.class, "id"), new BookIdGenerator())
                .supply(Binding.fieldBinding(BookDto.class, "isbn"), new IsbnGenerator())
                .supply(Binding.fieldBinding(BookDto.class, "cutterNumber"), new CutterNumberGenerator())
                .supply(Binding.fieldBinding(BookDto.class, "description"), new DescriptionGenerator())
                .supply(Binding.fieldBinding(BookDto.class, "pubDate"), new PubDateGenerator())
                .supply(Binding.fieldBinding(BookDto.class, "pubHouse"), new PubHouseGenerator())
                .supply(Binding.fieldBinding(BookDto.class, "title"), new TitleGenerator())
                .supply(Binding.fieldBinding(BookDto.class, "deweyDecimalCode"), new DeweyDecimalCodeGenerator())
                .supply(Binding.fieldBinding(BookDto.class, "availability"), new AvailabilityGenerator())
                .supply(Binding.fieldBinding(BookDto.class, "authorList"), () -> List.of(authorDto))
                .supply(Binding.fieldBinding(BookDto.class, "tagList"), () -> List.of(tagDto))
                .toModel();
    }


    public static Model<Book> bookModel() {
        Author author = Instancio.of(LewisCarrollModel.authorModel()).create();
        Tag tag = Instancio.of(FantasyTagModel.tagModel()).create();
        return Instancio.of(Book.class)
                .supply(Binding.fieldBinding(Book.class, "id"),new BookIdGenerator())
                .supply(Binding.fieldBinding(Book.class, "isbn"), new IsbnGenerator())
                .supply(Binding.fieldBinding(Book.class, "cutterNumber"), new CutterNumberGenerator())
                .supply(Binding.fieldBinding(Book.class, "description"), new DescriptionGenerator())
                .supply(Binding.fieldBinding(Book.class, "pubDate"), new PubDateGenerator())
                .supply(Binding.fieldBinding(Book.class, "pubHouse"), new PubHouseGenerator())
                .supply(Binding.fieldBinding(Book.class, "title"), new TitleGenerator())
                .supply(Binding.fieldBinding(Book.class, "deweyDecimalCode"), new DeweyDecimalCodeGenerator())
                .supply(Binding.fieldBinding(Book.class, "availability"), new AvailabilityGenerator())
                .supply(Binding.fieldBinding(Book.class, "authorList"), () -> List.of(author))
                .supply(Binding.fieldBinding(Book.class, "tagList"), () -> List.of(tag))
                .toModel();
    }

    public static Model<CreateBookDto> createBookDtoModel(){
        AuthorDto authorDto = Instancio.of(LewisCarrollModel.authorDtoModel()).create();
        TagDto tagDto = Instancio.of(FantasyTagModel.tagDtoModel()).create();
        return Instancio.of(CreateBookDto.class)
                .supply(Binding.fieldBinding(CreateBookDto.class, "id"), new BookIdGenerator())
                .supply(Binding.fieldBinding(CreateBookDto.class, "isbn"), new IsbnGenerator())
                .supply(Binding.fieldBinding(CreateBookDto.class, "description"), new DescriptionGenerator())
                .supply(Binding.fieldBinding(CreateBookDto.class, "pubDate"), new PubDateGenerator())
                .supply(Binding.fieldBinding(CreateBookDto.class, "pubHouse"), new PubHouseGenerator())
                .supply(Binding.fieldBinding(CreateBookDto.class, "title"), new TitleGenerator())
                .supply(Binding.fieldBinding(CreateBookDto.class, "deweyDecimalCode"), new DeweyDecimalCodeGenerator())
                .supply(Binding.fieldBinding(CreateBookDto.class, "availability"), new AvailabilityGenerator())
                .supply(Binding.fieldBinding(CreateBookDto.class, "authorIdList"), () -> List.of(authorDto.getId()))
                .supply(Binding.fieldBinding(CreateBookDto.class, "tagIdList"), () -> List.of(tagDto.getId()))
                .toModel();
    }
}
