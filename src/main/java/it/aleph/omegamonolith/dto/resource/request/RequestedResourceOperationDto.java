package it.aleph.omegamonolith.dto.resource.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RequestedResourceOperationDto {

    @NotNull
    private RequestedTypeOperationDto requestedTypeOperationDto;
    @NotNull
    private String resourceType;
    private String additionalInformation;
    @NotNull
    private Boolean valid;
    @NotNull
    private Map<String, Object> resource;
    @NotNull
    private String address;
    @NotNull
    private String associatedUser;
    @NotNull
    private Boolean executed;

}
