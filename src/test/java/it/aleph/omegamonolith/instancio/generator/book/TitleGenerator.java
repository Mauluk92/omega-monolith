package it.aleph.omegamonolith.instancio.generator.book;

import org.instancio.Generator;

public class TitleGenerator implements Generator<String> {

    @Override
    public String generate() {
        return "Alice in Wonderland";
    }
}
