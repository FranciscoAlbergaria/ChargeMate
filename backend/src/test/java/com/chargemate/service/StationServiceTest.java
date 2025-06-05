package com.chargemate.service;

import com.chargemate.dto.Coordinates;
import com.chargemate.model.Station;
import com.chargemate.repository.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StationServiceTest {

    private static final double DELTA = 0.0001;

    @Mock
    private StationRepository stationRepository;

    @Mock
    private GeocodingService geocodingService;

    @InjectMocks
    private StationService stationService;

    private Station testStation;

    @BeforeEach
    void setUp() {
        testStation = new Station();
        testStation.setName("Test Station");
        testStation.setAddress("123 Main St");
        testStation.setLatitude(38.7223);
        testStation.setLongitude(-9.1393);
    }

    @Test
    void createStation_WithCoordinates_SavesStation() {
        // Arrange
        when(stationRepository.save(any(Station.class))).thenReturn(testStation);

        // Act
        Station savedStation = stationService.createStation(testStation);

        // Assert
        assertNotNull(savedStation);
        assertEquals("Test Station", savedStation.getName());
        assertEquals(38.7223, savedStation.getLatitude(), DELTA);
        assertEquals(-9.1393, savedStation.getLongitude(), DELTA);
        verify(stationRepository).save(testStation);
        verify(geocodingService, never()).getCoordinatesFromAddress(anyString());
    }

    @Test
    void createStation_WithoutCoordinates_FetchesAndSavesStation() {
        // Arrange
        Station station = new Station();
        station.setName("Test Station");
        station.setAddress("123 Main St");

        Coordinates coordinates = new Coordinates(38.7223, -9.1393);
        when(geocodingService.getCoordinatesFromAddress("123 Main St")).thenReturn(coordinates);
        when(stationRepository.save(any(Station.class))).thenReturn(station);

        // Act
        Station savedStation = stationService.createStation(station);

        // Assert
        assertNotNull(savedStation);
        assertEquals("Test Station", savedStation.getName());
        verify(geocodingService).getCoordinatesFromAddress("123 Main St");
        verify(stationRepository).save(station);
    }

    @Test
    void getAllStations_ReturnsAllStations() {
        // Arrange
        List<Station> stations = Arrays.asList(testStation, new Station());
        when(stationRepository.findAll()).thenReturn(stations);

        // Act
        List<Station> result = stationService.getAllStations();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(stationRepository).findAll();
    }

    @Test
    void getStationById_ExistingStation_ReturnsStation() {
        // Arrange
        Long stationId = 1L;
        testStation.setId(stationId);
        when(stationRepository.findById(stationId)).thenReturn(Optional.of(testStation));

        // Act
        Station result = stationService.getStationById(stationId);

        // Assert
        assertNotNull(result);
        assertEquals(stationId, result.getId());
        assertEquals("Test Station", result.getName());
        verify(stationRepository).findById(stationId);
    }

    @Test
    void getStationById_NonExistingStation_ThrowsException() {
        // Arrange
        Long stationId = 1L;
        when(stationRepository.findById(stationId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            stationService.getStationById(stationId)
        );
        verify(stationRepository).findById(stationId);
    }
} 