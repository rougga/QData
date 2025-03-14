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
import ma.rougga.qdata.modal.GblRow;
import ma.rougga.qdata.modal.Zone;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.LoggerFactory;

public class GblTableController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(GblTableController.class);

    private final String[] gblCols = new String[]{"Site", "Service", "Nb. Tickets", "Nb. Traités", "Nb. Absents", "Nb. Traités <1mn", "Nb. Sans affectation", "Absents/Nb. Tickets(%)", "Traités<1mn/Nb. Tickets(%)", "Sans affect/Nb. Tickets(%)", "Moyenne d'attente", ">Cible", "%Cible", "Moyenne Traitement", ">Cible", "%Cible"};

    AgenceController ac = new AgenceController();

    public GblTableController() {
    }

    public String[] getGblCols() {
        return gblCols;
    }

    public boolean addRow(GblRow row) {
        try {
            Connection con = new CPConnection().getConnection();
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

            PreparedStatement pstmt = con.prepareCall(sql);
            // Set parameters
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
            // Execute the insert
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                con.close();
                return true;
            } else {
                logger.info("A  row wasnt inserted successfully!");
                con.close();
                return false;
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public boolean updateRow(GblRow row) {
        try {
            Connection con = new CPConnection().getConnection();
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
            PreparedStatement pstmt = con.prepareCall(sql);
            // Set parameters

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

            // Execute the insert
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                con.close();
                return true;
            } else {
                logger.info("A  row wasnt UPDATED successfully!");
                con.close();
                return false;
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public GblRow getRowById(String id) {
        GblRow row = null;
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT id, id_service, service_name, nb_t, nb_tt, nb_a, nb_tl1, nb_sa, perApT, PERTL1pt, perSApT, "
                    + "avgSec_A, nb_ca, percapt, avgSec_T, nb_ct, perctpt, date, id_agence "
                    + "FROM rougga_gbl_table WHERE id = ?";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row = new GblRow();
                row.setId(UUID.fromString(rs.getString("id")));
                row.setIdService(rs.getString("id_service"));
                row.setServiceName(rs.getString("service_name"));
                row.setNbT(rs.getLong("nb_t"));
                row.setNbTt(rs.getLong("nb_tt"));
                row.setNbA(rs.getLong("nb_a"));
                row.setNbTl1(rs.getLong("nb_tl1"));
                row.setNbSa(rs.getLong("nb_sa"));
                row.setPerApT(rs.getDouble("perApT"));
                row.setPertl1Pt(rs.getDouble("PERTL1pt"));
                row.setPerSaPt(rs.getDouble("perSApT"));
                row.setAvgSecA(rs.getDouble("avgSec_A"));
                row.setNbCa(rs.getLong("nb_ca"));
                row.setPercapt(rs.getDouble("percapt"));
                row.setAvgSecT(rs.getDouble("avgSec_T"));
                row.setNbCt(rs.getLong("nb_ct"));
                row.setPerctPt(rs.getDouble("perctpt"));
                row.setDate(CfgHandler.getFormatedDateAsString(CfgHandler.getFormatedDateAsDate(rs.getString("date"))));
                row.setIdAgence(rs.getString("id_agence"));

            } else {
                logger.info("No row found for id: {}", id);
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
//            logger.info("batchInsert: inserted {} rows", batchResults.length);
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
//            logger.info("batchUpdate: updated {} rows", batchResults.length);
            con.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return isSuccess;
    }

    //
    public GblRow getTotaleRowByAgence(String id_agence, String date1, String date2) {
        GblRow row = new GblRow();
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT "
                    + "id_agence, "
                    + "'Sous-Totale' AS service_name, "
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
                    + "FROM rougga_gbl_table "
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
                row.setIdService(null);
                row.setServiceName(rs.getString("service_name"));
                row.setNbT(rs.getLong("nb_t"));
                row.setNbTt(rs.getLong("nb_tt"));
                row.setNbA(rs.getLong("nb_a"));
                row.setNbTl1(rs.getLong("nb_tl1"));
                row.setNbSa(rs.getLong("nb_sa"));
                row.setPerApT(rs.getDouble("perApT"));
                row.setPertl1Pt(rs.getDouble("PERTL1pt"));
                row.setPerSaPt(rs.getDouble("perSApT"));
                row.setAvgSecA(rs.getDouble("avgSec_A"));
                row.setNbCa(rs.getLong("nb_ca"));
                row.setPercapt(rs.getDouble("percapt"));
                row.setAvgSecT(rs.getDouble("avgSec_T"));
                row.setNbCt(rs.getLong("nb_ct"));
                row.setPerctPt(rs.getDouble("perctpt"));
                row.setDate(null);  // Setting `date` to null since it's not in the query output
                row.setIdAgence(rs.getString("id_agence"));
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return row;
    }

    public GblRow getTotaleRow(String date1, String date2, String[] agences) {
        GblRow row = new GblRow();
        try {
            // Establish connection
            if (agences == null || agences.length == 0) {
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
                    + "'Totale' AS service_name, "
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
                    + "FROM rougga_gbl_table "
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
                row.setServiceName(rs.getString("service_name"));
                row.setNbT(rs.getLong("nb_t"));
                row.setNbTt(rs.getLong("nb_tt"));
                row.setNbA(rs.getLong("nb_a"));
                row.setNbTl1(rs.getLong("nb_tl1"));
                row.setNbSa(rs.getLong("nb_sa"));
                row.setPerApT(rs.getDouble("perApT"));
                row.setPertl1Pt(rs.getDouble("PERTL1pt"));
                row.setPerSaPt(rs.getDouble("perSApT"));
                row.setAvgSecA(rs.getDouble("avgSec_A"));
                row.setNbCa(rs.getLong("nb_ca"));
                row.setPercapt(rs.getDouble("percapt"));
                row.setAvgSecT(rs.getDouble("avgSec_T"));
                row.setNbCt(rs.getLong("nb_ct"));
                row.setPerctPt(rs.getDouble("perctpt"));
                row.setIdAgence(null);  // No id_agence provided in query output
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return row;
    }

    public List<GblRow> getRowsByIdAgenceBetweenDates(String id_agence, String date1, String date2) {
        List<GblRow> services = new ArrayList<>();
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT "
                    + "id_service, "
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
                    + "FROM rougga_gbl_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
                    + "AND id_agence = ? "
                    + "GROUP BY id_service, service_name "
                    + "ORDER BY service_name";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date1);  // Start date
            pstmt.setString(2, date2);  // End date
            pstmt.setString(3, id_agence);  // id_agence

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            while (rs.next()) {
                GblRow row = new GblRow();
                row.setId(null);  // Set id to null 
                row.setIdService(rs.getString("id_service"));
                row.setServiceName(rs.getString("service_name"));
                row.setNbT(rs.getLong("nb_t"));
                row.setNbTt(rs.getLong("nb_tt"));
                row.setNbA(rs.getLong("nb_a"));
                row.setNbTl1(rs.getLong("nb_tl1"));
                row.setNbSa(rs.getLong("nb_sa"));
                row.setPerApT(rs.getDouble("perApT"));
                row.setPertl1Pt(rs.getDouble("PERTL1pt"));
                row.setPerSaPt(rs.getDouble("perSApT"));
                row.setAvgSecA(rs.getDouble("avgSec_A"));
                row.setNbCa(rs.getLong("nb_ca"));
                row.setPercapt(rs.getDouble("percapt"));
                row.setAvgSecT(rs.getDouble("avgSec_T"));
                row.setNbCt(rs.getLong("nb_ct"));
                row.setPerctPt(rs.getDouble("perctpt"));
                row.setIdAgence(id_agence);
                services.add(row);
            }
            if (services.size() == 0) {
                con.close();
                return services;
            }
            services.add(this.getTotaleRowByAgence(id_agence, date1, date2)); // adding subtotale row as a service 
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return services;
    }

    public GblRow getRowByDate(String date, String id_agence, String id_service) {
        GblRow row = null;
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT id, id_service, service_name, nb_t, nb_tt, nb_a, nb_tl1, nb_sa, perApT, PERTL1pt, perSApT, "
                    + "avgSec_A, nb_ca, percapt, avgSec_T, nb_ct, perctpt, date, id_agence "
                    + "FROM rougga_gbl_table WHERE  to_date(to_char(date,'YYYY-MM-DD'),'YYYY-MM-DD') = TO_DATE(?,'YYYY-MM-DD') and id_agence= ? and id_service = ?";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date);
            pstmt.setString(2, id_agence);
            pstmt.setString(3, id_service);
            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row = new GblRow();
                row.setId(UUID.fromString(rs.getString("id")));
                row.setIdService(rs.getString("id_service"));
                row.setServiceName(rs.getString("service_name"));
                row.setNbT(rs.getLong("nb_t"));
                row.setNbTt(rs.getLong("nb_tt"));
                row.setNbA(rs.getLong("nb_a"));
                row.setNbTl1(rs.getLong("nb_tl1"));
                row.setNbSa(rs.getLong("nb_sa"));
                row.setPerApT(rs.getDouble("perApT"));
                row.setPertl1Pt(rs.getDouble("PERTL1pt"));
                row.setPerSaPt(rs.getDouble("perSApT"));
                row.setAvgSecA(rs.getDouble("avgSec_A"));
                row.setNbCa(rs.getLong("nb_ca"));
                row.setPercapt(rs.getDouble("percapt"));
                row.setAvgSecT(rs.getDouble("avgSec_T"));
                row.setNbCt(rs.getLong("nb_ct"));
                row.setPerctPt(rs.getDouble("perctpt"));
                row.setDate(CfgHandler.getFormatedDateAsString(CfgHandler.getFormatedDateAsDate(rs.getString("date"))));
                row.setIdAgence(rs.getString("id_agence"));
                logger.info("row found for date: {} id_agence = {} id_service = {}", date, id_agence, id_service);
            } else {
                logger.info("No row found for date: {} id_agence = {} id_service = {}", date, id_agence, id_service);
            }

            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return row;
    }

    //get gbl row between 2 dates and insert json result to database for every agency
    public void updateFromJson(String date1, String date2) {
        List<Agence> agences = ac.getAllAgence();
        for (Agence a : agences) {
            if (ac.isOnline(a.getId())) {
                this.updateAgenceFromJson(date1, date2, a.getId().toString());
            }
        }
    }

    //get gbl row between 2 dates and insert json result to database for one agency
    public boolean updateAgenceFromJson(String date1, String date2, String agenceId) {
        boolean isDone = false;
        Agence a = new Agence();
        List<GblRow> rowsToInsert = new ArrayList<>();
        List<GblRow> rowsToUpdate = new ArrayList<>();
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
            String url = CfgHandler.prepareTableJsonUrl(a.getHost(), a.getPort(), CfgHandler.API_GBL_TABLE_JSON, date1, date2);
            logger.info(" -- Updating {}'s GBL Table for {} : {} ", a.getName(),date2, url);
            JSONObject json = UpdateController.getJsonFromUrl(url);

            if (json != null) {
                JSONArray result = (JSONArray) json.get("result");
                for (Object s : result) {
                    try {
                        JSONObject service = (JSONObject) s;
                        String id_service = service.get("service_id").toString(),
                                id_agence = a.getId().toString();
                        GblRow row = this.getRowByDate(date2,
                                id_agence,
                                id_service);
                        if (row != null) {
                            try {
                                row.setServiceName(service.get("service_name").toString());
                                logger.info("Nb. T : {} to {}", row.getNbT(), service.get("nb_t"));
                                row.setNbT((long) service.get("nb_t"));
                                row.setNbTt((long) service.get("nb_tt"));
                                row.setNbA((long) service.get("nb_a"));
                                row.setNbTl1((long) service.get("nb_tl1"));
                                row.setNbSa((long) service.get("nb_sa"));
                                row.setPerApT((Double) service.get("perApT"));
                                row.setPertl1Pt((Double) service.get("PERTL1pt"));
                                row.setPerSaPt((Double) service.get("perSApT"));
                                row.setAvgSecA((Double) service.get("avgSec_A"));
                                row.setNbCa((long) service.get("nb_ca"));
                                row.setPercapt((Double) service.get("percapt"));
                                row.setAvgSecT((Double) service.get("avgSec_T"));
                                row.setNbCt((long) service.get("nb_ct"));
                                row.setPerctPt((Double) service.get("perctpt"));
                                row.setDate(CfgHandler.getFormatedDateAsString(CfgHandler.format.parse(date2)));
//                                this.updateRow(row);
                                rowsToUpdate.add(row);
                            } catch (ParseException ex) {
                                logger.error(ex.getMessage());
                                return false;
                            }
                        } else {
                            try {
                                row = new GblRow(
                                        this.getUniquId(),
                                        id_service,
                                        service.get("service_name").toString(),
                                        (long) service.get("nb_t"),
                                        (long) service.get("nb_tt"),
                                        (long) service.get("nb_a"),
                                        (long) service.get("nb_tl1"),
                                        (long) service.get("nb_sa"),
                                        (Double) service.get("perApT"),
                                        (Double) service.get("PERTL1pt"),
                                        (Double) service.get("perSApT"),
                                        (Double) service.get("avgSec_A"),
                                        (long) service.get("nb_ca"),
                                        (Double) service.get("percapt"),
                                        (Double) service.get("avgSec_T"),
                                        (long) service.get("nb_ct"),
                                        (Double) service.get("perctpt"),
                                        CfgHandler.getFormatedDateAsString(CfgHandler.format.parse(date2)),
                                        id_agence);
//                                this.addRow(row);
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

        this.batchInsert(rowsToInsert);
        this.batchUpdate(rowsToUpdate);
        logger.info(" --  GBL Table Updated. ");
        return isDone;
    }

    //returns whole gbl table for report page
    public List<Map> getTableAsList(String date1, String date2, String[] agences) {
        List<Map> result = new ArrayList<>();
        date1 = (date1 == null) ? CfgHandler.format.format(new Date()) : date1;
        date2 = (date2 == null) ? CfgHandler.format.format(new Date()) : date2;

        List<Agence> dbs = ac.getAgencesFromStringArray(agences);
        if (dbs == null || dbs.isEmpty()) {
            dbs = ac.getAllAgence();
        }
        for (Agence a : dbs) {
            List<GblRow> services = this.getRowsByIdAgenceBetweenDates(a.getId().toString(), date1, date2);
            if (!services.isEmpty()) {
                Map<String, Object> newAgence = new HashMap<>();
                newAgence.put("id_agence", a.getId().toString());
                String agenceName = a.getName();
                Zone zone = ac.getAgenceZoneByAgenceId(a.getId());
                if (zone != null) {
                    agenceName += " (" + zone.getName() + ")";
                }
                newAgence.put("agence_name", agenceName);
                newAgence.put("services", services);
                result.add(newAgence);
            }

        }

        //adding totale
        if (!result.isEmpty()) {
            List<GblRow> services = new ArrayList<>();
            Map<String, Object> newAgence = new HashMap<>();
            newAgence.put("id_agence", "Totale");
            newAgence.put("agence_name", "Totale");
            services.add(this.getTotaleRow(date1, date2, agences));
            newAgence.put("services", services);
            result.add(newAgence);
        }

        return result;

    }

    //gets unique UUID after checking in gbl table
    private UUID getUniquId() {
        UUID uniqueId = UUID.randomUUID();
        while (this.doesIdExist(uniqueId)) {
            uniqueId = UUID.randomUUID();
        }
        return uniqueId;
    }

    //checks if the id exists in gbl table
    private boolean doesIdExist(UUID uniqueId) {
        try {
            Connection con = new CPConnection().getConnection();
            String sql = "select * from rougga_gbl_table where id=?";
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
            //add error handling
            this.restoreOldRowsByAgenceId(a.getId());
        }
    }

    //restore old rows from the oldest date to now for one agency
    public boolean restoreOldRowsByAgenceId(UUID id_agence) {
        Agence a = ac.getAgenceById(id_agence);
        if (a == null) {
            return false;
        }
        Date oldestDate = ac.getOldesTicketDate(a.getId());
        if (oldestDate != null) {
            while (new Date().compareTo(oldestDate) > 0) {
                logger.info("Restoring GBL table data of {} for date:{}", a.getName(), CfgHandler.getFormatedDateAsString(oldestDate));
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

        //add error handling
        return true;
    }

}
