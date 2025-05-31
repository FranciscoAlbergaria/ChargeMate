package com.chargemate.openchargemap.controller;

import com.chargemate.openchargemap.dto.StationDto;
import com.chargemate.openchargemap.service.OpenChargeMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {

    private final OpenChargeMapService mapService;

    @Operation(summary = "Get charging stations by coordinates and distance")
    @GetMapping
    public ResponseEntity<List<StationDto>> getStations(
            @Parameter(description = "Latitude of location") @RequestParam double lat,
            @Parameter(description = "Longitude of location") @RequestParam double lon,
            @Parameter(description = "Distance in KM (default 10)") @RequestParam(defaultValue = "10") double distance) {

        List<StationDto> stations = mapService.getStations(lat, lon, distance);
        return ResponseEntity.ok(stations);
    }
}
