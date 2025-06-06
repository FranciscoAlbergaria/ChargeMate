package com.chargemate.integration;

import com.chargemate.openchargemap.dto.StationDto;
import com.chargemate.openchargemap.service.OpenChargeMapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.web.client.TestRestTemplate;
import app.getxray.xray.junit.customjunitxml.annotations.Requirement;
import org.springframework.context.annotation.Import;
import com.chargemate.config.TestRestTemplateConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(TestRestTemplateConfig.class)
public class StationOpenChargeControllerIT extends AbstractIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private OpenChargeMapService openChargeMapService;

    @Test
    @Requirement("CMATE-70")
    void shouldReturnStationsNearLocation() {
        // Arrange
        String url = "http://localhost:" + port + "/api/v1/openchargemap/stations?lat=38.7223&lon=-9.1393&distance=5";

        // Mock the service response
        StationDto mockStation = new StationDto();
        StationDto.AddressInfo addressInfo = new StationDto.AddressInfo();
        addressInfo.setTitle("Test Station");
        addressInfo.setLatitude(38.7223);
        addressInfo.setLongitude(-9.1393);
        mockStation.setAddressInfo(addressInfo);

        when(openChargeMapService.getStations(anyDouble(), anyDouble(), anyDouble()))
            .thenReturn(Arrays.asList(mockStation));

        // Act
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Assert
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()).contains("Test Station");
        assertThat(response.getBody()).contains("38.7223");
        assertThat(response.getBody()).contains("-9.1393");
    }
}