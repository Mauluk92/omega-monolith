package it.aleph.omegamonolith.providers.service.catalog.book;

import it.aleph.omegamonolith.callnumber.calculator.CallNumberResult;
import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.catalog.book.CreateBookDto;
import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import it.aleph.omegamonolith.instancio.model.AliceInWonderlandModel;
import it.aleph.omegamonolith.instancio.model.FantasyTagModel;
import it.aleph.omegamonolith.instancio.model.LewisCarrollModel;
import it.aleph.omegamonolith.model.catalog.Book;
import org.instancio.Binding;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

public class AddBookServiceProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        CreateBookDto request = Instancio.of(AliceInWonderlandModel.createBookDtoModel()).create();
        BookDto mappedRequest = Instancio.of(AliceInWonderlandModel.bookDtoModel())
                .ignore(Binding.fieldBinding(BookDto.class, "id"))
                .ignore(Binding.fieldBinding(BookDto.class, "cutterNumber"))
                .ignore(Binding.fieldBinding(BookDto.class, "authorList"))
                .ignore(Binding.fieldBinding(BookDto.class, "tagList"))
                .create();
        Book mappedEntityRequest = Instancio.of(AliceInWonderlandModel.bookModel())
                .ignore(Binding.fieldBinding(Book.class, "id"))
                .create();
        Book bookSaved = Instancio.of(AliceInWonderlandModel.bookModel())
                .create();
        BookDto response = Instancio.of(AliceInWonderlandModel.bookDtoModel()).create();
        List<AuthorDto> authorList = List.of(Instancio.of(LewisCarrollModel.authorDtoModel()).create());
        List<TagDto> tagList = List.of(Instancio.of(FantasyTagModel.tagDtoModel()).create());
        CallNumberResult callNumberResult = new CallNumberResult();
        callNumberResult.setCutterNumber(bookSaved.getCutterNumber());

        Arguments arguments = Arguments.of(request, mappedRequest, authorList, tagList, callNumberResult, mappedEntityRequest, bookSaved);

        return Stream.of(arguments);
    }
}
