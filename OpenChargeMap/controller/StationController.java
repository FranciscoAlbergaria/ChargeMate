package OpenChargeMap.controller;

import OpenChargeMap.dto.StationDto;
import OpenChargeMap.service.OpenChargeMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {

    private final OpenChargeMapService mapService;

    @GetMapping
    public ResponseEntity<List<StationDto>> getStations(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "10") double distance) {

        List<StationDto> stations = mapService.getStations(lat, lon, distance);
        return ResponseEntity.ok(stations);
    }
}
