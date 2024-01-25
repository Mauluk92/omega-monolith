package it.aleph.omegamonolith.cutter.mapping.impl;

import it.aleph.omegamonolith.cutter.mapping.CutterTableBuilder;

import java.util.HashMap;
import java.util.Map;

public class CutterGlobalTableBuilder implements CutterTableBuilder<Map<String, String>> {

    private final Map<String, Map<String, String>> mapping = new HashMap<>();
    private final static String CHARACTER_LIST = "abcdefghijklmnopqrstuvwxyz";


    @Override
    public CutterTableBuilder<Map<String, String>> range(String start, String end, Map<String, String> value) {
        char[] characters = CHARACTER_LIST.toCharArray();
        int lastIndex = 0;
        int firstIndex = 0;
        for(int indexChar = 0; indexChar < characters.length; indexChar++){
            if(String.valueOf(characters[indexChar]).equals(start)){
                firstIndex = indexChar;
            } else if (String.valueOf(characters[indexChar]).equals(end)) {
                lastIndex = indexChar;
            }
        }
        for(int index = firstIndex; index <= lastIndex; index++){
            this.mapping.put(String.valueOf(characters[index]), value);
        }
        return this;
    }

    @Override
    public CutterTableBuilder<Map<String, String>> singleCharacter(String charTarget, Map<String, String> target) {
        this.mapping.put(String.valueOf(charTarget), target);
        return this;
    }

    @Override
    public Map<String, Map<String, String>> build() {
        return this.mapping;
    }
}
