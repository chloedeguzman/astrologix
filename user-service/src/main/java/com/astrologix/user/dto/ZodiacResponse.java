package com.astrologix.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ZodiacResponse {
    private String zodiacSign;
    private String element;
    private String modality;
    private String rulingPlanet;
    private String description;
}
