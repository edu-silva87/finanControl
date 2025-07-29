package com.finan_control.auth_service.dtos;

import jakarta.validation.constraints.NotBlank;

public record PasswordDto( 
    @NotBlank(message = "Email must not be blank")
    String email) {

}
