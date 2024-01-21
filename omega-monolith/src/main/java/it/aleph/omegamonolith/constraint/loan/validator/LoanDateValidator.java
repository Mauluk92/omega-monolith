package it.aleph.omegamonolith.constraint.loan.validator;

import it.aleph.omegamonolith.constraint.loan.annotations.LoanDateConstraint;
import it.aleph.omegamonolith.dto.loan.LoanDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LoanDateValidator implements ConstraintValidator<LoanDateConstraint, LoanDto> {


    @Override
    public void initialize(LoanDateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LoanDto loanDto, ConstraintValidatorContext constraintValidatorContext) {
        return loanDto.getStartDate().before(loanDto.getEndDate());
    }
}
