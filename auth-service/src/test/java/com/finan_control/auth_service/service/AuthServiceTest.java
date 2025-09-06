package com.finan_control.auth_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.server.ResponseStatusException;

import com.finan_control.auth_service.dtos.LoginRequestDto;
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
import com.finan_control.auth_service.util.TokenGenerator;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Mock
    private JwtEncoder jwtEncoder;
    
    @Mock
    private WelcomeEmailProducer welcomeEmail;
    
    @Mock
    private ResetPasswordEmailProducer resetEmail;
    
    @Mock
    private TokenRepository tokenRepository;

    @Autowired
    @InjectMocks
    private AuthService authService;

    private AutoCloseable mocks;
    
    @BeforeEach
    void setUp(){
        mocks = MockitoAnnotations.openMocks(this);
    }


    
    @Test
    @DisplayName("Should return LoginResponseDto when Login is Successful")
    void login_ShouldReturnLoginResponseDto_WhenLoginIsSuccessful() {
        var loginDto = new LoginRequestDto("email.mock@test.com", "passwordForTest");
            
        var user = spy(UserModel.builder()
                .id(UUID.randomUUID())
                .email("email.mock@test.com")
                .password("passwordForTest")
                .build());
        
        when(userRepository.findByEmail(loginDto.email()))
                .thenReturn(Optional.of(user));
                
        doReturn(true).when(user)
                .isLoginCorrect(eq(loginDto), any(BCryptPasswordEncoder.class));

       
        var jwtToken = new Jwt("auth-service-test", 
                Instant.now(), 
                Instant.now().plusSeconds(300),
                Map.of("alg", "none"), 
                Map.of("sub", "mockito-test"));

        when(jwtEncoder.encode(any(JwtEncoderParameters.class)))
                .thenReturn(jwtToken);
                
        var response = authService.login(loginDto);

        assertNotNull(response);
        assertEquals("auth-service-test", response.accessToken());
        assertEquals(300L, response.expiresIn());
        
    }
    
    @Test
    @DisplayName("Should throw BadCredentialsException when User or password is invalid")
    void login_ShouldThrowBadCredentialsException_WhenUserOrPasswordIsInvalid() {
        var loginDto = new LoginRequestDto("email.mock@test.com", "passwordForTest");

        var user = spy(UserModel.builder()
                .id(UUID.randomUUID())
                .email("email.mock@test.com")
                .password("passwordForTest")
                .build());

        when(userRepository.findByEmail(loginDto.email()))
                .thenReturn(Optional.empty());

        doReturn(false).when(user)
                .isLoginCorrect(eq(loginDto), any(BCryptPasswordEncoder.class));
        
        assertThrows(BadCredentialsException.class,
                () -> authService.login(loginDto));
    }
        
        
    @Test
    @DisplayName("Should save user when successful")
    void register_ShouldSaveUser_WhenSuccessful() throws InvalidPasswordException {
        RegisterDto registerDto = new RegisterDto("test.auth@test.com", "@#63128fDRTYRQ@#$");

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        authService.register(registerDto);

        verify(userRepository, times(1)).save(any());
        verify(welcomeEmail, times(1)).publishMessageEmail(any());
    }
     
    @Test 
    @DisplayName("Should throw ResponseStatusException when user exists ")
    void register_ShouldThrowResponseStatusException_WhenUserExists() throws InvalidPasswordException {
        RegisterDto registerDto = new RegisterDto("test.auth@test.com", "test-123");
        
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(UserModel.builder().build()));
        
        assertThrows(ResponseStatusException.class, () -> authService.register(registerDto));
    }


    @Test 
    @DisplayName("Should throw InavalidPasswordException when password not strong")
    void register_ShouldThrowInvalidPasswordException_WhenPasswordNotStrong() throws InvalidPasswordException {
        RegisterDto registerDto = new RegisterDto("test.auth@test.com", "test-123");
        
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());
        
        assertThrows(InvalidPasswordException.class, () -> authService.register(registerDto));
    }

    @Test
    @DisplayName("Should send reset email when user exist")
    void sendResetEmail_ShouldSendResetEmail_WhenUserExist() {
        var user = Optional.of(UserModel.builder()
                        .id(UUID.randomUUID())
                        .email("test@gmail.com")
                        .password("dpnsjefjf")        
                        .build());

        when(userRepository.findByEmail(anyString()))
                .thenReturn(user);

        authService.sendResetEmail(anyString());

        verify(resetEmail, times(1)).publishMessageEmail(any());

    }
    
    @Test
    @DisplayName("Should return when user not exist")
    void sendResetEmail_ShouldReturn_WhenUserNotExist() {
        
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        authService.sendResetEmail(anyString());

        verify(resetEmail, times(0)).publishMessageEmail(any());

    }
    
    
    @Test
    @DisplayName("Should update user password when token is valid")
    void changePassword_ShouldUpdateUserPassword_WhenTokenIsValid() throws InvalidTokenException, InvalidPasswordException, ExpireTokenException {
        var now = Instant.now();
        var tokenValue = TokenGenerator.generateSafeToken();

        var user = UserModel.builder()
                .id(UUID.randomUUID())
                .email("test@gmail.com")
                .password("@#63128fDRTYRQ@#$")
                .build();
        
        var token = TokenModel.builder()
                .id(UUID.randomUUID())
                .tokenValue(tokenValue)
                .user(user)
                .createdAt(now)
                .expireAt(now.plusSeconds(900))
                .build();
        
        var resetPasswordDto = new ResetPasswordDto(tokenValue, "@#63128fDRTYRQ@#$");
        
        when(tokenRepository.findByTokenValue(anyString()))
                .thenReturn(Optional.of(token));

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(user));

        authService.changePassword(resetPasswordDto);

        verify(userRepository, times(1)).save(any());
        
    }

    @Test
    @DisplayName("Should throw InvalidTokenException when token not found")
    void changePassword_ShouldThrowInvalidTokenException_WhenTokenNotFound() throws InvalidTokenException, InvalidPasswordException, ExpireTokenException {
        var resetDto = new ResetPasswordDto("test", "123");

        assertThrows(InvalidTokenException.class, () -> authService.changePassword(resetDto));
        
        verify(userRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Should throw ExpireTokenException  when token is expire")
    void changePassword_ShouldThrowExpireTokenException_WhenTokenIsExpire() throws InvalidTokenException, InvalidPasswordException, ExpireTokenException {
        var now = Instant.now();
        var resetDto = new ResetPasswordDto("test", "123");

        var token = TokenModel.builder()
                .id(UUID.randomUUID())
                .tokenValue("tewst")
                .user(new UserModel())
                .createdAt(now)
                .expireAt(now.minusSeconds(100))
                .build();

        
        when(tokenRepository.findByTokenValue(resetDto.token()))
                .thenReturn(Optional.of(token));
        
        assertThrows(ExpireTokenException.class, () -> authService.changePassword(resetDto));
        
        verify(userRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Should throw InvalidPasswordException when password not strong")
    void changePassword_ShouldThrowInvalidPasswordException_WhenPasswordNotStrong() throws InvalidTokenException, InvalidPasswordException, ExpireTokenException {
        var now = Instant.now();
        var resetDto = new ResetPasswordDto("test", "123");

        var token = TokenModel.builder()
                .id(UUID.randomUUID())
                .tokenValue("tewst")
                .user(new UserModel())
                .createdAt(now)
                .expireAt(now.plusSeconds(900))
                .build();

        
        when(tokenRepository.findByTokenValue(resetDto.token()))
                .thenReturn(Optional.of(token));


        assertThrows(InvalidPasswordException.class, () -> authService.changePassword(resetDto));
        
        verify(userRepository, times(0)).save(any());
    }


    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }
}
