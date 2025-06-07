package com.chargemate.controller;

import com.chargemate.model.Station;
import com.chargemate.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/internal/stations")
@RequiredArgsConstructor
public class InternalStationController {

    private final StationService stationService;

    @GetMapping("/nearby")
    public ResponseEntity<List<Station>> getNearbyStations(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "10") double distance) {

        List<Station> stations = stationService.findStationsWithinRadius(lat, lon, distance);
        return ResponseEntity.ok(stations);
    }
}
