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
                        .param("date", "03-25")
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
                        .param("date", "03-32")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.date").value("Invalid date provided"));
    }

    @Test
    @DisplayName("Get Zodiac Sign - Invalid Month")
    void shouldReturnBadRequestForInvalidMonth() throws Exception {
        mockMvc.perform(get("/api/astrology/zodiac")
                        .param("date", "13-25")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.date").value("Invalid date provided"));
    }

    @Test
    @DisplayName("Get Zodiac Sign - Both Invalid")
    void shouldReturnBadRequestForBothInvalidInputs() throws Exception {
        mockMvc.perform(get("/api/astrology/zodiac")
                        .param("date", "13-32")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.date").value("Invalid date provided"));
    }

    @Test
    @DisplayName("Get Zodiac Sign - Invalid Date Format")
    void shouldReturnBadRequestForInvalidDateFormat() throws Exception {
        mockMvc.perform(get("/api/astrology/zodiac")
                        .param("date", "invalid-format")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.date").value("Invalid date provided"));
    }
}
