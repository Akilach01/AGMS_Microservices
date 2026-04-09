package lk.ijse.ZoneService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private ZoneRepository zoneRepository;

    @postMapping
    public ResponseEntity<Zone> createZone(@RequestBody Zone zone){
        try{
            Zone createdZone = zoneService.createZone(zone);
            return new ResponseEntity<>(createdZone, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
