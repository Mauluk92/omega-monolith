package it.aleph.omegamonolith.instancio.generator.author;

import org.instancio.Generator;

public class BiographyGenerator implements Generator<String> {

    @Override
    public String generate() {
        return "Famous logician and writer";
    }
}
