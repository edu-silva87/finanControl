package com.finanControl.auth_service.controller;

import com.finanControl.auth_service.dto.CreateUserDto;
import com.finanControl.auth_service.dto.LoginRequestDto;
import com.finanControl.auth_service.dto.LoginResponseDto;
import com.finanControl.auth_service.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }

    @PostMapping("/newUser")
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto createUserDto) {
        userService.newUser(createUserDto);
        return ResponseEntity.ok().build();
    }

}
