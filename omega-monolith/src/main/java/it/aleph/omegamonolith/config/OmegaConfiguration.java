package it.aleph.omegamonolith.config;

import it.aleph.omegamonolith.config.catalog.batch.CreateBookBatchConfiguration;
import it.aleph.omegamonolith.config.kafka.KafkaConfiguration;
import it.aleph.omegamonolith.config.loan.LoanConfiguration;
import it.aleph.omegamonolith.config.loan.drools.LoanKnowledgeBaseConfiguration;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@Import({LoanConfiguration.class,
        LoanKnowledgeBaseConfiguration.class,
        CreateBookBatchConfiguration.class,
        KafkaConfiguration.class
        })
@EnableBatchProcessing
@EnableKafka
public class OmegaConfiguration {

}
