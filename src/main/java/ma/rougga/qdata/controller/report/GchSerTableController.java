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
import ma.rougga.qdata.modal.Zone;
import ma.rougga.qdata.modal.report.EmpSerRow;
import ma.rougga.qdata.modal.report.GchSerRow;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GchSerTableController {

    private static final Logger logger = LoggerFactory.getLogger(GchSerTableController.class);
    private final AgenceController ac = new AgenceController();

    public boolean addRow(GchSerRow row) {
        String sql = "INSERT INTO rougga_gchser_table ("
                + "id, guichet_id, guichet_name, service_id, service_name, agence_id, "
                + "nb_t, nb_tt, nb_a, nb_tl1, nb_sa, "
                + "perApT, perTl1Pt, perSaPt, avgSec_A, avgSec_T, "
                + "nb_ca, percapt, nb_ct, perCtPt, date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, to_date(?,'YYYY-MM-DD HH24:MI:SS'))";

        try (Connection con = new CPConnection().getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, row.getId().toString());
            pstmt.setString(2, row.getGuichetId());
            pstmt.setString(3, row.getGuichetName());
            pstmt.setString(4, row.getServiceId());
            pstmt.setString(5, row.getServiceName());
            pstmt.setString(6, row.getAgenceId());
            pstmt.setLong(7, row.getNbT());
            pstmt.setLong(8, row.getNbTt());
            pstmt.setLong(9, row.getNbA());
            pstmt.setLong(10, row.getNbTl1());
            pstmt.setLong(11, row.getNbSa());
            pstmt.setDouble(12, row.getPerApT());
            pstmt.setDouble(13, row.getPerTl1Pt());
            pstmt.setDouble(14, row.getPerSaPt());
            pstmt.setDouble(15, row.getAvgSecA());
            pstmt.setDouble(16, row.getAvgSecT());
            pstmt.setLong(17, row.getNbCa());
            pstmt.setDouble(18, row.getPerCapt());
            pstmt.setLong(19, row.getNbCt());
            pstmt.setDouble(20, row.getPerCtPt());
            pstmt.setString(21, row.getDate());

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
            logger.error("Error inserting row: " + e.getMessage());
            return false;
        }
    }

    public boolean updateRow(GchSerRow row) {
        String sql = "UPDATE rougga_gchser_table SET "
                + "guichet_id = ?, guichet_name = ?, service_id = ?, service_name = ?, agence_id = ?, "
                + "nb_t = ?, nb_tt = ?, nb_a = ?, nb_tl1 = ?, nb_sa = ?, "
                + "perApT = ?, perTl1Pt = ?, perSaPt = ?, avgSec_A = ?, avgSec_T = ?, "
                + "nb_ca = ?, percapt = ?, nb_ct = ?, perCtPt = ?, date = to_date(?,'YYYY-MM-DD HH24:MI:SS') "
                + "WHERE id = ?";

        try (Connection con = new CPConnection().getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, row.getGuichetId());
            pstmt.setString(2, row.getGuichetName());
            pstmt.setString(3, row.getServiceId());
            pstmt.setString(4, row.getServiceName());
            pstmt.setString(5, row.getAgenceId());
            pstmt.setLong(6, row.getNbT());
            pstmt.setLong(7, row.getNbTt());
            pstmt.setLong(8, row.getNbA());
            pstmt.setLong(9, row.getNbTl1());
            pstmt.setLong(10, row.getNbSa());
            pstmt.setDouble(11, row.getPerApT());
            pstmt.setDouble(12, row.getPerTl1Pt());
            pstmt.setDouble(13, row.getPerSaPt());
            pstmt.setDouble(14, row.getAvgSecA());
            pstmt.setDouble(15, row.getAvgSecT());
            pstmt.setLong(16, row.getNbCa());
            pstmt.setDouble(17, row.getPerCapt());
            pstmt.setLong(18, row.getNbCt());
            pstmt.setDouble(19, row.getPerCtPt());
            pstmt.setString(20, row.getDate());
            pstmt.setString(21, row.getId().toString());

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
            logger.error("Error updating row: " + e.getMessage());
            return false;
        }
    }

    public GchSerRow getRowById(String id) {
        String sql = "SELECT * FROM rougga_gchser_table WHERE id = ?";
        GchSerRow row = null;

        try (Connection con = new CPConnection().getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                row = new GchSerRow();
                row.setId(UUID.fromString(rs.getString("id")));
                row.setGuichetId(rs.getString("guichet_id"));
                row.setGuichetName(rs.getString("guichet_name"));
                row.setServiceId(rs.getString("service_id"));
                row.setServiceName(rs.getString("service_name"));
                row.setAgenceId(rs.getString("agence_id"));
                row.setNbT(rs.getLong("nb_t"));
                row.setNbTt(rs.getLong("nb_tt"));
                row.setNbA(rs.getLong("nb_a"));
                row.setNbTl1(rs.getLong("nb_tl1"));
                row.setNbSa(rs.getLong("nb_sa"));
                row.setPerApT(rs.getDouble("perApT"));
                row.setPerTl1Pt(rs.getDouble("perTl1Pt"));
                row.setPerSaPt(rs.getDouble("perSaPt"));
                row.setAvgSecA(rs.getDouble("avgSec_A"));
                row.setAvgSecT(rs.getDouble("avgSec_T"));
                row.setNbCa(rs.getLong("nb_ca"));
                row.setPerCapt(rs.getDouble("percapt"));
                row.setNbCt(rs.getLong("nb_ct"));
                row.setPerCtPt(rs.getDouble("perCtPt"));
                row.setDate(CfgHandler.getFormatedDateAsString(CfgHandler.getFormatedDateAsDate(rs.getString("date"))));
            } else {
                logger.info("No row found for id: " + id);
            }
            con.close();
        } catch (SQLException e) {
            logger.error("Error retrieving row: " + e.getMessage());
        }
        return row;
    }

    public boolean batchInsert(List<GchSerRow> rows) {
        boolean isSuccess = false;
        try {
            Connection con = new CPConnection().getConnection();
            con.setAutoCommit(false); // Disable auto-commit
            String sql = "INSERT INTO rougga_gchser_table ("
                    + "id, guichet_id, guichet_name, service_id, service_name, agence_id, "
                    + "nb_t, nb_tt, nb_a, nb_tl1, nb_sa, "
                    + "perApT, perTl1Pt, perSaPt, avgSec_A, avgSec_T, "
                    + "nb_ca, percapt, nb_ct, perCtPt, date) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, to_date(?,'YYYY-MM-DD HH24:MI:SS'))";
            PreparedStatement pstmt = con.prepareStatement(sql);

            for (GchSerRow row : rows) {
                pstmt.setString(1, row.getId().toString());
                pstmt.setString(2, row.getGuichetId());
                pstmt.setString(3, row.getGuichetName());
                pstmt.setString(4, row.getServiceId());
                pstmt.setString(5, row.getServiceName());
                pstmt.setString(6, row.getAgenceId());
                pstmt.setLong(7, row.getNbT());
                pstmt.setLong(8, row.getNbTt());
                pstmt.setLong(9, row.getNbA());
                pstmt.setLong(10, row.getNbTl1());
                pstmt.setLong(11, row.getNbSa());
                pstmt.setDouble(12, row.getPerApT());
                pstmt.setDouble(13, row.getPerTl1Pt());
                pstmt.setDouble(14, row.getPerSaPt());
                pstmt.setDouble(15, row.getAvgSecA());
                pstmt.setDouble(16, row.getAvgSecT());
                pstmt.setLong(17, row.getNbCa());
                pstmt.setDouble(18, row.getPerCapt());
                pstmt.setLong(19, row.getNbCt());
                pstmt.setDouble(20, row.getPerCtPt());
                pstmt.setString(21, row.getDate());
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

    public boolean batchUpdate(List<GchSerRow> rows) {
        boolean isSuccess = false;
        try {
            Connection con = new CPConnection().getConnection();
            con.setAutoCommit(false); // Disable auto-commit
            con.setAutoCommit(false); // Disable auto-commit
            String sql = "UPDATE rougga_gchser_table SET "
                    + "guichet_id = ?, guichet_name = ?, service_id = ?, service_name = ?, agence_id = ?, "
                    + "nb_t = ?, nb_tt = ?, nb_a = ?, nb_tl1 = ?, nb_sa = ?, "
                    + "perApT = ?, perTl1Pt = ?, perSaPt = ?, avgSec_A = ?, avgSec_T = ?, "
                    + "nb_ca = ?, percapt = ?, nb_ct = ?, perCtPt = ?, date = to_date(?,'YYYY-MM-DD HH24:MI:SS') "
                    + "WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            for (GchSerRow row : rows) {
                pstmt.setString(1, row.getGuichetId());
                pstmt.setString(2, row.getGuichetName());
                pstmt.setString(3, row.getServiceId());
                pstmt.setString(4, row.getServiceName());
                pstmt.setString(5, row.getAgenceId());
                pstmt.setLong(6, row.getNbT());
                pstmt.setLong(7, row.getNbTt());
                pstmt.setLong(8, row.getNbA());
                pstmt.setLong(9, row.getNbTl1());
                pstmt.setLong(10, row.getNbSa());
                pstmt.setDouble(11, row.getPerApT());
                pstmt.setDouble(12, row.getPerTl1Pt());
                pstmt.setDouble(13, row.getPerSaPt());
                pstmt.setDouble(14, row.getAvgSecA());
                pstmt.setDouble(15, row.getAvgSecT());
                pstmt.setLong(16, row.getNbCa());
                pstmt.setDouble(17, row.getPerCapt());
                pstmt.setLong(18, row.getNbCt());
                pstmt.setDouble(19, row.getPerCtPt());
                pstmt.setString(20, row.getDate());
                pstmt.setString(21, row.getId().toString());
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
    public GchSerRow getTotaleRowByAgence(String agenceId, String date1, String date2) {
        GchSerRow row = new GchSerRow();
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT "
                    + "agence_id, "
                    + "'Sous-Totale' AS guichet_name,"
                    + "'Sous-Totale' AS service_name,"
                    + "COUNT(*) AS rowCount, "
                    + "SUM(nb_t) AS nb_t, "
                    + "SUM(nb_tt) AS nb_tt, "
                    + "SUM(nb_a) AS nb_a, "
                    + "SUM(nb_tl1) AS nb_tl1, "
                    + "SUM(nb_sa) AS nb_sa, "
                    + "ROUND(SUM(perApT) / COUNT(*), 2) AS perApT, "
                    + "ROUND(SUM(PERTL1pt) / COUNT(*), 2) AS PERTL1pt, "
                    + "ROUND(SUM(perSApT) / COUNT(*), 2) AS perSApT, "
                    + "ROUND(SUM(avgSec_A) / COUNT(*), 2) AS avgSec_A, "
                    + "SUM(nb_ca) AS nb_ca, "
                    + "ROUND(SUM(percapt) / COUNT(*), 2) AS percapt, "
                    + "ROUND(SUM(avgSec_T) / COUNT(*), 2) AS avgSec_T, "
                    + "SUM(nb_ct) AS nb_ct, "
                    + "ROUND(SUM(perctpt) / COUNT(*), 2) AS perctpt "
                    + "FROM rougga_gchser_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
                    + "AND agence_id = ? "
                    + "GROUP BY agence_id";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date1); // Start date
            pstmt.setString(2, date2); // End date
            pstmt.setString(3, agenceId); // agenceId

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row = new GchSerRow();
                row.setId(null);
                row.setGuichetId(null);
                row.setGuichetName(rs.getString("guichet_name"));
                row.setServiceId(null);
                row.setServiceName(rs.getString("service_name"));
                row.setAgenceId(agenceId);
                row.setNbT(rs.getLong("nb_t"));
                row.setNbTt(rs.getLong("nb_tt"));
                row.setNbA(rs.getLong("nb_a"));
                row.setNbTl1(rs.getLong("nb_tl1"));
                row.setNbSa(rs.getLong("nb_sa"));
                row.setPerApT(rs.getDouble("perApT"));
                row.setPerTl1Pt(rs.getDouble("perTl1Pt"));
                row.setPerSaPt(rs.getDouble("perSaPt"));
                row.setAvgSecA(rs.getDouble("avgSec_A"));
                row.setAvgSecT(rs.getDouble("avgSec_T"));
                row.setNbCa(rs.getLong("nb_ca"));
                row.setPerCapt(rs.getDouble("percapt"));
                row.setNbCt(rs.getLong("nb_ct"));
                row.setPerCtPt(rs.getDouble("perCtPt"));
                row.setDate(null);
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return row;
    }

    public GchSerRow getTotaleRow(String date1, String date2, String[] agences) {
        GchSerRow row = new GchSerRow();
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

            String sql = "SELECT "
                    + "'Totale' AS guichet_name,"
                    + "'Totale' AS service_name,"
                    + "COUNT(*) AS rowCount, "
                    + "SUM(nb_t) AS nb_t, "
                    + "SUM(nb_tt) AS nb_tt, "
                    + "SUM(nb_a) AS nb_a, "
                    + "SUM(nb_tl1) AS nb_tl1, "
                    + "SUM(nb_sa) AS nb_sa, "
                    + "ROUND(SUM(perApT) / COUNT(*), 2) AS perApT, "
                    + "ROUND(SUM(PERTL1pt) / COUNT(*), 2) AS PERTL1pt, "
                    + "ROUND(SUM(perSApT) / COUNT(*), 2) AS perSApT, "
                    + "ROUND(SUM(avgSec_A) / COUNT(*), 2) AS avgSec_A, "
                    + "SUM(nb_ca) AS nb_ca, "
                    + "ROUND(SUM(percapt) / COUNT(*), 2) AS percapt, "
                    + "ROUND(SUM(avgSec_T) / COUNT(*), 2) AS avgSec_T, "
                    + "SUM(nb_ct) AS nb_ct, "
                    + "ROUND(SUM(perctpt) / COUNT(*), 2) AS perctpt "
                    + "FROM rougga_gchser_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') "
                    + "BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
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
                row = new GchSerRow();
                row.setId(null);
                row.setGuichetId(null);
                row.setGuichetName(rs.getString("guichet_name"));
                row.setServiceId(null);
                row.setServiceName(rs.getString("service_name"));
                row.setAgenceId(null);
                row.setNbT(rs.getLong("nb_t"));
                row.setNbTt(rs.getLong("nb_tt"));
                row.setNbA(rs.getLong("nb_a"));
                row.setNbTl1(rs.getLong("nb_tl1"));
                row.setNbSa(rs.getLong("nb_sa"));
                row.setPerApT(rs.getDouble("perApT"));
                row.setPerTl1Pt(rs.getDouble("perTl1Pt"));
                row.setPerSaPt(rs.getDouble("perSaPt"));
                row.setAvgSecA(rs.getDouble("avgSec_A"));
                row.setAvgSecT(rs.getDouble("avgSec_T"));
                row.setNbCa(rs.getLong("nb_ca"));
                row.setPerCapt(rs.getDouble("percapt"));
                row.setNbCt(rs.getLong("nb_ct"));
                row.setPerCtPt(rs.getDouble("perCtPt"));
                row.setDate(null);
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return row;
    }

    public List<GchSerRow> getRowsByIdAgenceBetweenDates(String agenceId, String date1, String date2) {
        List<GchSerRow> emps = new ArrayList<>();
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT "
                    + "guichet_id, "
                    + "guichet_name,"
                    + "service_id,"
                    + "service_name, "
                    + "COUNT(*) AS rowCount, "
                    + "SUM(nb_t) AS nb_t, "
                    + "SUM(nb_tt) AS nb_tt, "
                    + "SUM(nb_a) AS nb_a, "
                    + "SUM(nb_tl1) AS nb_tl1, "
                    + "SUM(nb_sa) AS nb_sa, "
                    + "ROUND(SUM(perApT) / COUNT(*), 2) AS perApT, "
                    + "ROUND(SUM(PERTL1pt) / COUNT(*), 2) AS PERTL1pt, "
                    + "ROUND(SUM(perSApT) / COUNT(*), 2) AS perSApT, "
                    + "ROUND(SUM(avgSec_A) / COUNT(*), 2) AS avgSec_A, "
                    + "SUM(nb_ca) AS nb_ca, "
                    + "ROUND(SUM(percapt) / COUNT(*), 2) AS percapt, "
                    + "ROUND(SUM(avgSec_T) / COUNT(*), 2) AS avgSec_T, "
                    + "SUM(nb_ct) AS nb_ct, "
                    + "ROUND(SUM(perctpt) / COUNT(*), 2) AS perctpt "
                    + "FROM rougga_gchser_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
                    + "AND agence_id = ? "
                    + "GROUP BY guichet_id, guichet_name,service_id,service_name "
                    + "ORDER BY guichet_name,service_name";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date1); // Start date
            pstmt.setString(2, date2); // End date
            pstmt.setString(3, agenceId); // agence_id

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            while (rs.next()) {
                GchSerRow row = new GchSerRow();
                row.setId(null);
                row.setGuichetId(rs.getString("guichet_id"));
                row.setGuichetName(rs.getString("guichet_name"));
                row.setServiceId(rs.getString("service_id"));
                row.setServiceName(rs.getString("service_name"));
                row.setAgenceId(agenceId);
                row.setNbT(rs.getLong("nb_t"));
                row.setNbTt(rs.getLong("nb_tt"));
                row.setNbA(rs.getLong("nb_a"));
                row.setNbTl1(rs.getLong("nb_tl1"));
                row.setNbSa(rs.getLong("nb_sa"));
                row.setPerApT(rs.getDouble("perApT"));
                row.setPerTl1Pt(rs.getDouble("perTl1Pt"));
                row.setPerSaPt(rs.getDouble("perSaPt"));
                row.setAvgSecA(rs.getDouble("avgSec_A"));
                row.setAvgSecT(rs.getDouble("avgSec_T"));
                row.setNbCa(rs.getLong("nb_ca"));
                row.setPerCapt(rs.getDouble("percapt"));
                row.setNbCt(rs.getLong("nb_ct"));
                row.setPerCtPt(rs.getDouble("perCtPt"));
                row.setDate(null);
                emps.add(row);
            }
            if (emps.size() <= 0) {
                con.close();
                return emps; // if no rows exists return empty list
            }
            emps.add(this.getTotaleRowByAgence(agenceId, date1, date2)); // adding subtotale row as a service
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return emps;
    }

    public GchSerRow getRowByDate(String date, String id_agence, String guichetId, String serviceId) {
        GchSerRow row = null;
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT * "
                    + "FROM rougga_gchser_table "
                    + "WHERE  to_date(to_char(date,'YYYY-MM-DD'),'YYYY-MM-DD') = TO_DATE(?,'YYYY-MM-DD') and agence_id= ? and guichet_id = ? and service_id=?";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date);
            pstmt.setString(2, id_agence);
            pstmt.setString(3, guichetId);
            pstmt.setString(4, serviceId);
            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row = new GchSerRow();
                row.setId(UUID.fromString(rs.getString("id")));
                row.setGuichetId(rs.getString("guichet_id"));
                row.setGuichetName(rs.getString("guichet_name"));
                row.setServiceId(rs.getString("service_id"));
                row.setServiceName(rs.getString("service_name"));
                row.setAgenceId(rs.getString("agence_id"));
                row.setNbT(rs.getLong("nb_t"));
                row.setNbTt(rs.getLong("nb_tt"));
                row.setNbA(rs.getLong("nb_a"));
                row.setNbTl1(rs.getLong("nb_tl1"));
                row.setNbSa(rs.getLong("nb_sa"));
                row.setPerApT(rs.getDouble("perApT"));
                row.setPerTl1Pt(rs.getDouble("perTl1Pt"));
                row.setPerSaPt(rs.getDouble("perSaPt"));
                row.setAvgSecA(rs.getDouble("avgSec_A"));
                row.setAvgSecT(rs.getDouble("avgSec_T"));
                row.setNbCa(rs.getLong("nb_ca"));
                row.setPerCapt(rs.getDouble("percapt"));
                row.setNbCt(rs.getLong("nb_ct"));
                row.setPerCtPt(rs.getDouble("perCtPt"));
                row.setDate(CfgHandler.getFormatedDateAsString(CfgHandler.getFormatedDateAsDate(rs.getString("date"))));

                logger.info("row found for date: " + date + " agence_id = "
                        + id_agence
                        + " guichet_id = " + guichetId);
            } else {
                logger.info("No row found for date: " + date + " agence_id = "
                        + id_agence
                        + " guichet_id = " + guichetId);
            }

            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return row;
    }

    // get gchser row between 2 dates and insert json result to database for every
    // agency
    public void updateFromJson(String date1, String date2) {
        List<Agence> agences = ac.getAllAgence();
        for (Agence a : agences) {
            this.updateAgenceFromJson(date1, date2, a.getId().toString());
        }
    }

    // get gchser row between 2 dates and insert json result to database for one
    // agency
    public boolean updateAgenceFromJson(String date1, String date2, String agenceId) {
        boolean isDone = false;
        Agence a = new Agence();
        List<GchSerRow> rowsToInsert = new ArrayList<>();
        List<GchSerRow> rowsToUpdate = new ArrayList<>();
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
            logger.info(" -- Updating " + a.getName() + "'s GCHSER Table ... ");
            String url = CfgHandler.prepareTableJsonUrl(a.getHost(), a.getPort(), CfgHandler.API_GCHSER_TABLE_JSON,
                    date1, date2);
            logger.info("URL = " + url + " - " + a.getName());
            JSONObject json = UpdateController.getJsonFromUrl(url);

            if (json != null) {
                JSONArray result = (JSONArray) json.get("result");
                for (Object s : result) {
                    try {
                        JSONObject emp = (JSONObject) s;
                        String guichetId = emp.get("guichet_id").toString();
                        String serviceId = emp.get("service_id").toString();
                        String id_agence = a.getId().toString();
                        GchSerRow row = this.getRowByDate(date2,
                                id_agence,
                                guichetId, serviceId);
                        if (row != null) {
                            try {
                                row.setGuichetId(guichetId);
                                row.setGuichetName(emp.get("guichet_name").toString());
                                row.setServiceId(serviceId);
                                row.setServiceName(emp.get("service_name").toString());
                                row.setAgenceId(id_agence);
                                row.setNbT((long) emp.get("nb_t"));
                                row.setNbTt((long) emp.get("nb_tt"));
                                row.setNbA((long) emp.get("nb_a"));
                                row.setNbTl1((long) emp.get("nb_tl1"));
                                row.setNbSa((long) emp.get("nb_sa"));
                                row.setPerApT((Double) emp.get("perApT"));
                                row.setPerTl1Pt((Double) emp.get("PERTL1pt"));
                                row.setPerSaPt((Double) emp.get("perSApT"));
                                row.setAvgSecA((Double) emp.get("avgSec_A"));
                                row.setNbCa((long) emp.get("nb_ca"));
                                row.setPerCapt((Double) emp.get("percapt"));
                                row.setAvgSecT((Double) emp.get("avgSec_T"));
                                row.setNbCt((long) emp.get("nb_ct"));
                                row.setPerCtPt((Double) emp.get("perctpt"));
                                row.setDate(CfgHandler.getFormatedDateAsString(CfgHandler.format.parse(date2)));
                                //this.updateRow(row);
                                rowsToUpdate.add(row);
                                logger.info("GchSerRow id: " + row.getId() + " found and updated ");
                            } catch (ParseException ex) {
                                logger.error(ex.getMessage());
                                return false;
                            }
                        } else {
                            try {
                                row = new GchSerRow();
                                row.setId(this.getUniquId());
                                row.setGuichetId(guichetId);
                                row.setGuichetName(emp.get("guichet_name").toString());
                                row.setServiceId(serviceId);
                                row.setServiceName(emp.get("service_name").toString());
                                row.setAgenceId(id_agence);
                                row.setNbT((long) emp.get("nb_t"));
                                row.setNbTt((long) emp.get("nb_tt"));
                                row.setNbA((long) emp.get("nb_a"));
                                row.setNbTl1((long) emp.get("nb_tl1"));
                                row.setNbSa((long) emp.get("nb_sa"));
                                row.setPerApT((Double) emp.get("perApT"));
                                row.setPerTl1Pt((Double) emp.get("PERTL1pt"));
                                row.setPerSaPt((Double) emp.get("perSApT"));
                                row.setAvgSecA((Double) emp.get("avgSec_A"));
                                row.setNbCa((long) emp.get("nb_ca"));
                                row.setPerCapt((Double) emp.get("percapt"));
                                row.setAvgSecT((Double) emp.get("avgSec_T"));
                                row.setNbCt((long) emp.get("nb_ct"));
                                row.setPerCtPt((Double) emp.get("perctpt"));
                                row.setDate(CfgHandler.getFormatedDateAsString(CfgHandler.format.parse(date2)));
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
        logger.info(" --  GchSer Table for " + a.getName() + " is Updated. ");
        return isDone;
    }

    // returns whole gchser table for report page
    public List<Map> getTableAsList(String date1, String date2, String[] agences) {
        List<Map> result = new ArrayList<>();
        date1 = (date1 == null) ? CfgHandler.format.format(new Date()) : date1;
        date2 = (date2 == null) ? CfgHandler.format.format(new Date()) : date2;

        List<Agence> dbs = ac.getAgencesFromStringArray(agences);
        if (dbs == null || dbs.isEmpty()) {
            dbs = ac.getAllAgence();
        }
        for (Agence a : dbs) {
            List<GchSerRow> emps = this.getRowsByIdAgenceBetweenDates(a.getId().toString(), date1, date2);
            if (!emps.isEmpty()) {
                Map<String, Object> newAgence = new HashMap<>();
                newAgence.put("id_agence", a.getId().toString());
                String agenceName = a.getName();
                Zone zone = ac.getAgenceZoneByAgenceId(a.getId());
                if ( zone != null) {
                    agenceName+= " (" + zone.getName() + ")";
                }
                newAgence.put("agence_name", agenceName);
                newAgence.put("emps", emps);
                result.add(newAgence);
            }

        }

        // adding totale
        if (!result.isEmpty()) {
            List<GchSerRow> emps = new ArrayList<>();
            Map<String, Object> newAgence = new HashMap<>();
            newAgence.put("id_agence", "Totale");
            newAgence.put("agence_name", "Totale");
            emps.add(this.getTotaleRow(date1, date2, agences));
            newAgence.put("emps", emps);
            result.add(newAgence);
        }

        return result;

    }

    // gets unique UUID after checking in gchser table
    private UUID getUniquId() {
        UUID uniqueId = UUID.randomUUID();
        while (this.doesIdExist(uniqueId)) {
            uniqueId = UUID.randomUUID();
        }
        return uniqueId;
    }

    // checks if the id exists in gchser table
    private boolean doesIdExist(UUID uniqueId) {
        try {
            Connection con = new CPConnection().getConnection();
            String sql = "select 1 from rougga_gchser_table where id=? limit 1";
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
    public boolean restoreOldRowsByAgenceId(UUID id_agence) {
        Agence a = ac.getAgenceById(id_agence);
        if (a == null) {
            logger.error("restoreOldRowsByAgenceId: agence not found!");
            return false;
        }
        Date oldestDate = ac.getOldesTicketDate(a.getId());
        if (oldestDate != null) {
            while (new Date().compareTo(oldestDate) > 0) {
                logger.info("Restoring GCHSER table data of "
                        + a.getName()
                        + " for date:"
                        + CfgHandler.getFormatedDateAsString(oldestDate));
                this.updateAgenceFromJson(
                        CfgHandler.format.format(oldestDate),
                        CfgHandler.format.format(oldestDate),
                        id_agence.toString());
                Calendar c = Calendar.getInstance();
                c.setTime(oldestDate);
                c.add(Calendar.DATE, 1);
                oldestDate = c.getTime();
            }
            logger.info("Resored GCHSER table data of "
                    + a.getName());
        } else {
            logger.error("restoreOldRowsByAgenceId: oldest ticket date not found!");
            return false;
        }

        // add error handling
        return true;
    }

}
