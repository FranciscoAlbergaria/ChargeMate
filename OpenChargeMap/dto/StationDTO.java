package OpenChargeMap.dto;

import lombok.Data;
import java.util.List;

@Data
public class StationDto {
    private Long ID;
    private AddressInfo AddressInfo;
    private List<Connection> Connections;

    @Data
    public static class AddressInfo {
        private String Title;
        private String AddressLine1;
        private String Town;
        private Double Latitude;
        private Double Longitude;
    }

    @Data
    public static class Connection {
        private String ConnectionTypeTitle;
        private Integer Amps;
        private Double PowerKW;
    }
}
