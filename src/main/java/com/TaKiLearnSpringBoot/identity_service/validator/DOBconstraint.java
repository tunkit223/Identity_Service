package com.TaKiLearnSpringBoot.identity_service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DOBValidator.class })
public @interface DOBconstraint {
    String message() default "Invalid date of birth";

    int min();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };


}
