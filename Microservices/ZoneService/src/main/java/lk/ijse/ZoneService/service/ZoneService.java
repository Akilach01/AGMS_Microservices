package lk.ijse.ZoneService.service;

import lk.ijse.ZoneService.dto.ZoneDto;

import java.util.List;

public interface ZoneService {

  ZoneDto save(ZoneDto dto);
  ZoneDto update(int id, ZoneDto dto);
  List<ZoneDto> getAll();
  ZoneDto getById(int id);
  void delete(int id);
  String checkZoneStatus(int id, double currentTemp);
  List<ZoneDto> searchByName(String name);
    List<ZoneDto> filterByTemperature(double temp);
    List<String> getAllDeviceIds();
    long countZones();

}
