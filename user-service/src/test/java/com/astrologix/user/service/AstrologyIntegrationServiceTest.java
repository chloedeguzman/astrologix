package com.astrologix.user.service;

import com.astrologix.user.dto.ZodiacResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class AstrologyIntegrationServiceTest {

    private AstrologyIntegrationService astrologyIntegrationService;
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;

    private final String astrologyServiceUrl = "http://astrology-service/api/astrology/zodiac";

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        astrologyIntegrationService = new AstrologyIntegrationService(restTemplate);

        // Inject astrologyServiceUrl into the service
        ReflectionTestUtils.setField(astrologyIntegrationService, "astrologyServiceUrl", astrologyServiceUrl);
    }

    @Test
    @DisplayName("Fetch Zodiac Details - Valid Response")
    void shouldFetchZodiacDetailsForValidDate() {
        String date = "03-25";

        // Mock the response
        mockServer.expect(requestTo(astrologyServiceUrl + "?date=" + date))
                .andRespond(withSuccess(
                        """
                        {
                            "zodiacSign": "ARIES",
                            "element": "Fire",
                            "modality": "Cardinal",
                            "rulingPlanet": "Mars",
                            "description": "Dynamic and passionate"
                        }
                        """, APPLICATION_JSON));

        // Call the method
        ZodiacResponse response = astrologyIntegrationService.getZodiacDetails(date);

        // Assertions
        assertNotNull(response, "Response should not be null");
        assertEquals("ARIES", response.getZodiacSign());
        assertEquals("Fire", response.getElement());
        assertEquals("Cardinal", response.getModality());
        assertEquals("Mars", response.getRulingPlanet());
        assertEquals("Dynamic and passionate", response.getDescription());

        // Verify all expectations were met
        mockServer.verify();
    }
}
