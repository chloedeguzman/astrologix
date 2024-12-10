package com.astrologix.astrology.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AstrologyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Get Zodiac Sign - Valid Input")
    void shouldGetZodiacSignForValidInput() throws Exception {
        mockMvc.perform(get("/api/astrology/zodiac")
                        .param("day", "25")
                        .param("month", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zodiacSign").value("ARIES"))
                .andExpect(jsonPath("$.element").value("Fire"))
                .andExpect(jsonPath("$.modality").value("Cardinal"))
                .andExpect(jsonPath("$.rulingPlanet").value("Mars"))
                .andExpect(jsonPath("$.description").value("Aries is bold, ambitious, and ready to take on any challenge."));
    }

    @Test
    @DisplayName("Get Zodiac Sign - Invalid Day")
    void shouldReturnBadRequestForInvalidDay() throws Exception {
        mockMvc.perform(get("/api/astrology/zodiac")
                        .param("day", "32")
                        .param("month", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.day").value("must be less than or equal to 31"));
    }

    @Test
    @DisplayName("Get Zodiac Sign - Invalid Month")
    void shouldReturnBadRequestForInvalidMonth() throws Exception {
        mockMvc.perform(get("/api/astrology/zodiac")
                        .param("day", "25")
                        .param("month", "13")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.month").value("must be less than or equal to 12"));
    }

    @Test
    @DisplayName("Get Zodiac Sign - Both Invalid")
    void shouldReturnBadRequestForBothInvalidInputs() throws Exception {
        mockMvc.perform(get("/api/astrology/zodiac")
                        .param("day", "32")
                        .param("month", "13")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.day").value("must be less than or equal to 31"))
                .andExpect(jsonPath("$.month").value("must be less than or equal to 12"));
    }
}
