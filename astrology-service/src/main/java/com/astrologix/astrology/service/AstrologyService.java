package com.astrologix.astrology.service;

import com.astrologix.astrology.dto.ZodiacResponseDTO;
import com.astrologix.astrology.model.ZodiacSign;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.MonthDay;

@Service
public class AstrologyService {

    public ZodiacResponseDTO getZodiacDetails(int day, int month) {
        MonthDay inputDate;
        try {
            inputDate = MonthDay.of(month, day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid date provided: " + e.getMessage());
        }

        for (ZodiacSign sign : ZodiacSign.values()) {
            if (isDateWithinRange(inputDate, sign.getStartDate(), sign.getEndDate())) {
                return mapToZodiacResponseDTO(sign);
            }
        }

        throw new IllegalArgumentException("No zodiac sign matches the given date.");
    }

    private boolean isDateWithinRange(MonthDay date, MonthDay start, MonthDay end) {
        if (start.isBefore(end)) {
            return !date.isBefore(start) && !date.isAfter(end);
        } else {
            return !date.isBefore(start) || !date.isAfter(end);
        }
    }

    private ZodiacResponseDTO mapToZodiacResponseDTO(ZodiacSign sign) {
        return new ZodiacResponseDTO(
                sign.name(),
                sign.getElement(),
                sign.getModality(),
                sign.getRulingPlanet(),
                sign.getDescription()
        );
    }
}
