package ennate.academy.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;


@Entity
public class VehicleReading {

    @EmbeddedId
    private CompositeKey vehicle;
    private double latitude;
    private double longitude;
    private double fuelVolume;
    private int speed;
    private int engineHp;
    private boolean checkEngineLightOn;
    private boolean engineCoolantLow;
    private boolean cruiseControlOn;
    private int engineRpm;
    private int frontLeft;
    private int frontRight;
    private int rearLeft;
    private int rearRight;



    public VehicleReading(){
    }

    public VehicleReading(Readings reading,Vehicles vehicle) {
        this.vehicle = new CompositeKey(vehicle,reading.getTimestamp());
        this.latitude = reading.getLatitude();
        this.longitude = reading.getLongitude();
        this.fuelVolume = reading.getFuelVolume();
        this.speed = reading.getSpeed();
        this.engineHp = reading.getEngineHp();
        this.checkEngineLightOn = reading.isCheckEngineLightOn();
        this.engineCoolantLow = reading.isEngineCoolantLow();
        this.cruiseControlOn = reading.isCruiseControlOn();
        this.engineRpm = reading.getEngineRpm();
        this.frontLeft = reading.getTires().getFrontLeft();
        this.frontRight = reading.getTires().getFrontRight();
        this.rearLeft = reading.getTires().getRearLeft();
        this.rearRight = reading.getTires().getRearRight();
    }

    public CompositeKey getVehicle() {
        return vehicle;
    }

    public void setVehicle(CompositeKey vehicle) {
        this.vehicle = vehicle;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getFuelVolume() {
        return fuelVolume;
    }

    public void setFuelVolume(double fuelVolume) {
        this.fuelVolume = fuelVolume;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getEngineHp() {
        return engineHp;
    }

    public void setEngineHp(int engineHp) {
        this.engineHp = engineHp;
    }

    public boolean isCheckEngineLightOn() {
        return checkEngineLightOn;
    }

    public void setCheckEngineLightOn(boolean checkEngineLightOn) {
        this.checkEngineLightOn = checkEngineLightOn;
    }

    public boolean isEngineCoolantLow() {
        return engineCoolantLow;
    }

    public void setEngineCoolantLow(boolean engineCoolantLow) {
        this.engineCoolantLow = engineCoolantLow;
    }

    public boolean isCruiseControlOn() {
        return cruiseControlOn;
    }

    public void setCruiseControlOn(boolean cruiseControlOn) {
        this.cruiseControlOn = cruiseControlOn;
    }

    public int getEngineRpm() {
        return engineRpm;
    }

    public void setEngineRpm(int engineRpm) {
        this.engineRpm = engineRpm;
    }

    /*public Tires getTires() {
        return tires;
    }

    public void setTires(Tires tires) {
        this.tires = tires;
    }*/
}
