package it.aleph.omegamonolith.dto.catalog;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchAuthorsDto {
    private String name;
}
