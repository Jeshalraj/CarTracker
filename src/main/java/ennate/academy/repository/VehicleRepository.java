package ennate.academy.repository;

import ennate.academy.Entity.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VehicleRepository extends CrudRepository<Vehicles,String>  {
     /*void storeVehicleRecords(List<Vehicles> vehicles);
     Vehicles storeVehicleReading(Readings reading);
     List<Vehicles> getVehicles();
     void storeAlert(CompositeKey compositeKey, String priority, int id);
     List<Vehicles> findAll();
     List<Alert> fetchAlerts();

     List<Alert> fetchVehicleAlerts(String vehicleId);

     List<Readings> fetchVehicleGeoLocation(String vehicleId);*/
}
