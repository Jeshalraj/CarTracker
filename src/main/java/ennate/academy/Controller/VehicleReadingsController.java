package ennate.academy.Controller;

import ennate.academy.Entity.Alert;
import ennate.academy.Entity.Readings;
import ennate.academy.Entity.Vehicles;
import ennate.academy.Service.VehicleService;
import ennate.academy.exception.BadRequestException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@Api(description = "Vehicle Controller endpoints")
public class VehicleReadingsController {

    @Autowired
    private VehicleService vehicleService;

    @CrossOrigin(origins = "http://mocker.egen.io")
    @PutMapping("/vehicles")
    @ApiOperation(value = "Store Vehicle Data",
                    notes = "Storing Vehicle Data using PUT Method")
    public void getAll(@RequestBody List<Vehicles> vehicles){
        vehicleService.getAll(vehicles);
        //vehicleService.getAll(Collections.emptyList());

    }

    @CrossOrigin(origins = "http://mocker.egen.io")
    @PostMapping("/readings")
    @ApiOperation(value = "Store Vehicle Readings",
                    notes = "Storing Vehicle Readings using Post Method")
    public void getReading(@RequestBody Readings reading){
        System.out.println(reading);
        vehicleService.getReading(reading);
    }

    @GetMapping("/vehicles")
    @ApiOperation(value = "Get All Vehicles",
                    notes = "Fetching All Vehicle Records from Database")
    public List<Vehicles> getVehicles(){
        return vehicleService.getVehicles();
    }

    @GetMapping("/fetchHighAlerts")
    @ApiOperation(value = "Get High Alerts",
                    notes = "Fetching all vehicles having high alerts in past 2 hours from Database")
    public List<Alert> getHighAlerts(){
        return vehicleService.getHighAlerts();
    }

    @GetMapping("/fetchGeoLocation/{vin}")
    @ApiOperation(value = "Get Vehicle Geo location",
            notes = "Returning Vehicle's geo location in past 30 mins ")
    public List<List<Double>> getGeoLocation(@PathVariable("vin") String vin){

        return vehicleService.getGeoLocation(vin);
    }

    @GetMapping("/fetchAlerts/{vin}")
    @ApiOperation(value = "Get All Alerts",
            notes = "Fetching All Vehicle Alert Records from Database")
    public List<Alert> getAlerts(@PathVariable("vin") String vin){
        return vehicleService.getAlerts(vin);
    }


}
