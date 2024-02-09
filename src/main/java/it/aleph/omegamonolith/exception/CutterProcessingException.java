package it.aleph.omegamonolith.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Cutter Number calculation exception
 */
@Getter
@Setter
@Builder
public class CutterProcessingException extends RuntimeException {

    private List<String> messageCauseList;
    private String message;
}
