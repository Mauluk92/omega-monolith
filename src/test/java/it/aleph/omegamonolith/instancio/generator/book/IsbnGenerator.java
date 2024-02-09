package it.aleph.omegamonolith.instancio.generator.book;

import org.instancio.Generator;

public class IsbnGenerator implements Generator<String> {

    @Override
    public String generate() {
        return "1234567890123";
    }
}
