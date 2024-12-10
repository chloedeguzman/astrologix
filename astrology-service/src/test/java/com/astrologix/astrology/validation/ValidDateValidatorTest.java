package com.astrologix.astrology.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidDateValidatorTest {

    private final ValidDateValidator validator = new ValidDateValidator();

    @Test
    void shouldValidateCorrectDate() {
        assertTrue(validator.isValid("03-25", null));
        assertTrue(validator.isValid("02-29", null)); // Leap year valid date
    }

    @Test
    void shouldInvalidateIncorrectDate() {
        assertFalse(validator.isValid("02-30", null));
        assertFalse(validator.isValid("13-01", null));
        assertFalse(validator.isValid("03-00", null));
        assertFalse(validator.isValid("abc", null));
    }
}
