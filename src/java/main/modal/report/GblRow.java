
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
    private String perApt;
    private String perTl1pt;
    private String perSapt;
    private String avgA;
    private long gCibleA;
    private String perCibleA;
    private String avgT;
    private long gCibleT;
    private String perCibleT;
    private  String[] gblCols = new String[]{"Site", "Service", "Nb. Tickets", "Nb. Traités", "Nb. Absents", "Nb. Traités <1mn", "Nb. Sans affectation", "Absents/Nb. Tickets(%)", "Traités<1mn/Nb. Tickets(%)", "Sans affect/Nb. Tickets(%)", "Moyenne d'attente", ">Cible", "%Cible", "Moyenne Traitement", ">Cible", "%Cible"};

    public GblRow(UUID id_db, String name_db, String biz_type_id,
            String service_name, long nb_t, long nb_tt, long nb_ta, long nb_ttl1, 
            long nb_tsa, String perApt, String perTl1pt, String perSapt, String avgA,
            long gCibleA, String perCibleA, String avgT, long gCibleT, String perCibleT) {
        this.id_db = id_db;
        this.name_db = name_db;
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

    public void setId_db(UUID id_db) {
        this.id_db = id_db;
    }

    public void setName_db(String name_db) {
        this.name_db = name_db;
    }

    public void setBiz_type_id(String biz_type_id) {
        this.biz_type_id = biz_type_id;
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

    public void setPerApt(String perApt) {
        this.perApt = perApt;
    }

    public void setPerTl1pt(String perTl1pt) {
        this.perTl1pt = perTl1pt;
    }

    public void setPerSapt(String perSapt) {
        this.perSapt = perSapt;
    }

    public void setAvgA(String avgA) {
        this.avgA = avgA;
    }

    public void setgCibleA(long gCibleA) {
        this.gCibleA = gCibleA;
    }

    public void setPerCibleA(String perCibleA) {
        this.perCibleA = perCibleA;
    }

    public void setAvgT(String avgT) {
        this.avgT = avgT;
    }

    public void setgCibleT(long gCibleT) {
        this.gCibleT = gCibleT;
    }

    public void setPerCibleT(String perCibleT) {
        this.perCibleT = perCibleT;
    }

    public void setGblCols(String[] gblCols) {
        this.gblCols = gblCols;
    }

    public UUID getId_db() {
        return id_db;
    }

    public String getName_db() {
        return name_db;
    }

    public String getBiz_type_id() {
        return biz_type_id;
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

    public String getPerApt() {
        return perApt;
    }

    public String getPerTl1pt() {
        return perTl1pt;
    }

    public String getPerSapt() {
        return perSapt;
    }

    public String getAvgA() {
        return avgA;
    }

    public long getgCibleA() {
        return gCibleA;
    }

    public String getPerCibleA() {
        return perCibleA;
    }

    public String getAvgT() {
        return avgT;
    }

    public long getgCibleT() {
        return gCibleT;
    }

    public String getPerCibleT() {
        return perCibleT;
    }

    public String[] getGblCols() {
        return gblCols;
    }

}