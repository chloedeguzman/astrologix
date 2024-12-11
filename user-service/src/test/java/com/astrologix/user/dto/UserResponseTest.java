package com.astrologix.user.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ZodiacResponseTest {

    @Test
    @DisplayName("Should correctly set and get fields")
    void shouldSetAndGetFields() {
        ZodiacResponse response = new ZodiacResponse("ARIES", "Fire", "Cardinal", "Mars", "Dynamic and passionate");

        assertThat(response.getZodiacSign()).isEqualTo("ARIES");
        assertThat(response.getElement()).isEqualTo("Fire");
        assertThat(response.getModality()).isEqualTo("Cardinal");
        assertThat(response.getRulingPlanet()).isEqualTo("Mars");
        assertThat(response.getDescription()).isEqualTo("Dynamic and passionate");
    }

    @Test
    @DisplayName("Should correctly serialize and deserialize")
    void shouldSerializeAndDeserialize() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ZodiacResponse response = new ZodiacResponse("ARIES", "Fire", "Cardinal", "Mars", "Dynamic and passionate");

        String json = objectMapper.writeValueAsString(response);
        ZodiacResponse deserialized = objectMapper.readValue(json, ZodiacResponse.class);

        assertThat(deserialized).isEqualTo(response);
    }

    @Test
    @DisplayName("Should correctly implement equals and hashCode")
    void shouldImplementEqualsAndHashCode() {
        ZodiacResponse response1 = new ZodiacResponse("ARIES", "Fire", "Cardinal", "Mars", "Dynamic and passionate");
        ZodiacResponse response2 = new ZodiacResponse("ARIES", "Fire", "Cardinal", "Mars", "Dynamic and passionate");
        ZodiacResponse response3 = new ZodiacResponse("TAURUS", "Earth", "Fixed", "Venus", "Stable and practical");

        assertThat(response1).isEqualTo(response2);
        assertThat(response1).hasSameHashCodeAs(response2);
        assertThat(response1).isNotEqualTo(response3);
    }
}

