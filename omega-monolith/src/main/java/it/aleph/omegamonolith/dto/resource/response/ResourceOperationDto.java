package it.aleph.omegamonolith.dto.resource.response;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
public class ResourceOperationDto {

    @NotNull
    private TypeOperationDto typeOperationDto;
    @NotNull
    private OperationStatusDto operationStatusDto;
    @NotNull
    private Map<String, Object> resource;
    private String additionalInfo;
    private String address;
    @NotNull
    private Instant timestamp;
}
