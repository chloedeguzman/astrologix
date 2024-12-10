package com.astrologix.astrology.controller;

import com.astrologix.astrology.dto.ZodiacResponse;
import com.astrologix.astrology.service.AstrologyService;
import com.astrologix.astrology.validation.ValidDate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Fetch zodiac details",
            description = "Retrieve zodiac sign details based on a provided date in MM-DD format."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved zodiac details."),
            @ApiResponse(responseCode = "400", description = "Invalid date format or missing parameters."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/zodiac")
    public ResponseEntity<ZodiacResponse> getZodiacSign(
            @RequestParam
            @Parameter(
                    description = "Date in MM-DD format",
                    example = "03-25",
                    required = true
            )
            @ValidDate String date) {

        String[] parts = date.split("-");
        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);

        ZodiacResponse response = astrologyService.getZodiacDetails(day, month);
        return ResponseEntity.ok(response);
    }
}
