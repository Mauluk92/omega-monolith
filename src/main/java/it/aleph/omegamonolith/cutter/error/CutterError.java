package it.aleph.omegamonolith.cutter.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CutterError {

    private String message;
    private String cause;
}
