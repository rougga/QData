
package ma.rougga.qdata.modal.report;

import java.util.UUID;

public class GltRow {
    private UUID id;
    private String date;
    private String agenceId;
    private String serviceId;
    private String serviceName;
    private Long s0_15;
    private Long s15_30;
    private Long s30_60;
    private Long s60_90;
    private Long s90_120;
    private Long s120;
    private Long m0_5;
    private Long m5_10;
    private Long m10_20;
    private Long m20_30;
    private Long m30_45;
    private Long m45_50;
    private Long m50;
    private Long total;

    // Constructors, Getters, and Setters
    public GltRow() {}

    public GltRow(UUID id, String date, String agenceId, String serviceId, String serviceName, Long s0_15, Long s15_30, Long s30_60, Long s60_90, Long s90_120, Long s120, Long m0_5, Long m5_10, Long m10_20, Long m20_30, Long m30_45, Long m45_50, Long m50, Long total) {
        this.id = id;
        this.date = date;
        this.agenceId = agenceId;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.s0_15 = s0_15;
        this.s15_30 = s15_30;
        this.s30_60 = s30_60;
        this.s60_90 = s60_90;
        this.s90_120 = s90_120;
        this.s120 = s120;
        this.m0_5 = m0_5;
        this.m5_10 = m5_10;
        this.m10_20 = m10_20;
        this.m20_30 = m20_30;
        this.m30_45 = m30_45;
        this.m45_50 = m45_50;
        this.m50 = m50;
        this.total = total;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAgenceId() {
        return agenceId;
    }

    public void setAgenceId(String agenceId) {
        this.agenceId = agenceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getS0_15() {
        return s0_15;
    }

    public void setS0_15(Long s0_15) {
        this.s0_15 = s0_15;
    }

    public Long getS15_30() {
        return s15_30;
    }

    public void setS15_30(Long s15_30) {
        this.s15_30 = s15_30;
    }

    public Long getS30_60() {
        return s30_60;
    }

    public void setS30_60(Long s30_60) {
        this.s30_60 = s30_60;
    }

    public Long getS60_90() {
        return s60_90;
    }

    public void setS60_90(Long s60_90) {
        this.s60_90 = s60_90;
    }

    public Long getS90_120() {
        return s90_120;
    }

    public void setS90_120(Long s90_120) {
        this.s90_120 = s90_120;
    }

    public Long getS120() {
        return s120;
    }

    public void setS120(Long s120) {
        this.s120 = s120;
    }

    public Long getM0_5() {
        return m0_5;
    }

    public void setM0_5(Long m0_5) {
        this.m0_5 = m0_5;
    }

    public Long getM5_10() {
        return m5_10;
    }

    public void setM5_10(Long m5_10) {
        this.m5_10 = m5_10;
    }

    public Long getM10_20() {
        return m10_20;
    }

    public void setM10_20(Long m10_20) {
        this.m10_20 = m10_20;
    }

    public Long getM20_30() {
        return m20_30;
    }

    public void setM20_30(Long m20_30) {
        this.m20_30 = m20_30;
    }

    public Long getM30_45() {
        return m30_45;
    }

    public void setM30_45(Long m30_45) {
        this.m30_45 = m30_45;
    }

    public Long getM45_50() {
        return m45_50;
    }

    public void setM45_50(Long m45_50) {
        this.m45_50 = m45_50;
    }

    public Long getM50() {
        return m50;
    }

    public void setM50(Long m50) {
        this.m50 = m50;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
