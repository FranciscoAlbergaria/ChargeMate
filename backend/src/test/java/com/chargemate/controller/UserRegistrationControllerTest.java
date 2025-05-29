package com.chargemate.controller;

import com.chargemate.dto.UserRegistrationDTO;
import com.chargemate.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.getxray.xray.junit.customjunitxml.annotations.Requirement;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import app.getxray.xray.junit.customjunitxml.annotations.XrayTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserRegistrationController.class)
public class UserRegistrationControllerTest {

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll()
                );
            return http.build();
        }
    }

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
    @XrayTest(key = "CMATE-83")
    @Requirement("CMATE-63")
    void registerEVDriver_ShouldReturnCreated() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register/ev-driver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(evDriverDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("EV Driver registered successfully"));
    }

    @Test
    @XrayTest(key = "CMATE-83")
    @Requirement("CMATE-63")
    void registerEVDriver_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        evDriverDTO.setEmail("invalid-email");
        
        mockMvc.perform(post("/api/v1/auth/register/ev-driver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(evDriverDTO)))
                .andExpect(status().isBadRequest());
    }
} 