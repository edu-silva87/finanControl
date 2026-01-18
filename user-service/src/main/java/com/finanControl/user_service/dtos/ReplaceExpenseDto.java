package com.finanControl.user_service.dtos;

import com.finanControl.user_service.enums.TypeExpense;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReplaceExpenseDto(
        @NotNull
        UUID expenseId,
        @NotNull
        TypeExpense typeExpense,
        @NotBlank
        String name,
        @NotNull
        @Positive
        BigDecimal price,
        @NotNull
        LocalDate date
) {
}
