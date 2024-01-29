package it.aleph.omegamonolith.exception;

import lombok.Builder;

@Builder
public class ResourceGenerationException extends RuntimeException {

    private String message;
    private String error;
}
