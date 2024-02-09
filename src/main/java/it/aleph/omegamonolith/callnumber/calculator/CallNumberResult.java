package it.aleph.omegamonolith.callnumber.calculator;

import lombok.Getter;
import lombok.Setter;

/**
 * This POJO encapsulates the results from a call number calculation using a
 * @see CallNumberCalculator
 */
@Getter
@Setter
public class CallNumberResult {

    private String cutterNumber;
    private String callNumber;
}
