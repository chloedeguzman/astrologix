package com.astrologix.astrology;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@OpenAPIDefinition(
        info = @Info(
                title = "AstroLogix Astrology Service API",
                version = "1.0",
                description = "API for astrology-related calculations and insights"
        )
)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AstrologyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AstrologyServiceApplication.class, args);
    }
}
