package it.aleph.omegamonolith.dto.catalog;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class AssociateBookDto {
    @NotEmpty
    @NotNull
    private List<Long> authorIdList;
    @NotEmpty
    @NotNull
    private List<Long> tagIdList;
}
