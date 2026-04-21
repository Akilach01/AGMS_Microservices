package lk.ijse.ZoneService.service;

import lk.ijse.ZoneService.dto.ZoneDto;
import lk.ijse.ZoneService.entity.Zone;
import lk.ijse.ZoneService.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository repo;
    private final IotApiClient iotApiClient;

    @Override
    public ZoneDto save(ZoneDto dto){
        if(dto.getMinTemp() >= dto.getMaxTemp()){
            throw new RuntimeException("minimum temp must be less than maximum temp");
        }
        String deviceId;
        try{
            deviceId = iotApiClient.registerDevice(
                    dto.getName() + "Sensor",
                    "Zone" + dto.getName()
            );
            log.info("Device registered with deviceId {}", deviceId);
        }catch (Exception e){
            log.error("Device registration failed", e.getMessage());
            deviceId = "fallback" + System.currentTimeMillis();
        }

        //dto to entity
        Zone zone =Zone.builder()
                .name(dto.getName())
                .minTemp(dto.getMinTemp())
                .maxTemp(dto.getMaxTemp())
                .deviceId(deviceId)
                .build();

        return toDTO(repo.save(zone));
    }

    @Override
    public ZoneDto update(int id, ZoneDto dto){
        Zone zone = repo.findById(id).orElseThrow(
                ()-> new RuntimeException("Zone not found with id " + id)
        );
        if (dto.getMinTemp() >= dto.getMaxTemp()){
            throw new RuntimeException("minimum temp must be less than maximum temp");
        }
        zone.setName(dto.getName());
        zone.setMinTemp(dto.getMinTemp());
        zone.setMaxTemp(dto.getMaxTemp());

        return  toDTO(repo.save(zone));
    }
    @Override


}
