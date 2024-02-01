package it.aleph.omegamonolith.config.callnumber;

import it.aleph.omegamonolith.callnumber.CallNumberTable;
import it.aleph.omegamonolith.config.drools.DroolsConfig;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import({DroolsConfig.class})
public class CallNumberTableConfig {

    @Bean
    public CallNumberTable<String> afterInitialVowels(KieContainer kieContainer) {
        CallNumberTable<String> callNumberTable = new CallNumberTable<>(kieContainer, 5);
        callNumberTable.put("b", "2");
        callNumberTable.put("d", "3");
        callNumberTable.put("[l-m]", "4");
        callNumberTable.put("n", "5");
        callNumberTable.put("p", "6");
        callNumberTable.put("r", "7");
        callNumberTable.put("[s-t]", "8");
        callNumberTable.put("[u-y]", "9");
        return callNumberTable;
    }

    @Bean
    public CallNumberTable<String> afterInitialLetterS(KieContainer kieContainer) {
        CallNumberTable<String> callNumberTable = new CallNumberTable<>(kieContainer, 5);
        callNumberTable.setGroups("ch");
        callNumberTable.put("a", "2");
        callNumberTable.put("ch", "3");
        callNumberTable.put("e", "4");
        callNumberTable.put("[h-i]", "5");
        callNumberTable.put("[m-p]", "6");
        callNumberTable.put("t", "7");
        callNumberTable.put("u", "8");
        callNumberTable.put("[w-z]", "9");
        return callNumberTable;
    }

    @Bean
    public CallNumberTable<String> afterInitialQu(KieContainer kieContainer) {
        CallNumberTable<String> callNumberTable = new CallNumberTable<>(kieContainer, 5);
        callNumberTable.put("a", "3");
        callNumberTable.put("e", "4");
        callNumberTable.put("i", "5");
        callNumberTable.put("o", "6");
        callNumberTable.put("r", "7");
        callNumberTable.put("t", "8");
        callNumberTable.put("y", "9");
        return callNumberTable;
    }

    @Bean
    public CallNumberTable<String> afterInitialAnyOtherConsonants(KieContainer kieContainer) {
        CallNumberTable<String> callNumberTable = new CallNumberTable<>(kieContainer, 5);
        callNumberTable.put("a", "3");
        callNumberTable.put("e", "4");
        callNumberTable.put("i", "5");
        callNumberTable.put("o", "6");
        callNumberTable.put("r", "7");
        callNumberTable.put("u", "8");
        callNumberTable.put("y", "9");
        return callNumberTable;
    }

    @Bean
    public CallNumberTable<String> expansion(KieContainer kieContainer){
        CallNumberTable<String> callNumberTable = new CallNumberTable<>(kieContainer, 5);
        callNumberTable.put("[a-d]", "3");
        callNumberTable.put("[e-h]", "4");
        callNumberTable.put("[i-l]", "5");
        callNumberTable.put("[m-o]", "6");
        callNumberTable.put("[p-s]", "7");
        callNumberTable.put("[t-v]", "8");
        callNumberTable.put("[w-z]", "9");
        return callNumberTable;
    }

    @Bean
    public CallNumberTable<CallNumberTable<String>> callNumberTable(KieContainer kieContainer){
        CallNumberTable<CallNumberTable<String>> callNumberTable = new CallNumberTable<>(kieContainer, 5);
        callNumberTable.put("[aeiouy]", afterInitialVowels(kieContainer));
        callNumberTable.put("s", afterInitialLetterS(kieContainer));
        callNumberTable.put("qu", afterInitialQu(kieContainer));
        callNumberTable.put("[^aeiouysq]", afterInitialAnyOtherConsonants(kieContainer));
        callNumberTable.putExpansion(expansion(kieContainer));
        return callNumberTable;
    }


}
