package lk.ijse.ZoneService.dto;


import jdk.jfr.DataAmount;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ZoneDto {
    private int id;
    private String name;
    private Double minTemp;
    private Double maxTemp;
    private String deviceId;
}
