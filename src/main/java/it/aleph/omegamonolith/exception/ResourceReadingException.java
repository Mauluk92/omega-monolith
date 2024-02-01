package it.aleph.omegamonolith.exception;

import lombok.Builder;

@Builder
public class ResourceReadingException extends RuntimeException {

    private String message;
    private String error;
}
