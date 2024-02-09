package it.aleph.omegamonolith.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

/**
 * Api error pojo class used for transmitting errors
 */
@Builder
@Getter
@Setter
public class OmegaAPIError {

    private Instant timestamp;
    private Integer status;
    private String message;
    private String path;
    private List<String> errorMessages;
}
