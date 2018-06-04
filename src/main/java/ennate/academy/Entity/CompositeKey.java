package ennate.academy.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
public class CompositeKey implements Serializable {


    @ManyToOne
    private Vehicles vin;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime timestamp;

    public CompositeKey(){

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

    public CompositeKey(Vehicles vin, String timestamp) {
        this.vin = vin;
        this.timestamp = LocalDateTime.parse(timestamp.substring(0,23));
    }
}
