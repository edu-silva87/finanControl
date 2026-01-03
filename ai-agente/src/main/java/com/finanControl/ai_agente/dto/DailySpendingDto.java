package com.finanControl.ai_agente.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DailySpendingDto(
        @NotNull(message = "Day not be null")
        LocalDateTime day,

        @NotNull(message = "Total amount must not be null")
        @PositiveOrZero(message = "Total amount must be zero or positive")
        BigDecimal totalAmount
) {
}
