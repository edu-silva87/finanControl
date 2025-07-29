package com.finan_control.auth_service.dtos;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordDto(
    @NotBlank(message = "Token must not be blank")
    String token,    
    @NotBlank(message = "New Password must not be blank")
    String newPassword
) {

}
