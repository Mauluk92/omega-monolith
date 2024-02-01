package it.aleph.omegamonolith.config.loan;

import it.aleph.omegamonolith.config.loan.batch.LoanBatchConfiguration;
import org.springframework.context.annotation.Import;

@Import({LoanBatchConfiguration.class})
public class LoanConfiguration {
}
