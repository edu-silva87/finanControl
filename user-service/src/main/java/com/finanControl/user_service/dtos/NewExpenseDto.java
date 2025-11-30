package com.finanControl.user_service.dtos;

import com.finanControl.user_service.enums.TypeExpense;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NewExpenseDto(
        @NotBlank
        String name,
        @NotBlank
        TypeExpense typeExpense,
        @NotBlank
        BigDecimal price,
        @NotBlank
        LocalDate date

) {
}
