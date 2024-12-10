package com.astrologix.astrology.controller;

import com.astrologix.astrology.dto.ZodiacResponseDTO;
import com.astrologix.astrology.service.AstrologyService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

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
            @RequestParam @Min(1) @Max(31) int day,
            @RequestParam @Min(1) @Max(12) int month) {
        return ResponseEntity.ok(astrologyService.getZodiacDetails(day, month));
    }
}
