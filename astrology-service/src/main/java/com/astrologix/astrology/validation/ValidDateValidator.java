package com.astrologix.astrology.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.MonthDay;
import java.time.DateTimeException;

public class ValidDateValidator implements ConstraintValidator<ValidDate, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || !value.contains("-")) {
            return false;
        }

        String[] parts = value.split("-");
        if (parts.length != 2) {
            return false;
        }

        try {
            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);
            MonthDay.of(month, day); // Validate the combination
            return true;
        } catch (NumberFormatException | DateTimeException e) {
            return false;
        }
    }
}
