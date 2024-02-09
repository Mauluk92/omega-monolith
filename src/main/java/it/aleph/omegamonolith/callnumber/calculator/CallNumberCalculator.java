package it.aleph.omegamonolith.callnumber.calculator;

import it.aleph.omegamonolith.dto.catalog.book.BookDto;

/**
 * CallNumber calculator interface. Each calculator must implement
 * this interface which specifies that it will accept a Book domain model business object
 * and returns a CallNumberResult. A default implementation is provided
 * @see it.aleph.omegamonolith.callnumber.calculator.impl.DefaultCallNumberCalculator
 */
@FunctionalInterface
public interface CallNumberCalculator {
    CallNumberResult calculate(BookDto bookDto);
}
