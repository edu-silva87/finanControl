package com.finan_control.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finan_control.auth_service.dtos.LoginRequestDto;
import com.finan_control.auth_service.dtos.LoginResponseDto;
import com.finan_control.auth_service.dtos.RegisterDto;
import com.finan_control.auth_service.exception.InvalidPasswordException;
import com.finan_control.auth_service.service.AuthService;

@RestController
@RequestMapping("auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginDto){
        return ResponseEntity
        .ok()
        .body(authService.login(loginDto));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterDto registerDto) throws InvalidPasswordException{
        authService.register(registerDto);
        return ResponseEntity.ok().build();
    }

}