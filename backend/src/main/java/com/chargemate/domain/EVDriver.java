package com.chargemate.domain;

import java.util.*;

public class EVDriver extends User {
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<FavoriteStation> favoriteStations = new ArrayList<>();

    public void setId(Long id) { super.setId(id); }
    public void setEmail(String email) { super.setEmail(email); }
    public void setName(String name) { super.setName(name); }
    public void setPassword(String password) { super.setPassword(password); }

    public void addVehicle(Vehicle v) { vehicles.add(v); }
    public List<Vehicle> getVehicles() { return vehicles; }
    public Reservation createReservation(ChargingSlot slot, java.time.LocalDateTime start, java.time.LocalDateTime end) { throw new UnsupportedOperationException(); }
    public FavoriteStation addFavoriteStation(ChargingStation station) { throw new UnsupportedOperationException(); }
    public List<FavoriteStation> getFavoriteStations() { return favoriteStations; }
} 