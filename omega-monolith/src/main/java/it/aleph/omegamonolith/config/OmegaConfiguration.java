package it.aleph.omegamonolith.config;

import it.aleph.omegamonolith.config.callnumber.CallNumberTableConfig;
import it.aleph.omegamonolith.config.kafka.KafkaConfiguration;
import it.aleph.omegamonolith.config.loan.LoanConfiguration;
import it.aleph.omegamonolith.config.resource.batch.job.ResourceJobConfiguration;
import it.aleph.omegamonolith.config.security.SecurityConfig;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@Import({LoanConfiguration.class,
        KafkaConfiguration.class,
        SecurityConfig.class,
        ResourceJobConfiguration.class,
        CallNumberTableConfig.class
        })
@EnableBatchProcessing
@EnableKafka
@EnableWebSecurity
public class OmegaConfiguration  {

}
