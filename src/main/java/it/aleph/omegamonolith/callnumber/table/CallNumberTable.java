package it.aleph.omegamonolith.callnumber.table;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.map.AbstractHashedMap;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.List;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A standard call number table, which represents the mapping between a value inside access point,
 * and a numeric value (or another mapping yet, since a Cutter Number Table may contain other
 * subtables).
 * @see <a href="https://www.loc.gov/aba/pcc/053/table.html">Cutter Table</a>
 * @param <T> The type value of the cutter table, which should ideally be another
 *           cutter table or a String
 */

@Getter
@Setter
public class CallNumberTable<T> extends AbstractHashedMap<String, T> {

    private final static String CHARACTER_LIST = "abcdefghijklmnopqrstuvwxyz";
    private final KieContainer kieContainer;
    private String groups;


    public CallNumberTable(KieContainer kieContainer, int capacity){
        super(capacity);
        this.kieContainer = kieContainer;
    }
    @Override
    public T put(String key, T value) {
        Pattern pattern = Pattern.compile(key);
        Matcher matcher = pattern.matcher(CHARACTER_LIST);
        List<String> resultList = matcher.results().map(MatchResult::group).toList();
        CallNumberFact fact = new CallNumberFact();
        fact.setMatchList(resultList);
        fact.setKeyRegex(key);

        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("groups", groups);
        kieSession.insert(fact);
        kieSession.fireAllRules();
        kieSession.dispose();
        fact.getMatchList().forEach(m -> super.put(m, value));
        Optional.ofNullable(fact.getGroupMatch()).ifPresentOrElse(g -> super.put(g, value),
                () -> fact.getMatchList().forEach(m -> super.put(m, value)));
        return value;
    }

    public void putExpansion(T value){
        super.put("expansion", value);
    }
}
