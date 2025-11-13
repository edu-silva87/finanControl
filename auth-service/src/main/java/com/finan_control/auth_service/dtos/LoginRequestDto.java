package com.finan_control.auth_service.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "Email must not be blank")
        @Email(message = "You need to provide a valid email")
        String email,

        @NotBlank(message = "Password must not be blank")
        String password
) {
}
