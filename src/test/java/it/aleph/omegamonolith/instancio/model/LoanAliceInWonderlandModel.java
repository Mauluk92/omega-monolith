package it.aleph.omegamonolith.instancio.model;

import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.loan.LoanDto;
import it.aleph.omegamonolith.dto.loan.LoanStatusDto;
import it.aleph.omegamonolith.instancio.generator.loan.EndDateGenerator;
import it.aleph.omegamonolith.instancio.generator.loan.IssuedTimestampGenerator;
import it.aleph.omegamonolith.instancio.generator.loan.LoanIdGenerator;
import it.aleph.omegamonolith.instancio.generator.loan.StartDateGenerator;
import it.aleph.omegamonolith.model.catalog.Book;
import it.aleph.omegamonolith.model.loan.Loan;
import it.aleph.omegamonolith.model.loan.LoanStatus;
import org.instancio.Binding;
import org.instancio.Instancio;
import org.instancio.Model;

public class LoanAliceInWonderlandModel {


    public static Model<LoanDto> provideLoanDtoModel(){
        BookDto associatedBook = Instancio.of(AliceInWonderlandModel.bookDtoModel()).create();
        Long bookId = associatedBook.getId();
        return Instancio.of(LoanDto.class)
                .supply(Binding.fieldBinding(LoanDto.class, "id"), new LoanIdGenerator())
                .supply(Binding.fieldBinding(LoanDto.class, "startDate"), new StartDateGenerator())
                .supply(Binding.fieldBinding(LoanDto.class, "endDate"), new EndDateGenerator())
                .supply(Binding.fieldBinding(LoanDto.class, "issuedTimestamp"), new IssuedTimestampGenerator())
                .supply(Binding.fieldBinding(LoanDto.class, "associatedBook"),() -> associatedBook)
                .supply(Binding.fieldBinding(LoanDto.class, "bookId"), () -> bookId)
                .supply(Binding.fieldBinding(LoanDto.class, "loanStatus"), () -> LoanStatusDto.ACCEPTED)
                .toModel();
    }

    public static Model<Loan> provideLoanModel(){
        Book associatedBook = Instancio.of(AliceInWonderlandModel.bookModel()).create();
        Long bookId = associatedBook.getId();
        return Instancio.of(Loan.class)
                .supply(Binding.fieldBinding(Loan.class, "id"), new LoanIdGenerator())
                .supply(Binding.fieldBinding(Loan.class, "startDate"), new StartDateGenerator())
                .supply(Binding.fieldBinding(Loan.class, "endDate"), new EndDateGenerator())
                .supply(Binding.fieldBinding(Loan.class, "issuedTimestamp"), new IssuedTimestampGenerator())
                .supply(Binding.fieldBinding(Loan.class, "associatedBook"),() -> associatedBook)
                .supply(Binding.fieldBinding(Loan.class, "bookId"), () -> bookId)
                .supply(Binding.fieldBinding(Loan.class, "loanStatus"), () -> LoanStatus.ACCEPTED)
                .toModel();
    }
}
