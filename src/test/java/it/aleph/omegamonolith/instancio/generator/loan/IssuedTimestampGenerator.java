package it.aleph.omegamonolith.instancio.generator.loan;

import org.instancio.Generator;

import java.time.Instant;

public class IssuedTimestampGenerator implements Generator<Instant> {
    @Override
    public Instant generate() {
        return Instant.now();
    }
}
