package com.chargemate.integration;

import com.chargemate.dto.UserRegistrationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import app.getxray.xray.junit.customjunitxml.annotations.Requirement;
import com.chargemate.repository.UserRepository;
import com.chargemate.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StationOperatorRegistrationIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Requirement("CMATE-64")
    @DisplayName("Integration test: should register Station Operator and return 201")
    void registerStationOperator_ShouldReturnCreated() throws Exception {
        // Arrange
        String url = "http://localhost:" + port + "/api/v1/auth/register/station-operator";

        UserRegistrationDTO operatorDTO = new UserRegistrationDTO();
        operatorDTO.setName("Station Owner");
        operatorDTO.setEmail("operator@example.com");
        operatorDTO.setPassword("securepass");
        operatorDTO.setUserType("STATION_OPERATOR");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(operatorDTO), headers);

        // Act
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).contains("Station Operator registered successfully");

        // And: user is persisted in the database
        User user = userRepository.findByEmail("operator@example.com").orElse(null);
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("Station Owner");
    }

}