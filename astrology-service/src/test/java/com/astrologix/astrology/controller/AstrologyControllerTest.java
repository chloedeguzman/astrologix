package com.astrologix.astrology.controller;

import com.astrologix.astrology.AstrologyServiceApplication;
import com.astrologix.astrology.dto.ZodiacResponse;
import com.astrologix.astrology.service.AstrologyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AstrologyController.class)
@ContextConfiguration(classes = AstrologyServiceApplication.class)

class AstrologyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AstrologyService astrologyService;

    @Test
    @DisplayName("Get Zodiac Sign - Valid Input")
    void shouldGetZodiacSignForValidInput() throws Exception {
        when(astrologyService.getZodiacDetails(anyInt(), anyInt()))
                .thenReturn(new ZodiacResponse("ARIES", "Fire", "Cardinal", "Mars", "Dynamic and passionate"));

        mockMvc.perform(get("/api/astrology/zodiac")
                        .param("date", "03-25")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zodiacSign").value("ARIES"))
                .andExpect(jsonPath("$.element").value("Fire"))
                .andExpect(jsonPath("$.modality").value("Cardinal"))
                .andExpect(jsonPath("$.rulingPlanet").value("Mars"))
                .andExpect(jsonPath("$.description").value("Dynamic and passionate"));
    }

    @Test
    @DisplayName("Get Zodiac Sign - Missing Date Parameter")
    void shouldReturnBadRequestForMissingDate() throws Exception {
        mockMvc.perform(get("/api/astrology/zodiac")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.message").value("Required request parameter is missing"))
                .andExpect(jsonPath("$.details.date").value("Required request parameter 'date' for method parameter type String is not present"));
    }
}
