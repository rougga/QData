
package main.modal.report;

import java.util.UUID;

public class GblRow {
    private UUID id_db;
    private String name_db;
    private String biz_type_id;
    private String service_name;
    private long nb_t;
    private long nb_tt;
    private long nb_ta;
    private long nb_ttl1;
    private long nb_tsa;
    private float perApt;
    private float perTl1pt;
    private float perSapt;
    private float avgA;
    private long gCibleA;
    private float perCibleA;
    private float avgT;
    private long gCibleT;
    private float perCibleT;
    private  String[] gblCols = new String[]{"Site", "Service", "Nb. Tickets", "Nb. Traités", "Nb. Absents", "Nb. Traités <1mn", "Nb. Sans affectation", "Absents/Nb. Tickets(%)", "Traités<1mn/Nb. Tickets(%)", "Sans affect/Nb. Tickets(%)", "Moyenne d'attente", ">Cible", "%Cible", "Moyenne Traitement", ">Cible", "%Cible"};

    
    public GblRow(UUID id_db,String name_db,String biz_type_id, String service_name, long nb_t, long nb_tt, long nb_ta, long nb_ttl1, long nb_tsa, float perApt, float perTl1pt, float perSapt, float avgA, long gCibleA, float perCibleA, float avgT, long gCibleT, float perCibleT) {
        this.id_db = id_db;
        this.name_db=name_db;
        this.biz_type_id = biz_type_id;
        this.service_name = service_name;
        this.nb_t = nb_t;
        this.nb_tt = nb_tt;
        this.nb_ta = nb_ta;
        this.nb_ttl1 = nb_ttl1;
        this.nb_tsa = nb_tsa;
        this.perApt = perApt;
        this.perTl1pt = perTl1pt;
        this.perSapt = perSapt;
        this.avgA = avgA;
        this.gCibleA = gCibleA;
        this.perCibleA = perCibleA;
        this.avgT = avgT;
        this.gCibleT = gCibleT;
        this.perCibleT = perCibleT;
    }

    public UUID getId_db() {
        return id_db;
    }

    public String getService_name() {
        return service_name;
    }

    public long getNb_t() {
        return nb_t;
    }

    public long getNb_tt() {
        return nb_tt;
    }

    public long getNb_ta() {
        return nb_ta;
    }

    public long getNb_ttl1() {
        return nb_ttl1;
    }

    public long getNb_tsa() {
        return nb_tsa;
    }

    public float getPerApt() {
        return perApt;
    }

    public float getPerTl1pt() {
        return perTl1pt;
    }

    public float getPerSapt() {
        return perSapt;
    }

    public float getAvgA() {
        return avgA;
    }

    public long getgCibleA() {
        return gCibleA;
    }

    public float getPerCibleA() {
        return perCibleA;
    }

    public float getAvgT() {
        return avgT;
    }

    public long getgCibleT() {
        return gCibleT;
    }

    public float getPerCibleT() {
        return perCibleT;
    }

    public void setId_db(UUID id_db) {
        this.id_db = id_db;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public void setNb_t(long nb_t) {
        this.nb_t = nb_t;
    }

    public void setNb_tt(long nb_tt) {
        this.nb_tt = nb_tt;
    }

    public void setNb_ta(long nb_ta) {
        this.nb_ta = nb_ta;
    }

    public void setNb_ttl1(long nb_ttl1) {
        this.nb_ttl1 = nb_ttl1;
    }

    public void setNb_tsa(long nb_tsa) {
        this.nb_tsa = nb_tsa;
    }

    public void setPerApt(float perApt) {
        this.perApt = perApt;
    }

    public void setPerTl1pt(float perTl1pt) {
        this.perTl1pt = perTl1pt;
    }

    public void setPerSapt(float perSapt) {
        this.perSapt = perSapt;
    }

    public void setAvgA(float avgA) {
        this.avgA = avgA;
    }

    public void setgCibleA(long gCibleA) {
        this.gCibleA = gCibleA;
    }

    public void setPerCibleA(float perCibleA) {
        this.perCibleA = perCibleA;
    }

    public void setAvgT(float avgT) {
        this.avgT = avgT;
    }

    public void setgCibleT(long gCibleT) {
        this.gCibleT = gCibleT;
    }

    public void setPerCibleT(float perCibleT) {
        this.perCibleT = perCibleT;
    }

    public String[] getGblCols() {
        return gblCols;
    }

    public String getName_db() {
        return name_db;
    }

    public String getBiz_type_id() {
        return biz_type_id;
    }

    public void setName_db(String name_db) {
        this.name_db = name_db;
    }

    public void setBiz_type_id(String biz_type_id) {
        this.biz_type_id = biz_type_id;
    }
    
}
