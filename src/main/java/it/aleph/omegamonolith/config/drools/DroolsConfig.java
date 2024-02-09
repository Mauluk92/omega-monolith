package it.aleph.omegamonolith.config.drools;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;

/**
 * Drools configuration class, with all rules introduced
 */
public class DroolsConfig {


    @Bean
    public KieContainer kieContainer(){
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("drools/cutter/cutterNumberRule.drl"));
        kieFileSystem.write(ResourceFactory.newClassPathResource("drools/cutter/callNumberBuildingRule.drl"));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        return KieServices.get().newKieContainer(kieModule.getReleaseId());
    }
}
