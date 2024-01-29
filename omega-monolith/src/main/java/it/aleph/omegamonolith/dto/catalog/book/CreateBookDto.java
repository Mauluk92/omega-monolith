package it.aleph.omegamonolith.dto.catalog.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateBookDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
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
}
