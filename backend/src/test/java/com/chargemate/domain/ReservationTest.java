package com.chargemate.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class ReservationTest {
    
    private Reservation reservation;
    private EVDriver driver;
    private ChargingSlot slot;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    @BeforeEach
    void setUp() {
        driver = new EVDriver();
        driver.setId(1L);
        
        slot = new ChargingSlot();
        slot.setId(1L);
        slot.setStatus(SlotStatus.AVAILABLE);
        
        startTime = LocalDateTime.now();
        endTime = startTime.plusHours(2);
        
        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setDriverId(driver.getId());
        reservation.setSlotId(slot.getId());
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setStatus(ReservationStatus.PENDING);
    }
    
    @Test
    @DisplayName("Should create reservation with valid data")
    void testCreateReservation() {
        assertNotNull(reservation);
        assertEquals(1L, reservation.getId());
        assertEquals(driver.getId(), reservation.getDriverId());
        assertEquals(slot.getId(), reservation.getSlotId());
        assertEquals(startTime, reservation.getStartTime());
        assertEquals(endTime, reservation.getEndTime());
        assertEquals(ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    @DisplayName("Should confirm reservation")
    void testConfirmReservation() {
        reservation.confirm();
        assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    @DisplayName("Should cancel reservation")
    void testCancelReservation() {
        reservation.cancel();
        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
    }
    
    @Test
    @DisplayName("Should start charging session")
    void testStartChargingSession() {
        reservation.confirm();
        ChargingSession session = reservation.startChargingSession();
        
        assertNotNull(session);
        assertEquals(reservation.getId(), session.getReservationId());
        assertEquals(SessionStatus.ACTIVE, session.getStatus());
        assertNotNull(session.getStartTime());
        assertNull(session.getEndTime());
        assertEquals(0.0, session.getEnergyConsumed());
    }
    
    @Test
    @DisplayName("Should not start charging session for unconfirmed reservation")
    void testStartChargingSessionUnconfirmed() {
        assertThrows(IllegalStateException.class, () -> {
            reservation.startChargingSession();
        });
    }
    
    @Test
    @DisplayName("Should not start charging session for cancelled reservation")
    void testStartChargingSessionCancelled() {
        reservation.cancel();
        assertThrows(IllegalStateException.class, () -> {
            reservation.startChargingSession();
        });
    }
} 