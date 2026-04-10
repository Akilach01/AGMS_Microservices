package lk.ijse.ZoneService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<Zone> getAllZones(){
        return zoneRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Zone> getZoneById(@PathVariable Long id){
        return zoneRepository.findById(id)
                .map(zone -> new ResponseEntity<>(zone,HttpStatus. OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable Long id){
        if(zoneRepository.existById(id)){
            zoneRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}

   @PutMapping("/{id}")
    public ResponseEntity<Zone> updateZone(@PathVariable Long id, @RequestBody Zone zoneDetails){
        return zoneRepository.findById(id)
                .map(existingZone ->{

                    existingZone.setName(zoneDetails.getName());
                    existingZone.setMinTemp(zoneDetails.getMinTemp());
                    existingZone.setMAxTemp(zoneDetails.getMaxTemp());

                    if (existingZone.getMinTemp() >= existingZone.getMaxTemp()){
                        throw new IllegalArgumentException("Minimum temp exceeded");
                    }

                    Zone updateZone = zoneRepository.save(existingZone);
                    return new ResponseEntity<>(updateZone, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
   }


}
