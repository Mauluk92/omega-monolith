package it.aleph.omegamonolith.specification.catalog.impl;

import it.aleph.omegamonolith.dto.catalog.author.SearchAuthorsDto;
import it.aleph.omegamonolith.model.catalog.Author;
import it.aleph.omegamonolith.specification.catalog.AuthorSpecificationBuilder;
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
public class AuthorByNameSpecificationImpl implements Specification<Author>, AuthorSpecificationBuilder {

    private final static String AUTHOR_FIELD = "name";
    private SearchAuthorsDto searchAuthorsDto;

    @Override
    public SpecificationBuilder<SearchAuthorsDto, Author> setFilter(SearchAuthorsDto filterRequest) {
        searchAuthorsDto = filterRequest;
        return this;
    }

    @Override
    public Predicate toPredicate(@Nonnull Root<Author> root, @Nonnull CriteriaQuery<?> query, @Nonnull CriteriaBuilder criteriaBuilder) {
        return Objects.nonNull(searchAuthorsDto.getName()) ?
                criteriaBuilder.like(root.get(AUTHOR_FIELD), "%" + searchAuthorsDto.getName() + "%") :
                criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    @Override
    public Specification<Author> build() {
        return this;
    }
}
