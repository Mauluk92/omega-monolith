package it.aleph.omegamonolith.dto.catalog.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchBooksDto {

    private Long tagId;
    private Long authorId;
    private String title;
    private String address;
}
