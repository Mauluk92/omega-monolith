package it.aleph.omegamonolith.cutter.mapping;

import java.util.Map;

public interface CutterTableMappingBuilder {

    CutterTableMappingBuilder range(Character start, Character end, Character value);
    CutterTableMappingBuilder singleCharacter(Character charTarget, Character target);

    Map<Character, Character> build();
}
