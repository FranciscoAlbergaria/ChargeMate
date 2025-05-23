package com.chargemate.domain;

public class ChargingSlot {
    public void setId(Long id) {}
    public void setSlotNumber(int n) {}
    public void setStatus(SlotStatus s) {}
    public void setPowerRating(double p) {}
    public void setConnectorType(String t) {}
    public void setPricePerHour(double p) {}
    public void setStationId(Long id) {}
    public Long getId() { return 1L; }
    public double getPricePerHour() { return 0.35; }
} 