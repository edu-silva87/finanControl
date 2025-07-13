package com.finan_control.auth_service.dtos;

public record LoginResponseDto(String accessToken, long expiresIn) {

}
