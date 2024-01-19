package it.aleph.omegamonolith.cutter.calculator.impl;

import it.aleph.omegamonolith.cutter.calculator.CutterCalculator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;
@Getter
@Setter
@Component
public class BasicCutterCalculator implements CutterCalculator {

    private Map<Character,Map<Character, Character>> cutterTableMapping;
    private Map<Character, Character> cutterTableExpansionMapping;
    @Override
    public String calculate(String accessPoint, Integer expansionPrecision) {
        StringBuilder cutterNumber = new StringBuilder(String.valueOf(accessPoint.charAt(0)));
        Map<Character, Character> mapping = cutterTableMapping.get(cutterNumber.charAt(0));
        cutterNumber.append(mapping.get(accessPoint.charAt(1)));
        for (int i = 2; i <= expansionPrecision; i++){
            cutterNumber.append(cutterTableExpansionMapping.get(accessPoint.charAt(i)));
        }
        return cutterNumber.toString();

    }
}
