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
import ma.rougga.qdata.modal.report.EmpRow;
import ma.rougga.qdata.modal.report.EmpSerRow;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmpTableController {

    private static final Logger logger = LoggerFactory.getLogger(EmpTableController.class);
    private final AgenceController ac = new AgenceController();

    public EmpTableController() {
    }

    public boolean addRow(EmpRow row) {
        try {
            Connection con = new CPConnection().getConnection();
            String sql = "INSERT INTO rougga_emp_table ("
                    + "id, id_emp, emp_name, nb_t, nb_tt, nb_a, nb_tl1, nb_sa, "
                    + "perApT, PERTL1pt, perSApT, avgSec_A, nb_ca, percapt, "
                    + "avgSec_T, nb_ct, perctpt, date, id_agence) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, to_date(?,'YYYY-MM-DD HH24:MI:SS'), ?)";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, row.getId().toString());
            pstmt.setString(2, row.getUserId());
            pstmt.setString(3, row.getUserName());
            pstmt.setLong(4, row.getNbT());
            pstmt.setLong(5, row.getNbTt());
            pstmt.setLong(6, row.getNbA());
            pstmt.setLong(7, row.getNbTl1());
            pstmt.setLong(8, row.getNbSa());
            pstmt.setDouble(9, row.getPerApT());
            pstmt.setDouble(10, row.getPerTl1Pt());
            pstmt.setDouble(11, row.getPerSaPt());
            pstmt.setDouble(12, row.getAvgSecA());
            pstmt.setLong(13, row.getNbCa());
            pstmt.setDouble(14, row.getPerCapt());
            pstmt.setDouble(15, row.getAvgSecT());
            pstmt.setLong(16, row.getNbCt());
            pstmt.setDouble(17, row.getPerCtPt());
            pstmt.setString(18, row.getDate());
            pstmt.setString(19, row.getAgenceId());

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
            logger.error(e.getMessage());
            return false;
        }
    }

    public boolean updateRow(EmpRow row) {
        try {
            Connection con = new CPConnection().getConnection();
            String sql = "UPDATE rougga_emp_table SET "
                    + "id_emp = ?, emp_name = ?, nb_t = ?, nb_tt = ?, nb_a = ?, nb_tl1 = ?, "
                    + "nb_sa = ?, perApT = ?, PERTL1pt = ?, perSApT = ?, avgSec_A = ?, "
                    + "nb_ca = ?, percapt = ?, avgSec_T = ?, nb_ct = ?, perctpt = ?, "
                    + "date = to_date(?,'YYYY-MM-DD HH24:MI:SS'), id_agence = ? "
                    + "WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, row.getUserId());
            pstmt.setString(2, row.getUserName());
            pstmt.setLong(3, row.getNbT());
            pstmt.setLong(4, row.getNbTt());
            pstmt.setLong(5, row.getNbA());
            pstmt.setLong(6, row.getNbTl1());
            pstmt.setLong(7, row.getNbSa());
            pstmt.setDouble(8, row.getPerApT());
            pstmt.setDouble(9, row.getPerTl1Pt());
            pstmt.setDouble(10, row.getPerSaPt());
            pstmt.setDouble(11, row.getAvgSecA());
            pstmt.setLong(12, row.getNbCa());
            pstmt.setDouble(13, row.getPerCapt());
            pstmt.setDouble(14, row.getAvgSecT());
            pstmt.setLong(15, row.getNbCt());
            pstmt.setDouble(16, row.getPerCtPt());
            pstmt.setString(17, row.getDate());
            pstmt.setString(18, row.getAgenceId());
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
            logger.error(e.getMessage());
            return false;
        }
    }

    public EmpRow getRowById(String id) {
        EmpRow row = null;
        try {
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT * FROM rougga_emp_table WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                row = new EmpRow(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("id_emp"),
                        rs.getString("emp_name"),
                        rs.getString("id_agence"),
                        CfgHandler.getFormatedDateAsString(CfgHandler.getFormatedDateAsDate(rs.getString("date"))),
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
                        rs.getDouble("perctpt")
                );
            } else {
                logger.info("No row found for id: " + id);
            }
            con.close();

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return row;
    }

    public boolean batchInsert(List<EmpRow> rows) {
        boolean isSuccess = false;
        try {
            Connection con = new CPConnection().getConnection();
            con.setAutoCommit(false); // Disable auto-commit
            String sql = "INSERT INTO rougga_emp_table ("
                    + "id, id_emp, emp_name, nb_t, nb_tt, nb_a, nb_tl1, nb_sa, "
                    + "perApT, PERTL1pt, perSApT, avgSec_A, nb_ca, percapt, "
                    + "avgSec_T, nb_ct, perctpt, date, id_agence) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, to_date(?,'YYYY-MM-DD HH24:MI:SS'), ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            for (EmpRow row : rows) {
                pstmt.setString(1, row.getId().toString());
                pstmt.setString(2, row.getUserId());
                pstmt.setString(3, row.getUserName());
                pstmt.setLong(4, row.getNbT());
                pstmt.setLong(5, row.getNbTt());
                pstmt.setLong(6, row.getNbA());
                pstmt.setLong(7, row.getNbTl1());
                pstmt.setLong(8, row.getNbSa());
                pstmt.setDouble(9, row.getPerApT());
                pstmt.setDouble(10, row.getPerTl1Pt());
                pstmt.setDouble(11, row.getPerSaPt());
                pstmt.setDouble(12, row.getAvgSecA());
                pstmt.setLong(13, row.getNbCa());
                pstmt.setDouble(14, row.getPerCapt());
                pstmt.setDouble(15, row.getAvgSecT());
                pstmt.setLong(16, row.getNbCt());
                pstmt.setDouble(17, row.getPerCtPt());
                pstmt.setString(18, row.getDate());
                pstmt.setString(19, row.getAgenceId());
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

    public boolean batchUpdate(List<EmpRow> rows) {
        boolean isSuccess = false;
        try {
            Connection con = new CPConnection().getConnection();
            con.setAutoCommit(false); // Disable auto-commit
            con.setAutoCommit(false); // Disable auto-commit
            String sql = "UPDATE rougga_emp_table SET "
                    + "id_emp = ?, emp_name = ?, nb_t = ?, nb_tt = ?, nb_a = ?, nb_tl1 = ?, "
                    + "nb_sa = ?, perApT = ?, PERTL1pt = ?, perSApT = ?, avgSec_A = ?, "
                    + "nb_ca = ?, percapt = ?, avgSec_T = ?, nb_ct = ?, perctpt = ?, "
                    + "date = to_date(?,'YYYY-MM-DD HH24:MI:SS'), id_agence = ? "
                    + "WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            for (EmpRow row : rows) {
                pstmt.setString(1, row.getUserId());
                pstmt.setString(2, row.getUserName());
                pstmt.setLong(3, row.getNbT());
                pstmt.setLong(4, row.getNbTt());
                pstmt.setLong(5, row.getNbA());
                pstmt.setLong(6, row.getNbTl1());
                pstmt.setLong(7, row.getNbSa());
                pstmt.setDouble(8, row.getPerApT());
                pstmt.setDouble(9, row.getPerTl1Pt());
                pstmt.setDouble(10, row.getPerSaPt());
                pstmt.setDouble(11, row.getAvgSecA());
                pstmt.setLong(12, row.getNbCa());
                pstmt.setDouble(13, row.getPerCapt());
                pstmt.setDouble(14, row.getAvgSecT());
                pstmt.setLong(15, row.getNbCt());
                pstmt.setDouble(16, row.getPerCtPt());
                pstmt.setString(17, row.getDate());
                pstmt.setString(18, row.getAgenceId());
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
    public EmpRow getTotaleRowByAgence(String id_agence, String date1, String date2) {
        EmpRow row = new EmpRow();
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT "
                    + "id_agence, "
                    + "'Sous-Totale' AS user_name, "
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
                    + "FROM rougga_emp_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
                    + "AND id_agence = ? "
                    + "GROUP BY id_agence";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date1);  // Start date
            pstmt.setString(2, date2);  // End date
            pstmt.setString(3, id_agence);  // id_agence

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row.setId(null);  // Setting `id` to null 
                row.setUserId(null);
                row.setUserName(rs.getString("user_name"));
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
                row.setDate(null);  // Setting `date` to null since it's not in the query output
                row.setAgenceId(rs.getString("id_agence"));
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return row;
    }

    public EmpRow getTotaleRow(String date1, String date2, String[] agences) {
        EmpRow row = new EmpRow();
        try {
            // Establish connection
            if (agences == null || agences.length <= 0) {
                agences = ac.putAgencesToStringArray(ac.getAllAgence());
            }

            Connection con = new CPConnection().getConnection();
            StringBuilder sqlBuilder = new StringBuilder("");
            if (agences.length > 0) {
                sqlBuilder.append(" AND  id_agence IN (");
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
                    + "'Totale' AS user_name, "
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
                    + "FROM rougga_emp_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') "
                    + "BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
                    + agenceCondition;
            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date1);  // Start date
            pstmt.setString(2, date2);  // End date

            //adding selected agences to preparedstatement
            for (int i = 0; i < agences.length; i++) {
                pstmt.setString(i + 3, agences[i]);
            }

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row.setId(null);  // Set ID to null
                row.setUserName(rs.getString("user_name"));
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
                row.setAgenceId(null);  // No id_agence provided in query output
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return row;
    }

    public List<EmpRow> getRowsByIdAgenceBetweenDates(String id_agence, String date1, String date2) {
        List<EmpRow> emps = new ArrayList<>();
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT "
                    + "id_emp, "
                    + "emp_name, "
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
                    + "FROM rougga_emp_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
                    + "AND id_agence = ? "
                    + "GROUP BY id_emp, emp_name "
                    + "ORDER BY emp_name";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date1);  // Start date
            pstmt.setString(2, date2);  // End date
            pstmt.setString(3, id_agence);  // id_agence

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            while (rs.next()) {
                EmpRow row = new EmpRow();
                row.setId(null);  // Set id to null 
                row.setUserId(rs.getString("id_emp"));
                row.setUserName(rs.getString("emp_name"));
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
                return emps; //if no rows exists return empty list
            }
            emps.add(this.getTotaleRowByAgence(id_agence, date1, date2)); // adding subtotale row as a service 
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return emps;
    }

    public EmpRow getRowByDate(String date, String id_agence, String userId) {
        EmpRow row = null;
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT * "
                    + "FROM rougga_emp_table "
                    + "WHERE  to_date(to_char(date,'YYYY-MM-DD'),'YYYY-MM-DD') = TO_DATE(?,'YYYY-MM-DD') and id_agence= ? and id_emp = ?";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date);
            pstmt.setString(2, id_agence);
            pstmt.setString(3, userId);
            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row = new EmpRow(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("id_emp"),
                        rs.getString("emp_name"),
                        rs.getString("id_agence"),
                        CfgHandler.getFormatedDateAsString(CfgHandler.getFormatedDateAsDate(rs.getString("date"))),
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
                        rs.getDouble("perctpt")
                );
                logger.info("row found for date: " + date + " id_agence = "
                        + id_agence
                        + " id_emp = " + userId);
            } else {
                logger.info("No row found for date: " + date + " id_agence = "
                        + id_agence
                        + " id_emp = " + userId);
            }

            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return row;
    }

    //get emp row between 2 dates and insert json result to database for every agency
    public void updateFromJson(String date1, String date2) {
        List<Agence> agences = ac.getAllAgence();
        for (Agence a : agences) {
            this.updateAgenceFromJson(date1, date2, a.getId().toString());
        }
    }

    //get emp row between 2 dates and insert json result to database for one agency
    public boolean updateAgenceFromJson(String date1, String date2, String agenceId) {
        boolean isDone = false;
        Agence a = new Agence();
        List<EmpRow> rowsToInsert = new ArrayList<>();
        List<EmpRow> rowsToUpdate = new ArrayList<>();
        //validationg data
        if (StringUtils.isBlank(agenceId)) {
            logger.error("updateAgenceFromJson: id agence null;");
            return false;
        }
        if (StringUtils.isAnyBlank(date1, date2)) {
            date1 = date2 = CfgHandler.format.format(new Date());
        }
        a = ac.getAgenceById(UUID.fromString(agenceId));
        if (a != null) {
            logger.info(" -- Updating " + a.getName() + "'s EMP Table ... ");
            String url = CfgHandler.prepareTableJsonUrl(a.getHost(), a.getPort(), CfgHandler.API_EMP_TABLE_JSON, date1, date2);
            logger.info("URL = " + url + " - " + a.getName());
            JSONObject json = UpdateController.getJsonFromUrl(url);

            if (json != null) {
                JSONArray result = (JSONArray) json.get("result");
                for (Object s : result) {
                    try {
                        JSONObject emp = (JSONObject) s;
                        String userId = emp.get("user_id").toString();
                        String id_agence = a.getId().toString();
                        EmpRow row = this.getRowByDate(date2,
                                id_agence,
                                userId);
                        if (row != null) {
                            try {
                                row.setUserName(emp.get("user_name").toString());
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
                                logger.info("EmpRow id: " + row.getId() + " found and updated ");
                            } catch (ParseException ex) {
                                logger.error(ex.getMessage());
                                return false;
                            }
                        } else {
                            try {
                                row = new EmpRow(
                                        this.getUniquId(),
                                        userId,
                                        emp.get("user_name").toString(),
                                        id_agence,
                                        CfgHandler.getFormatedDateAsString(CfgHandler.format.parse(date2)),
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
                                        (Double) emp.get("perctpt")
                                );
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
        logger.info(" --  Emp Table for " + a.getName() + " is Updated. ");
        return isDone;
    }

    //returns whole emp table for report page
    public List<Map> getTableAsList(String date1, String date2, String[] agences) {
        List<Map> result = new ArrayList<>();
        date1 = (date1 == null) ? CfgHandler.format.format(new Date()) : date1;
        date2 = (date2 == null) ? CfgHandler.format.format(new Date()) : date2;

        List<Agence> dbs = ac.getAgencesFromStringArray(agences);
        if (dbs == null || dbs.isEmpty()) {
            dbs = ac.getAllAgence();
        }
        for (Agence a : dbs) {
            List<EmpRow> emps = this.getRowsByIdAgenceBetweenDates(a.getId().toString(), date1, date2);
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

        //adding totale
        if (!result.isEmpty()) {
            List<EmpRow> emps = new ArrayList<>();
            Map<String, Object> newAgence = new HashMap<>();
            newAgence.put("id_agence", "Totale");
            newAgence.put("agence_name", "Totale");
            emps.add(this.getTotaleRow(date1, date2, agences));
            newAgence.put("emps", emps);
            result.add(newAgence);
        }

        return result;

    }

    //gets unique UUID after checking in emp table
    private UUID getUniquId() {
        UUID uniqueId = UUID.randomUUID();
        while (this.doesIdExist(uniqueId)) {
            uniqueId = UUID.randomUUID();
        }
        return uniqueId;
    }

    //checks if the id exists in emp table
    private boolean doesIdExist(UUID uniqueId) {
        try {
            Connection con = new CPConnection().getConnection();
            String sql = "select 1 from rougga_emp_table where id=? limit 1";
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

    //restore old rows from the oldest date to now for all agencies
    public void restoreOldRowsForAllAgences() {
        for (Agence a : ac.getAllAgence()) {
            if (this.restoreOldRowsByAgenceId(a.getId())) {

            } else {
                logger.error("restoreOldRowsForAllAgences: Couldn't restore data for " + a.getName());
            }
        }
        logger.info("restoreOldRowsForAllAgences: all agences's data restored!");
    }

    //restore old rows from the oldest date to now for one agency
    public boolean restoreOldRowsByAgenceId(UUID id_agence) {
        Agence a = ac.getAgenceById(id_agence);
        if (a == null) {
            logger.error("restoreOldRowsByAgenceId: agence not found!");
            return false;
        }
        Date oldestDate = ac.getOldesTicketDate(a.getId());
        if (oldestDate != null) {
            while (new Date().compareTo(oldestDate) > 0) {
                logger.info("Restoring EMP table data of "
                        + a.getName()
                        + " for date:"
                        + CfgHandler.getFormatedDateAsString(oldestDate)
                );
                this.updateAgenceFromJson(
                        CfgHandler.format.format(oldestDate),
                        CfgHandler.format.format(oldestDate),
                        id_agence.toString());
                Calendar c = Calendar.getInstance();
                c.setTime(oldestDate);
                c.add(Calendar.DATE, 1);
                oldestDate = c.getTime();
            }
            logger.info("Resored EMP table data of "
                    + a.getName());
        } else {
            logger.error("restoreOldRowsByAgenceId: oldest ticket date not found!");
            return false;
        }

        //add error handling
        return true;
    }

}
