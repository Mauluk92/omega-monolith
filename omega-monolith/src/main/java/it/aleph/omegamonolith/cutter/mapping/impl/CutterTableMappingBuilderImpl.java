package it.aleph.omegamonolith.cutter.mapping.impl;

import it.aleph.omegamonolith.cutter.mapping.CutterTableMappingBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class CutterTableMappingBuilderImpl implements CutterTableMappingBuilder {

    private final Map<Character, Character> mapping = new HashMap<>();
    private final static String CHARACTER_LIST = "abcdefghijklmnopqrstuvwxyz";

    @Override
    public CutterTableMappingBuilder range(Character start, Character end, Character value) {
        char[] characters = CHARACTER_LIST.toCharArray();
        Map<Character, Character> mappingTarget = new HashMap<>();
        int lastIndex = 0;
        while(characters[lastIndex] != end){
            mappingTarget.put(characters[lastIndex], value);
            lastIndex++;
        }
        this.mapping.putAll(mappingTarget);
        return this;
    }

    @Override
    public CutterTableMappingBuilder singleCharacter(Character charTarget, Character target) {
        this.mapping.put(charTarget, target);
        return this;
    }

    @Override
    public Map<Character, Character> build() {
        return this.mapping;
    }
}
