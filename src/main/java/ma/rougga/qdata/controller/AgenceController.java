package ma.rougga.qdata.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.rougga.qdata.CfgHandler;
import ma.rougga.qdata.PgConnection;
import ma.rougga.qdata.modal.Agence;
import ma.rougga.qdata.modal.Zone;
import org.json.simple.JSONObject;
import org.slf4j.LoggerFactory;

public class AgenceController {
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AgenceController.class);
    public AgenceController() {
    }

    public List<Agence> getAllAgence() {
        try {
            List<Agence> agences = new ArrayList();
            PgConnection con = new PgConnection();
            ResultSet r = con.getStatement().executeQuery("select * from rougga_agences order by name;");
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
            con.closeConnection();
            return agences;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public List<Agence> getAgencesByZone(UUID id_zone) {
        try {
            List<Agence> agences = new ArrayList();
            PgConnection con = new PgConnection();
            PreparedStatement ps = con.getStatement()
                    .getConnection()
                    .prepareStatement("select id_agence from rougga_agence_zone where id_zone=?;");

            ps.setString(1, id_zone.toString());
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                agences.add(this.getAgenceById(UUID.fromString(r.getString("id_agence"))));
            }
            con.closeConnection();
            return agences;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public int addAgence(Agence a) {
        try {
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("insert into rougga_agences values(?,?,?,?,?,?);");
            p.setString(1, a.getId().toString());
            p.setString(2, a.getName());
            p.setString(3, a.getHost());
            p.setInt(4, a.getPort());
            p.setString(5, a.getLastupdated_at());
            p.setInt(6, a.getStatus());
            p.execute();
            con.closeConnection();
            return 1;

        } catch (Exception ex) {
            Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public boolean editAgence(Agence a) {
        try {
            PgConnection con = new PgConnection();
            String SQL = "update rougga_agences set name=?,host=?,port=?,lastupdated_at=?,status=? where id=?;";
            PreparedStatement p = con.getStatement().getConnection().prepareStatement(SQL);
            p.setString(1, a.getName());
            p.setString(2, a.getHost());
            p.setInt(3, a.getPort());
            p.setString(4, a.getLastupdated_at());
            p.setInt(5, a.getStatus());
            p.setString(6, a.getId().toString());
            p.execute();
            con.closeConnection();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Agence getAgenceById(UUID id) {
        try {
            Agence a = new Agence();
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("select * from rougga_agences where id=? ;");
            p.setString(1, id.toString());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                a.setId(id);
                a.setName(r.getString("name"));
                a.setHost(r.getString("host"));
                a.setPort(r.getInt("port"));
                a.setLastupdated_at(r.getString("lastupdated_at"));
                a.setStatus(r.getInt("status"));
                con.closeConnection();
                return a;
            } else {
                con.closeConnection();
                return null;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int deleteAgenceById(UUID id) {
        try {
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("delete from rougga_agences where id=?;");
            p.setString(1, id.toString());
            p.execute();
            return 1;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public boolean isOnlineJson(UUID id) {
        Agence a = getAgenceById(id);
        if (a != null) {
            String url = "http://" + a.getHost()
                    + ":" + a.getPort()
                    + "/" + CfgHandler.API_CHECK_STATUS;
            System.out.println("URL = " + url);
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

    public int updateName(UUID id, String name) {
        try {
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("update rougga_agences set name=? where id=?;");
            p.setString(1, name);
            p.setString(2, id.toString());
            p.execute();
            con.closeConnection();
            return 1;

        } catch (Exception ex) {
            Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, null, ex);
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
            this.editAgence(a);
        }
    }

    public void setZone(UUID id_agence, UUID id_zone) {
        try {
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("insert into rougga_agence_zone values(?,?);");
            p.setString(1, id_agence.toString());
            p.setString(2, id_zone.toString());
            p.execute();
            con.closeConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void editZone(UUID id_agence, UUID id_zone) {
        try {
            PgConnection con = new PgConnection();
            if (this.getAgenceZoneByAgenceId(id_agence) != null) {
                String SQL = "";
                PreparedStatement p;
                if (id_zone != null) {
                    SQL = "update rougga_agence_zone set id_zone=? where  id_agence=?;";
                    p = con.getStatement().getConnection().prepareStatement(SQL);
                    p.setString(2, id_agence.toString());
                    p.setString(1, id_zone.toString());
                } else {
                    SQL = "delete from rougga_agence_zone where id_agence=?";
                    p = con.getStatement().getConnection().prepareStatement(SQL);
                    p.setString(1, id_agence.toString());
                }
                p.execute();
                con.closeConnection();
            } else {
                this.setZone(id_agence, id_zone);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public Zone getAgenceZoneByAgenceId(UUID id_agence) {
        try {
            Zone a;
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("select * from rougga_agence_zone where id_agence=? ;");
            p.setString(1, id_agence.toString());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                a = new ZoneController().getZoneById(UUID.fromString(r.getString("id_zone")));
                con.closeConnection();
                return a;
            } else {
                con.closeConnection();
                return null;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
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
            logger.info("OldestTicketDate in agence:"+a.getName()+" is "+ oldesTicketDate);
            return CfgHandler.getFormatedDateAsDate(oldesTicketDate);
        }
        return null;
    }

}
