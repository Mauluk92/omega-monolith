package it.aleph.omegamonolith.instancio.generator.loan;

import org.instancio.Generator;

import java.time.Instant;
import java.util.Date;

public class PastStartDateGenerator implements Generator<Date> {

    private final static Long DAY_IN_MILLISECONDS = 86400L * 1000L;
    private final static Long NUMBER_OF_DAYS = 2L;

    @Override
    public Date generate() {
        return Date.from(Instant.now().minusMillis(DAY_IN_MILLISECONDS * NUMBER_OF_DAYS));
    }
}
