package com.chargemate.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import app.getxray.xray.junit.extensions.annotations.XrayTest;

@XrayTest(projectKey = "CHARGEMATE", testPlanKey = "CHARGEMATE-1")
class EVDriverTest {
    
    private EVDriver driver;
    
    @BeforeEach
    void setUp() {
        driver = new EVDriver();
        driver.setId(1L);
        driver.setEmail("test@example.com");
        driver.setName("Test Driver");
        driver.setPassword("password123");
    }
    
    @Test
    @DisplayName("Should create EVDriver with valid data")
    @XrayTest(testKey = "CHARGEMATE-2")
    void testCreateEVDriver() {
        assertNotNull(driver);
        assertEquals(1L, driver.getId());
        assertEquals("test@example.com", driver.getEmail());
        assertEquals("Test Driver", driver.getName());
        assertEquals("password123", driver.getPassword());
    }
    
    @Test
    @DisplayName("Should add vehicle to driver")
    @XrayTest(testKey = "CHARGEMATE-3")
    void testAddVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setLicensePlate("ABC123");
        vehicle.setModel("Tesla Model 3");
        vehicle.setBrand("Tesla");
        vehicle.setBatteryCapacity(75.0);
        
        driver.addVehicle(vehicle);
        
        assertTrue(driver.getVehicles().contains(vehicle));
        assertEquals(1, driver.getVehicles().size());
    }
    
    @Test
    @DisplayName("Should create reservation")
    @XrayTest(testKey = "CHARGEMATE-4")
    void testCreateReservation() {
        ChargingSlot slot = new ChargingSlot();
        slot.setId(1L);
        
        Reservation reservation = driver.createReservation(slot, 
            LocalDateTime.now(), 
            LocalDateTime.now().plusHours(2));
        
        assertNotNull(reservation);
        assertEquals(driver.getId(), reservation.getDriverId());
        assertEquals(slot.getId(), reservation.getSlotId());
        assertEquals(ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    @DisplayName("Should add favorite station")
    @XrayTest(testKey = "CHARGEMATE-5")
    void testAddFavoriteStation() {
        ChargingStation station = new ChargingStation();
        station.setId(1L);
        
        FavoriteStation favorite = driver.addFavoriteStation(station);
        
        assertNotNull(favorite);
        assertEquals(driver.getId(), favorite.getDriverId());
        assertEquals(station.getId(), favorite.getStationId());
        assertTrue(driver.getFavoriteStations().contains(favorite));
    }
} 