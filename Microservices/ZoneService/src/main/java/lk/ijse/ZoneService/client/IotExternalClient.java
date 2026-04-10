package lk.ijse.ZoneService.client;

import lk.ijse.ZoneService.dto.DeviceRegistrationRequest;
import lk.ijse.ZoneService.dto.LoginRequest;
import lk.ijse.ZoneService.dto.LoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name ="iot-external-service",url ="")
public interface IotExternalClient {

    @PostMapping("/auth/login")
    LoginResponse login(@RequestBody LoginRequest loginRequest);

    @PostMapping("/devices")
    Map<String,Object> registerDevice(@RequestBody DeviceRegistrationRequest deviceRegistrationRequest);


}
