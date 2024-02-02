package it.aleph.omegamonolith.callnumber.calculator;

import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallNumberResult {

    private String cutterNumber;
    private String callNumber;
}
