package com.astrologix.astrology.service;

import com.astrologix.astrology.dto.ZodiacResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class AstrologyServiceTest {

    private final AstrologyService astrologyService = new AstrologyService();

    @ParameterizedTest
    @CsvSource({
            "21, 3, ARIES", "19, 4, ARIES",
            "20, 4, TAURUS", "20, 5, TAURUS",
            "21, 5, GEMINI", "20, 6, GEMINI",
            "21, 6, CANCER", "22, 7, CANCER",
            "23, 7, LEO", "22, 8, LEO",
            "23, 8, VIRGO", "22, 9, VIRGO",
            "23, 9, LIBRA", "22, 10, LIBRA",
            "23, 10, SCORPIO", "21, 11, SCORPIO",
            "22, 11, SAGITTARIUS", "21, 12, SAGITTARIUS",
            "22, 12, CAPRICORN", "19, 1, CAPRICORN",
            "20, 1, AQUARIUS", "18, 2, AQUARIUS",
            "19, 2, PISCES", "20, 3, PISCES"
    })
    @DisplayName("Test all zodiac signs for valid dates")
    void shouldReturnCorrectZodiacSignForValidDates(int day, int month, String expectedSign) {
        ZodiacResponse response = astrologyService.getZodiacDetails(day, month);

        assertNotNull(response);
        assertEquals(expectedSign, response.getZodiacSign());
    }

    @ParameterizedTest
    @CsvSource({
            "32, 3", // Invalid day
            "25, 13" // Invalid month
    })
    @DisplayName("Test invalid dates")
    void shouldThrowExceptionForInvalidDates(int day, int month) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> astrologyService.getZodiacDetails(day, month));
        assertTrue(exception.getMessage().contains("Invalid date provided"));
    }
}
