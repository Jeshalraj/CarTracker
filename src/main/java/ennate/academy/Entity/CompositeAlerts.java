package ennate.academy.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Embeddable
public class CompositeAlerts implements Serializable {


    @ManyToOne

    private Vehicles vin;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime timestamp;

    private int ruleId;

    public CompositeAlerts(Vehicles vin, String timestamp, int ruleId) {
        this.vin = vin;
        this.timestamp = LocalDateTime.parse(timestamp.substring(0,23));
        this.ruleId = ruleId;
    }

    public CompositeAlerts(){

    }

    public Vehicles getVin() {
        return vin;
    }

    public void setVin(Vehicles vin) {
        this.vin = vin;
    }

    /*public String getVin() {
            return vin;
        }

        public void setVin(String vin) {
            this.vin = vin;
        }
        */

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = LocalDateTime.parse(timestamp.substring(0,23));
    }
}
