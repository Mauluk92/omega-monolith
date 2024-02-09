package it.aleph.omegamonolith.instancio.generator.book;

import org.instancio.Generator;

public class BookIdGenerator implements Generator<Long> {
    @Override
    public Long generate() {
        return 1L;
    }
}
