package it.aleph.omegamonolith.instancio.generator.author;

import org.instancio.Generator;

public class AuthorIdGenerator implements Generator<Long> {


    @Override
    public Long generate() {
        return 1L;
    }
}
