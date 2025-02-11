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
import ma.rougga.qdata.modal.report.ThSARow;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThSATableController {

    public ThSATableController() {
    }
    private static final Logger logger = LoggerFactory.getLogger(ThSATableController.class);
    private final AgenceController ac = new AgenceController();

    public boolean addRow(ThSARow row) {
        String sql = "INSERT INTO rougga_thsa_table ("
                + "id, date, agence_id, service_id, service_name, "
                + "h0, h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11, h12, h13, h14, h15, h16, h17, h18, h19, h20, h21, h22, h23) "
                + "VALUES (?, to_date(?,'YYYY-MM-DD HH24:MI:SS'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = new CPConnection().getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, row.getId().toString());
            pstmt.setString(2, row.getDate());
            pstmt.setString(3, row.getAgenceId());
            pstmt.setString(4, row.getServiceId());
            pstmt.setString(5, row.getServiceName());

            pstmt.setLong(6, row.getH0());
            pstmt.setLong(7, row.getH1());
            pstmt.setLong(8, row.getH2());
            pstmt.setLong(9, row.getH3());
            pstmt.setLong(10, row.getH4());
            pstmt.setLong(11, row.getH5());
            pstmt.setLong(12, row.getH6());
            pstmt.setLong(13, row.getH7());
            pstmt.setLong(14, row.getH8());
            pstmt.setLong(15, row.getH9());
            pstmt.setLong(16, row.getH10());
            pstmt.setLong(17, row.getH11());
            pstmt.setLong(18, row.getH12());
            pstmt.setLong(19, row.getH13());
            pstmt.setLong(20, row.getH14());
            pstmt.setLong(21, row.getH15());
            pstmt.setLong(22, row.getH16());
            pstmt.setLong(23, row.getH17());
            pstmt.setLong(24, row.getH18());
            pstmt.setLong(25, row.getH19());
            pstmt.setLong(26, row.getH20());
            pstmt.setLong(27, row.getH21());
            pstmt.setLong(28, row.getH22());
            pstmt.setLong(29, row.getH23());

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
            logger.error("Error inserting row: {}", e.getMessage());
            return false;
        }
    }

    public boolean updateRow(ThSARow row) {
        String sql = "UPDATE rougga_thsa_table SET "
                + "date = to_date(?,'YYYY-MM-DD HH24:MI:SS'), "
                + "agence_id = ?, "
                + "service_id = ?, "
                + "service_name = ?, "
                + "h0 = ?, h1 = ?, h2 = ?, h3 = ?, h4 = ?, h5 = ?, h6 = ?, h7 = ?, h8 = ?, h9 = ?, h10 = ?, "
                + "h11 = ?, h12 = ?, h13 = ?, h14 = ?, h15 = ?, h16 = ?, h17 = ?, h18 = ?, h19 = ?, h20 = ?, h21 = ?, h22 = ?, h23 = ? "
                + "WHERE id = ?";

        try (Connection con = new CPConnection().getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, row.getDate());
            pstmt.setString(2, row.getAgenceId());
            pstmt.setString(3, row.getServiceId());
            pstmt.setString(4, row.getServiceName());

            pstmt.setLong(5, row.getH0());
            pstmt.setLong(6, row.getH1());
            pstmt.setLong(7, row.getH2());
            pstmt.setLong(8, row.getH3());
            pstmt.setLong(9, row.getH4());
            pstmt.setLong(10, row.getH5());
            pstmt.setLong(11, row.getH6());
            pstmt.setLong(12, row.getH7());
            pstmt.setLong(13, row.getH8());
            pstmt.setLong(14, row.getH9());
            pstmt.setLong(15, row.getH10());
            pstmt.setLong(16, row.getH11());
            pstmt.setLong(17, row.getH12());
            pstmt.setLong(18, row.getH13());
            pstmt.setLong(19, row.getH14());
            pstmt.setLong(20, row.getH15());
            pstmt.setLong(21, row.getH16());
            pstmt.setLong(22, row.getH17());
            pstmt.setLong(23, row.getH18());
            pstmt.setLong(24, row.getH19());
            pstmt.setLong(25, row.getH20());
            pstmt.setLong(26, row.getH21());
            pstmt.setLong(27, row.getH22());
            pstmt.setLong(28, row.getH23());

            pstmt.setString(29, row.getId().toString());

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
            logger.error("Error updating row: {}", e.getMessage());
            return false;
        }
    }

    public ThSARow getRowById(String id) {
        String sql = "SELECT * FROM rougga_thsa_table WHERE id = ?";
        ThSARow row = null;

        try (Connection con = new CPConnection().getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                row = new ThSARow();
                row.setId(UUID.fromString(rs.getString("id")));
                row.setDate(CfgHandler.getFormatedDateAsString(CfgHandler.getFormatedDateAsDate(rs.getString("date"))));
                row.setAgenceId(rs.getString("agence_id"));
                row.setServiceId(rs.getString("service_id"));
                row.setServiceName(rs.getString("service_name"));

                row.setH0(rs.getLong("h0"));
                row.setH1(rs.getLong("h1"));
                row.setH2(rs.getLong("h2"));
                row.setH3(rs.getLong("h3"));
                row.setH4(rs.getLong("h4"));
                row.setH5(rs.getLong("h5"));
                row.setH6(rs.getLong("h6"));
                row.setH7(rs.getLong("h7"));
                row.setH8(rs.getLong("h8"));
                row.setH9(rs.getLong("h9"));
                row.setH10(rs.getLong("h10"));
                row.setH11(rs.getLong("h11"));
                row.setH12(rs.getLong("h12"));
                row.setH13(rs.getLong("h13"));
                row.setH14(rs.getLong("h14"));
                row.setH15(rs.getLong("h15"));
                row.setH16(rs.getLong("h16"));
                row.setH17(rs.getLong("h17"));
                row.setH18(rs.getLong("h18"));
                row.setH19(rs.getLong("h19"));
                row.setH20(rs.getLong("h20"));
                row.setH21(rs.getLong("h21"));
                row.setH22(rs.getLong("h22"));
                row.setH23(rs.getLong("h23"));
            }

        } catch (SQLException e) {
            logger.error("Error retrieving row: {}", e.getMessage());
        }

        return row;
    }

    public boolean batchInsert(List<ThSARow> rows) {
        boolean isSuccess = false;
        try {
            Connection con = new CPConnection().getConnection();
            con.setAutoCommit(false); // Disable auto-commit
            String sql = "INSERT INTO rougga_thsa_table ("
                    + "id, date, agence_id, service_id, service_name, "
                    + "h0, h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11, h12, h13, h14, h15, h16, h17, h18, h19, h20, h21, h22, h23) "
                    + "VALUES (?, to_date(?,'YYYY-MM-DD HH24:MI:SS'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            for (ThSARow row : rows) {
                pstmt.setString(1, row.getId().toString());
                pstmt.setString(2, row.getDate());
                pstmt.setString(3, row.getAgenceId());
                pstmt.setString(4, row.getServiceId());
                pstmt.setString(5, row.getServiceName());

                pstmt.setLong(6, row.getH0());
                pstmt.setLong(7, row.getH1());
                pstmt.setLong(8, row.getH2());
                pstmt.setLong(9, row.getH3());
                pstmt.setLong(10, row.getH4());
                pstmt.setLong(11, row.getH5());
                pstmt.setLong(12, row.getH6());
                pstmt.setLong(13, row.getH7());
                pstmt.setLong(14, row.getH8());
                pstmt.setLong(15, row.getH9());
                pstmt.setLong(16, row.getH10());
                pstmt.setLong(17, row.getH11());
                pstmt.setLong(18, row.getH12());
                pstmt.setLong(19, row.getH13());
                pstmt.setLong(20, row.getH14());
                pstmt.setLong(21, row.getH15());
                pstmt.setLong(22, row.getH16());
                pstmt.setLong(23, row.getH17());
                pstmt.setLong(24, row.getH18());
                pstmt.setLong(25, row.getH19());
                pstmt.setLong(26, row.getH20());
                pstmt.setLong(27, row.getH21());
                pstmt.setLong(28, row.getH22());
                pstmt.setLong(29, row.getH23());

                pstmt.addBatch(); // Add to batch
            }

            int[] batchResults = pstmt.executeBatch(); // Execute batch
            con.commit(); // Commit transaction
            isSuccess = batchResults.length == rows.size();
            logger.info("batchInsert: inserted {} rows", batchResults.length);
            con.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return isSuccess;
    }

    public boolean batchUpdate(List<ThSARow> rows) {
        boolean isSuccess = false;
        try {
            Connection con = new CPConnection().getConnection();
            con.setAutoCommit(false); // Disable auto-commit
            con.setAutoCommit(false); // Disable auto-commit
            String sql = "UPDATE rougga_thsa_table SET "
                    + "date = to_date(?,'YYYY-MM-DD HH24:MI:SS'), "
                    + "agence_id = ?, "
                    + "service_id = ?, "
                    + "service_name = ?, "
                    + "h0 = ?, h1 = ?, h2 = ?, h3 = ?, h4 = ?, h5 = ?, h6 = ?, h7 = ?, h8 = ?, h9 = ?, h10 = ?, "
                    + "h11 = ?, h12 = ?, h13 = ?, h14 = ?, h15 = ?, h16 = ?, h17 = ?, h18 = ?, h19 = ?, h20 = ?, h21 = ?, h22 = ?, h23 = ? "
                    + "WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            for (ThSARow row : rows) {

                pstmt.setString(1, row.getDate());
                pstmt.setString(2, row.getAgenceId());
                pstmt.setString(3, row.getServiceId());
                pstmt.setString(4, row.getServiceName());

                pstmt.setLong(5, row.getH0());
                pstmt.setLong(6, row.getH1());
                pstmt.setLong(7, row.getH2());
                pstmt.setLong(8, row.getH3());
                pstmt.setLong(9, row.getH4());
                pstmt.setLong(10, row.getH5());
                pstmt.setLong(11, row.getH6());
                pstmt.setLong(12, row.getH7());
                pstmt.setLong(13, row.getH8());
                pstmt.setLong(14, row.getH9());
                pstmt.setLong(15, row.getH10());
                pstmt.setLong(16, row.getH11());
                pstmt.setLong(17, row.getH12());
                pstmt.setLong(18, row.getH13());
                pstmt.setLong(19, row.getH14());
                pstmt.setLong(20, row.getH15());
                pstmt.setLong(21, row.getH16());
                pstmt.setLong(22, row.getH17());
                pstmt.setLong(23, row.getH18());
                pstmt.setLong(24, row.getH19());
                pstmt.setLong(25, row.getH20());
                pstmt.setLong(26, row.getH21());
                pstmt.setLong(27, row.getH22());
                pstmt.setLong(28, row.getH23());

                pstmt.setString(29, row.getId().toString());
                pstmt.addBatch(); // Add to batch
            }

            int[] batchResults = pstmt.executeBatch(); // Execute batch
            con.commit();// Commit transaction
            isSuccess = batchResults.length == rows.size();
            logger.info("batchUpdate: updated {} rows", batchResults.length);
            con.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return isSuccess;
    }

    //
    public ThSARow getTotaleRowByAgence(String agenceId, String date1, String date2) {
        ThSARow row = new ThSARow();
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT "
                    + "    agence_id, "
                    + "'Sous-Totale' AS service_name,"
                    + "    SUM(h0) AS h0, "
                    + "    SUM(h1) AS h1, "
                    + "    SUM(h2) AS h2, "
                    + "    SUM(h3) AS h3, "
                    + "    SUM(h4) AS h4, "
                    + "    SUM(h5) AS h5, "
                    + "    SUM(h6) AS h6, "
                    + "    SUM(h7) AS h7, "
                    + "    SUM(h8) AS h8, "
                    + "    SUM(h9) AS h9, "
                    + "    SUM(h10) AS h10, "
                    + "    SUM(h11) AS h11, "
                    + "    SUM(h12) AS h12, "
                    + "    SUM(h13) AS h13, "
                    + "    SUM(h14) AS h14, "
                    + "    SUM(h15) AS h15, "
                    + "    SUM(h16) AS h16, "
                    + "    SUM(h17) AS h17, "
                    + "    SUM(h18) AS h18, "
                    + "    SUM(h19) AS h19, "
                    + "    SUM(h20) AS h20, "
                    + "    SUM(h21) AS h21, "
                    + "    SUM(h22) AS h22, "
                    + "    SUM(h23) AS h23 "
                    + "FROM rougga_thsa_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
                    + "AND agence_id = ? "
                    + "GROUP BY agence_id;";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date1); // Start date
            pstmt.setString(2, date2); // End date
            pstmt.setString(3, agenceId); // agenceId

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row = new ThSARow();
                row.setId(null);
                row.setDate(null);
                row.setAgenceId(rs.getString("agence_id"));
                row.setServiceId(null);
                row.setServiceName(rs.getString("service_name"));

                row.setH0(rs.getLong("h0"));
                row.setH1(rs.getLong("h1"));
                row.setH2(rs.getLong("h2"));
                row.setH3(rs.getLong("h3"));
                row.setH4(rs.getLong("h4"));
                row.setH5(rs.getLong("h5"));
                row.setH6(rs.getLong("h6"));
                row.setH7(rs.getLong("h7"));
                row.setH8(rs.getLong("h8"));
                row.setH9(rs.getLong("h9"));
                row.setH10(rs.getLong("h10"));
                row.setH11(rs.getLong("h11"));
                row.setH12(rs.getLong("h12"));
                row.setH13(rs.getLong("h13"));
                row.setH14(rs.getLong("h14"));
                row.setH15(rs.getLong("h15"));
                row.setH16(rs.getLong("h16"));
                row.setH17(rs.getLong("h17"));
                row.setH18(rs.getLong("h18"));
                row.setH19(rs.getLong("h19"));
                row.setH20(rs.getLong("h20"));
                row.setH21(rs.getLong("h21"));
                row.setH22(rs.getLong("h22"));
                row.setH23(rs.getLong("h23"));

            }
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return row;
    }

    public ThSARow getTotaleRow(String date1, String date2, String[] agences) {
        ThSARow row = new ThSARow();
        try {
            // Establish connection
            if (agences == null || agences.length == 0) {
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
                    + " 'Totale' as service_name,"
                    + "    SUM(h0) AS h0, "
                    + "    SUM(h1) AS h1, "
                    + "    SUM(h2) AS h2, "
                    + "    SUM(h3) AS h3, "
                    + "    SUM(h4) AS h4, "
                    + "    SUM(h5) AS h5, "
                    + "    SUM(h6) AS h6, "
                    + "    SUM(h7) AS h7, "
                    + "    SUM(h8) AS h8, "
                    + "    SUM(h9) AS h9, "
                    + "    SUM(h10) AS h10, "
                    + "    SUM(h11) AS h11, "
                    + "    SUM(h12) AS h12, "
                    + "    SUM(h13) AS h13, "
                    + "    SUM(h14) AS h14, "
                    + "    SUM(h15) AS h15, "
                    + "    SUM(h16) AS h16, "
                    + "    SUM(h17) AS h17, "
                    + "    SUM(h18) AS h18, "
                    + "    SUM(h19) AS h19, "
                    + "    SUM(h20) AS h20, "
                    + "    SUM(h21) AS h21, "
                    + "    SUM(h22) AS h22, "
                    + "    SUM(h23) AS h23 "
                    + " FROM rougga_thsa_table "
                    + " WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') "
                    + " BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
                    + agenceCondition;
            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(
                    1, date1); // Start date
            pstmt.setString(
                    2, date2); // End date

            // adding selected agences to preparedstatement
            for (int i = 0;
                    i < agences.length;
                    i++) {
                pstmt.setString(i + 3, agences[i]);
            }

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row = new ThSARow();
                row.setId(null);
                row.setDate(null);
                row.setAgenceId(null);
                row.setServiceId(null);
                row.setServiceName(rs.getString("service_name"));

                row.setH0(rs.getLong("h0"));
                row.setH1(rs.getLong("h1"));
                row.setH2(rs.getLong("h2"));
                row.setH3(rs.getLong("h3"));
                row.setH4(rs.getLong("h4"));
                row.setH5(rs.getLong("h5"));
                row.setH6(rs.getLong("h6"));
                row.setH7(rs.getLong("h7"));
                row.setH8(rs.getLong("h8"));
                row.setH9(rs.getLong("h9"));
                row.setH10(rs.getLong("h10"));
                row.setH11(rs.getLong("h11"));
                row.setH12(rs.getLong("h12"));
                row.setH13(rs.getLong("h13"));
                row.setH14(rs.getLong("h14"));
                row.setH15(rs.getLong("h15"));
                row.setH16(rs.getLong("h16"));
                row.setH17(rs.getLong("h17"));
                row.setH18(rs.getLong("h18"));
                row.setH19(rs.getLong("h19"));
                row.setH20(rs.getLong("h20"));
                row.setH21(rs.getLong("h21"));
                row.setH22(rs.getLong("h22"));
                row.setH23(rs.getLong("h23"));
            }

            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return row;
    }

    public List<ThSARow> getRowsByIdAgenceBetweenDates(String agenceId, String date1, String date2) {
        List<ThSARow> emps = new ArrayList<>();
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql
                    = "SELECT "
                    + "    agence_id, "
                    + "    service_id, "
                    + "    service_name, "
                    + "    SUM(h0) AS h0, "
                    + "    SUM(h1) AS h1, "
                    + "    SUM(h2) AS h2, "
                    + "    SUM(h3) AS h3, "
                    + "    SUM(h4) AS h4, "
                    + "    SUM(h5) AS h5, "
                    + "    SUM(h6) AS h6, "
                    + "    SUM(h7) AS h7, "
                    + "    SUM(h8) AS h8, "
                    + "    SUM(h9) AS h9, "
                    + "    SUM(h10) AS h10, "
                    + "    SUM(h11) AS h11, "
                    + "    SUM(h12) AS h12, "
                    + "    SUM(h13) AS h13, "
                    + "    SUM(h14) AS h14, "
                    + "    SUM(h15) AS h15, "
                    + "    SUM(h16) AS h16, "
                    + "    SUM(h17) AS h17, "
                    + "    SUM(h18) AS h18, "
                    + "    SUM(h19) AS h19, "
                    + "    SUM(h20) AS h20, "
                    + "    SUM(h21) AS h21, "
                    + "    SUM(h22) AS h22, "
                    + "    SUM(h23) AS h23 "
                    + "FROM rougga_thsa_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
                    + "AND agence_id = ? "
                    + "GROUP BY agence_id, service_id, service_name "
                    + "ORDER BY service_name;";
            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date1); // Start date
            pstmt.setString(2, date2); // End date
            pstmt.setString(3, agenceId); // agence_id

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            while (rs.next()) {
                ThSARow row = new ThSARow();
                row.setId(null);
                row.setDate(null);
                row.setAgenceId(rs.getString("agence_id"));
                row.setServiceId(rs.getString("service_id"));
                row.setServiceName(rs.getString("service_name"));

                row.setH0(rs.getLong("h0"));
                row.setH1(rs.getLong("h1"));
                row.setH2(rs.getLong("h2"));
                row.setH3(rs.getLong("h3"));
                row.setH4(rs.getLong("h4"));
                row.setH5(rs.getLong("h5"));
                row.setH6(rs.getLong("h6"));
                row.setH7(rs.getLong("h7"));
                row.setH8(rs.getLong("h8"));
                row.setH9(rs.getLong("h9"));
                row.setH10(rs.getLong("h10"));
                row.setH11(rs.getLong("h11"));
                row.setH12(rs.getLong("h12"));
                row.setH13(rs.getLong("h13"));
                row.setH14(rs.getLong("h14"));
                row.setH15(rs.getLong("h15"));
                row.setH16(rs.getLong("h16"));
                row.setH17(rs.getLong("h17"));
                row.setH18(rs.getLong("h18"));
                row.setH19(rs.getLong("h19"));
                row.setH20(rs.getLong("h20"));
                row.setH21(rs.getLong("h21"));
                row.setH22(rs.getLong("h22"));
                row.setH23(rs.getLong("h23"));
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

    public ThSARow getRowByDate(String date, String id_agence, String serviceId) {
        ThSARow row = null;
        try {
            // Establish connection
            Connection con = new CPConnection().getConnection();
            String sql = "SELECT * "
                    + "FROM rougga_thsa_table "
                    + "WHERE  to_date(to_char(date,'YYYY-MM-DD'),'YYYY-MM-DD') = TO_DATE(?,'YYYY-MM-DD') and agence_id= ?  and service_id=?;";

            // Prepare statement
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date);
            pstmt.setString(2, id_agence);
            pstmt.setString(3, serviceId);
            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row = new ThSARow();
                row.setId(UUID.fromString(rs.getString("id")));
                row.setDate(CfgHandler.getFormatedDateAsString(CfgHandler.getFormatedDateAsDate(rs.getString("date"))));
                row.setAgenceId(rs.getString("agence_id"));
                row.setServiceId(rs.getString("service_id"));
                row.setServiceName(rs.getString("service_name"));

                row.setH0(rs.getLong("h0"));
                row.setH1(rs.getLong("h1"));
                row.setH2(rs.getLong("h2"));
                row.setH3(rs.getLong("h3"));
                row.setH4(rs.getLong("h4"));
                row.setH5(rs.getLong("h5"));
                row.setH6(rs.getLong("h6"));
                row.setH7(rs.getLong("h7"));
                row.setH8(rs.getLong("h8"));
                row.setH9(rs.getLong("h9"));
                row.setH10(rs.getLong("h10"));
                row.setH11(rs.getLong("h11"));
                row.setH12(rs.getLong("h12"));
                row.setH13(rs.getLong("h13"));
                row.setH14(rs.getLong("h14"));
                row.setH15(rs.getLong("h15"));
                row.setH16(rs.getLong("h16"));
                row.setH17(rs.getLong("h17"));
                row.setH18(rs.getLong("h18"));
                row.setH19(rs.getLong("h19"));
                row.setH20(rs.getLong("h20"));
                row.setH21(rs.getLong("h21"));
                row.setH22(rs.getLong("h22"));
                row.setH23(rs.getLong("h23"));
                logger.info("row found for date: {} agence_id = {}", date, id_agence);
            } else {
                logger.info("No row found for date: {} agence_id = {}", date, id_agence);
            }

            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return row;
    }

    // get THT row between 2 dates and insert json result to database for every
    // agency
    public void updateFromJson(String date1, String date2) {
        List<Agence> agences = ac.getAllAgence();
        for (Agence a : agences) {
            this.updateAgenceFromJson(date1, date2, a.getId().toString());
        }
    }

    // get THT row between 2 dates and insert json result to database for one
    // agency
    public boolean updateAgenceFromJson(String date1, String date2, String agenceId) {
        boolean isDone = false;
        Agence a;
        List<ThSARow> rowsToInsert = new ArrayList<>();
        List<ThSARow> rowsToUpdate = new ArrayList<>();
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
            logger.info(" -- Updating {}'s THT Table ... ", a.getName());
            String url = CfgHandler.prepareTableJsonUrl(a.getHost(), a.getPort(), CfgHandler.API_THSA_TABLE_JSON,
                    date1, date2);
            logger.info("URL = {} - {}", url, a.getName());
            JSONObject json = UpdateController.getJsonFromUrl(url);

            if (json != null) {
                JSONArray result = (JSONArray) json.get("result");
                for (Object s : result) {
                    try {
                        JSONObject emp = (JSONObject) s;
                        String serviceId = emp.get("service_id").toString();
                        String id_agence = a.getId().toString();
                        ThSARow row = this.getRowByDate(date2,
                                id_agence, serviceId);
                        if (row != null) {
                            try {
                                row.setDate(CfgHandler.getFormatedDateAsString(CfgHandler.format.parse(date2)));
                                row.setAgenceId(id_agence);
                                row.setServiceId(serviceId);
                                row.setServiceName(emp.get("service_name").toString());

                                row.setH0(Long.valueOf(emp.get("h0").toString()));
                                row.setH1(Long.valueOf(emp.get("h1").toString()));
                                row.setH2(Long.valueOf(emp.get("h2").toString()));
                                row.setH3(Long.valueOf(emp.get("h3").toString()));
                                row.setH4(Long.valueOf(emp.get("h4").toString()));
                                row.setH5(Long.valueOf(emp.get("h5").toString()));
                                row.setH6(Long.valueOf(emp.get("h6").toString()));
                                row.setH7(Long.valueOf(emp.get("h7").toString()));
                                row.setH8(Long.valueOf(emp.get("h8").toString()));
                                row.setH9(Long.valueOf(emp.get("h9").toString()));
                                row.setH10(Long.valueOf(emp.get("h10").toString()));
                                row.setH11(Long.valueOf(emp.get("h11").toString()));
                                row.setH12(Long.valueOf(emp.get("h12").toString()));
                                row.setH13(Long.valueOf(emp.get("h13").toString()));
                                row.setH14(Long.valueOf(emp.get("h14").toString()));
                                row.setH15(Long.valueOf(emp.get("h15").toString()));
                                row.setH16(Long.valueOf(emp.get("h16").toString()));
                                row.setH17(Long.valueOf(emp.get("h17").toString()));
                                row.setH18(Long.valueOf(emp.get("h18").toString()));
                                row.setH19(Long.valueOf(emp.get("h19").toString()));
                                row.setH20(Long.valueOf(emp.get("h20").toString()));
                                row.setH21(Long.valueOf(emp.get("h21").toString()));
                                row.setH22(Long.valueOf(emp.get("h22").toString()));
                                row.setH23(Long.valueOf(emp.get("h23").toString()));

                                //this.updateRow(row);
                                rowsToUpdate.add(row);
                                logger.info("ThSARow id: {} found and updated ", row.getId());
                            } catch (ParseException ex) {
                                logger.error(ex.getMessage());
                                return false;
                            }
                        } else {
                            try {
                                row = new ThSARow();
                                row.setId(this.getUniquId());
                                row.setDate(CfgHandler.getFormatedDateAsString(CfgHandler.format.parse(date2)));
                                row.setAgenceId(id_agence);
                                row.setServiceId(serviceId);
                                row.setServiceName(emp.get("service_name").toString());
                                
                                row.setH0(Long.valueOf(emp.get("h0").toString()));
                                row.setH1(Long.valueOf(emp.get("h1").toString()));
                                row.setH2(Long.valueOf(emp.get("h2").toString()));
                                row.setH3(Long.valueOf(emp.get("h3").toString()));
                                row.setH4(Long.valueOf(emp.get("h4").toString()));
                                row.setH5(Long.valueOf(emp.get("h5").toString()));
                                row.setH6(Long.valueOf(emp.get("h6").toString()));
                                row.setH7(Long.valueOf(emp.get("h7").toString()));
                                row.setH8(Long.valueOf(emp.get("h8").toString()));
                                row.setH9(Long.valueOf(emp.get("h9").toString()));
                                row.setH10(Long.valueOf(emp.get("h10").toString()));
                                row.setH11(Long.valueOf(emp.get("h11").toString()));
                                row.setH12(Long.valueOf(emp.get("h12").toString()));
                                row.setH13(Long.valueOf(emp.get("h13").toString()));
                                row.setH14(Long.valueOf(emp.get("h14").toString()));
                                row.setH15(Long.valueOf(emp.get("h15").toString()));
                                row.setH16(Long.valueOf(emp.get("h16").toString()));
                                row.setH17(Long.valueOf(emp.get("h17").toString()));
                                row.setH18(Long.valueOf(emp.get("h18").toString()));
                                row.setH19(Long.valueOf(emp.get("h19").toString()));
                                row.setH20(Long.valueOf(emp.get("h20").toString()));
                                row.setH21(Long.valueOf(emp.get("h21").toString()));
                                row.setH22(Long.valueOf(emp.get("h22").toString()));
                                row.setH23(Long.valueOf(emp.get("h23").toString()));

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
                // insert and update using batch processing
                this.batchInsert(rowsToInsert);
                this.batchUpdate(rowsToUpdate);
                ac.setLastUpdate(UUID.fromString(agenceId));
                logger.info(" --  THT Table for {} is Updated. ", a.getName());
                isDone = true;
            } else {
                logger.error("updateAgenceFromJson: json null;");
                return false;
            }
        } else {
            logger.error("updateAgenceFromJson: no agence found;");
        }

        return isDone;
    }

    // returns whole THT table for report page
    public List<Map> getTableAsList(String date1, String date2, String[] agences) {
        List<Map> result = new ArrayList<>();
        date1 = (date1 == null) ? CfgHandler.format.format(new Date()) : date1;
        date2 = (date2 == null) ? CfgHandler.format.format(new Date()) : date2;

        List<Agence> dbs = ac.getAgencesFromStringArray(agences);
        if (dbs == null || dbs.isEmpty()) {
            dbs = ac.getAllAgence();
        }
        for (Agence a : dbs) {
            List<ThSARow> emps = this.getRowsByIdAgenceBetweenDates(a.getId().toString(), date1, date2);
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
            List<ThSARow> emps = new ArrayList<>();
            Map<String, Object> newAgence = new HashMap<>();
            newAgence.put("id_agence", "Totale");
            newAgence.put("agence_name", "Totale");
            emps.add(this.getTotaleRow(date1, date2, agences));
            newAgence.put("emps", emps);
            result.add(newAgence);
        }

        return result;

    }

    // gets unique UUID after checking in THT table
    private UUID getUniquId() {
        UUID uniqueId = UUID.randomUUID();
        while (this.doesIdExist(uniqueId)) {
            uniqueId = UUID.randomUUID();
        }
        return uniqueId;
    }

    // checks if the id exists in THT table
    private boolean doesIdExist(UUID uniqueId) {
        try {
            Connection con = new CPConnection().getConnection();
            String sql = "select 1 from rougga_thsa_table where id=? limit 1";
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
                logger.info("restoreOldRowsForAllAgences:  data restored for {}", a.getName());
            } else {
                logger.error("restoreOldRowsForAllAgences: Couldn't restore data for {}", a.getName());
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
                logger.info("Restoring THT table data of {} for date:{}", a.getName(), CfgHandler.getFormatedDateAsString(oldestDate));
                this.updateAgenceFromJson(
                        CfgHandler.format.format(oldestDate),
                        CfgHandler.format.format(oldestDate),
                        id_agence.toString());
                Calendar c = Calendar.getInstance();
                c.setTime(oldestDate);
                c.add(Calendar.DATE, 1);
                oldestDate = c.getTime();
            }
            logger.info("Resored THT table data of {}", a.getName());
        } else {
            logger.error("restoreOldRowsByAgenceId: oldest ticket date not found!");
            return false;
        }

        // add error handling
        return true;
    }

}
