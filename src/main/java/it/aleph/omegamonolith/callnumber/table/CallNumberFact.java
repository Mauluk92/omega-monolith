package it.aleph.omegamonolith.callnumber.table;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Utility POJO class used to encapsulates all information needed for
 * further processing of a callNumber
 */
@Getter
@Setter
public class CallNumberFact {

    private String keyRegex;
    private List<String> matchList;
    private String groupMatch;
}
