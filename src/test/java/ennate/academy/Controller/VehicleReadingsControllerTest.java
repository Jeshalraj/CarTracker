package ennate.academy.Controller;

import com.fasterxml.jackson.core.json.WriterBasedJsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import ennate.academy.Entity.*;
import ennate.academy.repository.AlertRepository;
import ennate.academy.repository.VehicleReadingRepository;
import ennate.academy.repository.VehicleRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.ByteArrayOutputStream;
import java.io.Writer;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
public class VehicleReadingsControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    VehicleRepository repository;
    @Autowired
    VehicleReadingRepository vehicleReadingRepository;
    @Autowired
    AlertRepository alertRepository;

    @Before
    public void setup() {
        Vehicles veh = new Vehicles("WP1AB29P63LA60179","PORSCHE","CAYENNE",2015,8000,18,"2017-03-25T17:31:25.268Z");
        Tires tires = new Tires(34,36,29,34);
        VehicleReading vr = new VehicleReading();
        vr.setVehicle(new CompositeKey(veh,"2017-05-25T17:31:25.268Z"));
        CompositeKey ck = new CompositeKey(veh,"2017-05-25T17:31:25.268Z");
        Alert alert = new Alert(ck,"High",1);

        repository.save(veh);
        vehicleReadingRepository.save(vr);
        alertRepository.save(alert);


    }

    @After
    public void cleanup() {
        alertRepository.deleteAll();
        vehicleReadingRepository.deleteAll();
        repository.deleteAll();
    }

    @Test
    public void getAll() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Vehicles veh = new Vehicles("1HGCR2F3XFA027534","PORSCHE","CAYENNE",2015,8000,18,"2017-03-25T17:31:25.268Z");
        List<Vehicles> vehicles = Arrays.asList(veh);

        mvc.perform(MockMvcRequestBuilders.put("/vehicles")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(vehicles))
        )
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
    }

    @Test
    public void getReading()throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        Readings readings = new Readings();
        readings.setVin("WP1AB29P63LA60179");
        readings.setTimestamp("2017-03-25T17:31:25.268Z");
        readings.setTires(new Tires(34,36,29,34));
        mvc.perform(MockMvcRequestBuilders.post("/readings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(readings))
        )
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());

    }

    @Test
    public void getVehicles() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
    }

    @Test
    public void getHighAlerts() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/fetchHighAlerts"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
    }

    @Test
    public void getGeoLocation() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fetchGeoLocation/WP1AB29P63LA60179"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());

    }

    @Test
    public void getGeoLocation404() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fetchGeoLocation/ahdndcdklsl8947"))
                .andExpect(MockMvcResultMatchers.status()
                        .isNotFound());

    }

    @Test
    public void getAlerts() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/fetchAlerts/WP1AB29P63LA60179"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
    }

    @Test
    public void getAlerts404() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fetchAlerts/ahdndcdkl"))
                .andExpect(MockMvcResultMatchers.status()
                        .isNotFound());
    }


}