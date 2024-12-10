package com.astrologix.astrology.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Define the annotation
@Constraint(validatedBy = ValidDateValidator.class)
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
    String message() default "Invalid date provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
