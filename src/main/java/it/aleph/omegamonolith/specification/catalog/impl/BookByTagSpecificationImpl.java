package it.aleph.omegamonolith.specification.catalog.impl;

import it.aleph.omegamonolith.dto.catalog.book.SearchBooksDto;
import it.aleph.omegamonolith.model.catalog.Book;
import it.aleph.omegamonolith.model.catalog.Tag;
import it.aleph.omegamonolith.specification.catalog.BookSpecificationBuilder;
import it.aleph.omegamonolith.specification.SpecificationBuilder;
import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BookByTagSpecificationImpl implements BookSpecificationBuilder, Specification<Book> {

    private SearchBooksDto searchBooksDto;
    private final static String TAG_FIELD_IN_BOOK = "tagList";
    private final static String TAG_FIELD = "id";

    @Override
    public SpecificationBuilder<SearchBooksDto, Book> setFilter(SearchBooksDto searchBooksDto) {
        this.searchBooksDto = searchBooksDto;
        return this;
    }
    @Override
    public Predicate toPredicate(@Nonnull Root<Book> root, @Nonnull CriteriaQuery<?> query, @Nonnull CriteriaBuilder criteriaBuilder) {
        if(Objects.nonNull(searchBooksDto.getTagId())){
            Join<Book, Tag> booksByTag = root.join(TAG_FIELD_IN_BOOK);
            return criteriaBuilder.equal(booksByTag.get(TAG_FIELD), searchBooksDto.getTagId());
        }
        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    @Override
    public Specification<Book> build() {
        return this;
    }
}
