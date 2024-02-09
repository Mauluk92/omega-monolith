package it.aleph.omegamonolith.specification.catalog.impl;

import it.aleph.omegamonolith.dto.catalog.book.SearchBooksDto;
import it.aleph.omegamonolith.model.catalog.Book;
import it.aleph.omegamonolith.specification.catalog.BookSpecificationBuilder;
import it.aleph.omegamonolith.specification.SpecificationBuilder;
import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BookByAddressSpecificationImpl implements BookSpecificationBuilder, Specification<Book> {

    private SearchBooksDto searchBooksDto;
    private final static String BOOK_FIELD_FIRST = "deweyDecimalCode";
    private final static String BOOK_FIELD_SECOND = "cutterNumber";
    @Override
    public SpecificationBuilder<SearchBooksDto, Book> setFilter(SearchBooksDto searchBooksDto) {
        this.searchBooksDto = searchBooksDto;
        return this;
    }

    @Override
    public Predicate toPredicate(@Nonnull Root<Book> root, @Nonnull CriteriaQuery<?> query, @Nonnull CriteriaBuilder criteriaBuilder) {
        return Objects.nonNull(searchBooksDto.getAddress()) ?
                criteriaBuilder.equal(criteriaBuilder.concat(root.get(BOOK_FIELD_FIRST), root.get(BOOK_FIELD_SECOND)),criteriaBuilder.literal(searchBooksDto.getAddress())) :
                criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    @Override
    public Specification<Book> build() {
        return this;
    }
}
