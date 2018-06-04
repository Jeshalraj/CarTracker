package ennate.academy.Service;

import ennate.academy.Entity.Alert;
import ennate.academy.Entity.Readings;
import ennate.academy.Entity.Vehicles;

import java.util.List;

public interface VehicleService {

    Vehicles getAll(List<Vehicles> vehicles);
    void getReading(Readings reading);
    List<Vehicles> getVehicles();
    List<Alert> getHighAlerts();
    List<List<Double>> getGeoLocation(String vin);
    List<Alert> getAlerts(String vin);



}
