package com.chargemate.controller;

import com.chargemate.config.TestSecurityConfig;
import com.chargemate.dto.AuthResponse;
import com.chargemate.dto.LoginRequestDTO;
import com.chargemate.exception.UnauthorizedException;
import com.chargemate.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import app.getxray.xray.junit.customjunitxml.annotations.Requirement;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import com.chargemate.security.JwtAuthenticationFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
    controllers = AuthController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
)
@Import(TestSecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    @Requirement("CMATE-65")
    void shouldLoginSuccessfullyWithValidEVDriverCredentials() throws Exception {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("driver@example.com");
        loginRequest.setPassword("password123");

        AuthResponse authResponse = new AuthResponse("valid.jwt.token", 1L, "driver@example.com", "EV_DRIVER");
        when(authService.login(any(LoginRequestDTO.class))).thenReturn(authResponse);

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("valid.jwt.token"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.email").value("driver@example.com"))
                .andExpect(jsonPath("$.role").value("EV_DRIVER"));
    }

    @Test
    @Requirement("CMATE-65")
    void shouldFailLoginWithInvalidCredentials() throws Exception {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("driver@example.com");
        loginRequest.setPassword("wrongpassword");

        when(authService.login(any(LoginRequestDTO.class)))
                .thenThrow(new UnauthorizedException("Invalid credentials"));

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid credentials"));
    }
} 