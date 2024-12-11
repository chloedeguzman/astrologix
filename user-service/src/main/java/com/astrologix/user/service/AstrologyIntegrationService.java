package com.astrologix.user.service;

import com.astrologix.user.dto.ZodiacResponse;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

@Service
public class AstrologyIntegrationService {

    private static final Logger log = LoggerFactory.getLogger(AstrologyIntegrationService.class);

    private final RestTemplate restTemplate;

    @Value("${astrology.service.url}")
    private String astrologyServiceUrl;

    public AstrologyIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ZodiacResponse getZodiacDetails(String date) {
        try {
            return fetchZodiacDetails(date);
        } catch (Exception e) {
            handleException(e, date);
            return null;
        }
    }

    private ZodiacResponse fetchZodiacDetails(String date) {
        ResponseEntity<ZodiacResponse> response = restTemplate.getForEntity(
                astrologyServiceUrl + "?date=" + date, ZodiacResponse.class);
        return response.getBody();
    }

    private void handleException(Exception e, String date) {
        if (e instanceof ResourceAccessException) {
            log.error(e, () -> "Astrology service timed out for date: " + date);
        } else if (e instanceof HttpClientErrorException.NotFound) {
            log.error(e, () -> "Astrology service returned 404 for date: " + date);
        } else if (e instanceof HttpServerErrorException) {
            log.error(e, () -> "Astrology service returned 500 for date: " + date);
        } else if (e instanceof HttpClientErrorException clientError) {
            log.error(e, () -> "Astrology service returned error " + clientError.getStatusCode() + " for date: " + date);
        } else {
            log.error(e, () -> "An unexpected error occurred: " + e.getMessage());
            throw new RuntimeException("Unhandled exception occurred during astrology service call", e);
        }
    }
}
