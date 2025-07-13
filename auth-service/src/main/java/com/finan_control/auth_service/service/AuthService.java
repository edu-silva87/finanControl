package com.finan_control.auth_service.service;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.finan_control.auth_service.dtos.LoginRequestDto;
import com.finan_control.auth_service.dtos.LoginResponseDto;
import com.finan_control.auth_service.dtos.RegisterDto;
import com.finan_control.auth_service.exception.InvalidPasswordException;
import com.finan_control.auth_service.model.UserModel;
import com.finan_control.auth_service.repository.UserRepository;
import com.finan_control.auth_service.util.PasswordValidation;

@Service
public class AuthService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtEncoder jwtEncoder;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
            JwtEncoder jwtEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public LoginResponseDto login(LoginRequestDto loginDto) {
        var user = userRepository.findByEmail(loginDto.email());
        
        if(user.isEmpty() || !user.get().isLoginCorrect(loginDto, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("User or password is invalid!");
        }

        var now = Instant.now();
        var expireIn = 300L;

        var claims = JwtClaimsSet.builder()
                .issuer("auth-service")
                .subject(user.get().getId().toString())
                .expiresAt(now.plusSeconds(expireIn))
                .issuedAt(now)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        
        return new LoginResponseDto(jwtValue, expireIn);
    }

    public void register(RegisterDto registerDto) throws InvalidPasswordException {
        var userFromDto = userRepository.findByEmail(registerDto.email());

        if (userFromDto.isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "A user with this email already exists."
            );
        }

        if (!PasswordValidation.isValid(registerDto.password())) {
            throw new InvalidPasswordException(
                "Invalid password: minimum of 8 characters, including 1 digit, " +
                "1 uppercase letter, 1 lowercase letter, 1 special character, and no spaces."
            );
        }

        var user = UserModel.builder()
                .email(registerDto.email())
                .password(bCryptPasswordEncoder.encode(registerDto.password().toString()))
                .build();
                
        userRepository.save(user);
    }
   

}