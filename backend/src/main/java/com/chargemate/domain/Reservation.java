package com.chargemate.domain;

import java.time.LocalDateTime;

public class Reservation {
    public void setId(Long id) {}
    public void setDriverId(Long id) {}
    public void setSlotId(Long id) {}
    public void setStartTime(LocalDateTime t) {}
    public void setEndTime(LocalDateTime t) {}
    public void setStatus(ReservationStatus s) {}
    public Long getId() { return 1L; }
    public Long getDriverId() { return 1L; }
    public Long getSlotId() { return 1L; }
    public LocalDateTime getStartTime() { return LocalDateTime.now(); }
    public LocalDateTime getEndTime() { return LocalDateTime.now().plusHours(2); }
    public ReservationStatus getStatus() { return ReservationStatus.PENDING; }
    public void confirm() { throw new UnsupportedOperationException(); }
    public void cancel() { throw new UnsupportedOperationException(); }
    public ChargingSession startChargingSession() { throw new UnsupportedOperationException(); }
} 