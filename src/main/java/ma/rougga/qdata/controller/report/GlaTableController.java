package ma.rougga.qdata.controller.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import ma.rougga.qdata.CPConnection;
import ma.rougga.qdata.CfgHandler;
import ma.rougga.qdata.controller.AgenceController;
import ma.rougga.qdata.controller.UpdateController;
import ma.rougga.qdata.modal.Agence;
import ma.rougga.qdata.modal.report.GlaRow;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlaTableController {

    private static final Logger logger = LoggerFactory.getLogger(GlaTableController.class);
    private final AgenceController ac = new AgenceController();

    public boolean addRow(GlaRow row) {
        String sql = "INSERT INTO rougga_gla_table ("
                + "id, date, agence_id, service_id, service_name, "
                + "s0_15, s15_30, s30_60, s60_90, s90_120, s120, "
                + "m0_5, m5_10, m10_20, m20_30, m30_45, m45_50, m50, total) "
                + "VALUES (?, to_date(?,'YYYY-MM-DD HH24:MI:SS'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = new CPConnection().getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, row.getId().toString());
            pstmt.setString(2, row.getDate());
            pstmt.setString(3, row.getAgenceId());
            pstmt.setString(4, row.getServiceId());
            pstmt.setString(5, row.getServiceName());
            pstmt.setLong(6, row.getS0_15());
            pstmt.setLong(7, row.getS15_30());
            pstmt.setLong(8, row.getS30_60());
            pstmt.setLong(9, row.getS60_90());
            pstmt.setLong(10, row.getS90_120());
            pstmt.setLong(11, row.getS120());
            pstmt.setLong(12, row.getM0_5());
            pstmt.setLong(13, row.getM5_10());
            pstmt.setLong(14, row.getM10_20());
            pstmt.setLong(15, row.getM20_30());
            pstmt.setLong(16, row.getM30_45());
            pstmt.setLong(17, row.getM45_50());
            pstmt.setLong(18, row.getM50());
            pstmt.setLong(19, row.getTotal());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                con.close();
                return true;
            } else {
                logger.error("A  row wasnt inserted successfully!");
                con.close();
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error inserting row into rougga_gla_table: " + e.getMessage());
            return false;
        }
    }

    public boolean updateRow(GlaRow row) {
        String sql = "UPDATE rougga_gla_table SET "
                + "date = to_date(?,'YYYY-MM-DD HH24:MI:SS'), agence_id = ?, service_id = ?, service_name = ?, "
                + "s0_15 = ?, s15_30 = ?, s30_60 = ?, s60_90 = ?, s90_120 = ?, s120 = ?, "
                + "m0_5 = ?, m5_10 = ?, m10_20 = ?, m20_30 = ?, m30_45 = ?, m45_50 = ?, m50 = ?, total = ? "
                + "WHERE id = ?";

        try (Connection con = new CPConnection().getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, row.getDate());
            pstmt.setString(2, row.getAgenceId());
            pstmt.setString(3, row.getServiceId());
            pstmt.setString(4, row.getServiceName());
            pstmt.setLong(5, row.getS0_15());
            pstmt.setLong(6, row.getS15_30());
            pstmt.setLong(7, row.getS30_60());
            pstmt.setLong(8, row.getS60_90());
            pstmt.setLong(9, row.getS90_120());
            pstmt.setLong(10, row.getS120());
            pstmt.setLong(11, row.getM0_5());
            pstmt.setLong(12, row.getM5_10());
            pstmt.setLong(13, row.getM10_20());
            pstmt.setLong(14, row.getM20_30());
            pstmt.setLong(15, row.getM30_45());
            pstmt.setLong(16, row.getM45_50());
            pstmt.setLong(17, row.getM50());
            pstmt.setLong(18, row.getTotal());
            pstmt.setString(19, row.getId().toString());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                con.close();
                return true;
            } else {
                logger.error("updateRow: row wasnt updated successfully!");
                con.close();
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error updating row in rougga_gla_table: " + e.getMessage());
            return false;
        }
    }

    public GlaRow getRowById(String id) {
        String sql = "SELECT * FROM rougga_gla_table WHERE id = ?";
        GlaRow row = null;

        try (Connection con = new CPConnection().getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                row = new GlaRow();
                row.setId(UUID.fromString(rs.getString("id")));
                row.setDate(CfgHandler.getFormatedDateAsString(CfgHandler.getFormatedDateAsDate(rs.getString("date"))));
                row.setAgenceId(rs.getString("agence_id"));
                row.setServiceId(rs.getString("service_id"));
                row.setServiceName(rs.getString("service_name"));
                row.setS0_15(rs.getLong("s0_15"));
                row.setS15_30(rs.getLong("s15_30"));
                row.setS30_60(rs.getLong("s30_60"));
                row.setS60_90(rs.getLong("s60_90"));
                row.setS90_120(rs.getLong("s90_120"));
                row.setS120(rs.getLong("s120"));
                row.setM0_5(rs.getLong("m0_5"));
                row.setM5_10(rs.getLong("m5_10"));
                row.setM10_20(rs.getLong("m10_20"));
                row.setM20_30(rs.getLong("m20_30"));
                row.setM30_45(rs.getLong("m30_45"));
                row.setM45_50(rs.getLong("m45_50"));
                row.setM50(rs.getLong("m50"));
                row.setTotal(rs.getLong("total"));
            } else {
                logger.info("No row found for id: " + id);
            }
            con.close();

        } catch (SQLException e) {
            logger.error("Error retrieving row from rougga_gla_table: " + e.getMessage());
        }

        return row;
    }

    public boolean batchInsert(List<GlaRow> rows) {
        boolean isSuccess = false;
        try {
            Connection con = new CPConnection().getConnection();
            con.setAutoCommit(false); // Disable auto-commit
            String sql = "INSERT INTO rougga_gla_table ("
                    + "id, date, agence_id, service_id, service_name, "
                    + "s0_15, s15_30, s30_60, s60_90, s90_120, s120, "
                    + "m0_5, m5_10, m10_20, m20_30, m30_45, m45_50, m50, total) "
                    + "VALUES (?, to_date(?,'YYYY-MM-DD HH24:MI:SS'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            for (GlaRow row : rows) {
                pstmt.setString(1, row.getId().toString());
                pstmt.setString(2, row.getDate());
                pstmt.setString(3, row.getAgenceId());
                pstmt.setString(4, row.getServiceId());
                pstmt.setString(5, row.getServiceName());
                pstmt.setLong(6, row.getS0_15());
                pstmt.setLong(7, row.getS15_30());
                pstmt.setLong(8, row.getS30_60());
                pstmt.setLong(9, row.getS60_90());
                pstmt.setLong(10, row.getS90_120());
                pstmt.setLong(11, row.getS120());
                pstmt.setLong(12, row.getM0_5());
                pstmt.setLong(13, row.getM5_10());
                pstmt.setLong(14, row.getM10_20());
                pstmt.setLong(15, row.getM20_30());
                pstmt.setLong(16, row.getM30_45());
                pstmt.setLong(17, row.getM45_50());
                pstmt.setLong(18, row.getM50());
                pstmt.setLong(19, row.getTotal());
                pstmt.addBatch(); // Add to batch
            }

            int[] batchResults = pstmt.executeBatch(); // Execute batch
            con.commit(); // Commit transaction
            isSuccess = batchResults.length == rows.size();
            logger.info("batchInsert: inserted " + batchResults.length + " rows");
            con.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return isSuccess;
    }

    public boolean batchUpdate(List<GlaRow> rows) {
        boolean isSuccess = false;
        try {
            Connection con = new CPConnection().getConnection();
            con.setAutoCommit(false); // Disable auto-commit
            con.setAutoCommit(false); // Disable auto-commit
            String sql = "UPDATE rougga_gla_table SET "
                    + "date = to_date(?,'YYYY-MM-DD HH24:MI:SS'), agence_id = ?, service_id = ?, service_name = ?, "
                    + "s0_15 = ?, s15_30 = ?, s30_60 = ?, s60_90 = ?, s90_120 = ?, s120 = ?, "
                    + "m0_5 = ?, m5_10 = ?, m10_20 = ?, m20_30 = ?, m30_45 = ?, m45_50 = ?, m50 = ?, total = ? "
                    + "WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            for (GlaRow row : rows) {
                pstmt.setString(1, row.getDate());
                pstmt.setString(2, row.getAgenceId());
                pstmt.setString(3, row.getServiceId());
                pstmt.setString(4, row.getServiceName());
                pstmt.setLong(5, row.getS0_15());
                pstmt.setLong(6, row.getS15_30());
                pstmt.setLong(7, row.getS30_60());
                pstmt.setLong(8, row.getS60_90());
                pstmt.setLong(9, row.getS90_120());
                pstmt.setLong(10, row.getS120());
                pstmt.setLong(11, row.getM0_5());
                pstmt.setLong(12, row.getM5_10());
                pstmt.setLong(13, row.getM10_20());
                pstmt.setLong(14, row.getM20_30());
                pstmt.setLong(15, row.getM30_45());
                pstmt.setLong(16, row.getM45_50());
                pstmt.setLong(17, row.getM50());
                pstmt.setLong(18, row.getTotal());
                pstmt.setString(19, row.getId().toString());
                pstmt.addBatch(); // Add to batch
            }

            int[] batchResults = pstmt.executeBatch(); // Execute batch
            con.commit();// Commit transaction
            isSuccess = batchResults.length == rows.size();
            logger.info("batchUpdate: updated " + batchResults.length + " rows");
            con.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return isSuccess;
    }

    //
    public GlaRow getTotaleRowByAgence(String agence_id, String date1, String date2) {
        GlaRow row = new GlaRow();
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();

            String sql = "SELECT agence_id, 'Sous-Totale' AS service_name, "
                    + "SUM(s0_15) AS s0_15, SUM(s15_30) AS s15_30, SUM(s30_60) AS s30_60, "
                    + "SUM(s60_90) AS s60_90, SUM(s90_120) AS s90_120, SUM(s120) AS s120, "
                    + "SUM(m0_5) AS m0_5, SUM(m5_10) AS m5_10, SUM(m10_20) AS m10_20, "
                    + "SUM(m20_30) AS m20_30, SUM(m30_45) AS m30_45, SUM(m45_50) AS m45_50, "
                    + "SUM(m50) AS m50, SUM(total) AS total "
                    + "FROM rougga_gla_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') "
                    + "BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
                    + "AND agence_id = ? "
                    + "GROUP BY agence_id";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date1); // Start date
            pstmt.setString(2, date2); // End date
            pstmt.setString(3, agence_id); // agence_id

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row.setId(null);
                row.setDate(null);
                row.setAgenceId(rs.getString("agence_id"));
                row.setServiceId(null);
                row.setServiceName(rs.getString("service_name"));
                row.setS0_15(rs.getLong("s0_15"));
                row.setS15_30(rs.getLong("s15_30"));
                row.setS30_60(rs.getLong("s30_60"));
                row.setS60_90(rs.getLong("s60_90"));
                row.setS90_120(rs.getLong("s90_120"));
                row.setS120(rs.getLong("s120"));
                row.setM0_5(rs.getLong("m0_5"));
                row.setM5_10(rs.getLong("m5_10"));
                row.setM10_20(rs.getLong("m10_20"));
                row.setM20_30(rs.getLong("m20_30"));
                row.setM30_45(rs.getLong("m30_45"));
                row.setM45_50(rs.getLong("m45_50"));
                row.setM50(rs.getLong("m50"));
                row.setTotal(rs.getLong("total"));
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return row;
    }

    public GlaRow getTotaleRow(String date1, String date2, String[] agences) {
        GlaRow row = new GlaRow();
        try {
            // Establish connection
            if (agences == null || agences.length <= 0) {
                agences = ac.putAgencesToStringArray(ac.getAllAgence());
            }

            Connection con = new CPConnection().getConnection();
            StringBuilder sqlBuilder = new StringBuilder("");
            if (agences.length > 0) {
                sqlBuilder.append(" AND  agence_id IN (");
                for (int i = 0; i < agences.length; i++) {
                    sqlBuilder.append("?");
                    if (i < agences.length - 1) {
                        sqlBuilder.append(", ");
                    }
                }
                sqlBuilder.append(");");
            }

            String agenceCondition = sqlBuilder.toString();

            String sql = "SELECT 'Totale' AS service_name, "
                    + "SUM(s0_15) AS s0_15, SUM(s15_30) AS s15_30, SUM(s30_60) AS s30_60, "
                    + "SUM(s60_90) AS s60_90, SUM(s90_120) AS s90_120, SUM(s120) AS s120, "
                    + "SUM(m0_5) AS m0_5, SUM(m5_10) AS m5_10, SUM(m10_20) AS m10_20, "
                    + "SUM(m20_30) AS m20_30, SUM(m30_45) AS m30_45, SUM(m45_50) AS m45_50, "
                    + "SUM(m50) AS m50, SUM(total) AS total "
                    + "FROM rougga_gla_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') "
                    + "BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')"
                    + agenceCondition;
            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date1); // Start date
            pstmt.setString(2, date2); // End date

            // adding selected agences to preparedstatement
            for (int i = 0; i < agences.length; i++) {
                pstmt.setString(i + 3, agences[i]);
            }

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row.setId(null);
                row.setDate(null);
                row.setAgenceId(null);
                row.setServiceId(null);
                row.setServiceName(rs.getString("service_name"));
                row.setS0_15(rs.getLong("s0_15"));
                row.setS15_30(rs.getLong("s15_30"));
                row.setS30_60(rs.getLong("s30_60"));
                row.setS60_90(rs.getLong("s60_90"));
                row.setS90_120(rs.getLong("s90_120"));
                row.setS120(rs.getLong("s120"));
                row.setM0_5(rs.getLong("m0_5"));
                row.setM5_10(rs.getLong("m5_10"));
                row.setM10_20(rs.getLong("m10_20"));
                row.setM20_30(rs.getLong("m20_30"));
                row.setM30_45(rs.getLong("m30_45"));
                row.setM45_50(rs.getLong("m45_50"));
                row.setM50(rs.getLong("m50"));
                row.setTotal(rs.getLong("total"));
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return row;
    }

    public List<GlaRow> getRowsByIdAgenceBetweenDates(String agence_id, String date1, String date2) {
        List<GlaRow> emps = new ArrayList<>();
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();

            String sql = "SELECT service_id, service_name, "
                    + "SUM(s0_15) AS s0_15, SUM(s15_30) AS s15_30, SUM(s30_60) AS s30_60, "
                    + "SUM(s60_90) AS s60_90, SUM(s90_120) AS s90_120, SUM(s120) AS s120, "
                    + "SUM(m0_5) AS m0_5, SUM(m5_10) AS m5_10, SUM(m10_20) AS m10_20, "
                    + "SUM(m20_30) AS m20_30, SUM(m30_45) AS m30_45, SUM(m45_50) AS m45_50, "
                    + "SUM(m50) AS m50, SUM(total) AS total "
                    + "FROM rougga_gla_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') "
                    + "BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
                    + "AND agence_id = ? "
                    + "GROUP BY  service_id, service_name "
                    + "ORDER BY  service_name";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date1); // Start date
            pstmt.setString(2, date2); // End date
            pstmt.setString(3, agence_id); // agence_id

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            while (rs.next()) {
                GlaRow row = new GlaRow();

                row.setId(null);
                row.setDate(null);
                row.setAgenceId(agence_id);
                row.setServiceId(rs.getString("service_id"));
                row.setServiceName(rs.getString("service_name"));
                row.setS0_15(rs.getLong("s0_15"));
                row.setS15_30(rs.getLong("s15_30"));
                row.setS30_60(rs.getLong("s30_60"));
                row.setS60_90(rs.getLong("s60_90"));
                row.setS90_120(rs.getLong("s90_120"));
                row.setS120(rs.getLong("s120"));
                row.setM0_5(rs.getLong("m0_5"));
                row.setM5_10(rs.getLong("m5_10"));
                row.setM10_20(rs.getLong("m10_20"));
                row.setM20_30(rs.getLong("m20_30"));
                row.setM30_45(rs.getLong("m30_45"));
                row.setM45_50(rs.getLong("m45_50"));
                row.setM50(rs.getLong("m50"));
                row.setTotal(rs.getLong("total"));
                emps.add(row);
            }
            if (emps.size() <= 0) {
                con.close();
                return emps; // if no rows exists return empty list
            }
            emps.add(this.getTotaleRowByAgence(agence_id, date1, date2)); // adding subtotale row as a service
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return emps;
    }

    public GlaRow getRowByDate(String date, String agenceId, String serviceId) {
        GlaRow row = null;
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT * "
                    + "FROM rougga_gla_table "
                    + "WHERE  to_date(to_char(date,'YYYY-MM-DD'),'YYYY-MM-DD') = TO_DATE(?,'YYYY-MM-DD') and agence_id= ? and service_id = ?";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date);
            pstmt.setString(2, agenceId);
            pstmt.setString(3, serviceId);
            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row = new GlaRow();
                row.setId(UUID.fromString(rs.getString("id")));
                row.setDate(CfgHandler.getFormatedDateAsString(CfgHandler.getFormatedDateAsDate(rs.getString("date"))));
                row.setAgenceId(rs.getString("agence_id"));
                row.setServiceId(rs.getString("service_id"));
                row.setServiceName(rs.getString("service_name"));
                row.setS0_15(rs.getLong("s0_15"));
                row.setS15_30(rs.getLong("s15_30"));
                row.setS30_60(rs.getLong("s30_60"));
                row.setS60_90(rs.getLong("s60_90"));
                row.setS90_120(rs.getLong("s90_120"));
                row.setS120(rs.getLong("s120"));
                row.setM0_5(rs.getLong("m0_5"));
                row.setM5_10(rs.getLong("m5_10"));
                row.setM10_20(rs.getLong("m10_20"));
                row.setM20_30(rs.getLong("m20_30"));
                row.setM30_45(rs.getLong("m30_45"));
                row.setM45_50(rs.getLong("m45_50"));
                row.setM50(rs.getLong("m50"));
                row.setTotal(rs.getLong("total"));
                logger.info("row found for date: " + date + " agence_id = "
                        + agenceId
                        + " service_id = " + serviceId);
            } else {
                logger.info("No row found for date: " + date + " agence_id = "
                        + agenceId
                        + " service_id = " + serviceId);
            }

            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return row;
    }

    // get GLA row between 2 dates and insert json result to database for every
    // agency
    public void updateFromJson(String date1, String date2) {
        List<Agence> agences = ac.getAllAgence();
        for (Agence a : agences) {
            this.updateAgenceFromJson(date1, date2, a.getId().toString());
        }
    }

    // get GLA row between 2 dates and insert json result to database for one agency
    public boolean updateAgenceFromJson(String date1, String date2, String agenceId) {
        boolean isDone = false;
        Agence a = new Agence();
        List<GblRow> rowsToInsert = new ArrayList<>();
        List<GblRow> rowsToUpdate = new ArrayList<>();
        // validationg data
        if (StringUtils.isBlank(agenceId)) {
            logger.error("updateAgenceFromJson: id agence null;");
            return false;
        }
        if (StringUtils.isAnyBlank(date1, date2)) {
            date1 = date2 = CfgHandler.format.format(new Date());
        }
        a = ac.getAgenceById(UUID.fromString(agenceId));
        if (a != null) {
            logger.info(" -- Updating " + a.getName() + "'s GLA Table ... ");
            String url = CfgHandler.prepareTableJsonUrl(a.getHost(), a.getPort(), CfgHandler.API_GLA_TABLE_JSON, date1,
                    date2);
            logger.info("URL = " + url + " - " + a.getName());
            JSONObject json = UpdateController.getJsonFromUrl(url);

            if (json != null) {
                JSONArray result = (JSONArray) json.get("result");
                for (Object s : result) {
                    try {
                        JSONObject emp = (JSONObject) s;
                        String serviceId = emp.get("service_id").toString();
                        String agence_id = a.getId().toString();
                        GlaRow row = this.getRowByDate(date2,
                                agence_id,
                                serviceId);
                        if (row != null) {
                            try {
                                row.setDate(CfgHandler.getFormatedDateAsString(CfgHandler.format.parse(date2)));
                                row.setAgenceId(a.getId().toString());
                                row.setServiceId(serviceId);
                                row.setServiceName(emp.get("service_name").toString());
                                row.setS0_15((long) emp.get("s0_15"));
                                row.setS15_30((long) emp.get("s15_30"));
                                row.setS30_60((long) emp.get("s30_60"));
                                row.setS60_90((long) emp.get("s60_90"));
                                row.setS90_120((long) emp.get("s90_120"));
                                row.setS120((long) emp.get("s120"));
                                row.setM0_5((long) emp.get("m0_5"));
                                row.setM5_10((long) emp.get("m5_10"));
                                row.setM10_20((long) emp.get("m10_20"));
                                row.setM20_30((long) emp.get("m20_30"));
                                row.setM30_45((long) emp.get("m30_45"));
                                row.setM45_50((long) emp.get("m45_50"));
                                row.setM50((long) emp.get("m50"));
                                row.setTotal((long) emp.get("total"));
                                //this.updateRow(row);
                                rowsToUpdate.add(row);
                                logger.info("GlaRow id: " + row.getId() + " found and updated ");
                            } catch (ParseException ex) {
                                logger.error(ex.getMessage());
                                return false;
                            }
                        } else {
                            try {
                                row = new GlaRow();
                                row.setId(this.getUniquId());
                                row.setDate(CfgHandler.getFormatedDateAsString(CfgHandler.format.parse(date2)));
                                row.setAgenceId(a.getId().toString());
                                row.setServiceId(serviceId);
                                row.setServiceName(emp.get("service_name").toString());
                                row.setS0_15((long) emp.get("s0_15"));
                                row.setS15_30((long) emp.get("s15_30"));
                                row.setS30_60((long) emp.get("s30_60"));
                                row.setS60_90((long) emp.get("s60_90"));
                                row.setS90_120((long) emp.get("s90_120"));
                                row.setS120((long) emp.get("s120"));
                                row.setM0_5((long) emp.get("m0_5"));
                                row.setM5_10((long) emp.get("m5_10"));
                                row.setM10_20((long) emp.get("m10_20"));
                                row.setM20_30((long) emp.get("m20_30"));
                                row.setM30_45((long) emp.get("m30_45"));
                                row.setM45_50((long) emp.get("m45_50"));
                                row.setM50((long) emp.get("m50"));
                                row.setTotal((long) emp.get("total"));
                                //this.addRow(row);
                                rowsToInsert.add(row);
                            } catch (ParseException ex) {
                                logger.error(ex.getMessage());
                            }
                        }
                    } catch (NullPointerException e) {
                        logger.error("updateAgenceFromJson: some json variables are null;");
                    }
                }
                ac.setLastUpdate(UUID.fromString(agenceId));
                isDone = true;
            } else {
                logger.error("updateAgenceFromJson: json null;");
                return false;
            }
        } else {
            logger.error("updateAgenceFromJson: no agence found;");
        }
        // insert and update using batch processing
        this.batchInsert(rowsToInsert);
        this.batchUpdate(rowsToUpdate);
        logger.info(" --  GLA Table for " + a.getName() + " is Updated. ");
        return isDone;
    }

    // returns whole GLA table for report page
    public List<Map> getTableAsList(String date1, String date2, String[] agences) {
        List<Map> result = new ArrayList<>();
        date1 = (date1 == null) ? CfgHandler.format.format(new Date()) : date1;
        date2 = (date2 == null) ? CfgHandler.format.format(new Date()) : date2;

        List<Agence> dbs = ac.getAgencesFromStringArray(agences);
        if (dbs == null || dbs.isEmpty()) {
            dbs = ac.getAllAgence();
        }
        for (Agence a : dbs) {
            List<GlaRow> emps = this.getRowsByIdAgenceBetweenDates(a.getId().toString(), date1, date2);
            if (!emps.isEmpty()) {
                Map<String, Object> newAgence = new HashMap<>();
                newAgence.put("agence_id", a.getId().toString());
                newAgence.put("agence_name", a.getName());
                newAgence.put("emps", emps);
                result.add(newAgence);
            }

        }

        // adding totale
        if (!result.isEmpty()) {
            List<GlaRow> emps = new ArrayList<>();
            Map<String, Object> newAgence = new HashMap<>();
            newAgence.put("agence_id", "Totale");
            newAgence.put("agence_name", "Totale");
            emps.add(this.getTotaleRow(date1, date2, agences));
            newAgence.put("emps", emps);
            result.add(newAgence);
        }

        return result;

    }

    // gets unique UUID after checking in GLA table
    private UUID getUniquId() {
        UUID uniqueId = UUID.randomUUID();
        while (this.doesIdExist(uniqueId)) {
            uniqueId = UUID.randomUUID();
        }
        return uniqueId;
    }

    // checks if the id exists in GLA table
    private boolean doesIdExist(UUID uniqueId) {
        try {
            Connection con = new CPConnection().getConnection();
            String sql = "select 1 from rougga_gla_table where id=? limit 1";
            PreparedStatement pstmt = con.prepareCall(sql);
            pstmt.setString(1, uniqueId.toString());
            ResultSet rst = pstmt.executeQuery();
            boolean result = rst.next();
            con.close();
            return result;

        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    // restore old rows from the oldest date to now for all agencies
    public void restoreOldRowsForAllAgences() {
        for (Agence a : ac.getAllAgence()) {
            if (this.restoreOldRowsByAgenceId(a.getId())) {

            } else {
                logger.error("restoreOldRowsForAllAgences: Couldn't restore data for " + a.getName());
            }
        }
        logger.info("restoreOldRowsForAllAgences: all agences's data restored!");
    }

    // restore old rows from the oldest date to now for one agency
    public boolean restoreOldRowsByAgenceId(UUID agence_id) {
        Agence a = ac.getAgenceById(agence_id);
        if (a == null) {
            logger.error("restoreOldRowsByAgenceId: agence not found!");
            return false;
        }
        Date oldestDate = ac.getOldesTicketDate(a.getId());
        if (oldestDate != null) {
            while (new Date().compareTo(oldestDate) > 0) {
                logger.info("Restoring GLA table data of "
                        + a.getName()
                        + " for date:"
                        + CfgHandler.getFormatedDateAsString(oldestDate));
                this.updateAgenceFromJson(
                        CfgHandler.format.format(oldestDate),
                        CfgHandler.format.format(oldestDate),
                        agence_id.toString());
                Calendar c = Calendar.getInstance();
                c.setTime(oldestDate);
                c.add(Calendar.DATE, 1);
                oldestDate = c.getTime();
            }
            logger.info("Resored GLA table data of "
                    + a.getName());
        } else {
            logger.error("restoreOldRowsByAgenceId: oldest ticket date not found!");
            return false;
        }

        // add error handling
        return true;
    }

}
