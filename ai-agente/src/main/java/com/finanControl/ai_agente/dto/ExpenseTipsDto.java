package com.finanControl.ai_agente.dto;

import jakarta.validation.constraints.NotBlank;

public record ExpenseTipsDto(
        @NotBlank(message = "Title of expense tips not be blank")
        String title,
        @NotBlank(message = "Description of expense tips not be blank")
        String description
) {
}
