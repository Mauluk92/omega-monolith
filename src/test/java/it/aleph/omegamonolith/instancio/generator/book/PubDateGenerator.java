package it.aleph.omegamonolith.instancio.generator.book;

import org.instancio.Generator;

import java.util.Date;

public class PubDateGenerator implements Generator<Date> {

    @Override
    public Date generate() {
        return new Date();
    }
}
