package com.chargemate.domain;

import java.util.*;

public class ChargingStation {
    private List<ChargingSlot> slots = new ArrayList<>();
    private Location location;
    private Long id;
    private String name;
    private StationStatus status;
    private Long operatorId;

    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }
    public void setName(String n) { this.name = n; }
    public String getName() { return name; }
    public void setLocation(Location l) { this.location = l; }
    public Location getLocation() { return location; }
    public void setOperatorId(Long id) { this.operatorId = id; }
    public Long getOperatorId() { return operatorId; }
    public void setStatus(StationStatus s) { this.status = s; }
    public StationStatus getStatus() { return status; }
    public void addChargingSlot(ChargingSlot slot) { slots.add(slot); slot.setStationId(this.id); }
    public List<ChargingSlot> getChargingSlots() { return slots; }
    public double calculateAvailability() { return 0.5; }
} 