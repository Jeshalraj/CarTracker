package ennate.academy.Service;

import ennate.academy.Entity.*;
import ennate.academy.exception.BadRequestException;
import ennate.academy.exception.ResourceNotFoundException;
import ennate.academy.repository.AlertRepository;
import ennate.academy.repository.VehicleReadingRepository;
import ennate.academy.repository.VehicleRepository;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class VehicleServiceImplTest {

    @TestConfiguration
    static class VehicleServiceImplTestConfiguration {

        @Bean
        public VehicleService getService() {
            return new VehicleServiceImpl();
        }
    }

    private List<Vehicles> vehicles;
    private List<Alert> alerts;

    @MockBean
    private VehicleRepository repository;

    @MockBean
    private VehicleReadingRepository vehicleReadingRepository;

    @MockBean
    private AlertRepository alertRepository;

    private Readings readingVehicle;
    private Readings vehicleReading;
    private List<Alert> alert;
    private Vehicles vh;

    @Before
    public void setup() {
        Vehicles veh = new Vehicles("WP1AB29P63LA60179","PORSCHE","CAYENNE",2015,8000,18,"2017-03-25T17:31:25.268Z");
        vehicles = new ArrayList<Vehicles>();
        vehicles.add(veh);
        Mockito.when(repository.save(vehicles.get(0))).thenReturn(veh);


        Tires tires = new Tires(34,36,29,34);
        readingVehicle = new Readings("WP1AB29P63LA60179",41.803194,-88.144406,"2017-05-25T17:31:25.268Z",1.5,85,240,false,true,true,6300,tires);
        Mockito.when(repository.findById(readingVehicle.getVin()))
                .thenReturn(Optional.of(veh));
        Mockito.when(repository.findAll())
                .thenReturn(vehicles)
                .thenThrow(ResourceNotFoundException.class);

        CompositeAlerts ck = new CompositeAlerts(veh,readingVehicle.getTimestamp(),1);
        Alert al = new Alert(ck,"High");
        alert = new ArrayList<>();
        alert = Collections.singletonList(al);
        Mockito.when(alertRepository.findHighAlerts())
                .thenThrow(ResourceNotFoundException.class);


        Vehicles vh = new Vehicles();
        vh.setVin("1HGB3456KKSL");
        Mockito.when(repository.findById(vh.getVin()))
                .thenReturn(Optional.of(vh));

        Vehicles vh1 = new Vehicles();
        vh.setVin("1HGB3456KKSLP");
        Mockito.when(repository.findById(vh.getVin()))
                .thenThrow(new ResourceNotFoundException("No Such Vehicle Exist"));
        Mockito.when(vehicleReadingRepository.findByVehicleId(vh))
                .thenThrow(new ResourceNotFoundException("No Such Reading exist for vehicle"));



    }

    @After
    public void cleanup() {
    }



    @Autowired
    private VehicleService service;



    private Readings reading;


    @Test(expected = BadRequestException.class)
    public void getEmptyVehicleException() {
            service.getAll(Collections.emptyList());
    }

    @Test
    public void getVehicle() {
        Vehicles veh = repository.save(vehicles.get(0));
        assertEquals("Vehicle id should match",vehicles.get(0).getVin(),veh.getVin());
    }

    @Test(expected = BadRequestException.class)
    public void getInValidReading() {
        Readings readings = null;
        service.getReading(readings);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getInValidVehicle() {
        Readings readings = new Readings();
        readings.setVin("ahajjaja");
        service.getReading(readings);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getNoVehicles(){
        service.getVehicles();
    }

    @Test
    public void getAllVehicles() {
        List<Vehicles> result = service.getVehicles();
        assertEquals("List should match",result,vehicles);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void getEmptyAlerts() {
        service.getHighAlerts();
    }

    /*@Test
    public void getHighAlerts(){
        List<Alert>  highAlerts = service.getHighAlerts();
        assertEquals("Both Alert List should match",highAlerts,alert);

    }*/

    @Test(expected = ResourceNotFoundException.class)
    public void getAlerts() {
        Vehicles vh = new Vehicles();
        service.getAlerts(vh.getVin());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void getVehicleForAlert(){
        service.getAlerts("1HGB3456KKSL");

    }

    @Test(expected =BadRequestException.class)
    public void getInvalidVehicle() {
        service.getGeoLocation(null);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getEmptyVehicle(){
        service.getGeoLocation("1HGB3456KKSLP");
    }

    @Test
    public void getGeoLocation(){
        service.getGeoLocation("WP1AB29P63LA60179");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getNoReading(){
        service.getGeoLocation("1HGB3456KKSL");

    }
}