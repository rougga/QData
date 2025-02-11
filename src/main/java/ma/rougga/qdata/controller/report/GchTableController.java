package ma.rougga.qdata.controller.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ma.rougga.qdata.CPConnection;
import ma.rougga.qdata.CfgHandler;
import ma.rougga.qdata.controller.AgenceController;
import ma.rougga.qdata.controller.UpdateController;
import ma.rougga.qdata.modal.Agence;
import ma.rougga.qdata.modal.Zone;
import ma.rougga.qdata.modal.report.GchRow;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GchTableController {

    private static final Logger logger = LoggerFactory.getLogger(GchTableController.class);
    AgenceController ac = new AgenceController();

    public GchTableController() {
    }

    public boolean addRow(GchRow row) {
        try (Connection con = new CPConnection().getConnection()) {

            String sql = "INSERT INTO rougga_gch_table ("
                    + "id, guichet_id, guichet_name, agence_id, nb_t, nb_tt, nb_a, nb_tl1, nb_sa, "
                    + "perApT, perTl1Pt, perSaPt, avgSec_A, avgSec_T, nb_ca, percapt, nb_ct, perCtPt, date) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'))";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, row.getId().toString());
            pstmt.setString(2, row.getGuichetId());
            pstmt.setString(3, row.getGuichetName());
            pstmt.setString(4, row.getAgenceId());
            pstmt.setLong(5, row.getNbT());
            pstmt.setLong(6, row.getNbTt());
            pstmt.setLong(7, row.getNbA());
            pstmt.setLong(8, row.getNbTl1());
            pstmt.setLong(9, row.getNbSa());
            pstmt.setDouble(10, row.getPerApT());
            pstmt.setDouble(11, row.getPerTl1Pt());
            pstmt.setDouble(12, row.getPerSaPt());
            pstmt.setDouble(13, row.getAvgSecA());
            pstmt.setDouble(14, row.getAvgSecT());
            pstmt.setLong(15, row.getNbCa());
            pstmt.setDouble(16, row.getPerCapt());
            pstmt.setLong(17, row.getNbCt());
            pstmt.setDouble(18, row.getPerCtPt());
            pstmt.setString(19, row.getDate());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                con.close();
                return true;
            } else {
                logger.info("A  row wasnt inserted successfully!");
                con.close();
                return false;
            }
        } catch (Exception e) {
            logger.error("Error inserting GchRow: " + e.getMessage());
            return false;
        }
    }

    public boolean updateRow(GchRow row) {
        try (Connection con = new CPConnection().getConnection()) {
            String sql = "UPDATE rougga_gch_table SET "
                    + "guichet_id = ?, guichet_name = ?, agence_id = ?, nb_t = ?, nb_tt = ?, nb_a = ?, nb_tl1 = ?, nb_sa = ?, "
                    + "perApT = ?, perTl1Pt = ?, perSaPt = ?, avgSec_A = ?, avgSec_T = ?, nb_ca = ?, percapt = ?, "
                    + "nb_ct = ?, perCtPt = ?, date = to_timestamp(?,'YYYY-MM-DD HH24:MI:SS') "
                    + "WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, row.getGuichetId());
            pstmt.setString(2, row.getGuichetName());
            pstmt.setString(3, row.getAgenceId());
            pstmt.setLong(4, row.getNbT());
            pstmt.setLong(5, row.getNbTt());
            pstmt.setLong(6, row.getNbA());
            pstmt.setLong(7, row.getNbTl1());
            pstmt.setLong(8, row.getNbSa());
            pstmt.setDouble(9, row.getPerApT());
            pstmt.setDouble(10, row.getPerTl1Pt());
            pstmt.setDouble(11, row.getPerSaPt());
            pstmt.setDouble(12, row.getAvgSecA());
            pstmt.setDouble(13, row.getAvgSecT());
            pstmt.setLong(14, row.getNbCa());
            pstmt.setDouble(15, row.getPerCapt());
            pstmt.setLong(16, row.getNbCt());
            pstmt.setDouble(17, row.getPerCtPt());
            pstmt.setString(18, row.getDate());
            pstmt.setString(19, row.getId().toString());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                con.close();
                return true;
            } else {
                logger.info("A  row wasnt inserted successfully!");
                con.close();
                return false;
            }
        } catch (Exception e) {
            logger.error("Error updating GchRow: " + e.getMessage());
            return false;
        }
    }

    public GchRow getRowById(String id) {
        GchRow row = null;
        try (Connection con = new CPConnection().getConnection()) {
            String sql = "SELECT * FROM rougga_gch_table WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                row = new GchRow(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("agence_id"),
                        rs.getString("guichet_id"),
                        rs.getString("guichet_name"),
                        rs.getLong("nb_t"),
                        rs.getLong("nb_tt"),
                        rs.getLong("nb_a"),
                        rs.getLong("nb_tl1"),
                        rs.getLong("nb_sa"),
                        rs.getDouble("perApT"),
                        rs.getDouble("perTl1Pt"),
                        rs.getDouble("perSaPt"),
                        rs.getDouble("avgSec_A"),
                        rs.getLong("nb_ca"),
                        rs.getDouble("percapt"),
                        rs.getDouble("avgSec_T"),
                        rs.getLong("nb_ct"),
                        rs.getDouble("perCtPt"),
                        CfgHandler.getFormatedDateAsString(CfgHandler.getFormatedDateAsDate(rs.getString("date"))));
            } else {
                logger.info("No row found for id: " + id);
            }
            con.close();
        } catch (Exception e) {
            logger.error("Error fetching GchRow: " + e.getMessage());
        }
        return row;
    }

    public boolean deleteRowById(String id) {
        try (Connection con = new CPConnection().getConnection()) {
            String sql = "DELETE FROM rougga_gch_table WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                con.close();
                return true;
            } else {
                logger.info("A  row wasnt deleted successfully!");
                con.close();
                return false;
            }

        } catch (Exception e) {
            logger.error("Error deleting GchRow: " + e.getMessage());
            return false;
        }
    }

    public boolean batchInsert(List<GchRow> rows) {
        boolean isSuccess = false;
        try {
            Connection con = new CPConnection().getConnection();
            con.setAutoCommit(false); // Disable auto-commit
            String sql = "INSERT INTO rougga_gch_table ("
                    + "id, guichet_id, guichet_name, agence_id, nb_t, nb_tt, nb_a, nb_tl1, nb_sa, "
                    + "perApT, perTl1Pt, perSaPt, avgSec_A, avgSec_T, nb_ca, percapt, nb_ct, perCtPt, date) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'))";

            PreparedStatement pstmt = con.prepareStatement(sql);

            for (GchRow row : rows) {
                pstmt.setString(1, row.getId().toString());
                pstmt.setString(2, row.getGuichetId());
                pstmt.setString(3, row.getGuichetName());
                pstmt.setString(4, row.getAgenceId());
                pstmt.setLong(5, row.getNbT());
                pstmt.setLong(6, row.getNbTt());
                pstmt.setLong(7, row.getNbA());
                pstmt.setLong(8, row.getNbTl1());
                pstmt.setLong(9, row.getNbSa());
                pstmt.setDouble(10, row.getPerApT());
                pstmt.setDouble(11, row.getPerTl1Pt());
                pstmt.setDouble(12, row.getPerSaPt());
                pstmt.setDouble(13, row.getAvgSecA());
                pstmt.setDouble(14, row.getAvgSecT());
                pstmt.setLong(15, row.getNbCa());
                pstmt.setDouble(16, row.getPerCapt());
                pstmt.setLong(17, row.getNbCt());
                pstmt.setDouble(18, row.getPerCtPt());
                pstmt.setString(19, row.getDate());
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

    public boolean batchUpdate(List<GchRow> rows) {
        boolean isSuccess = false;
        try {
            Connection con = new CPConnection().getConnection();
            con.setAutoCommit(false); // Disable auto-commit
            con.setAutoCommit(false); // Disable auto-commit
            String sql = "UPDATE rougga_gch_table SET "
                    + "guichet_id = ?, guichet_name = ?, agence_id = ?, nb_t = ?, nb_tt = ?, nb_a = ?, nb_tl1 = ?, nb_sa = ?, "
                    + "perApT = ?, perTl1Pt = ?, perSaPt = ?, avgSec_A = ?, avgSec_T = ?, nb_ca = ?, percapt = ?, "
                    + "nb_ct = ?, perCtPt = ?, date = to_timestamp(?,'YYYY-MM-DD HH24:MI:SS') "
                    + "WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            for (GchRow row : rows) {
                pstmt.setString(1, row.getGuichetId());
                pstmt.setString(2, row.getGuichetName());
                pstmt.setString(3, row.getAgenceId());
                pstmt.setLong(4, row.getNbT());
                pstmt.setLong(5, row.getNbTt());
                pstmt.setLong(6, row.getNbA());
                pstmt.setLong(7, row.getNbTl1());
                pstmt.setLong(8, row.getNbSa());
                pstmt.setDouble(9, row.getPerApT());
                pstmt.setDouble(10, row.getPerTl1Pt());
                pstmt.setDouble(11, row.getPerSaPt());
                pstmt.setDouble(12, row.getAvgSecA());
                pstmt.setDouble(13, row.getAvgSecT());
                pstmt.setLong(14, row.getNbCa());
                pstmt.setDouble(15, row.getPerCapt());
                pstmt.setLong(16, row.getNbCt());
                pstmt.setDouble(17, row.getPerCtPt());
                pstmt.setString(18, row.getDate());
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

    public GchRow getTotaleRowByAgence(String id_agence, String date1, String date2) {
        GchRow row = new GchRow();
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT "
                    + "agence_id, "
                    + "'Sous-Totale' AS guichet_name, "
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
                    + "FROM rougga_gch_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
                    + "AND agence_id = ? "
                    + "GROUP BY agence_id";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date1); // Start date
            pstmt.setString(2, date2); // End date
            pstmt.setString(3, id_agence); // id_agence

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row.setId(null); // Setting `id` to null
                row.setGuichetId(null);
                row.setGuichetName(rs.getString("guichet_name"));
                row.setNbT(rs.getLong("nb_t"));
                row.setNbTt(rs.getLong("nb_tt"));
                row.setNbA(rs.getLong("nb_a"));
                row.setNbTl1(rs.getLong("nb_tl1"));
                row.setNbSa(rs.getLong("nb_sa"));
                row.setPerApT(rs.getDouble("perApT"));
                row.setPerTl1Pt(rs.getDouble("PERTL1pt"));
                row.setPerSaPt(rs.getDouble("perSApT"));
                row.setAvgSecA(rs.getDouble("avgSec_A"));
                row.setNbCa(rs.getLong("nb_ca"));
                row.setPerCapt(rs.getDouble("percapt"));
                row.setAvgSecT(rs.getDouble("avgSec_T"));
                row.setNbCt(rs.getLong("nb_ct"));
                row.setPerCtPt(rs.getDouble("perctpt"));
                row.setDate(null); // Setting `date` to null since it's not in the query output
                row.setAgenceId(rs.getString("agence_id"));
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return row;
    }

    public GchRow getTotaleRow(String date1, String date2, String[] agences) {
        GchRow row = new GchRow();
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
                    + "'Totale' AS guichet_id, "
                    + "'Totale' AS guichet_name, "
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
                    + "FROM rougga_gch_table "
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
                row.setId(null); // Set ID to null
                row.setGuichetName(rs.getString("guichet_name"));
                row.setNbT(rs.getLong("nb_t"));
                row.setNbTt(rs.getLong("nb_tt"));
                row.setNbA(rs.getLong("nb_a"));
                row.setNbTl1(rs.getLong("nb_tl1"));
                row.setNbSa(rs.getLong("nb_sa"));
                row.setPerApT(rs.getDouble("perApT"));
                row.setPerTl1Pt(rs.getDouble("PERTL1pt"));
                row.setPerSaPt(rs.getDouble("perSApT"));
                row.setAvgSecA(rs.getDouble("avgSec_A"));
                row.setNbCa(rs.getLong("nb_ca"));
                row.setPerCapt(rs.getDouble("percapt"));
                row.setAvgSecT(rs.getDouble("avgSec_T"));
                row.setNbCt(rs.getLong("nb_ct"));
                row.setPerCtPt(rs.getDouble("perctpt"));
                row.setAgenceId(null); // No id_agence provided in query output
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return row;
    }

    public List<GchRow> getRowsByIdAgenceBetweenDates(String id_agence, String date1, String date2) {
        List<GchRow> emps = new ArrayList<>();
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT "
                    + "guichet_id, "
                    + "guichet_name, "
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
                    + "FROM rougga_gch_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
                    + "AND agence_id = ? "
                    + "GROUP BY guichet_id, guichet_name "
                    + "ORDER BY guichet_name";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date1); // Start date
            pstmt.setString(2, date2); // End date
            pstmt.setString(3, id_agence); // id_agence

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            while (rs.next()) {
                GchRow row = new GchRow();
                row.setId(null); // Set id to null
                row.setGuichetId(rs.getString("guichet_id"));
                row.setGuichetName(rs.getString("guichet_name"));
                row.setNbT(rs.getLong("nb_t"));
                row.setNbTt(rs.getLong("nb_tt"));
                row.setNbA(rs.getLong("nb_a"));
                row.setNbTl1(rs.getLong("nb_tl1"));
                row.setNbSa(rs.getLong("nb_sa"));
                row.setPerApT(rs.getDouble("perApT"));
                row.setPerTl1Pt(rs.getDouble("PERTL1pt"));
                row.setPerSaPt(rs.getDouble("perSApT"));
                row.setAvgSecA(rs.getDouble("avgSec_A"));
                row.setNbCa(rs.getLong("nb_ca"));
                row.setPerCapt(rs.getDouble("percapt"));
                row.setAvgSecT(rs.getDouble("avgSec_T"));
                row.setNbCt(rs.getLong("nb_ct"));
                row.setPerCtPt(rs.getDouble("perctpt"));
                row.setAgenceId(id_agence);
                emps.add(row);
            }
            if (emps.size() <= 0) {
                con.close();
                return emps; // if no rows exists return empty list
            }
            emps.add(this.getTotaleRowByAgence(id_agence, date1, date2)); // adding subtotale row as a service
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return emps;
    }

    public GchRow getRowByDate(String date, String id_agence, String guichetId) {
        GchRow row = null;
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT * "
                    + "FROM rougga_gch_table "
                    + "WHERE  to_date(to_char(date,'YYYY-MM-DD'),'YYYY-MM-DD') = TO_DATE(?,'YYYY-MM-DD') and agence_id= ? and guichet_id = ?";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date);
            pstmt.setString(2, id_agence);
            pstmt.setString(3, guichetId);
            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row = new GchRow(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("agence_id"),
                        rs.getString("guichet_id"),
                        rs.getString("guichet_name"),
                        rs.getLong("nb_t"),
                        rs.getLong("nb_tt"),
                        rs.getLong("nb_a"),
                        rs.getLong("nb_tl1"),
                        rs.getLong("nb_sa"),
                        rs.getDouble("perApT"),
                        rs.getDouble("PERTL1pt"),
                        rs.getDouble("perSApT"),
                        rs.getDouble("avgSec_A"),
                        rs.getLong("nb_ca"),
                        rs.getDouble("percapt"),
                        rs.getDouble("avgSec_T"),
                        rs.getLong("nb_ct"),
                        rs.getDouble("perctpt"),
                        CfgHandler.getFormatedDateAsString(CfgHandler.getFormatedDateAsDate(rs.getString("date"))));
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

    // get emp row between 2 dates and insert json result to database for every
    // agency
    public void updateFromJson(String date1, String date2) {
        List<Agence> agences = ac.getAllAgence();
        for (Agence a : agences) {
            this.updateAgenceFromJson(date1, date2, a.getId().toString());
        }
    }

    // get emp row between 2 dates and insert json result to database for one agency
    public boolean updateAgenceFromJson(String date1, String date2, String agenceId) {
        boolean isDone = false;
        Agence a = new Agence();
        List<GchRow> rowsToInsert = new ArrayList<>();
        List<GchRow> rowsToUpdate = new ArrayList<>();
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
            logger.info(" -- Updating " + a.getName() + "'s GCH Table ... ");
            String url = CfgHandler.prepareTableJsonUrl(a.getHost(), a.getPort(), CfgHandler.API_GCH_TABLE_JSON, date1,
                    date2);
            logger.info("URL = " + url + " - " + a.getName());
            JSONObject json = UpdateController.getJsonFromUrl(url);

            if (json != null) {
                JSONArray result = (JSONArray) json.get("result");
                for (Object s : result) {
                    try {
                        JSONObject emp = (JSONObject) s;
                        String guichetId = emp.get("guichet_id").toString();
                        String id_agence = a.getId().toString();
                        GchRow row = this.getRowByDate(date2,
                                id_agence,
                                guichetId);
                        if (row != null) {
                            try {
                                row.setGuichetName(emp.get("guichet_name").toString());
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
                                logger.info("GchRow id: " + row.getId() + " found and updated ");
                            } catch (ParseException ex) {
                                logger.error(ex.getMessage());
                                return false;
                            }
                        } else {
                            try {
                                row = new GchRow(
                                        this.getUniquId(),
                                        id_agence,
                                        guichetId,
                                        emp.get("guichet_name").toString(),
                                        (long) emp.get("nb_t"),
                                        (long) emp.get("nb_tt"),
                                        (long) emp.get("nb_a"),
                                        (long) emp.get("nb_tl1"),
                                        (long) emp.get("nb_sa"),
                                        (Double) emp.get("perApT"),
                                        (Double) emp.get("PERTL1pt"),
                                        (Double) emp.get("perSApT"),
                                        (Double) emp.get("avgSec_A"),
                                        (long) emp.get("nb_ca"),
                                        (Double) emp.get("percapt"),
                                        (Double) emp.get("avgSec_T"),
                                        (long) emp.get("nb_ct"),
                                        (Double) emp.get("perctpt"),
                                        CfgHandler.getFormatedDateAsString(CfgHandler.format.parse(date2)));
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
        logger.info(" --  Gch Table for " + a.getName() + " is Updated. ");
        return isDone;
    }

    // returns whole emp table for report page
    public List<Map> getTableAsList(String date1, String date2, String[] agences) {
        List<Map> result = new ArrayList<>();
        date1 = (date1 == null) ? CfgHandler.format.format(new Date()) : date1;
        date2 = (date2 == null) ? CfgHandler.format.format(new Date()) : date2;

        List<Agence> dbs = ac.getAgencesFromStringArray(agences);
        if (dbs == null || dbs.isEmpty()) {
            dbs = ac.getAllAgence();
        }
        for (Agence a : dbs) {
            List<GchRow> emps = this.getRowsByIdAgenceBetweenDates(a.getId().toString(), date1, date2);
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
            List<GchRow> emps = new ArrayList<>();
            Map<String, Object> newAgence = new HashMap<>();
            newAgence.put("id_agence", "Totale");
            newAgence.put("agence_name", "Totale");
            emps.add(this.getTotaleRow(date1, date2, agences));
            newAgence.put("emps", emps);
            result.add(newAgence);
        }

        return result;

    }

    // gets unique UUID after checking in gbl table
    private UUID getUniquId() {
        UUID uniqueId = UUID.randomUUID();
        while (this.doesIdExist(uniqueId)) {
            uniqueId = UUID.randomUUID();
        }
        return uniqueId;
    }

    // checks if the id exists in gbl table
    private boolean doesIdExist(UUID uniqueId) {
        try {
            Connection con = new CPConnection().getConnection();
            String sql = "select 1 from rougga_gch_table where id=? limit 1;";
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
            return false;
        }
        Date oldestDate = ac.getOldesTicketDate(a.getId());
        if (oldestDate != null) {
            while (new Date().compareTo(oldestDate) > 0) {
                logger.info("Restoring GCH table data of "
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
        } else {
            return false;
        }

        // add error handling
        return true;
    }

}
