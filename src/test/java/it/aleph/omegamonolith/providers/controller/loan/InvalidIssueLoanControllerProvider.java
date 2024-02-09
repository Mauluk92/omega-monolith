package it.aleph.omegamonolith.providers.controller.loan;

import it.aleph.omegamonolith.dto.loan.LoanDto;
import it.aleph.omegamonolith.instancio.generator.loan.PastEndDateGenerator;
import it.aleph.omegamonolith.instancio.generator.loan.PastStartDateGenerator;
import it.aleph.omegamonolith.instancio.model.LoanAliceInWonderlandModel;
import org.instancio.Binding;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class InvalidIssueLoanControllerProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        LoanDto invalidLoanPastStartDate = Instancio.of(LoanAliceInWonderlandModel.provideLoanDtoModel())
                .supply(Binding.fieldBinding(LoanDto.class, "startDate"), new PastStartDateGenerator())
                .create();
        LoanDto invalidLoanPastEndDate = Instancio.of(LoanAliceInWonderlandModel.provideLoanDtoModel())
                .supply(Binding.fieldBinding(LoanDto.class, "endDate"), new PastEndDateGenerator())
                .create();

        Arguments pastStartDate = Arguments.of(invalidLoanPastStartDate);
        Arguments pastEndDate = Arguments.of(invalidLoanPastEndDate);

        return Stream.of(pastStartDate, pastEndDate);
    }
}
