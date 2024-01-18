package it.aleph.omegamonolith.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class NotFoundException extends RuntimeException{
    private List<Long> idListNotFound;
    private String message;
}
