package it.aleph.omegamonolith.config;

import it.aleph.omegamonolith.config.loan.LoanConfiguration;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({LoanConfiguration.class,
        })
@EnableBatchProcessing
public class OmegaConfiguration {

    public final static String BATCH_PREFIX_TABLE = "BATCH_OMEGA";



}
