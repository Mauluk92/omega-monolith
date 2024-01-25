package it.aleph.omegamonolith.config;

import it.aleph.omegamonolith.config.loan.LoanConfiguration;
import it.aleph.omegamonolith.config.loan.drools.LoanKnowledgeBaseConfiguration;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({LoanConfiguration.class,
        LoanKnowledgeBaseConfiguration.class
        })
@EnableBatchProcessing
public class OmegaConfiguration {

}
