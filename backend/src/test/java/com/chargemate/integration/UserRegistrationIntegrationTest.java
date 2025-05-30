package com.chargemate.integration;

import com.chargemate.dto.UserRegistrationDTO;
import com.chargemate.model.User;
import com.chargemate.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import app.getxray.xray.junit.customjunitxml.annotations.Requirement;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserRegistrationIntegrationTest extends AbstractIntegrationTest{

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Requirement("CMATE-63")
    void registerEVDriver_withValidData_shouldCreateUserAndReturn201() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setName("Test User");
        dto.setEmail("testuser@example.com");
        dto.setPassword("password123");
        dto.setUserType("EV_DRIVER");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserRegistrationDTO> request = new HttpEntity<>(dto, headers);

        // Act
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/register/ev-driver",
                request,
                String.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).contains("EV Driver registered successfully");

        // And: user is persisted in the database
        User user = userRepository.findByEmail("testuser@example.com").orElse(null);
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("Test User");
    }
} 