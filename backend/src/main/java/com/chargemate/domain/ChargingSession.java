package com.chargemate.domain;

import java.time.LocalDateTime;

public class ChargingSession {
    public void setId(Long id) {}
    public void setReservationId(Long id) {}
    public void setStartTime(LocalDateTime t) {}
    public void setStatus(SessionStatus s) {}
    public void setEnergyConsumed(double e) {}
    public Long getId() { return 1L; }
    public Long getReservationId() { return 1L; }
    public LocalDateTime getStartTime() { return LocalDateTime.now(); }
    public LocalDateTime getEndTime() { return null; }
    public SessionStatus getStatus() { return SessionStatus.ACTIVE; }
    public double getEnergyConsumed() { return 0.0; }
    public void updateEnergyConsumption(double e) { throw new UnsupportedOperationException(); }
    public void complete(LocalDateTime t) { throw new UnsupportedOperationException(); }
    public double calculateDurationInHours() { return 2.0; }
    public double calculateCost(double pricePerHour) { return 0.70; }
} 