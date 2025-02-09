package ma.rougga.qdata.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import ma.rougga.qdata.CPConnection;
import ma.rougga.qdata.CfgHandler;
import ma.rougga.qdata.modal.Agence;
import ma.rougga.qdata.modal.Cible;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.LoggerFactory;

public class CibleController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CibleController.class);
    AgenceController ac = new AgenceController();

    public CibleController() {
    }

    public List<Cible> getAll() {
        List<Cible> cibles = new ArrayList<>();
        String sql = "SELECT * FROM rougga_cibles order by agence_id";
        try (Connection con = new CPConnection().getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                cibles.add(new Cible(
                        rs.getString("service_id"),
                        rs.getString("service_name"),
                        UUID.fromString(rs.getString("agence_id")),
                        rs.getLong("cible_a"),
                        rs.getLong("cible_t"),
                        rs.getDouble("cible_per")
                ));
            }
            con.close();

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return cibles;
    }

    public Cible getOne(String serviceId, String agenceId) {
        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement p = con.prepareStatement("select * from rougga_cibles where service_id=? and agence_id=?");
            p.setString(1, serviceId);
            p.setString(2, agenceId);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                Cible c = new Cible(
                        r.getString("service_id"),
                        r.getString("service_name"),
                        UUID.fromString(r.getString("agence_id")),
                        r.getLong("cible_a"),
                        r.getLong("cible_t"),
                        r.getDouble("Cible_per")
                );
                con.close();
                return c;
            } else {
                con.close();
                return null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public boolean isUnique(String serviceId, String agenceId) {
        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement p = con.prepareStatement("select * from rougga_cibles where service_id=? and agence_id=?");
            p.setString(1, serviceId);
            p.setString(2, agenceId);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                con.close();
                return false;
            } else {
                con.close();
                return true;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }


    public boolean addCible(Cible cible) {
        String sql = "INSERT INTO rougga_cibles (service_id, service_name, cible_a, cible_t, cible_per, agence_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = new CPConnection().getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, cible.getService_id());
            pstmt.setString(2, cible.getService_name());
            pstmt.setLong(3, cible.getCibleA());
            pstmt.setLong(4, cible.getCibleT());
            pstmt.setDouble(5, cible.getCiblePer());
            pstmt.setString(6, cible.getAgence_id().toString());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                con.close();
                return true;
            } else {
                logger.info("A  row wasnt ADDED successfully!");
                con.close();
                return false;
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public boolean updateCible(Cible cible) {
        String sql = "UPDATE rougga_cibles SET service_name = ?, cible_a = ?, cible_t = ?, cible_per = ? "
                + "WHERE service_id = ? AND agence_id = ?";
        try (Connection con = new CPConnection().getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, cible.getService_name());
            pstmt.setLong(2, cible.getCibleA());
            pstmt.setLong(3, cible.getCibleT());
            pstmt.setDouble(4, cible.getCiblePer());
            pstmt.setString(5, cible.getService_id());
            pstmt.setString(6, cible.getAgence_id().toString());

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

    //
    public void updateFromJson() {
        List<Agence> agences = ac.getAllAgence();
        for (Agence a : agences) {
            this.updateAgenceFromJson(a.getId().toString());
        }
    }

    public boolean updateAgenceFromJson(String agenceId) {
        boolean isDone = false;
        Agence a = new Agence();
        //validationg data
        if (StringUtils.isBlank(agenceId)) {
            logger.error("updateAgenceFromJson: id agence null;");
            return false;
        }
        a = ac.getAgenceById(UUID.fromString(agenceId));
        if (a != null) {
            logger.info(" -- Updating " + a.getName() + "'s Cible Table ... ");
            String url = CfgHandler.prepareJsonUrl(a.getHost(), a.getPort(), CfgHandler.API_CIBLE_TABLE_JSON);
            logger.info("URL = " + url + " - " + a.getName());
            JSONObject json = UpdateController.getJsonFromUrl(url);

            if (json != null) {
                JSONArray result = (JSONArray) json.get("result");
                for (Object s : result) {
                    try {
                        JSONObject service = (JSONObject) s;
                        String id_service = service.get("service_id").toString();
                        Cible row = this.getOne(id_service, agenceId);
                        if (row != null) {
                            row.setCibleA((long) service.get("cible_a"));
                            row.setCibleT((long) service.get("cible_t"));
                            row.setCiblePer((double) service.get("cible_per"));
                            row.setService_id(id_service);
                            row.setService_name(service.get("service_name").toString());
                            row.setAgence_id(a.getId());
                            this.updateCible(row);
                        } else {
                            row = new Cible();
                            row.setCibleA((long) service.get("cible_a"));
                            row.setCibleT((long) service.get("cible_t"));
                            row.setCiblePer((double) service.get("cible_per"));
                            row.setService_id(id_service);
                            row.setService_name(service.get("service_name").toString());
                            row.setAgence_id(a.getId());
                            this.addCible(row);
                        }
                    } catch (NullPointerException e) {
                        logger.error("updateAgenceFromJson: some json variables are null;");
                    }
                }
                ac.setLastUpdate(a.getId());
                isDone = true;
            } else {
                logger.error("updateAgenceFromJson: json null;");
                return false;
            }
        } else {
            logger.error("updateAgenceFromJson: no agence found;");
        }

        logger.info(" --  Cible Table Updated. ");
        return isDone;
    }

}
