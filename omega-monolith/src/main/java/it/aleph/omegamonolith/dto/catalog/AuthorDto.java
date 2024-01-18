package it.aleph.omegamonolith.dto.catalog;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDto {

    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String biography;

}
