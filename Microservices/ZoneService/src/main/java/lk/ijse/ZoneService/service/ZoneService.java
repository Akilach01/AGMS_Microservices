package lk.ijse.ZoneService.service;

import lk.ijse.ZoneService.dto.DeviceRegistrationRequest;
import lk.ijse.ZoneService.dto.LoginRequest;
import lk.ijse.ZoneService.dto.LoginResponse;
import lk.ijse.ZoneService.entity.Zone;
import lk.ijse.ZoneService.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private IotExternalClient iotExternalClient;

    public Zone createZone(Zone zone) {
        if (zone.getMinTemp() >= zone.getMaxTemp()){
            throw new IllegalArgumentException("min temp must be less than max temp");
        }

        LoginResponse loginResponse = iotExternalClient.login(new LoginRequest("akila","1234"));
        String token = "Bearer "+loginResponse.getAccessToken();

        DeviceRegistrationRequest deviceRegistrationRequest = new DeviceRegistrationRequest(zone.getName()+ "-Sensor",zone.getName());
        Map<String,Object> deviceResponse = iotExternalClient.registerDevice(token,deviceRegistrationRequest);

        String externalDeviceId = (String) deviceResponse.get("deviceId");
        zone.setDeviceId(externalDeviceId);

        return zoneRepository.save(zone);
    }
}
