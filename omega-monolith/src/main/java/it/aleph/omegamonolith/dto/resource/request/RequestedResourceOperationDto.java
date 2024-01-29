package it.aleph.omegamonolith.dto.resource.request;

import it.aleph.omegamonolith.model.user.User;
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
    private User associatedUser;

}
