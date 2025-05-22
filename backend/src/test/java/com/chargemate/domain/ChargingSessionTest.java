package com.chargemate.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class ChargingSessionTest {
    
    private ChargingSession session;
    private Reservation reservation;
    private LocalDateTime startTime;
    
    @BeforeEach
    void setUp() {
        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        
        startTime = LocalDateTime.now();
        
        session = new ChargingSession();
        session.setId(1L);
        session.setReservationId(reservation.getId());
        session.setStartTime(startTime);
        session.setStatus(SessionStatus.ACTIVE);
        session.setEnergyConsumed(0.0);
    }
    
    @Test
    @DisplayName("Should create charging session with valid data")
    void testCreateChargingSession() {
        assertNotNull(session);
        assertEquals(1L, session.getId());
        assertEquals(reservation.getId(), session.getReservationId());
        assertEquals(startTime, session.getStartTime());
        assertEquals(SessionStatus.ACTIVE, session.getStatus());
        assertEquals(0.0, session.getEnergyConsumed());
    }
    
    @Test
    @DisplayName("Should update energy consumption")
    void testUpdateEnergyConsumption() {
        session.updateEnergyConsumption(25.5);
        assertEquals(25.5, session.getEnergyConsumed());
        
        session.updateEnergyConsumption(10.0);
        assertEquals(35.5, session.getEnergyConsumed());
    }
    
    @Test
    @DisplayName("Should complete charging session")
    void testCompleteChargingSession() {
        LocalDateTime endTime = startTime.plusHours(2);
        session.complete(endTime);
        
        assertEquals(SessionStatus.COMPLETED, session.getStatus());
        assertEquals(endTime, session.getEndTime());
    }
    
    @Test
    @DisplayName("Should calculate session duration")
    void testCalculateSessionDuration() {
        LocalDateTime endTime = startTime.plusHours(2);
        session.complete(endTime);
        
        assertEquals(2.0, session.calculateDurationInHours());
    }
    
    @Test
    @DisplayName("Should calculate session cost")
    void testCalculateSessionCost() {
        ChargingSlot slot = new ChargingSlot();
        slot.setPricePerHour(0.35);
        
        session.updateEnergyConsumption(50.0);
        LocalDateTime endTime = startTime.plusHours(2);
        session.complete(endTime);
        
        assertEquals(0.70, session.calculateCost(slot.getPricePerHour()));
    }
    
    @Test
    @DisplayName("Should not complete already completed session")
    void testCompleteAlreadyCompletedSession() {
        LocalDateTime endTime = startTime.plusHours(2);
        session.complete(endTime);
        
        assertThrows(IllegalStateException.class, () -> {
            session.complete(endTime.plusHours(1));
        });
    }
    
    @Test
    @DisplayName("Should not update energy consumption for completed session")
    void testUpdateEnergyConsumptionCompletedSession() {
        LocalDateTime endTime = startTime.plusHours(2);
        session.complete(endTime);
        
        assertThrows(IllegalStateException.class, () -> {
            session.updateEnergyConsumption(10.0);
        });
    }
} 