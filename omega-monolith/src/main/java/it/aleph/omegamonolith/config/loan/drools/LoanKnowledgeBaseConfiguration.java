package it.aleph.omegamonolith.config.loan.drools;

import it.aleph.omegamonolith.cutter.mapping.CutterTableBuilder;
import it.aleph.omegamonolith.cutter.mapping.impl.CutterGlobalTableBuilder;
import it.aleph.omegamonolith.cutter.mapping.impl.CutterTableBuilderImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Map;

public class LoanKnowledgeBaseConfiguration {


    @Bean
    public KieContainer kieContainer(){
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("drools/cutter/cutterNumberRule.drl"));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        return KieServices.get().newKieContainer(kieModule.getReleaseId());
    }


    private Map<String, String> vowelFirstCharMapping(){
        CutterTableBuilder<String> cutterTableBuilder = new CutterTableBuilderImpl();
        cutterTableBuilder.singleCharacter("b", "2");
        cutterTableBuilder.singleCharacter("d", "3");
        cutterTableBuilder.range("l", "m","4");
        cutterTableBuilder.singleCharacter("n","5");
        cutterTableBuilder.singleCharacter("p","6");
        cutterTableBuilder.singleCharacter("r","7");
        cutterTableBuilder.range("s","t","8");
        cutterTableBuilder.range("u","y", "9");
        return cutterTableBuilder.build();
    }


    private Map<String, String> sFirstCharMapping(){
        CutterTableBuilder<String> cutterTableBuilder = new CutterTableBuilderImpl();
        cutterTableBuilder.singleCharacter("a","2");
        cutterTableBuilder.singleCharacter("ch","3");
        cutterTableBuilder.singleCharacter("e", "4");
        cutterTableBuilder.range("h", "i", "5");
        cutterTableBuilder.range("m", "p", "6");
        cutterTableBuilder.singleCharacter("t", "7");
        cutterTableBuilder.singleCharacter("u", "8");
        return cutterTableBuilder.range("w","z","9").build();

    }


    private Map<String, String> quFirstCharsMapping(){
        CutterTableBuilder<String> cutterTableBuilder = new CutterTableBuilderImpl();
        cutterTableBuilder.singleCharacter("a", "3");
        cutterTableBuilder.singleCharacter("e", "4");
        cutterTableBuilder.singleCharacter("l", "5");
        cutterTableBuilder.singleCharacter("o", "6");
        cutterTableBuilder.singleCharacter("r","7");
        cutterTableBuilder.singleCharacter("t", "8");
        return cutterTableBuilder.singleCharacter("y", "9").build();
    }


    private Map<String, String> anyOtherConsonants(){
        CutterTableBuilder<String> cutterTableBuilder = new CutterTableBuilderImpl();
        cutterTableBuilder.singleCharacter("a", "3");
        cutterTableBuilder.singleCharacter("e", "4");
        cutterTableBuilder.singleCharacter("i", "5");
        cutterTableBuilder.singleCharacter("o","6");
        cutterTableBuilder.singleCharacter("r", "7");
        cutterTableBuilder.singleCharacter("u","8");
        return cutterTableBuilder.singleCharacter("y","9").build();
    }


    private Map<String, String> expansion(){
        CutterTableBuilder<String> cutterTableBuilder = new CutterTableBuilderImpl();
        cutterTableBuilder.range("a","d","3");
        cutterTableBuilder.range("e","h","4");
        cutterTableBuilder.range("i","l", "5");
        cutterTableBuilder.range("m","o", "6");
        cutterTableBuilder.range("p","s","7");
        cutterTableBuilder.range("t","v","8");
        return cutterTableBuilder.range("w","z","9").build();
    }

    @Bean
    public Map<String, Map<String, String>> globalCutterTable(){
        CutterTableBuilder<Map<String, String>> cutterTableBuilder = new CutterGlobalTableBuilder();
        cutterTableBuilder.singleCharacter("a", vowelFirstCharMapping());
        cutterTableBuilder.singleCharacter("e", vowelFirstCharMapping());
        cutterTableBuilder.singleCharacter("i", vowelFirstCharMapping());
        cutterTableBuilder.singleCharacter("o", vowelFirstCharMapping());
        cutterTableBuilder.singleCharacter("u", vowelFirstCharMapping());

        cutterTableBuilder.singleCharacter("q", quFirstCharsMapping());

        cutterTableBuilder.range("b", "d", anyOtherConsonants());
        cutterTableBuilder.range("f", "h", anyOtherConsonants());
        cutterTableBuilder.range("j", "n", anyOtherConsonants());
        cutterTableBuilder.singleCharacter("p", anyOtherConsonants());
        cutterTableBuilder.range("r", "t", anyOtherConsonants());
        cutterTableBuilder.range("v", "z", anyOtherConsonants());
        cutterTableBuilder.singleCharacter("expansion", expansion());
        return cutterTableBuilder.build();
    }


}
