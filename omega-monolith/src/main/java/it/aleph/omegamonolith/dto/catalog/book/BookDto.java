package it.aleph.omegamonolith.dto.catalog.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.aleph.omegamonolith.dto.catalog.author.AuthorDto;
import it.aleph.omegamonolith.dto.catalog.tag.TagDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BookDto {

    @NotNull
    private String title;
    @NotNull
    @Pattern(regexp = "^\\d{13}$", message = "ISBN should have exactly 10 numbers")
    private String isbn;
    @NotNull
    @Pattern(regexp = "^\\d{3}\\.\\d+$", message = "Dewey Decimal Code should have the following format: xxx.xx...")
    private String deweyDecimalCode;
    @NotNull
    private String description;
    @NotNull
    private Date pubDate;
    @NotNull
    private String pubHouse;
    @NotNull
    private Boolean availability;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String cutterNumber;


    private List<AuthorDto> authorList;
    private List<TagDto> tagList;
}
