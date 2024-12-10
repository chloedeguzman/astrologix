package com.astrologix.astrology.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZodiacResponse {
    private String zodiacSign;
    private String element;
    private String modality;
    private String rulingPlanet;
    private String description;
}

