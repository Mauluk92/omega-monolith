package it.aleph.omegamonolith.providers.controller.loan;

import it.aleph.omegamonolith.dto.loan.LoanDto;
import it.aleph.omegamonolith.instancio.model.LoanAliceInWonderlandModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class GetLoanControllerProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        LoanDto loanObtained = Instancio.of(LoanAliceInWonderlandModel.provideLoanDtoModel()).create();
        Long loanId = loanObtained.getId();
        return Stream.of(Arguments.of(loanId, loanObtained));
    }
}
