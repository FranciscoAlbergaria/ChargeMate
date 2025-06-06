package com.chargemate.integration;

import com.chargemate.model.Station;
import com.chargemate.repository.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.ResponseEntity;
import app.getxray.xray.junit.customjunitxml.annotations.Requirement;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class StationNearbyIntegrationIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        stationRepository.deleteAll();

        // Estação dentro do raio (~0.03km de distância)
        Station closeStation = new Station();
        closeStation.setName("Nearby Station");
        closeStation.setAddress("Avenida da Liberdade, Lisboa");
        closeStation.setLatitude(38.7225);
        closeStation.setLongitude(-9.1390);

        // Estação fora do raio (~10km de distância real)
        Station farStation = new Station();
        farStation.setName("Far Station");
        farStation.setAddress("Cascais");
        farStation.setLatitude(38.6970);
        farStation.setLongitude(-9.4200);

        stationRepository.saveAll(List.of(closeStation, farStation));
    }

    @Test
    @Requirement("CMATE-70")
    void shouldReturnOnlyStationsWithinRadius() {
        double lat = 38.7223;
        double lon = -9.1393;
        double radius = 2.0;

        String url = String.format("/api/v1/internal/stations/nearby?lat=%s&lon=%s&distance=%s", lat, lon, radius);

        ResponseEntity<Station[]> response = restTemplate.getForEntity(url, Station[].class);

        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Body: " + response.getBody());

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        Station[] stations = response.getBody();

        assertThat(stations).isNotNull();
        assertThat(stations.length).isEqualTo(1);
        assertThat(stations[0].getName()).isEqualTo("Nearby Station");
    }
}