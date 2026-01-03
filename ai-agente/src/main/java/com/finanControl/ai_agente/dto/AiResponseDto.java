package com.finanControl.ai_agente.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.List;

public record AiResponseDto(
        @NotNull(message = "Weekly totals must not be null")
        List<WeekTotalDto> weeklyTotalsDto,

        @NotNull(message = "Monthly total must not be null")
        @PositiveOrZero(message = "Monthly total must be zero or positive")
        BigDecimal monthlyTotal,

        @NotNull(message = "Yearly total must not be null")
        @PositiveOrZero(message = "Yearly total must be zero or positive")
        BigDecimal yearlyTotal,

        @NotNull(message = "highest spending category must not be null")
        CategorySummaryDto highestSpendingCategory,

        @NotNull(message = "highest spending day must not be null")
        DailySpendingDto highestSpendingDay,

        @NotNull(message = "Expense tips must not be null")
        List<ExpenseTipsDto> expenseTips
) {
}
