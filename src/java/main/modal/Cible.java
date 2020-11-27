
package main.modal;

import java.util.UUID;

public class Cible {
    String biz_type_id;
    UUID db_id;
    double cibleA;
    double cibleT;
    float dCible;

    public Cible(String biz_type_id, UUID db_id, double cibleA, double cibleT, float dCible) {
        this.biz_type_id = biz_type_id;
        this.db_id = db_id;
        this.cibleA = cibleA;
        this.cibleT = cibleT;
        this.dCible = dCible;
    }

    public String getBiz_type_id() {
        return biz_type_id;
    }

    public UUID getDb_id() {
        return db_id;
    }

    public double getCibleA() {
        return cibleA;
    }

    public double getCibleT() {
        return cibleT;
    }

    public float getdCible() {
        return dCible;
    }

    public void setBiz_type_id(String biz_type_id) {
        this.biz_type_id = biz_type_id;
    }

    public void setDb_id(UUID db_id) {
        this.db_id = db_id;
    }

    public void setCibleA(double cibleA) {
        this.cibleA = cibleA;
    }

    public void setCibleT(double cibleT) {
        this.cibleT = cibleT;
    }

    public void setdCible(float dCible) {
        this.dCible = dCible;
    }
    
}
