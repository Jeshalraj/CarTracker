package ennate.academy.repository;

import ennate.academy.Entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehicleReadingRepository extends CrudRepository<VehicleReading,CompositeKey> {

    @Query("SELECT vr FROM VehicleReading vr where vr.vehicle.vin = :vehicles")
    List<VehicleReading> findByVehicleId(@Param("vehicles")Vehicles vehicles);

    /*@Query("select al  from Alert al where Priority = 'High' order by timestamp desc")
    List<Alert> findHighAlerts();*/

}
