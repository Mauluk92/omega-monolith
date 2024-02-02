package it.aleph.omegamonolith.callnumber.calculator;

import it.aleph.omegamonolith.dto.catalog.book.BookDto;

@FunctionalInterface
public interface CallNumberCalculator {
    CallNumberResult calculate(BookDto bookDto);
}
