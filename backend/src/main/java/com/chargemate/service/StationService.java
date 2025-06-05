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
} 