package it.aleph.omegamonolith.callnumber;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CallNumberFact {

    private String keyRegex;
    private List<String> matchList;
    private String groupMatch;
}
