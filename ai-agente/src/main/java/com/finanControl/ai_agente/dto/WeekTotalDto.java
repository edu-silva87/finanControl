package com.finanControl.ai_agente.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WeekTotalDto(

        @Positive(message = "Week number must be positive")
        int weekNumber,

        @NotNull(message = "Start period is required")
        LocalDateTime startPeriod,

        @NotNull(message = "End period is required")
        LocalDateTime endPeriod,

        @NotNull(message = "Total amount must not be null")
        @PositiveOrZero(message = "Total amount must be zero or positive")
        BigDecimal total

) {
    public WeekTotalDto {
        if (endPeriod.isBefore(startPeriod)) {
            throw new IllegalArgumentException(
                    "End period must not be before start period"
            );
        }
    }
}

