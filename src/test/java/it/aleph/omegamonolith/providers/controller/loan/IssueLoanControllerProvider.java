package it.aleph.omegamonolith.providers.controller.loan;

import it.aleph.omegamonolith.dto.loan.LoanDto;
import it.aleph.omegamonolith.dto.loan.LoanStatusDto;
import it.aleph.omegamonolith.instancio.model.LoanAliceInWonderlandModel;
import org.instancio.Binding;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class IssueLoanControllerProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        LoanDto issuedLoan = Instancio.of(LoanAliceInWonderlandModel.provideLoanDtoModel()).
                ignore(Binding.fieldBinding(LoanDto.class, "id")).
                ignore(Binding.fieldBinding(LoanDto.class, "loanStatus")).
                create();
        LoanDto savedLoan = Instancio.of(LoanAliceInWonderlandModel.provideLoanDtoModel())
                .supply(Binding.fieldBinding(LoanDto.class, "loanStatus"), () -> LoanStatusDto.ISSUED)
                .create();
        return Stream.of(Arguments.of(issuedLoan, savedLoan));
    }
}
