
package ma.rougga.qdata.modal;

import java.util.UUID;

public class Cible {
    String service_id,service_name;
    UUID agence_id;
    long cibleA;
    long cibleT;
    double ciblePer;

    public Cible() {
    }

    public Cible(String service_id, String service_name, UUID agence_id, long cibleA, long cibleT, double ciblePer) {
        this.service_id = service_id;
        this.service_name = service_name;
        this.agence_id = agence_id;
        this.cibleA = cibleA;
        this.cibleT = cibleT;
        this.ciblePer = ciblePer;
    }

    public String getService_id() {
        return service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public UUID getAgence_id() {
        return agence_id;
    }

    public long getCibleA() {
        return cibleA;
    }

    public long getCibleT() {
        return cibleT;
    }

    public double getCiblePer() {
        return ciblePer;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public void setAgence_id(UUID agence_id) {
        this.agence_id = agence_id;
    }

    public void setCibleA(long cibleA) {
        this.cibleA = cibleA;
    }

    public void setCibleT(long cibleT) {
        this.cibleT = cibleT;
    }

    public void setCiblePer(double ciblePer) {
        this.ciblePer = ciblePer;
    }

}
