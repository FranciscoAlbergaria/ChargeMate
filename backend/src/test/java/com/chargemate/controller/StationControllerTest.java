package com.chargemate.controller;

import com.chargemate.dto.Coordinates;
import com.chargemate.model.Station;
import com.chargemate.service.GeocodingService;
import com.chargemate.service.StationService;
import com.chargemate.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import com.chargemate.config.TestSecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import com.chargemate.security.JwtAuthenticationFilter;
import com.chargemate.exception.StationNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
    controllers = StationController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
)
@Import(TestSecurityConfig.class)
class StationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StationService stationService;

    @MockBean
    private GeocodingService geocodingService;

    @MockBean
    private JwtService jwtService;

    private Station testStation;

    @BeforeEach
    void setUp() {
        testStation = new Station();
        testStation.setId(1L);
        testStation.setName("Test Station");
        testStation.setAddress("123 Main St");
        testStation.setLatitude(38.7223);
        testStation.setLongitude(-9.1393);
    }

    @Test
    void createStation_ValidStation_ReturnsCreatedStation() throws Exception {
        // Arrange
        when(stationService.createStation(any(Station.class))).thenReturn(testStation);

        // Act & Assert
        mockMvc.perform(post("/api/v1/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testStation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Station")))
                .andExpect(jsonPath("$.address", is("123 Main St")))
                .andExpect(jsonPath("$.latitude", is(38.7223)))
                .andExpect(jsonPath("$.longitude", is(-9.1393)));

        verify(stationService).createStation(any(Station.class));
    }

    @Test
    void getAllStations_ReturnsListOfStations() throws Exception {
        // Arrange
        List<Station> stations = Arrays.asList(testStation);
        when(stationService.getAllStations()).thenReturn(stations);

        // Act & Assert
        mockMvc.perform(get("/api/v1/stations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Test Station")));

        verify(stationService).getAllStations();
    }

    @Test
    void getStationById_ExistingStation_ReturnsStation() throws Exception {
        // Arrange
        when(stationService.getStationById(1L)).thenReturn(testStation);

        // Act & Assert
        mockMvc.perform(get("/api/v1/stations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Station")))
                .andExpect(jsonPath("$.address", is("123 Main St")));

        verify(stationService).getStationById(1L);
    }

    @Test
    void getStationById_NonExistingStation_ReturnsNotFound() throws Exception {
        // Arrange
        when(stationService.getStationById(999L))
                .thenThrow(new StationNotFoundException(999L));

        // Act & Assert
        mockMvc.perform(get("/api/v1/stations/999"))
                .andExpect(status().isNotFound());

        verify(stationService).getStationById(999L);
    }
} 