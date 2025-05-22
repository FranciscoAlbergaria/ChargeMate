package com.chargemate.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ChargingStationTest {
    
    private ChargingStation station;
    private StationOperator operator;
    private Location location;
    
    @BeforeEach
    void setUp() {
        operator = new StationOperator();
        operator.setId(1L);
        operator.setCompanyName("Test Company");
        operator.setTaxId("123456789");
        
        location = new Location();
        location.setLatitude(38.7223);
        location.setLongitude(-9.1393);
        location.setAddress("Test Street 123");
        location.setCity("Lisbon");
        location.setCountry("Portugal");
        
        station = new ChargingStation();
        station.setId(1L);
        station.setName("Test Station");
        station.setLocation(location);
        station.setOperatorId(operator.getId());
        station.setStatus(StationStatus.ACTIVE);
    }
    
    @Test
    @DisplayName("Should create charging station with valid data")
    void testCreateChargingStation() {
        assertNotNull(station);
        assertEquals(1L, station.getId());
        assertEquals("Test Station", station.getName());
        assertEquals(StationStatus.ACTIVE, station.getStatus());
        assertEquals(operator.getId(), station.getOperatorId());
        assertEquals(location, station.getLocation());
    }
    
    @Test
    @DisplayName("Should add charging slot to station")
    void testAddChargingSlot() {
        ChargingSlot slot = new ChargingSlot();
        slot.setId(1L);
        slot.setSlotNumber(1);
        slot.setStatus(SlotStatus.AVAILABLE);
        slot.setPowerRating(50.0);
        slot.setConnectorType("Type 2");
        slot.setPricePerHour(0.35);
        
        station.addChargingSlot(slot);
        
        assertTrue(station.getChargingSlots().contains(slot));
        assertEquals(1, station.getChargingSlots().size());
        assertEquals(station.getId(), slot.getStationId());
    }
    
    @Test
    @DisplayName("Should update station status")
    void testUpdateStationStatus() {
        station.setStatus(StationStatus.MAINTENANCE);
        assertEquals(StationStatus.MAINTENANCE, station.getStatus());
        
        station.setStatus(StationStatus.ACTIVE);
        assertEquals(StationStatus.ACTIVE, station.getStatus());
    }
    
    @Test
    @DisplayName("Should calculate station availability")
    void testCalculateAvailability() {
        ChargingSlot slot1 = new ChargingSlot();
        slot1.setStatus(SlotStatus.AVAILABLE);
        
        ChargingSlot slot2 = new ChargingSlot();
        slot2.setStatus(SlotStatus.OCCUPIED);
        
        station.addChargingSlot(slot1);
        station.addChargingSlot(slot2);
        
        assertEquals(0.5, station.calculateAvailability());
    }
} 