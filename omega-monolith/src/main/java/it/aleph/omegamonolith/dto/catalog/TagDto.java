package it.aleph.omegamonolith.dto.catalog;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDto {

    @NotNull
    private String tag;
    @NotNull
    private String description;

}
