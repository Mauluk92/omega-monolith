package it.aleph.omegamonolith.instancio.generator.book;

import org.instancio.Generator;

public class DeweyDecimalCodeGenerator implements Generator<String> {

    @Override
    public String generate() {
        return "100.00";
    }
}
