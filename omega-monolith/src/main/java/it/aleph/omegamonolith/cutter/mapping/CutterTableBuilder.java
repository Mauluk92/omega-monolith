package it.aleph.omegamonolith.cutter.mapping;

import java.util.Map;

public interface CutterTableBuilder<T> {

    CutterTableBuilder<T> range(String start, String end, T value);
    CutterTableBuilder<T> singleCharacter(String charTarget, T target);

    Map<String, T> build();
}
