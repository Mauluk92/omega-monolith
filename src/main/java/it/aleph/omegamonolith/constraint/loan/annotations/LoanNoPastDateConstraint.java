package it.aleph.omegamonolith.constraint.loan.annotations;

import it.aleph.omegamonolith.constraint.loan.validator.LoanDateValidator;
import it.aleph.omegamonolith.constraint.loan.validator.NoPastDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = NoPastDateValidator.class)
@Documented
public @interface LoanNoPastDateConstraint {

    String message() default  "Start date of a loan cannot be a past date!";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
