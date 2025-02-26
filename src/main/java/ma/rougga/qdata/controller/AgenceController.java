package ma.rougga.qdata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.rougga.qdata.CPConnection;
import ma.rougga.qdata.CfgHandler;
import ma.rougga.qdata.modal.Agence;
import ma.rougga.qdata.modal.Zone;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;

public class AgenceController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AgenceController.class);

    public AgenceController() {
    }

    public List<Agence> getAllAgence() {
        try {
            List<Agence> agences = new ArrayList();
            Connection con = new CPConnection().getConnection();
            ResultSet r = con.createStatement().executeQuery("select * from rougga_agences order by name;");
            while (r.next()) {
                Agence a = new Agence();
                a.setId(UUID.fromString(r.getString("id")));
                a.setName(r.getString("name"));
                a.setHost(r.getString("host"));
                a.setPort(r.getInt("port"));
                a.setLastupdated_at(r.getString("lastupdated_at"));
                a.setStatus(r.getInt("status"));
                agences.add(a);
            }
            con.close();
            return agences;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public List<Agence> getAgencesByZone(UUID id_zone) {
        try {
            List<Agence> agences = new ArrayList();
            Connection con = new CPConnection().getConnection();
            PreparedStatement ps = con.prepareStatement("select id_agence from rougga_agence_zone where id_zone=?;");

            ps.setString(1, id_zone.toString());
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                agences.add(this.getAgenceById(UUID.fromString(r.getString("id_agence"))));
            }
            con.close();
            return agences;
        } catch (Exception ex) {
            Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public int addAgence(Agence a) {
        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement p = con.prepareStatement("insert into rougga_agences values(?,?,?,?,?,?);");
            p.setString(1, a.getId().toString());
            p.setString(2, a.getName());
            p.setString(3, a.getHost());
            p.setInt(4, a.getPort());
            p.setString(5, a.getLastupdated_at());
            p.setInt(6, a.getStatus());
            p.execute();
            con.close();
            return 1;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return 0;
        }
    }

    public boolean editAgence(Agence a) {
        try {
            Connection con = new CPConnection().getConnection();
            String SQL = "update rougga_agences set name=?,host=?,port=?,lastupdated_at=?,status=? where id=?;";
            PreparedStatement p = con.prepareStatement(SQL);
            p.setString(1, a.getName());
            p.setString(2, a.getHost());
            p.setInt(3, a.getPort());
            p.setString(4, a.getLastupdated_at());
            p.setInt(5, a.getStatus());
            p.setString(6, a.getId().toString());
            p.execute();
            con.close();
            return true;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    public Agence getAgenceById(UUID id) {
        try {
            Agence a = new Agence();
            Connection con = new CPConnection().getConnection();
            PreparedStatement p = con.prepareStatement("select * from rougga_agences where id=? ;");
            p.setString(1, id.toString());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                a.setId(id);
                a.setName(r.getString("name"));
                a.setHost(r.getString("host"));
                a.setPort(r.getInt("port"));
                a.setLastupdated_at(r.getString("lastupdated_at"));
                a.setStatus(r.getInt("status"));
                con.close();
                return a;
            } else {
                con.close();
                return null;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public int deleteAgenceById(UUID id) {
        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement p = con.prepareStatement("delete from rougga_agences where id=?;");
            p.setString(1, id.toString());
            p.execute();
            con.close();
            return 1;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return 0;
        }
    }

    public boolean isOnlineJson(UUID id) {
        Agence a = getAgenceById(id);
        if (a != null) {
            String url = "http://" + a.getHost()
                    + ":" + a.getPort()
                    + "/" + CfgHandler.API_CHECK_STATUS;
            JSONObject json = UpdateController.getJsonFromUrl(url);
            if (json != null) {
                String result = json.get("isOnline").toString();
                return Boolean.parseBoolean(result);
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean isOnline(UUID id) {
        Agence a = getAgenceById(id);
        if (a != null) {
            try {
                // Create a URL object
                String urlString = "http://" + a.getHost()
                        + ":" + a.getPort()
                        + "/" + CfgHandler.API_CHECK_STATUS;
                URL url = new URL(urlString);

                // Open connection
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Accept-Charset", "UTF-8");
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
                connection.setConnectTimeout(3000);
                // Check response code
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    connection.disconnect();
                    return true;
                } else {
                    logger.error("Online check failed with Response Code: {}", responseCode);
                    connection.disconnect();
                    return false;
                }
            } catch (IOException e) {
                logger.error("{} IS OFFLINE", a.getName());
                return false;
            }
        } else {
            return false;
        }
    }

    public int updateName(UUID id, String name) {
        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement p = con.prepareStatement("update rougga_agences set name=? where id=?;");
            p.setString(1, name);
            p.setString(2, id.toString());
            p.execute();
            con.close();
            return 1;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return 0;
        }
    }

    public Date getLastUpdate(UUID id) {
        Agence a = this.getAgenceById(id);
        if (a != null) {
            return CfgHandler.getFormatedDateAsDate(a.getLastupdated_at());
        }
        return null;
    }

    public void setLastUpdate(UUID id) {
        Agence a = this.getAgenceById(id);
        if (a != null) {
            a.setLastupdated_at(CfgHandler.getFormatedDateAsString(new Date()));
            String name = this.getBranchName(id);
            if (name != null) {
                a.setName(name);
            }
            this.editAgence(a);
        }
    }

    public void setZone(UUID id_agence, UUID id_zone) {
        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement p = con.prepareStatement("insert into rougga_agence_zone values(?,?);");
            p.setString(1, id_agence.toString());
            p.setString(2, id_zone.toString());
            p.execute();
            con.close();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public void editZone(UUID id_agence, UUID id_zone) {
        try {
            Connection con = new CPConnection().getConnection();
            if (this.getAgenceZoneByAgenceId(id_agence) != null) {
                String SQL = "";
                PreparedStatement p;
                if (id_zone != null) {
                    SQL = "update rougga_agence_zone set id_zone=? where  id_agence=?;";
                    p = con.prepareStatement(SQL);
                    p.setString(2, id_agence.toString());
                    p.setString(1, id_zone.toString());
                } else {
                    SQL = "delete from rougga_agence_zone where id_agence=?";
                    p = con.prepareStatement(SQL);
                    p.setString(1, id_agence.toString());
                }
                p.execute();
                con.close();
            } else {
                this.setZone(id_agence, id_zone);
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public Zone getAgenceZoneByAgenceId(UUID id_agence) {
        try {
            Zone a;
            Connection con = new CPConnection().getConnection();
            PreparedStatement p = con.prepareStatement("select * from rougga_agence_zone where id_agence=? ;");
            p.setString(1, id_agence.toString());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                a = new ZoneController().getZoneById(UUID.fromString(r.getString("id_zone")));
                con.close();
                return a;
            } else {
                con.close();
                return null;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public List<Agence> getAgencesFromStringArray(String[] agences) {
        List<Agence> dbs = new ArrayList<>();
        if (agences != null) {
            for (String a : agences) {
                Agence agence = this.getAgenceById(UUID.fromString(a));
                if (agence != null) {
                    dbs.add(agence);
                }
            }
        }
        return dbs;
    }

    public static String getAgencesURLFromStringArray(String[] agences) {
        String agencesURLQuery = "";
        if (agences != null) {
            for (String a : agences) {
                agencesURLQuery += "&agences=" + a;
            }
        }
        return agencesURLQuery;
    }

    public String[] putAgencesToStringArray(List<Agence> dbs) {

        if (dbs != null) {
            String[] agences = new String[dbs.size()];
            for (int i = 0; i < dbs.size(); i++) {
                agences[i] = dbs.get(i).getId().toString();
            }
            return agences;
        }
        return new String[0];
    }

    public Date getOldesTicketDate(UUID id_agence) {
        Agence a = this.getAgenceById(id_agence);
        if (a != null) {
            StringBuilder sb = new StringBuilder("http://");
            sb.append(a.getHost());
            sb.append(":").append(a.getPort());
            sb.append("/").append(CfgHandler.APP_NODE);
            sb.append("/getoldestticketdate");

            JSONObject result = UpdateController.getJsonFromUrl(sb.toString());
            String oldesTicketDate = (String) result.get("oldestDate");
            logger.info("OldestTicketDate in agence:{} is {}", a.getName(), oldesTicketDate);
            return CfgHandler.getFormatedDateAsDate(oldesTicketDate);
        } else {
            logger.error("getOldestTicketDate: agence not found");
        }
        return null;
    }

    private String getBranchName(UUID id) {
        String BRANCH_NAME = null;
        Agence a = this.getAgenceById(id);
        if (a != null) {
            StringBuilder sb = new StringBuilder("http://");
            sb.append(a.getHost());
            sb.append(":").append(a.getPort());
            sb.append("/").append(CfgHandler.APP_NODE);
            sb.append("/getname");

            JSONObject result = UpdateController.getJsonFromUrl(sb.toString());
            if (result != null) {
                if (result.get("name") != null) {
                    BRANCH_NAME = (String) result.get("name");
                } else {
                    logger.error("getBranchName: name not found in json");
                }
            } else {
                logger.error("getBranchName: json object is null");
            }
        } else {
            logger.error("getBranchName: agence not found");
        }
        return BRANCH_NAME;

    }

}
