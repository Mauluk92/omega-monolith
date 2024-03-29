package it.aleph.omegamonolith.constraint.loan.validator;

import it.aleph.omegamonolith.constraint.loan.annotations.LoanNoPastDateConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * This validator ensure that the given date is not a past date, since no loan can
 * be issued for a time before the current date
 */
@Component
public class NoPastDateValidator implements ConstraintValidator<LoanNoPastDateConstraint, Date> {

    @Override
    public void initialize(LoanNoPastDateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        return date.compareTo(new Date()) >= 0;
    }
}
