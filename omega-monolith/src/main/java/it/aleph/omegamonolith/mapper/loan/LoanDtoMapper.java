package it.aleph.omegamonolith.mapper.loan;

import it.aleph.omegamonolith.dto.loan.LoanDto;
import it.aleph.omegamonolith.dto.loan.LoanStatusDto;
import it.aleph.omegamonolith.model.loan.Loan;
import it.aleph.omegamonolith.model.loan.LoanStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
public interface LoanDtoMapper {
    @Mapping(source="bookId", target="associatedBook.id")
    Loan toEntity(LoanDto loanDto);

    LoanDto toDto(Loan loan);

    LoanStatus toEntity(LoanStatusDto loanStatusDto);
}
