package com.finanControl.auth_service.services;

import com.finanControl.auth_service.dto.CreateUserDto;
import com.finanControl.auth_service.dto.LoginRequestDto;
import com.finanControl.auth_service.dto.LoginResponseDto;
import com.finanControl.auth_service.entities.User;
import com.finanControl.auth_service.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtEncoder jwtEncoder, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Optional<User> user = userRepository.findByEmail(loginRequestDto.email());

        if (user.isEmpty() || user.get().isCorrectPassword(loginRequestDto, passwordEncoder))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email or user is invalid");

        var now = Instant.now();
        var expiresIn = 300L;

        var jwtClaims = JwtClaimsSet.builder()
                .issuer("finanControl-backend")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaims)).getTokenValue();
        return new LoginResponseDto(jwtValue, expiresIn);
    }

    public void newUser(CreateUserDto createUserDto) {
        var isPresentInDb = userRepository.findByEmail(createUserDto.email()).isPresent();

        if (isPresentInDb) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The user already exist");

        User user = User.builder()
                .email(createUserDto.email())
                .password(passwordEncoder.encode(createUserDto.password()))
                .name(createUserDto.name())
                .build();

        userRepository.save(user);
    }
}
