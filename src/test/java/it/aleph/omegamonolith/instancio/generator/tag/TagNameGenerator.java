package it.aleph.omegamonolith.instancio.generator.tag;

import org.instancio.Generator;

public class TagNameGenerator implements Generator<String> {

    @Override
    public String generate() {
        return "Fantasy";
    }
}
