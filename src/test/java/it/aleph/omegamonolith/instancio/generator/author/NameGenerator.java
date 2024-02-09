package it.aleph.omegamonolith.instancio.generator.author;

import org.instancio.Generator;

public class NameGenerator implements Generator<String> {


    @Override
    public String generate() {
        return "Lewis Carroll";
    }
}
