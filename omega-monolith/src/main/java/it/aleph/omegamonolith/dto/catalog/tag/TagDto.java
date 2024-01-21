package it.aleph.omegamonolith.dto.catalog.tag;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDto {

    @NotNull
    private String name;
    @NotNull
    private String description;

}
