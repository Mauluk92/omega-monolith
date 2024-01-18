package it.aleph.omegamonolith.specification.catalog.impl;

import it.aleph.omegamonolith.dto.catalog.SearchTagsDto;
import it.aleph.omegamonolith.model.catalog.Tag;
import it.aleph.omegamonolith.specification.catalog.SpecificationBuilder;
import it.aleph.omegamonolith.specification.catalog.TagSpecificationBuilder;
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
public class TagByNameSpecificationImpl implements Specification<Tag>, TagSpecificationBuilder {

    private final static String TAG_FIELD = "tag";
    private SearchTagsDto searchTagsDto;

    @Override
    public SpecificationBuilder<SearchTagsDto, Tag> setFilter(SearchTagsDto filterRequest) {
        searchTagsDto = filterRequest;
        return this;
    }

    @Override
    public Predicate toPredicate(@Nonnull Root<Tag> root, @Nonnull CriteriaQuery<?> query, @Nonnull CriteriaBuilder criteriaBuilder) {
        return Objects.nonNull(searchTagsDto.getTag()) ?
                criteriaBuilder.like(root.get(TAG_FIELD), "%" + searchTagsDto.getTag() + "%") :
                criteriaBuilder.conjunction();
    }

    @Override
    public Specification<Tag> build() {
        return this;
    }
}
