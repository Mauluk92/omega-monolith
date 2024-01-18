package it.aleph.omegamonolith.dto.catalog;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateBookDto {

    @NotNull
    private String title;
    @NotNull
    @Pattern(regexp = "^\\d{13}$", message = "ISBN should have exactly 10 numbers")
    private String isbn;
    @NotNull
    @Pattern(regexp = "^\\d{3}\\.\\d+$", message = "Dewey Decimal Code should have the following format: xxx.xx...")
    private String deweyDecimalCode;
    @NotNull
    private String contentDescription;
    @NotNull
    private Date pubDate;
    @NotNull
    private String pubHouse;
    @NotNull
    private Boolean available;
}
