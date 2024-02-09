package it.aleph.omegamonolith.instancio.generator.book;

import org.instancio.Generator;

public class DescriptionGenerator implements Generator<String> {

    @Override
    public String generate() {
        return "Famous work by logician-writer Lewis Carroll";
    }
}
