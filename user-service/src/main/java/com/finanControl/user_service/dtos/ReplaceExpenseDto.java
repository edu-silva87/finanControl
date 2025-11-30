package com.finanControl.user_service.dtos;

import com.finanControl.user_service.enums.TypeExpense;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReplaceExpenseDto(
        @NotBlank
        UUID expenseId,
        @NotBlank
        TypeExpense typeExpense,
        @NotBlank
        String name,
        @NotBlank
        BigDecimal price,
        @NotBlank
        LocalDate date
) {
}
