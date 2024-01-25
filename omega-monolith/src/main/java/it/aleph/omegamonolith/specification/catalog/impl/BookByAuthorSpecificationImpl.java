package it.aleph.omegamonolith.specification.catalog.impl;

import it.aleph.omegamonolith.dto.catalog.book.SearchBooksDto;
import it.aleph.omegamonolith.model.catalog.Author;
import it.aleph.omegamonolith.model.catalog.Book;
import it.aleph.omegamonolith.specification.catalog.BookSpecificationBuilder;
import it.aleph.omegamonolith.specification.catalog.SpecificationBuilder;
import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BookByAuthorSpecificationImpl implements BookSpecificationBuilder, Specification<Book> {

    private SearchBooksDto searchBooksDto;
    private final static String AUTHOR_FIELD_IN_BOOK = "authorList";
    private final static String AUTHOR_FIELD = "id";

    @Override
    public SpecificationBuilder<SearchBooksDto, Book> setFilter(SearchBooksDto searchBooksDto) {
        this.searchBooksDto = searchBooksDto;
        return this;
    }

    @Override
    public Predicate toPredicate(@Nonnull Root<Book> root,@Nonnull CriteriaQuery<?> query, @Nonnull CriteriaBuilder criteriaBuilder) {
        if(Objects.nonNull(searchBooksDto.getAuthorId())){
            Join<Book, Author> booksByAuthor = root.join(AUTHOR_FIELD_IN_BOOK);
            return criteriaBuilder.equal(booksByAuthor.get(AUTHOR_FIELD), searchBooksDto.getAuthorId());
        }
        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    @Override
    public Specification<Book> build() {
        return this;
    }

}
