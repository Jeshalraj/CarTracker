package ennate.academy.Entity;

import javax.persistence.*;

@Entity
public class Alert {


    @EmbeddedId
    private CompositeAlerts alert;
    private String priority;

    public Alert(){

    }
    public Alert(CompositeAlerts ck, String high){
            this.alert = ck;
            this.priority = high;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "alert=" + alert +
                ", priority='" + priority + '\'' +
                '}';
    }

    public Alert(CompositeKey compositeKey, String priority, int ruleId) {
        //this.vehicle = vehicle;
        alert = new CompositeAlerts(compositeKey.getVin(),compositeKey.getTimestamp().toString(),ruleId);
        this.priority = priority;
    }

    public CompositeAlerts getAlert() {
        return alert;
    }

    public void setAlert(CompositeAlerts alert) {
        this.alert = alert;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
