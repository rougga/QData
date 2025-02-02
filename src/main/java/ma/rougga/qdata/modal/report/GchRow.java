package ma.rougga.qdata.modal.report;

import java.util.UUID;

public class GchRow {

    private UUID id;
    private String agenceId;
    private String guichetId;
    private String guichetName;
    private Long nbT;
    private Long nbTt;
    private Long nbA;
    private Long nbTl1;
    private Long nbSa;
    private Double perApT;
    private Double perTl1Pt;
    private Double perSaPt;
    private Double avgSecA;
    private Long nbCa;
    private Double perCapt;
    private Double avgSecT;
    private Long nbCt;
    private Double perCtPt;
    private String date;

    public GchRow() {
    }

    public GchRow(UUID id, String agenceId, String guichetId, String guichetName, Long nbT, Long nbTt, Long nbA, Long nbTl1, Long nbSa, Double perApT, Double perTl1Pt, Double perSaPt, Double avgSecA, Long nbCa, Double perCapt, Double avgSecT, Long nbCt, Double perCtPt, String date) {
        this.id = id;
        this.agenceId = agenceId;
        this.guichetId = guichetId;
        this.guichetName = guichetName;
        this.nbT = nbT;
        this.nbTt = nbTt;
        this.nbA = nbA;
        this.nbTl1 = nbTl1;
        this.nbSa = nbSa;
        this.perApT = perApT;
        this.perTl1Pt = perTl1Pt;
        this.perSaPt = perSaPt;
        this.avgSecA = avgSecA;
        this.nbCa = nbCa;
        this.perCapt = perCapt;
        this.avgSecT = avgSecT;
        this.nbCt = nbCt;
        this.perCtPt = perCtPt;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public String getAgenceId() {
        return agenceId;
    }

    public String getGuichetId() {
        return guichetId;
    }

    public String getGuichetName() {
        return guichetName;
    }

    public Long getNbT() {
        return nbT;
    }

    public Long getNbTt() {
        return nbTt;
    }

    public Long getNbA() {
        return nbA;
    }

    public Long getNbTl1() {
        return nbTl1;
    }

    public Long getNbSa() {
        return nbSa;
    }

    public Double getPerApT() {
        return perApT;
    }

    public Double getPerTl1Pt() {
        return perTl1Pt;
    }

    public Double getPerSaPt() {
        return perSaPt;
    }

    public Double getAvgSecA() {
        return avgSecA;
    }

    public Long getNbCa() {
        return nbCa;
    }

    public Double getPerCapt() {
        return perCapt;
    }

    public Double getAvgSecT() {
        return avgSecT;
    }

    public Long getNbCt() {
        return nbCt;
    }

    public Double getPerCtPt() {
        return perCtPt;
    }

    public String getDate() {
        return date;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAgenceId(String agenceId) {
        this.agenceId = agenceId;
    }

    public void setGuichetId(String guichetId) {
        this.guichetId = guichetId;
    }

    public void setGuichetName(String guichetName) {
        this.guichetName = guichetName;
    }

    public void setNbT(Long nbT) {
        this.nbT = nbT;
    }

    public void setNbTt(Long nbTt) {
        this.nbTt = nbTt;
    }

    public void setNbA(Long nbA) {
        this.nbA = nbA;
    }

    public void setNbTl1(Long nbTl1) {
        this.nbTl1 = nbTl1;
    }

    public void setNbSa(Long nbSa) {
        this.nbSa = nbSa;
    }

    public void setPerApT(Double perApT) {
        this.perApT = perApT;
    }

    public void setPerTl1Pt(Double perTl1Pt) {
        this.perTl1Pt = perTl1Pt;
    }

    public void setPerSaPt(Double perSaPt) {
        this.perSaPt = perSaPt;
    }

    public void setAvgSecA(Double avgSecA) {
        this.avgSecA = avgSecA;
    }

    public void setNbCa(Long nbCa) {
        this.nbCa = nbCa;
    }

    public void setPerCapt(Double perCapt) {
        this.perCapt = perCapt;
    }

    public void setAvgSecT(Double avgSecT) {
        this.avgSecT = avgSecT;
    }

    public void setNbCt(Long nbCt) {
        this.nbCt = nbCt;
    }

    public void setPerCtPt(Double perCtPt) {
        this.perCtPt = perCtPt;
    }

    public void setDate(String date) {
        this.date = date;
    }

   
}
