package OpenChargeMap.service;

import OpenChargeMap.config.OpenChargeMapProperties;
import OpenChargeMap.dto.StationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenChargeMapService {

    private final OpenChargeMapProperties properties;

    public List<StationDto> getStations(double lat, double lon, double distanceKm) {
        WebClient webClient = WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("output", "json")
                        .queryParam("latitude", lat)
                        .queryParam("longitude", lon)
                        .queryParam("distance", distanceKm)
                        .queryParam("distanceunit", "KM")
                        .queryParam("maxresults", 50)
                        .queryParam("key", properties.getKey())
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<StationDto>>() {})
                .block();
    }
}
