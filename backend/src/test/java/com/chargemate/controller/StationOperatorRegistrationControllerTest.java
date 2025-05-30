package com.chargemate.controller;

import com.chargemate.dto.UserRegistrationDTO;
import com.chargemate.model.UserType;
import com.chargemate.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StationOperatorRegistrationController.class)
class StationOperatorRegistrationControllerTest {

    @TestConfiguration
    static class TestSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
} 

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    @DisplayName("Should register Station Operator and return 201")
    void registerStationOperator_ShouldReturnCreated() throws Exception {
        String json = "{" +
                "\"name\": \"Station Owner\"," +
                "\"email\": \"operator@example.com\"," +
                "\"password\": \"securepass\"}";

        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setName("Station Owner");
        dto.setEmail("operator@example.com");
        dto.setPassword("securepass");
        dto.setUserType("STATION_OPERATOR");

        Mockito.when(userService.registerUser(Mockito.any(UserRegistrationDTO.class)))
                .thenReturn(new com.chargemate.model.User());

        mockMvc.perform(post("/api/v1/auth/register/station-operator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Station Operator registered successfully"));
    }

    @Test
    @WithMockUser
    @DisplayName("Should return 400 for invalid data")
    void registerStationOperator_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        String json = "{" +
                "\"name\": \"\"," +
                "\"email\": \"invalid-email\"," +
                "\"password\": \"\"}";

        mockMvc.perform(post("/api/v1/auth/register/station-operator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}
