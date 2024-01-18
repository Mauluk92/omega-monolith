package it.aleph.omegamonolith.dto.catalog;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchBookDto {
    @NotNull
    private Boolean status;
}
