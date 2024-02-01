package it.aleph.omegamonolith.specification;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T,E> {

    SpecificationBuilder<T, E> setFilter(T filterRequest);

    Specification<E> build();

}
