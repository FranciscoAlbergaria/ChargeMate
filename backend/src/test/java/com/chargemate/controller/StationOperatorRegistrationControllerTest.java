package com.chargemate.controller;

import com.chargemate.config.TestSecurityConfig;
import com.chargemate.dto.UserRegistrationDTO;
import com.chargemate.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import com.chargemate.security.JwtAuthenticationFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
    controllers = StationOperatorRegistrationController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
)
@Import(TestSecurityConfig.class)
class StationOperatorRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    @DisplayName("Should register Station Operator and return 201")
    void registerStationOperator_ShouldReturnCreated() throws Exception {
        Mockito.when(userService.registerUser(Mockito.any(UserRegistrationDTO.class)))
                .thenReturn(new com.chargemate.model.User());
        String json = "{" +
                "\"name\": \"Station Owner\"," +
                "\"email\": \"operator@example.com\"," +
                "\"password\": \"securepass\"," +
                "\"userType\": \"STATION_OPERATOR\"" +
                "}";
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
                "\"password\": \"\"" +
                "}";
        mockMvc.perform(post("/api/v1/auth/register/station-operator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("Should return 400 for business error")
    void registerStationOperator_WithExistingEmail_ShouldReturnBadRequest() throws Exception {
        Mockito.when(userService.registerUser(Mockito.any(UserRegistrationDTO.class)))
                .thenThrow(new RuntimeException("Email already registered"));
        String json = "{" +
                "\"name\": \"Station Owner\"," +
                "\"email\": \"operator@example.com\"," +
                "\"password\": \"securepass\"," +
                "\"userType\": \"STATION_OPERATOR\"" +
                "}";
        mockMvc.perform(post("/api/v1/auth/register/station-operator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Email already registered"));
    }
}