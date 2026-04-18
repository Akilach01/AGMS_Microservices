package lk.ijse.ZoneService.controller;


import lk.ijse.ZoneService.dto.ZoneDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
@CrossOrigin
public class ZoneController {

    @Autowired
    private final ZoneService zoneService;

    @PostMapping
    public ZoneDto save(RequestBody ZoneDto dto){
       return service.save(dto);
    }

    @PutMapping("/{id}")
    public ZoneDto update(@PathVariable String id, @RequestBody ZoneDto dto){
        return service.update(id, dto);
    }

    @GetMapping
    public List<ZoneDto> all(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ZoneDto findById(@PathVariable String id){
        return service.getById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id){
        service.delete(id);
    }

    @GetMapping("/{id}/status")
    public String chechStatus(@PathVariable int id, @RequestParam Double temp){
        return service.checkZoneStatus(id, temp);
    }
    @GetMapping("/search")
    public List<ZoneDto> search(@RequestParam String name){
        return service.searchByName(name);
    }
    @GetMapping("/filter")
    public List<ZoneDto> filter(@RequestParam Double temp){
        return service.filterByTemperature(temp);
    }
    @GetMapping("/devices")
    public List<String> getDevices(){
        return service.getAllDeviceIds();
    }
    @GetMapping("/count")
    public Long count(){
        return service.countZones();
    }

}
