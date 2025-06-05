package com.chargemate.service;

import com.chargemate.dto.LoginRequestDTO;
import com.chargemate.exception.UnauthorizedException;
import com.chargemate.model.User;
import com.chargemate.model.UserType;
import com.chargemate.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import app.getxray.xray.junit.customjunitxml.annotations.Requirement;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    @Requirement("CMATE-65")
    void shouldLoginSuccessfullyWithValidEVDriverCredentials() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("driver@example.com");
        loginRequest.setPassword("password123");

        User user = new User();
        user.setId(1L);
        user.setEmail("driver@example.com");
        user.setPassword("encodedPassword");
        user.setUserType(UserType.EV_DRIVER);

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user.getEmail(), user.getId(), user.getUserType().name()))
            .thenReturn("valid.jwt.token");

        // Act
        var response = authService.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals("valid.jwt.token", response.getToken());
        assertEquals(user.getId(), response.getUserId());
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getUserType().name(), response.getRole());
    }

    @Test
    @Requirement("CMATE-65")
    void shouldFailLoginWithInvalidCredentials() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("driver@example.com");
        loginRequest.setPassword("wrongpassword");

        User user = new User();
        user.setEmail("driver@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> authService.login(loginRequest));
    }

    @Test
    @Requirement("CMATE-65")
    void shouldFailLoginWithNonEVDriverUser() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("operator@example.com");
        loginRequest.setPassword("password123");

        User user = new User();
        user.setEmail("operator@example.com");
        user.setPassword("encodedPassword");
        user.setUserType(UserType.STATION_OPERATOR);

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> authService.login(loginRequest));
    }
} 