package ennate.academy.repository;

import ennate.academy.Entity.Alert;
import ennate.academy.Entity.CompositeAlerts;
import ennate.academy.Entity.VehicleReading;
import ennate.academy.Entity.Vehicles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface AlertRepository extends CrudRepository<Alert,CompositeAlerts> {



    @Query("select al  from Alert al where Priority = 'High' order by timestamp desc")
    List<Alert> findHighAlerts();

    @Query("SELECT alert FROM Alert alert where alert.alert.vin = :vehicles")
    List<Alert> getVehicleAlerts(@Param("vehicles") Vehicles vehicles);
}
