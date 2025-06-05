package com.chargemate.service;

import com.chargemate.dto.Coordinates;
import com.chargemate.exception.GeocodingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.mockito.ArgumentMatcher;
import java.net.URI;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeocodingServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GeocodingService geocodingService;

    private static final String TEST_ADDRESS = "123 Main St, Lisbon, Portugal";
    private static final ParameterizedTypeReference<List<Map<String, Object>>> RESPONSE_TYPE =
            new ParameterizedTypeReference<>() {};

    @BeforeEach
    void setUp() {
        List<Map<String, Object>> responseBody = new ArrayList<>();
        Map<String, Object> location = new HashMap<>();
        location.put("lat", "38.7223");
        location.put("lon", "-9.1393");
        responseBody.add(location);

        lenient().when(restTemplate.exchange(
            anyString(),
            eq(HttpMethod.GET),
            any(HttpEntity.class),
            eq(RESPONSE_TYPE)
        )).thenReturn(new ResponseEntity<>(responseBody, HttpStatus.OK));
    }

    @Test
    void getCoordinatesFromAddress_ValidAddress_ReturnsCoordinates() {
        Coordinates coordinates = geocodingService.getCoordinatesFromAddress(TEST_ADDRESS);
        assertNotNull(coordinates);
        assertEquals(38.7223, coordinates.getLatitude(), 0.0001);
        assertEquals(-9.1393, coordinates.getLongitude(), 0.0001);
    }

    @Test
    void getCoordinatesFromAddress_InvalidAddress_ThrowsException() {
        when(restTemplate.exchange(
            anyString(),
            eq(HttpMethod.GET),
            any(HttpEntity.class),
            eq(RESPONSE_TYPE)
        )).thenReturn(new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK));

        assertThrows(GeocodingException.class, () ->
            geocodingService.getCoordinatesFromAddress("Invalid Address")
        );
    }

    @Test
    void getCoordinatesFromAddress_EmptyAddress_ThrowsException() {
        assertThrows(GeocodingException.class, () ->
            geocodingService.getCoordinatesFromAddress("")
        );
    }

    @Test
    void getCoordinatesFromAddress_NullAddress_ThrowsException() {
        assertThrows(GeocodingException.class, () ->
            geocodingService.getCoordinatesFromAddress(null)
        );
    }

    @Test
    void getCoordinatesFromAddress_EmptyResponse_ThrowsException() {
        when(restTemplate.exchange(
            anyString(),
            eq(HttpMethod.GET),
            any(HttpEntity.class),
            eq(RESPONSE_TYPE)
        )).thenReturn(new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK));

        assertThrows(GeocodingException.class, () ->
            geocodingService.getCoordinatesFromAddress(TEST_ADDRESS)
        );
    }

    @Test
    void getCoordinatesFromAddress_ApiError_ThrowsException() {
        when(restTemplate.exchange(
            anyString(),
            eq(HttpMethod.GET),
            any(HttpEntity.class),
            eq(RESPONSE_TYPE)
        )).thenThrow(new RuntimeException("API Error"));

        assertThrows(GeocodingException.class, () ->
            geocodingService.getCoordinatesFromAddress(TEST_ADDRESS)
        );
    }
}