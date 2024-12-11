package com.astrologix.user.service;

import com.astrologix.user.dto.ZodiacResponse;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
            ResponseEntity<ZodiacResponse> response = restTemplate.getForEntity(
                    astrologyServiceUrl + "?date=" + date, ZodiacResponse.class);
            return response.getBody();
        } catch (Exception e) {
            return null;
        }
    }

}
