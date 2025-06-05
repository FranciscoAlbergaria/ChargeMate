package com.chargemate.controller;

import com.chargemate.config.TestSecurityConfig;
import com.chargemate.dto.UserRegistrationDTO;
import com.chargemate.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.getxray.xray.junit.customjunitxml.annotations.Requirement;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import com.chargemate.security.JwtAuthenticationFilter;

@WebMvcTest(
    controllers = UserRegistrationController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
)
@Import(TestSecurityConfig.class)
public class UserRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRegistrationDTO evDriverDTO;

    @BeforeEach
    void setUp() {
        evDriverDTO = new UserRegistrationDTO();
        evDriverDTO.setEmail("driver@example.com");
        evDriverDTO.setPassword("password123");
        evDriverDTO.setName("John Driver");
        evDriverDTO.setUserType("EV_DRIVER");
    }

    @Test
    @Requirement("CMATE-63")
    void registerEVDriver_ShouldReturnCreated() throws Exception {
        // Mocka o comportamento do userService para sucesso
        org.mockito.Mockito.when(userService.registerUser(org.mockito.Mockito.any(UserRegistrationDTO.class)))
                .thenReturn(new com.chargemate.model.User());
        mockMvc.perform(post("/api/v1/auth/register/ev-driver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(evDriverDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("EV Driver registered successfully"));
    }

    @Test
    @Requirement("CMATE-63")
    void registerEVDriver_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        evDriverDTO.setEmail("invalid-email");
        mockMvc.perform(post("/api/v1/auth/register/ev-driver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(evDriverDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Requirement("CMATE-63")
    void registerEVDriver_WithExistingEmail_ShouldReturnBadRequest() throws Exception {
        // Mocka o comportamento do userService para lançar exceção de negócio
        org.mockito.Mockito.when(userService.registerUser(org.mockito.Mockito.any(UserRegistrationDTO.class)))
                .thenThrow(new RuntimeException("Email already registered"));
        mockMvc.perform(post("/api/v1/auth/register/ev-driver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(evDriverDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Email already registered"));
    }
} 