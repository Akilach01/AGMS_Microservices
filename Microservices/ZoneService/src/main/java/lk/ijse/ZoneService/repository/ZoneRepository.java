package lk.ijse.ZoneService.repository;

import lk.ijse.ZoneService.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Integer> {
    List<Zone> findByNameContainingIgnoreCase(String name);

    List<Zone> findByMinTempLessThanEqualAndMaxTempGreaterThanEqual(Double min, Double max);
}
