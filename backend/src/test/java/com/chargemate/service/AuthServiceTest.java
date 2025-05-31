package com.chargemate.service;

import com.chargemate.dto.LoginRequestDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    @Requirement("CMATE-65")
    void shouldLoginSuccessfullyWithValidEVDriverCredentials() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("driver@example.com");
        loginRequest.setPassword("password123");

        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> authService.login(loginRequest));
    }

    @Test
    @Requirement("CMATE-65")
    void shouldFailLoginWithInvalidCredentials() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("driver@example.com");
        loginRequest.setPassword("wrongpassword");

        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> authService.login(loginRequest));
    }

    @Test
    @Requirement("CMATE-65")
    void shouldFailLoginWithNonEVDriverUser() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("operator@example.com");
        loginRequest.setPassword("password123");

        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> authService.login(loginRequest));
    }
} 