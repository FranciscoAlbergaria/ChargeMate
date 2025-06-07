package com.chargemate.service;

import com.chargemate.model.Station;
import com.chargemate.repository.StationRepository;
import com.chargemate.exception.StationNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StationService {

    private final StationRepository stationRepository;
    private final GeocodingService geocodingService;

    public StationService(StationRepository stationRepository, GeocodingService geocodingService) {
        this.stationRepository = stationRepository;
        this.geocodingService = geocodingService;
    }

    public Station createStation(Station station) {
        if (station.getLatitude() == null || station.getLongitude() == null) {
            var coordinates = geocodingService.getCoordinatesFromAddress(station.getAddress());
            station.setLatitude(coordinates.getLatitude());
            station.setLongitude(coordinates.getLongitude());
        }
        return stationRepository.save(station);
    }

    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    public Station getStationById(Long id) {
        return stationRepository.findById(id)
                .orElseThrow(() -> new StationNotFoundException(id));
    }

    public List<Station> findStationsWithinRadius(double latitude, double longitude, double radiusKm) {
        List<Station> allStations = stationRepository.findAll();
        return allStations.stream()
                .filter(station -> {
                    double distance = haversineDistance(latitude, longitude, station.getLatitude(), station.getLongitude());
                    return distance <= radiusKm;
                })
                .toList();
    }
    
    private double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_KM = 6371;
    
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
    
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
    
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    
        return EARTH_RADIUS_KM * c;
    }
} 