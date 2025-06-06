package com.chargemate.service;

import com.chargemate.dto.Coordinates;
import com.chargemate.exception.GeocodingException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;


import java.util.List;
import java.util.Map;

@Service
public class GeocodingService {

    private static final String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/search";
    private static final String USER_AGENT = "ChargeMate/1.0";

    private final RestTemplate restTemplate;

    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Coordinates getCoordinatesFromAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new GeocodingException("Address cannot be null or empty");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", USER_AGENT);

        String url = UriComponentsBuilder.fromHttpUrl(NOMINATIM_API_URL)
                .queryParam("q", address)
                .queryParam("format", "json")
                .queryParam("limit", 1)
                .build()
                .toUriString();

        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );

            if (response.getBody() == null || response.getBody().isEmpty()) {
                throw new GeocodingException("No coordinates found for address: " + address);
            }

            Map<String, Object> location = response.getBody().get(0);
            Double lat = Double.parseDouble(location.get("lat").toString());
            Double lon = Double.parseDouble(location.get("lon").toString());

            return new Coordinates(lat, lon);
        } catch (Exception e) {
            throw new GeocodingException("Failed to get coordinates for address: " + address, e);
        }
    }
} 