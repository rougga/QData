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
import ma.rougga.qdata.modal.report.EmpSerRow;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmpSerTableController {

    private static final Logger logger = LoggerFactory.getLogger(EmpSerTableController.class);
    private final AgenceController ac = new AgenceController();

    public EmpSerTableController() {
    }

    public boolean addRow(EmpSerRow row) {
        try {
            Connection con = new CPConnection().getConnection();
            String sql = "INSERT INTO rougga_empser_table ("
                    + "id, id_emp, emp_name, service_id, service_name, agence_id, nb_t, nb_tt, nb_a, nb_tl1, nb_sa, "
                    + "perApT, perTl1Pt, perSaPt, avgSec_A, avgSec_T, nb_ca, percapt, nb_ct, perCtPt, date) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, to_date(?,'YYYY-MM-DD HH24:MI:SS'));";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, row.getId().toString());
            pstmt.setString(2, row.getUserId());
            pstmt.setString(3, row.getUserName());
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
            logger.error(e.getMessage());
            return false;
        }
    }

    public boolean updateRow(EmpSerRow row) {
        try {
            Connection con = new CPConnection().getConnection();
            String sql = "UPDATE rougga_empser_table SET "
                    + "id_emp = ?, emp_name = ?, service_id = ?, service_name = ?, agence_id = ?, "
                    + "nb_t = ?, nb_tt = ?, nb_a = ?, nb_tl1 = ?, nb_sa = ?, "
                    + "perApT = ?, perTl1Pt = ?, perSaPt = ?, avgSec_A = ?, avgSec_T = ?, "
                    + "nb_ca = ?, percapt = ?, nb_ct = ?, perCtPt = ?, date = to_date(?,'YYYY-MM-DD HH24:MI:SS') "
                    + "WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, row.getUserId());
            pstmt.setString(2, row.getUserName());
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
            logger.error(e.getMessage());
            return false;
        }
    }

    public EmpSerRow getRowById(String id) {
        EmpSerRow row = null;
        try {
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT * FROM rougga_empser_table WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                row = new EmpSerRow(
                        UUID.fromString(rs.getString("id")), rs.getString("id_emp"),
                        rs.getString("emp_name"), rs.getString("service_id"),
                        rs.getString("service_name"), rs.getString("agence_id"),
                        rs.getLong("nb_t"), rs.getLong("nb_tt"), rs.getLong("nb_a"),
                        rs.getLong("nb_tl1"), rs.getLong("nb_sa"), rs.getDouble("perApT"),
                        rs.getDouble("perTl1Pt"), rs.getDouble("perSaPt"),
                        rs.getDouble("avgSec_A"), rs.getDouble("avgSec_T"), rs.getLong("nb_ca"),
                        rs.getDouble("percapt"), rs.getLong("nb_ct"), rs.getDouble("perCtPt"),
                        CfgHandler.getFormatedDateAsString(CfgHandler.getFormatedDateAsDate(rs.getString("date"))));
            } else {
                logger.info("No row found for id: " + id);
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return row;
    }
    
    
      public boolean batchInsert(List<GblRow> rows) {
        boolean isSuccess = false;
        try {
            Connection con = new CPConnection().getConnection();
            con.setAutoCommit(false); // Disable auto-commit
            String sql = "INSERT INTO rougga_gbl_table ("
                    + "id_service, "
                    + "service_name, "
                    + "nb_t, "
                    + "nb_tt, "
                    + "nb_a, "
                    + "nb_tl1, "
                    + "nb_sa, "
                    + "perApT, "
                    + "PERTL1pt, "
                    + "perSApT, "
                    + "avgSec_A, "
                    + "nb_ca, "
                    + "percapt, "
                    + "avgSec_T, "
                    + "nb_ct, "
                    + "perctpt, "
                    + "date, "
                    + "id_agence,"
                    + "id"
                    + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, to_date(?,'YYYY-MM-DD HH24:MI:SS'), ?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            for (GblRow row : rows) {
                pstmt.setString(1, row.getIdService());  // id_service
                pstmt.setString(2, row.getServiceName());  // service_name
                pstmt.setLong(3, row.getNbT());  // nb_t
                pstmt.setLong(4, row.getNbTt());  // nb_tt
                pstmt.setLong(5, row.getNbA());  // nb_a
                pstmt.setLong(6, row.getNbTl1());  // nb_tl1
                pstmt.setLong(7, row.getNbSa());  // nb_sa
                pstmt.setDouble(8, row.getPerApT());  // perApT
                pstmt.setDouble(9, row.getPertl1Pt());  // PERTL1pt
                pstmt.setDouble(10, row.getPerSaPt());  // perSApT
                pstmt.setDouble(11, row.getAvgSecA());  // avgSec_A
                pstmt.setLong(12, row.getNbCa());  // nb_ca
                pstmt.setDouble(13, row.getPercapt());  // percapt
                pstmt.setDouble(14, row.getAvgSecT());  // avgSec_T
                pstmt.setLong(15, row.getNbCt());  // nb_ct
                pstmt.setDouble(16, row.getPerctPt());  // perctpt
                pstmt.setString(17, row.getDate());  // date
                pstmt.setString(18, row.getIdAgence());  // id_agence
                pstmt.setString(19, row.getId().toString());  // id
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

    public boolean batchUpdate(List<GblRow> rows) {
        boolean isSuccess = false;
        try {
            Connection con = new CPConnection().getConnection();
            con.setAutoCommit(false); // Disable auto-commit
            con.setAutoCommit(false); // Disable auto-commit
            String sql = "UPDATE rougga_gbl_table SET "
                    + "service_name = ?, "
                    + "nb_t = ?, "
                    + "nb_tt = ?, "
                    + "nb_a = ?, "
                    + "nb_tl1 = ?, "
                    + "nb_sa = ?, "
                    + "perApT = ?, "
                    + "PERTL1pt = ?, "
                    + "perSApT = ?, "
                    + "avgSec_A = ?, "
                    + "nb_ca = ?, "
                    + "percapt = ?, "
                    + "avgSec_T = ?, "
                    + "nb_ct = ?, "
                    + "perctpt = ?, "
                    + "date = to_date(?,'YYYY-MM-DD HH24:MI:SS') ,"
                    + "id_service = ?,"
                    + "id_agence=?"
                    + "WHERE id = ?;";

            PreparedStatement pstmt = con.prepareStatement(sql);

            for (GblRow row : rows) {
                pstmt.setString(1, row.getServiceName());  // service_name
                pstmt.setLong(2, row.getNbT());  // nb_t
                pstmt.setLong(3, row.getNbTt());  // nb_tt
                pstmt.setLong(4, row.getNbA());  // nb_a
                pstmt.setLong(5, row.getNbTl1());  // nb_tl1
                pstmt.setLong(6, row.getNbSa());  // nb_sa
                pstmt.setDouble(7, row.getPerApT());  // perApT
                pstmt.setDouble(8, row.getPertl1Pt());  // PERTL1pt
                pstmt.setDouble(9, row.getPerSaPt());  // perSApT
                pstmt.setDouble(10, row.getAvgSecA());  // avgSec_A
                pstmt.setLong(11, row.getNbCa());  // nb_ca
                pstmt.setDouble(12, row.getPercapt());  // percapt
                pstmt.setDouble(13, row.getAvgSecT());  // avgSec_T
                pstmt.setLong(14, row.getNbCt());  // nb_ct
                pstmt.setDouble(15, row.getPerctPt());  // perctpt
                pstmt.setString(16, row.getDate());// date
                pstmt.setString(17, row.getIdService());  // id_service
                pstmt.setString(18, row.getIdAgence()); // id_agence
                pstmt.setString(19, row.getId().toString()); // id
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
    public EmpSerRow getTotaleRowByAgence(String agenceId, String date1, String date2) {
        EmpSerRow row = new EmpSerRow();
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT "
                    + "agence_id, "
                    + "'Sous-Totale' AS emp_name,"
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
                    + "FROM rougga_empser_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
                    + "AND agence_id = ? "
                    + "GROUP BY agence_id";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date1);  // Start date
            pstmt.setString(2, date2);  // End date
            pstmt.setString(3, agenceId);  // agenceId

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row = new EmpSerRow(
                        null,
                        null,
                        rs.getString("emp_name"),
                        null,
                        rs.getString("service_name"),
                        agenceId,
                        rs.getLong("nb_t"),
                        rs.getLong("nb_tt"),
                        rs.getLong("nb_a"),
                        rs.getLong("nb_tl1"),
                        rs.getLong("nb_sa"),
                        rs.getDouble("perApT"),
                        rs.getDouble("perTl1Pt"),
                        rs.getDouble("perSaPt"),
                        rs.getDouble("avgSec_A"),
                        rs.getDouble("avgSec_T"),
                        rs.getLong("nb_ca"),
                        rs.getDouble("percapt"),
                        rs.getLong("nb_ct"),
                        rs.getDouble("perCtPt"),
                        null
                );
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return row;
    }

    public EmpSerRow getTotaleRow(String date1, String date2, String[] agences) {
        EmpSerRow row = new EmpSerRow();
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
                    + "'Totale' AS emp_name,"
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
                    + "FROM rougga_empser_table "
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
                row = new EmpSerRow(
                        null,
                        null,
                        rs.getString("emp_name"),
                        null,
                        rs.getString("service_name"),
                        null,
                        rs.getLong("nb_t"),
                        rs.getLong("nb_tt"),
                        rs.getLong("nb_a"),
                        rs.getLong("nb_tl1"),
                        rs.getLong("nb_sa"),
                        rs.getDouble("perApT"),
                        rs.getDouble("perTl1Pt"),
                        rs.getDouble("perSaPt"),
                        rs.getDouble("avgSec_A"),
                        rs.getDouble("avgSec_T"),
                        rs.getLong("nb_ca"),
                        rs.getDouble("percapt"),
                        rs.getLong("nb_ct"),
                        rs.getDouble("perCtPt"),
                        null
                );
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return row;
    }

    public List<EmpSerRow> getRowsByIdAgenceBetweenDates(String agenceId, String date1, String date2) {
        List<EmpSerRow> emps = new ArrayList<>();
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT "
                    + "id_emp, "
                    + "emp_name,"
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
                    + "FROM rougga_empser_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
                    + "AND agence_id = ? "
                    + "GROUP BY id_emp, emp_name,service_id,service_name "
                    + "ORDER BY emp_name,service_name";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date1);  // Start date
            pstmt.setString(2, date2);  // End date
            pstmt.setString(3, agenceId);  // agence_id

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            while (rs.next()) {
                EmpSerRow row = new EmpSerRow(
                        null,
                        rs.getString("id_emp"),
                        rs.getString("emp_name"),
                        rs.getString("service_id"),
                        rs.getString("service_name"),
                        agenceId,
                        rs.getLong("nb_t"),
                        rs.getLong("nb_tt"),
                        rs.getLong("nb_a"),
                        rs.getLong("nb_tl1"),
                        rs.getLong("nb_sa"),
                        rs.getDouble("perApT"),
                        rs.getDouble("perTl1Pt"),
                        rs.getDouble("perSaPt"),
                        rs.getDouble("avgSec_A"),
                        rs.getDouble("avgSec_T"),
                        rs.getLong("nb_ca"),
                        rs.getDouble("percapt"),
                        rs.getLong("nb_ct"),
                        rs.getDouble("perCtPt"),
                        null
                );

                emps.add(row);
            }
            if (emps.size() <= 0) {
                con.close();
                return emps; //if no rows exists return empty list
            }
            emps.add(this.getTotaleRowByAgence(agenceId, date1, date2)); // adding subtotale row as a service 
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return emps;
    }

    public EmpSerRow getRowByDate(String date, String id_agence, String userId, String serviceId) {
        EmpSerRow row = null;
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT * "
                    + "FROM rougga_empser_table "
                    + "WHERE  to_date(to_char(date,'YYYY-MM-DD'),'YYYY-MM-DD') = TO_DATE(?,'YYYY-MM-DD') and agence_id= ? and id_emp = ? and service_id=?";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date);
            pstmt.setString(2, id_agence);
            pstmt.setString(3, userId);
            pstmt.setString(4, serviceId);
            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row = new EmpSerRow(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("id_emp"),
                        rs.getString("emp_name"),
                        rs.getString("service_id"),
                        rs.getString("service_name"),
                        rs.getString("agence_id"),
                        rs.getLong("nb_t"),
                        rs.getLong("nb_tt"),
                        rs.getLong("nb_a"),
                        rs.getLong("nb_tl1"),
                        rs.getLong("nb_sa"),
                        rs.getDouble("perApT"),
                        rs.getDouble("perTl1Pt"),
                        rs.getDouble("perSaPt"),
                        rs.getDouble("avgSec_A"),
                        rs.getDouble("avgSec_T"),
                        rs.getLong("nb_ca"),
                        rs.getDouble("percapt"),
                        rs.getLong("nb_ct"),
                        rs.getDouble("perCtPt"),
                        CfgHandler.getFormatedDateAsString(CfgHandler.getFormatedDateAsDate(rs.getString("date")))
                );
                logger.info("row found for date: " + date + " agence_id = "
                        + id_agence
                        + " id_emp = " + userId);
            } else {
                logger.info("No row found for date: " + date + " agence_id = "
                        + id_agence
                        + " id_emp = " + userId);
            }

            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return row;
    }

    //get empser row between 2 dates and insert json result to database for every agency
    public void updateFromJson(String date1, String date2) {
        List<Agence> agences = ac.getAllAgence();
        for (Agence a : agences) {
            this.updateAgenceFromJson(date1, date2, a.getId().toString());
        }
    }

    //get empser row between 2 dates and insert json result to database for one agency
    public boolean updateAgenceFromJson(String date1, String date2, String agenceId) {
        boolean isDone = false;
        Agence a = new Agence();
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
            logger.info(" -- Updating " + a.getName() + "'s EMPSER Table ... ");
            String url = CfgHandler.prepareTableJsonUrl(a.getHost(), a.getPort(), CfgHandler.API_EMPSER_TABLE_JSON, date1, date2);
            logger.info("URL = " + url + " - " + a.getName());
            JSONObject json = UpdateController.getJsonFromUrl(url);

            if (json != null) {
                JSONArray result = (JSONArray) json.get("result");
                for (Object s : result) {
                    try {
                        JSONObject emp = (JSONObject) s;
                        String userId = emp.get("user_id").toString();
                        String serviceId = emp.get("service_id").toString();
                        String id_agence = a.getId().toString();
                        EmpSerRow row = this.getRowByDate(date2,
                                id_agence,
                                userId, serviceId);
                        if (row != null) {
                            try {
                                row.setUserId(userId);
                                row.setUserName(emp.get("user_name").toString());
                                row.setServiceName(emp.get("service_name").toString());
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
                                this.updateRow(row);
                                logger.info("EmpSerRow id: " + row.getId() + " found and updated ");
                            } catch (ParseException ex) {
                                logger.error(ex.getMessage());
                                return false;
                            }
                        } else {
                            try {
                                row = new EmpSerRow(
                                        this.getUniquId(),
                                        userId,
                                        emp.get("user_name").toString(),
                                        serviceId,
                                        emp.get("service_name").toString(),
                                        id_agence,
                                        (long) emp.get("nb_t"),
                                        (long) emp.get("nb_tt"),
                                        (long) emp.get("nb_a"),
                                        (long) emp.get("nb_tl1"),
                                        (long) emp.get("nb_sa"),
                                        (Double) emp.get("perApT"),
                                        (Double) emp.get("PERTL1pt"),
                                        (Double) emp.get("perSApT"),
                                        (Double) emp.get("avgSec_A"),
                                        (Double) emp.get("avgSec_T"),
                                        (long) emp.get("nb_ca"),
                                        (Double) emp.get("percapt"),
                                        (long) emp.get("nb_ct"),
                                        (Double) emp.get("perctpt"),
                                        CfgHandler.getFormatedDateAsString(CfgHandler.format.parse(date2))
                                );
                                this.addRow(row);
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
        logger.info(" --  EmpSer Table for " + a.getName() + " is Updated. ");
        return isDone;
    }

    //returns whole empser table for report page
    public List<Map> getTableAsList(String date1, String date2, String[] agences) {
        List<Map> result = new ArrayList<>();
        date1 = (date1 == null) ? CfgHandler.format.format(new Date()) : date1;
        date2 = (date2 == null) ? CfgHandler.format.format(new Date()) : date2;

        List<Agence> dbs = ac.getAgencesFromStringArray(agences);
        if (dbs == null || dbs.isEmpty()) {
            dbs = ac.getAllAgence();
        }
        for (Agence a : dbs) {
            List<EmpSerRow> emps = this.getRowsByIdAgenceBetweenDates(a.getId().toString(), date1, date2);
            if (!emps.isEmpty()) {
                Map<String, Object> newAgence = new HashMap<>();
                newAgence.put("id_agence", a.getId().toString());
                newAgence.put("agence_name", a.getName());
                newAgence.put("emps", emps);
                result.add(newAgence);
            }

        }

        //adding totale
        if (!result.isEmpty()) {
            List<EmpSerRow> emps = new ArrayList<>();
            Map<String, Object> newAgence = new HashMap<>();
            newAgence.put("id_agence", "Totale");
            newAgence.put("agence_name", "Totale");
            emps.add(this.getTotaleRow(date1, date2, agences));
            newAgence.put("emps", emps);
            result.add(newAgence);
        }

        return result;

    }

    //gets unique UUID after checking in empser table
    private UUID getUniquId() {
        UUID uniqueId = UUID.randomUUID();
        while (this.doesIdExist(uniqueId)) {
            uniqueId = UUID.randomUUID();
        }
        return uniqueId;
    }

    //checks if the id exists in empser table
    private boolean doesIdExist(UUID uniqueId) {
        try {
            Connection con = new CPConnection().getConnection();
            String sql = "select 1 from rougga_empser_table where id=? limit 1";
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
                logger.info("Restoring EMPSER table data of "
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
            logger.info("Resored EMPSER table data of "
                    + a.getName());
        } else {
            logger.error("restoreOldRowsByAgenceId: oldest ticket date not found!");
            return false;
        }

        //add error handling
        return true;
    }

}
