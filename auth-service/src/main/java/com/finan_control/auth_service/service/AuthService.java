package com.finan_control.auth_service.service;

import java.time.Instant;
import java.util.UUID;

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
import com.finan_control.auth_service.dtos.ResetPasswordDto;
import com.finan_control.auth_service.exceptions.ExpireTokenException;
import com.finan_control.auth_service.exceptions.InvalidPasswordException;
import com.finan_control.auth_service.exceptions.InvalidTokenException;
import com.finan_control.auth_service.model.TokenModel;
import com.finan_control.auth_service.model.UserModel;
import com.finan_control.auth_service.producer.ResetPasswordEmailProducer;
import com.finan_control.auth_service.producer.WelcomeEmailProducer;
import com.finan_control.auth_service.repository.TokenRepository;
import com.finan_control.auth_service.repository.UserRepository;
import com.finan_control.auth_service.util.PasswordValidation;

import jakarta.validation.Valid;

@Service
public class AuthService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtEncoder jwtEncoder;
    private WelcomeEmailProducer welcomeEmail;
    private ResetPasswordEmailProducer resetEmail;
    private TokenRepository tokenRepository;

    public AuthService(UserRepository userRepository, 
            BCryptPasswordEncoder bCryptPasswordEncoder,
            JwtEncoder jwtEncoder, 
            WelcomeEmailProducer welcomeEmail, 
            ResetPasswordEmailProducer resetEmail,
            TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.welcomeEmail = welcomeEmail;
        this.resetEmail = resetEmail;
        this.tokenRepository = tokenRepository;
    }

    public LoginResponseDto login(@Valid LoginRequestDto loginDto) {
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

    public void register(@Valid RegisterDto registerDto) throws InvalidPasswordException {
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
                .password(bCryptPasswordEncoder.encode(registerDto.password()))
                .build();
                
        
        var userSaved = userRepository.save(user);
        welcomeEmail.publishMessageEmail(userSaved);
    }

    public void sendResetEmail(String email){
        var user = userRepository.findByEmail(email);

        if(user.isEmpty()) return;
        resetEmail.publishMessageEmail(user.get());
    }

    public void changePassword(@Valid ResetPasswordDto resetDto) throws InvalidTokenException, InvalidPasswordException, ExpireTokenException {
        TokenModel tokenModel = tokenRepository.findByTokenValue(resetDto.token())
                        .orElseThrow(() -> new InvalidTokenException("The token is incorret or not exist"));

    
        if(Instant.now().isAfter(tokenModel.getExpireAt())) throw new ExpireTokenException("The token has expired");
    
        if (!PasswordValidation.isValid(resetDto.newPassword())) {
            throw new InvalidPasswordException(
                "Invalid password: minimum of 8 characters, including 1 digit, " +
                "1 uppercase letter, 1 lowercase letter, 1 special character, and no spaces."
            );
        }

        var userId = tokenModel.getUser().getId();

        var user = userRepository.findById(userId).get();
        
        user.setPassword(bCryptPasswordEncoder.encode(resetDto.newPassword()));
        userRepository.save(user);
    }
}