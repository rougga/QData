package ma.rougga.qdata.modal.report;

import java.util.UUID;

public class EmpRow {

    private UUID id;
    private String userId;
    private String userName;
    private String agenceId;
    private String date;
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

    public EmpRow() {
    }

    public EmpRow(UUID id, String userId, String userName, String agenceId, String date, Long nbT, Long nbTt, Long nbA,
            Long nbTl1, Long nbSa, Double perApT, Double perTl1Pt, Double perSaPt, Double avgSecA,
            Long nbCa, Double perCapt, Double avgSecT, Long nbCt, Double perCtPt) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.agenceId = agenceId;
        this.date = date;
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
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAgenceId() {
        return agenceId;
    }

    public void setAgenceId(String agenceId) {
        this.agenceId = agenceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getNbT() {
        return nbT;
    }

    public void setNbT(Long nbT) {
        this.nbT = nbT;
    }

    public Long getNbTt() {
        return nbTt;
    }

    public void setNbTt(Long nbTt) {
        this.nbTt = nbTt;
    }

    public Long getNbA() {
        return nbA;
    }

    public void setNbA(Long nbA) {
        this.nbA = nbA;
    }

    public Long getNbTl1() {
        return nbTl1;
    }

    public void setNbTl1(Long nbTl1) {
        this.nbTl1 = nbTl1;
    }

    public Long getNbSa() {
        return nbSa;
    }

    public void setNbSa(Long nbSa) {
        this.nbSa = nbSa;
    }

    public Double getPerApT() {
        return perApT;
    }

    public void setPerApT(Double perApT) {
        this.perApT = perApT;
    }

    public Double getPerTl1Pt() {
        return perTl1Pt;
    }

    public void setPerTl1Pt(Double perTl1Pt) {
        this.perTl1Pt = perTl1Pt;
    }

    public Double getPerSaPt() {
        return perSaPt;
    }

    public void setPerSaPt(Double perSaPt) {
        this.perSaPt = perSaPt;
    }

    public Double getAvgSecA() {
        return avgSecA;
    }

    public void setAvgSecA(Double avgSecA) {
        this.avgSecA = avgSecA;
    }

    public Long getNbCa() {
        return nbCa;
    }

    public void setNbCa(Long nbCa) {
        this.nbCa = nbCa;
    }

    public Double getPerCapt() {
        return perCapt;
    }

    public void setPerCapt(Double perCapt) {
        this.perCapt = perCapt;
    }

    public Double getAvgSecT() {
        return avgSecT;
    }

    public void setAvgSecT(Double avgSecT) {
        this.avgSecT = avgSecT;
    }

    public Long getNbCt() {
        return nbCt;
    }

    public void setNbCt(Long nbCt) {
        this.nbCt = nbCt;
    }

    public Double getPerCtPt() {
        return perCtPt;
    }

    public void setPerCtPt(Double perCtPt) {
        this.perCtPt = perCtPt;
    }
}
