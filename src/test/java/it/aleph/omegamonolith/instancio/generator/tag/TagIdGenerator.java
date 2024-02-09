package it.aleph.omegamonolith.instancio.generator.tag;

import org.instancio.Generator;

public class TagIdGenerator implements Generator<Long> {

    @Override
    public Long generate() {
        return 1L;
    }
}
