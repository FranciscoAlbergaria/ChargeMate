package com.chargemate.openchargemap.service;

import com.chargemate.openchargemap.config.OpenChargeMapProperties;
import com.chargemate.openchargemap.dto.StationDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenChargeMapService {

    private final OpenChargeMapProperties properties;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        log.info("🚀 OpenChargeMapService initialized");
        log.info("🔑 Injected API Key: {}", properties.getKey());
    }

    public List<StationDto> getStations(double lat, double lon, double distance) {
        try {
            String apiUrl = UriComponentsBuilder.fromHttpUrl(properties.getBaseUrl())
                    .queryParam("output", "json")
                    .queryParam("countrycode", "PT")
                    .queryParam("latitude", lat)
                    .queryParam("longitude", lon)
                    .queryParam("distance", distance)
                    .queryParam("distanceunit", "KM")
                    .queryParam("key", properties.getKey())
                    .toUriString();

            log.info("🌐 Requesting data from: {}", apiUrl);

            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            int status = connection.getResponseCode();
            log.info("📡 Received HTTP status: {}", status);

            if (status != 200) {
                log.warn("❌ Failed to fetch data from OpenChargeMap API: status {}", status);
                return Collections.emptyList();
            }

            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder json = new StringBuilder();
            while (scanner.hasNext()) {
                json.append(scanner.nextLine());
            }
            scanner.close();

            log.debug("📦 Raw JSON response: {}", json);

            List<StationDto> stations = objectMapper.readValue(json.toString(), new TypeReference<>() {});
            log.info("✅ Parsed {} stations", stations.size());
            return stations;

        } catch (IOException e) {
            log.error("❗ Error while fetching stations from OpenChargeMap", e);
            return Collections.emptyList();
        }
    }
}
