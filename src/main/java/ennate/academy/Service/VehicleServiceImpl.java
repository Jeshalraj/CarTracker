package ennate.academy.Service;

import ennate.academy.Entity.*;
import ennate.academy.exception.BadRequestException;
import ennate.academy.exception.ResourceNotFoundException;
import ennate.academy.repository.AlertRepository;
import ennate.academy.repository.VehicleReadingRepository;
import ennate.academy.repository.VehicleRepository;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Rules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleRepository repository;

    @Autowired
    VehicleReadingRepository vehicleReadingRepository;

    @Autowired
    AlertRepository alertRepository;



    @Override
    public Vehicles getAll(List<Vehicles> vehicles) {
        //System.out.println(vehicles);
        if(vehicles.size()==0) throw new BadRequestException("Null Vehicle Object given as an input");
        for(int i=0;i<vehicles.size();i++)
            repository.save(vehicles.get(i));
        return null;
    }

    @Override
    public void getReading(Readings reading){
        //System.out.println(reading);
        if(reading==null) throw new BadRequestException("Null Reading Object passed");
        Optional<Vehicles> veh = repository.findById(reading.getVin());
        if(!veh.isPresent()) throw new ResourceNotFoundException("Vehicle Id = " + reading.getVin() + " of a reading doesn't exist.");
        Vehicles vehicle = veh.get();

        VehicleReading vr = new VehicleReading(reading,vehicle);
        vehicleReadingRepository.save(vr);
        CompositeKey ck = new CompositeKey(vehicle,reading.getTimestamp());


        if(reading.getEngineRpm() > vehicle.getRedlineRpm()){
            Alert alert = new Alert(ck,"High",1);
            alertRepository.save(alert);
        }
        if((vehicle.getMaxFuelVolume() * 0.1) > reading.getFuelVolume()){
            Alert alert = new Alert(ck,"Medium",2);
            alertRepository.save(alert);
        }
        if(!checkTirePressure(reading.getTires())){
            Alert alert = new Alert(ck,"Low",3);
            alertRepository.save(alert);
        }
        if(reading.isEngineCoolantLow() || reading.isCheckEngineLightOn()){
            Alert alert = new Alert(ck,"Low",4);
            alertRepository.save(alert);
        }
    }


    @Override
    public List<Vehicles> getVehicles() {
        Iterable<Vehicles> vehicleData = repository.findAll();
        List<Vehicles> vehicles = new ArrayList<>();
        if(vehicleData != null) {
            for(Vehicles vh: vehicleData ) {
                vehicles.add(vh);
            }
        }
        else throw new ResourceNotFoundException("No Vehicle Data exist");

         return vehicles;
        //return null;
    }

    @Override
    public List<Alert> getHighAlerts() {
        LocalDateTime currentTime = LocalDateTime.now();
        List<Alert> Alerts = alertRepository.findHighAlerts();

            int i=0;
            while(i<Alerts.size()){
                Date date1= null;
                LocalDateTime readingDate = Alerts.get(i).getAlert().getTimestamp();
                LocalDateTime currentDate = LocalDateTime.now();
                //System.out.println(readingDate + " , " + currentDate);
                Duration duration = Duration.between(currentDate,readingDate);
                long diff = Math.abs(duration.toMinutes());

                if(diff>120){
                    Alerts.remove(i);
                }
                else i++;
            }
            //System.out.println("After = " + Alerts.size());
            return Alerts;



    }

    @Override
    public List<Alert> getAlerts(String vin) {

        Optional<Vehicles> vehicle = repository.findById(vin);
        if(!vehicle.isPresent()) throw new ResourceNotFoundException("Vehicle with id " + vin + " does not exist.");
        Vehicles veh = vehicle.get();

        List<Alert> al = alertRepository.getVehicleAlerts(veh);

        if(al.size()==0) throw new ResourceNotFoundException("No Alert Exist for a given vehicle " + vin);
        else return al;
    }

    @Override
    public List<List<Double>> getGeoLocation(String vehicleId) {
        if(vehicleId == null) throw new BadRequestException("No Vehicle Id given");
        LocalDateTime currentDate = LocalDateTime.now();
        Optional<Vehicles> vehicle = repository.findById(vehicleId);
        if(!vehicle.isPresent()) throw new ResourceNotFoundException("Vehicle with id " + vehicleId + " does not exist.");
        Vehicles veh = vehicle.get();
        List<VehicleReading> readings = vehicleReadingRepository.findByVehicleId(veh);

        List<List<Double>> result = new ArrayList<List<Double>>();
        if(readings != null) {
            for(VehicleReading rd: readings ) {
                LocalDateTime readingDate = rd.getVehicle().getTimestamp();
                Duration duration = Duration.between(currentDate,readingDate);
                long diff = Math.abs(duration.toMinutes());
                if(diff<30){
                    List<Double> vehicleReading = new ArrayList<>();
                    vehicleReading.add(rd.getLatitude());
                    vehicleReading.add(rd.getLongitude());
                    result.add(vehicleReading);
                }
            }return result;
        }else throw new ResourceNotFoundException("Vehicle with id " + vehicleId + " Readings does not exist");
    }



    private boolean checkTirePressure(Tires tire) {
        if((tire.getFrontLeft() < 32 || tire.getFrontLeft() > 36) ||
        (tire.getFrontRight() < 32 || tire.getFrontRight() > 36) ||
        (tire.getRearLeft() < 32 || tire.getRearLeft() > 36) ||
        (tire.getRearRight() < 32 || tire.getRearRight() > 36)) return true;

        return false;
    }
}
