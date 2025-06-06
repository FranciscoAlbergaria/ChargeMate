package com.chargemate.openchargemap.controller;

import com.chargemate.openchargemap.dto.StationDto;
import com.chargemate.openchargemap.service.OpenChargeMapService;
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
import com.chargemate.service.JwtService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
    controllers = OpenChargeMapController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
)
@Import(TestSecurityConfig.class)
class OpenChargeMapControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OpenChargeMapService mapService;

    @MockBean
    private JwtService jwtService;

    private StationDto testStation;

    @BeforeEach
    void setUp() {
        testStation = new StationDto();
        testStation.setId(1L);
        StationDto.AddressInfo addressInfo = new StationDto.AddressInfo();
        addressInfo.setTitle("Test Station");
        addressInfo.setAddressLine1("123 Main St");
        addressInfo.setLatitude(38.7223);
        addressInfo.setLongitude(-9.1393);
        testStation.setAddressInfo(addressInfo);
    }

    @Test
    void getStations_ReturnsListOfStations() throws Exception {
        // Arrange
        List<StationDto> stations = Arrays.asList(testStation);
        when(mapService.getStations(anyDouble(), anyDouble(), anyDouble())).thenReturn(stations);

        // Act & Assert
        mockMvc.perform(get("/api/v1/openchargemap/stations")
                .param("lat", "38.7223")
                .param("lon", "-9.1393")
                .param("distance", "5.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].ID", is(1)))
                .andExpect(jsonPath("$[0].AddressInfo.Title", is("Test Station")));

        verify(mapService).getStations(38.7223, -9.1393, 5.0);
    }

    @Test
    void getStations_NoParameters_ReturnsBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/openchargemap/stations"))
                .andExpect(status().isBadRequest());

        verify(mapService, never()).getStations(anyDouble(), anyDouble(), anyDouble());
    }

    @Test
    void getStations_InvalidParameters_ReturnsBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/openchargemap/stations")
                .param("lat", "invalid")
                .param("lon", "-9.1393")
                .param("distance", "5.0"))
                .andExpect(status().isBadRequest());

        verify(mapService, never()).getStations(anyDouble(), anyDouble(), anyDouble());
    }
} 