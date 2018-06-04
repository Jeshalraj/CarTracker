package ennate.academy.Entity;

import org.apache.tomcat.jni.Local;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Vehicles {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    private String vin;
    private String make;
    private String model;
    private int year;
    private int redlineRpm;
    private int maxFuelVolume;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime lastServiceDate;

    @Override
    public String toString() {
        return "Vehicles{" +
                "vin='" + vin + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", readlineRpm=" + redlineRpm +
                ", maxFuelVolume=" + maxFuelVolume +
                ", lastServiceDate='" + lastServiceDate + '\'' +
                '}';
    }

    public Vehicles(){

    }

    public Vehicles(String vin, String make, String model, int year, int redlineRpm, int maxFuelVolume, String lastServiceDate) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.redlineRpm = redlineRpm;
        this.maxFuelVolume = maxFuelVolume;
        this.lastServiceDate = LocalDateTime.parse(lastServiceDate.substring(0,23));
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getRedlineRpm() {
        return redlineRpm;
    }

    public void setRedlineRpm(int redlineRpm) {
        this.redlineRpm = redlineRpm;
    }

    public LocalDateTime getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(String lastServiceDate) {
        this.lastServiceDate = LocalDateTime.parse(lastServiceDate.substring(0,23));
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public int getMaxFuelVolume() {
        return maxFuelVolume;
    }

    public void setMaxFuelVolume(int maxFuelVolume) {
        this.maxFuelVolume = maxFuelVolume;
    }

   }
