package com.chargemate.openchargemap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class StationDto {

    @JsonProperty("ID")
    private Long id;

    @JsonProperty("AddressInfo")
    private AddressInfo addressInfo;

    @JsonProperty("Connections")
    private List<Connection> connections;

    @Data
    public static class AddressInfo {
        @JsonProperty("Title")
        private String title;

        @JsonProperty("AddressLine1")
        private String addressLine1;

        @JsonProperty("Town")
        private String town;

        @JsonProperty("StateOrProvince")
        private String stateOrProvince;

        @JsonProperty("Postcode")
        private String postcode;

        @JsonProperty("Latitude")
        private Double latitude;

        @JsonProperty("Longitude")
        private Double longitude;
    }

    @Data
    public static class Connection {
        @JsonProperty("ConnectionTypeID")
        private Integer connectionTypeId;

        @JsonProperty("LevelID")
        private Integer levelId;

        @JsonProperty("CurrentTypeID")
        private Integer currentTypeId;

        @JsonProperty("Amps")
        private Integer amps;

        @JsonProperty("Voltage")
        private Integer voltage;

        @JsonProperty("PowerKW")
        private Double powerKW;
    }
}
