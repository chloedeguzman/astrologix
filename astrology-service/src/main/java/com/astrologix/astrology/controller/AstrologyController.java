package com.astrologix.astrology.controller;

import com.astrologix.astrology.dto.ZodiacResponseDTO;
import com.astrologix.astrology.service.AstrologyService;
import com.astrologix.astrology.validation.ValidDate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/astrology")
@Validated
public class AstrologyController {

    private final AstrologyService astrologyService;

    public AstrologyController(AstrologyService astrologyService) {
        this.astrologyService = astrologyService;
    }

    @GetMapping("/zodiac")
    public ResponseEntity<ZodiacResponseDTO> getZodiacSign(
            @RequestParam @ValidDate String date) {
        String[] parts = date.split("-");
        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);

        ZodiacResponseDTO response = astrologyService.getZodiacDetails(day, month);
        return ResponseEntity.ok(response);
    }
}
