package com.astrologix.user.service;

import com.astrologix.user.dto.ZodiacResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

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

        mockServer.verify();
    }

    @Test
    @DisplayName("When Astrology service times out, return null")
    void whenAstrologyServiceTimeoutThenReturnNull() {
        String date = "03-25";

        mockServer.expect(requestTo(astrologyServiceUrl + "?date=" + date))
                .andRespond(request -> {
                    throw new java.net.SocketTimeoutException("Timeout occurred");
                });

        ZodiacResponse response = astrologyIntegrationService.getZodiacDetails(date);

        assertNull(response, "Response should be null when the service times out");
        mockServer.verify();
    }

    @Test
    @DisplayName("When Astrology service returns 404, return null")
    void whenAstrologyServiceReturns404ThenReturnNull() {
        String date = "03-25";

        mockServer.expect(requestTo(astrologyServiceUrl + "?date=" + date))
                .andRespond(withStatus(HttpStatusCode.valueOf(404)));

        ZodiacResponse response = astrologyIntegrationService.getZodiacDetails(date);

        assertNull(response, "Response should be null when the service returns 404");
        mockServer.verify();
    }

    @Test
    @DisplayName("When Astrology service returns malformed JSON, throw exception")
    void whenAstrologyServiceReturnsMalformedJsonThenThrowException() {
        String date = "03-25";

        mockServer.expect(requestTo(astrologyServiceUrl + "?date=" + date))
                .andRespond(withSuccess("{ malformed json }", APPLICATION_JSON));

        assertThrows(Exception.class, () -> astrologyIntegrationService.getZodiacDetails(date),
                "Service should throw an exception for malformed JSON");

        mockServer.verify();
    }

    @Test
    @DisplayName("When Astrology service returns missing fields, handle gracefully")
    void whenAstrologyServiceReturnsMissingFieldsThenHandleGracefully() {
        String date = "03-25";

        mockServer.expect(requestTo(astrologyServiceUrl + "?date=" + date))
                .andRespond(withSuccess(
                        """
                        {
                            "zodiacSign": "ARIES"
                        }
                        """, APPLICATION_JSON));

        ZodiacResponse response = astrologyIntegrationService.getZodiacDetails(date);

        assertNotNull(response, "Response should not be null");
        assertEquals("ARIES", response.getZodiacSign());
        assertNull(response.getElement(), "Element should be null when missing in the response");
        mockServer.verify();
    }

    @Test
    @DisplayName("When Astrology service returns 500, return null")
    void whenAstrologyServiceReturns500ThenReturnNull() {
        String date = "03-25";

        mockServer.expect(requestTo(astrologyServiceUrl + "?date=" + date))
                .andRespond(withStatus(HttpStatusCode.valueOf(500)));

        ZodiacResponse response = astrologyIntegrationService.getZodiacDetails(date);

        assertNull(response, "Response should be null when the service returns 500");
        mockServer.verify();
    }


}
