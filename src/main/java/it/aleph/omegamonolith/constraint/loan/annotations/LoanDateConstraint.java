package it.aleph.omegamonolith.constraint.loan.annotations;

import it.aleph.omegamonolith.constraint.loan.validator.LoanDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = LoanDateValidator.class)
@Documented
public @interface LoanDateConstraint {

    String message() default  "End date of a loan should be after start date!";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
