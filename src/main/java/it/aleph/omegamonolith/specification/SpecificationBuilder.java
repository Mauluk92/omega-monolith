package it.aleph.omegamonolith.specification;

import org.springframework.data.jpa.domain.Specification;

/**
 * A specification Builder interface used to instantiate a JPA Specification
 * @param <T> The type of Filter DTO, such as SearchBooksDto
 * @param <E> The entity on which the specification builded acts upon
 */
public interface SpecificationBuilder<T,E> {

    SpecificationBuilder<T, E> setFilter(T filterRequest);

    Specification<E> build();

}
