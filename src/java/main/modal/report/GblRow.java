package main.modal.report;

import main.modal.*;
import java.util.UUID;

public class GblRow {

    private UUID id;
    private String idService;
    private String serviceName;
    private long nbT;
    private long nbTt;
    private long nbA;
    private long nbTl1;
    private long nbSa;
    private Double perApT;
    private Double pertl1Pt;
    private Double perSaPt;
    private Double avgSecA;
    private long nbCa;
    private Double percapt;
    private Double avgSecT;
    private long nbCt;
    private Double perctPt;
    private String date;
    private String idAgence;

    public GblRow() {
    }

    public GblRow(UUID id, String idService, String serviceName, long nbT, long nbTt, long nbA, long nbTl1, long nbSa, Double perApT, Double pertl1Pt, Double perSaPt, Double avgSecA, long nbCa, Double percapt, Double avgSecT, long nbCt, Double perctPt, String date, String idAgence) {
        this.id = id;
        this.idService = idService;
        this.serviceName = serviceName;
        this.nbT = nbT;
        this.nbTt = nbTt;
        this.nbA = nbA;
        this.nbTl1 = nbTl1;
        this.nbSa = nbSa;
        this.perApT = perApT;
        this.pertl1Pt = pertl1Pt;
        this.perSaPt = perSaPt;
        this.avgSecA = avgSecA;
        this.nbCa = nbCa;
        this.percapt = percapt;
        this.avgSecT = avgSecT;
        this.nbCt = nbCt;
        this.perctPt = perctPt;
        this.date = date;
        this.idAgence = idAgence;
    }

   

    public UUID getId() {
        return id;
    }

    public String getIdService() {
        return idService;
    }

    public String getServiceName() {
        return serviceName;
    }

    public long getNbT() {
        return nbT;
    }

    public long getNbTt() {
        return nbTt;
    }

    public long getNbA() {
        return nbA;
    }

    public long getNbTl1() {
        return nbTl1;
    }

    public long getNbSa() {
        return nbSa;
    }

    public Double getPerApT() {
        return perApT;
    }

    public Double getPertl1Pt() {
        return pertl1Pt;
    }

    public Double getPerSaPt() {
        return perSaPt;
    }

    public Double getAvgSecA() {
        return avgSecA;
    }

    public long getNbCa() {
        return nbCa;
    }

    public Double getPercapt() {
        return percapt;
    }

    public Double getAvgSecT() {
        return avgSecT;
    }

    public long getNbCt() {
        return nbCt;
    }

    public Double getPerctPt() {
        return perctPt;
    }

    public String getDate() {
        return date;
    }

    public String getIdAgence() {
        return idAgence;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setNbT(long nbT) {
        this.nbT = nbT;
    }

    public void setNbTt(long nbTt) {
        this.nbTt = nbTt;
    }

    public void setNbA(long nbA) {
        this.nbA = nbA;
    }

    public void setNbTl1(long nbTl1) {
        this.nbTl1 = nbTl1;
    }

    public void setNbSa(long nbSa) {
        this.nbSa = nbSa;
    }

    public void setPerApT(Double perApT) {
        this.perApT = perApT;
    }

    public void setPertl1Pt(Double pertl1Pt) {
        this.pertl1Pt = pertl1Pt;
    }

    public void setPerSaPt(Double perSaPt) {
        this.perSaPt = perSaPt;
    }

    public void setAvgSecA(Double avgSecA) {
        this.avgSecA = avgSecA;
    }

    public void setNbCa(long nbCa) {
        this.nbCa = nbCa;
    }

    public void setPercapt(Double percapt) {
        this.percapt = percapt;
    }

    public void setAvgSecT(Double avgSecT) {
        this.avgSecT = avgSecT;
    }

    public void setNbCt(long nbCt) {
        this.nbCt = nbCt;
    }

    public void setPerctPt(Double perctPt) {
        this.perctPt = perctPt;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIdAgence(String idAgence) {
        this.idAgence = idAgence;
    }

}
