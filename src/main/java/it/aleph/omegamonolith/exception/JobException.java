package it.aleph.omegamonolith.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Getter
@Setter
public class JobException extends RuntimeException{

    private String message;
    private List<String> errorMessages;
}
