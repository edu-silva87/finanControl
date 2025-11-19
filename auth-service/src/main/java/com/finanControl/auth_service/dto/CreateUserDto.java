package com.finanControl.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        @NotBlank
        String name
) {
}
