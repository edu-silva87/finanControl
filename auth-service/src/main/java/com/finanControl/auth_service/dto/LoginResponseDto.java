package com.finanControl.auth_service.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginResponseDto(
        @NotBlank
        String jwtValue,
        long expiresIn
) {
}
