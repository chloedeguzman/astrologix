package com.astrologix.astrology.model;

import lombok.Getter;

import java.time.MonthDay;

@Getter
public enum ZodiacSign {
    ARIES(MonthDay.of(3, 21), MonthDay.of(4, 19), "Fire", "Cardinal", "Mars", "Aries is bold, ambitious, and ready to take on any challenge."),
    TAURUS(MonthDay.of(4, 20), MonthDay.of(5, 20), "Earth", "Fixed", "Venus", "Taurus is reliable, patient, and loves the finer things in life."),
    GEMINI(MonthDay.of(5, 21), MonthDay.of(6, 20), "Air", "Mutable", "Mercury", "Gemini is versatile, communicative, and curious."),
    CANCER(MonthDay.of(6, 21), MonthDay.of(7, 22), "Water", "Cardinal", "Moon", "Cancer is nurturing, emotional, and deeply intuitive."),
    LEO(MonthDay.of(7, 23), MonthDay.of(8, 22), "Fire", "Fixed", "Sun", "Leo is confident, charismatic, and loves the spotlight."),
    VIRGO(MonthDay.of(8, 23), MonthDay.of(9, 22), "Earth", "Mutable", "Mercury", "Virgo is analytical, practical, and a perfectionist."),
    LIBRA(MonthDay.of(9, 23), MonthDay.of(10, 22), "Air", "Cardinal", "Venus", "Libra values balance, harmony, and relationships."),
    SCORPIO(MonthDay.of(10, 23), MonthDay.of(11, 21), "Water", "Fixed", "Pluto", "Scorpio is passionate, intense, and deeply transformative."),
    SAGITTARIUS(MonthDay.of(11, 22), MonthDay.of(12, 21), "Fire", "Mutable", "Jupiter", "Sagittarius is adventurous, optimistic, and philosophical."),
    CAPRICORN(MonthDay.of(12, 22), MonthDay.of(1, 19), "Earth", "Cardinal", "Saturn", "Capricorn is disciplined, responsible, and goal-oriented."),
    AQUARIUS(MonthDay.of(1, 20), MonthDay.of(2, 18), "Air", "Fixed", "Uranus", "Aquarius is innovative, independent, and forward-thinking."),
    PISCES(MonthDay.of(2, 19), MonthDay.of(3, 20), "Water", "Mutable", "Neptune", "Pisces is empathetic, creative, and deeply spiritual.");

    private final MonthDay startDate;
    private final MonthDay endDate;
    private final String element;
    private final String modality;
    private final String rulingPlanet;
    private final String description;

    ZodiacSign(MonthDay startDate, MonthDay endDate, String element, String modality, String rulingPlanet, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.element = element;
        this.modality = modality;
        this.rulingPlanet = rulingPlanet;
        this.description = description;
    }
}
