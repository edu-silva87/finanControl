package com.finanControl.ai_agente.dto;

import com.finanControl.ai_agente.enums.TypeExpense;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CategorySummaryDto(
        @NotNull(message = "Type expense not be null")
        TypeExpense typeExpense,

        @NotNull(message = "Total amount not be null")
        @PositiveOrZero(message = "Total amount must be zero or positive")
        BigDecimal totalAmount
) {
}
