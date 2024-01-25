package it.aleph.omegamonolith.cutter.model;

import it.aleph.omegamonolith.cutter.error.CutterError;
import it.aleph.omegamonolith.model.catalog.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CutterNumberFact {

    private String cutterNumber;
    private String accessPoint;
    private String isbn;
    private Book bookWithSameCutterNumber;
    private Map<String, String> cutterNumberMapping;
    private Map<String, String> cutterNumberExpansionMapping;
    private CutterError cutterError;
}
