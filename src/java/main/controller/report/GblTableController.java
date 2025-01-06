package main.controller.report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import main.CfgHandler;
import main.PgConnection;
import main.controller.AgenceController;
import main.controller.UpdateController;
import main.modal.Agence;
import main.modal.GblRow;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GblTableController {

    AgenceController ac = new AgenceController();

    public GblTableController() {
    }

    public boolean addRow(GblRow row) {
        try {
            PgConnection con = new PgConnection();
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

            PreparedStatement pstmt = con.getStatement().getConnection().prepareCall(sql);
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
                return true;
            } else {
                System.out.println("A  row wasnt inserted successfully!");
                return false;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateRow(GblRow row) {
        try {
            PgConnection con = new PgConnection();
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
            PreparedStatement pstmt = con.getStatement().getConnection().prepareCall(sql);
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
                return true;
            } else {
                System.out.println("A  row wasnt UPDATED successfully!");
                return false;
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public GblRow getRowById(String id) {
        GblRow row = null;
        try {
            // Establish connection
            PgConnection con = new PgConnection();
            String sql = "SELECT id, id_service, service_name, nb_t, nb_tt, nb_a, nb_tl1, nb_sa, perApT, PERTL1pt, perSApT, "
                    + "avgSec_A, nb_ca, percapt, avgSec_T, nb_ct, perctpt, date, id_agence "
                    + "FROM rougga_gbl_table WHERE id = ?";

            // Prepare statement
            PreparedStatement pstmt = con.getStatement().getConnection().prepareStatement(sql);
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
                System.out.println("No row found for id: " + id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return row;
    }

    public GblRow getTotaleRowByAgence(String id_agence, String date1, String date2) {
        GblRow row = new GblRow();
        try {
            // Establish connection
            PgConnection con = new PgConnection();
            String sql = "SELECT "
                    + "id_agence, "
                    + "'Sub-Totale' AS service_name, "
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
            PreparedStatement pstmt = con.getStatement().getConnection().prepareStatement(sql);
            pstmt.setString(1, date1);  // Start date
            pstmt.setString(2, date2);  // End date
            pstmt.setString(3, id_agence);  // id_agence

            // Execute query
            ResultSet rs = pstmt.executeQuery();

            // Process result set
            if (rs.next()) {
                row.setId(null);  // Setting `id` to null as requested
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
            con.closeConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error fetching row: " + e.getMessage());
        }
        return row;
    }

    public GblRow getTotaleRow(String date1, String date2, String[] agences) {
        GblRow row = new GblRow();
        try {
            // Establish connection
            if (agences.length == 0) {
                agences = ac.putAgencesToStringArray(ac.getAllAgence());
            }
            PgConnection con = new PgConnection();
            StringBuilder sqlBuilder = new StringBuilder(" AND  id_agence IN (");
            for (int i = 0; i < agences.length; i++) {
                sqlBuilder.append("?");
                if (i < agences.length - 1) {
                    sqlBuilder.append(", ");
                }
            }
            sqlBuilder.append(")");

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
            PreparedStatement pstmt = con.getStatement().getConnection().prepareStatement(sql);
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

            con.closeConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error fetching row: " + e.getMessage());
        }
        return row;
    }

    public List<GblRow> getRowsByIdAgenceBetweenDates(String id_agence, String date1, String date2) {
        List<GblRow> services = new ArrayList<>();
        try {
            // Establish connection
            PgConnection con = new PgConnection();
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
            PreparedStatement pstmt = con.getStatement().getConnection().prepareStatement(sql);
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
            services.add(this.getTotaleRowByAgence(id_agence, date1, date2)); // adding subtotale row as a service 
            con.closeConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error fetching row: " + e.getMessage());
        }

        return services;
    }

    public GblRow getRowByDate(String date, String id_agence, String id_service) {
        GblRow row = null;
        try {
            // Establish connection
            PgConnection con = new PgConnection();
            String sql = "SELECT id, id_service, service_name, nb_t, nb_tt, nb_a, nb_tl1, nb_sa, perApT, PERTL1pt, perSApT, "
                    + "avgSec_A, nb_ca, percapt, avgSec_T, nb_ct, perctpt, date, id_agence "
                    + "FROM rougga_gbl_table WHERE  to_date(to_char(date,'YYYY-MM-DD'),'YYYY-MM-DD') = TO_DATE(?,'YYYY-MM-DD') and id_agence= ? and id_service = ?";

            // Prepare statement
            PreparedStatement pstmt = con.getStatement().getConnection().prepareStatement(sql);
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
                System.out.println("row found for date: " + date + " id_agence = "
                        + id_agence
                        + " id_service = " + id_service);
            } else {
                System.out.println("No row found for date: " + date + " id_agence = "
                        + id_agence
                        + " id_service = " + id_service);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error fetching row: " + e.getMessage());
        }

        return row;
    }

    // to be globale for all tables
    public String prepareTableJsonUrl(String host, int port, String apiPoint, String date1, String date2) {
        String url = "http://" + host
                + ":" + port
                + "/" + apiPoint
                + "?date1=" + date1
                + "&date2=" + date2;
        return url;
    }

    public void updateFromJson(String date1, String date2) {
        List<Agence> agences = new AgenceController().getAllAgence();
        if (StringUtils.isAnyBlank(date1, date2)) {
            date1 = date2 = CfgHandler.format.format(new Date());
        }
        if (agences != null) {
            System.out.println(new Date().toString() + " -- Updating GBL Tables ... ");
            for (Agence a : agences) {
                System.out.println(new Date().toString() + " -- Updating GBL Table in " + a.getName());
                String url = prepareTableJsonUrl(a.getHost(), a.getPort(), CfgHandler.API_GBL_TABLE_JSON, date1, date2);
                System.out.println("URL = " + url + " - " + a.getName());
                JSONObject json = UpdateController.getJsonFromUrl(url);

                if (json != null) {
                    JSONArray result = (JSONArray) json.get("result");
                    for (Object s : result) {
                        JSONObject service = (JSONObject) s;
                        JSONObject data = (JSONObject) service.get("data");
                        String id_service = service.get("id").toString(),
                                id_agence = a.getId().toString();
                        GblRow row = this.getRowByDate(CfgHandler.format.format(new Date()),
                                id_agence,
                                id_service);
                        if (row != null) {
                            row.setServiceName(service.get("name").toString());
                            System.out.println("Nb. T : " + row.getNbT() + " to " + data.get("nb_t"));
                            row.setNbT((long) data.get("nb_t"));
                            row.setNbTt((long) data.get("nb_tt"));
                            row.setNbA((long) data.get("nb_a"));
                            row.setNbTl1((long) data.get("nb_tl1"));
                            row.setNbSa((long) data.get("nb_sa"));
                            row.setPerApT((Double) data.get("perApT"));
                            row.setPertl1Pt((Double) data.get("PERTL1pt"));
                            row.setPerSaPt((Double) data.get("perSApT"));
                            row.setAvgSecA((Double) data.get("avgSec_A"));
                            row.setNbCa((long) data.get("nb_ca"));
                            row.setPercapt((Double) data.get("percapt"));
                            row.setAvgSecT((Double) data.get("avgSec_T"));
                            row.setNbCt((long) data.get("nb_ct"));
                            row.setPerctPt((Double) data.get("perctpt"));
                            row.setDate(CfgHandler.getFormatedDateAsString(new Date()));
                            this.updateRow(row);
                        } else {
                            row = new GblRow(
                                    this.getUniquId(),
                                    id_service,
                                    service.get("name").toString(),
                                    (long) data.get("nb_t"),
                                    (long) data.get("nb_tt"),
                                    (long) data.get("nb_a"),
                                    (long) data.get("nb_tl1"),
                                    (long) data.get("nb_sa"),
                                    (Double) data.get("perApT"),
                                    (Double) data.get("PERTL1pt"),
                                    (Double) data.get("perSApT"),
                                    (Double) data.get("avgSec_A"),
                                    (long) data.get("nb_ca"),
                                    (Double) data.get("percapt"),
                                    (Double) data.get("avgSec_T"),
                                    (long) data.get("nb_ct"),
                                    (Double) data.get("perctpt"),
                                    CfgHandler.getFormatedDateAsString(new Date()),
                                    id_agence);
                            this.addRow(row);
                        }

                    }
                }
            }
            System.out.println(new Date().toString() + " -- All GBL Tables Updated. ");
        }
    }

    public List<Map> getTableAsList(String date1, String date2, String[] agences) {
        List<Map> result = new ArrayList<>();
        date1 = (date1 == null) ? CfgHandler.format.format(new Date()) : date1;
        date2 = (date2 == null) ? CfgHandler.format.format(new Date()) : date2;

        List<Agence> dbs = ac.getAgencesFromStringArray(agences);
        if (dbs.isEmpty() || dbs == null) {
            dbs = ac.getAllAgence();
        }
        for (Agence a : dbs) {
            Map<String, Object> newAgence = new HashMap<>();
            newAgence.put("id_agence", a.getId().toString());
            newAgence.put("agence_name", a.getName());
            newAgence.put("services", this.getRowsByIdAgenceBetweenDates(a.getId().toString(), date1, date2));
            result.add(newAgence);
        }

        //adding totale
        List<GblRow> services = new ArrayList<>();
        Map<String, Object> newAgence = new HashMap<>();
        newAgence.put("id_agence", "Totale");
        newAgence.put("agence_name", "Totale");
        services.add(this.getTotaleRow(date1, date2, agences));
        newAgence.put("services", services);
        result.add(newAgence);
        return result;

    }

    private UUID getUniquId() {
        UUID uniqueId = UUID.randomUUID();
        while (this.doesIdExist(uniqueId)) {
            uniqueId = UUID.randomUUID();
        }
        return uniqueId;
    }

    private boolean doesIdExist(UUID uniqueId) {
        try {
            PgConnection con = new PgConnection();
            String sql = "select * from rougga_gbl_table where id=?";
            PreparedStatement pstmt = con.getStatement().getConnection().prepareCall(sql);
            pstmt.setString(1, uniqueId.toString());
            ResultSet rst = pstmt.executeQuery();
            return rst.next();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void restoreOldRows() {

        //loop on agences
        //get the oldest ticket's date
        //loop from the oldest date to now by days
        //get json of that day 
        //insert to database
    }
}
