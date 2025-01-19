package main.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.CfgHandler;
import main.PgConnection;
import main.PgMultiConnection;
import main.modal.Agence;
import main.modal.Zone;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AgenceController {

    public AgenceController() {
    }

    public List<Agence> getAllAgence() {
        try {
            List<Agence> agences = new ArrayList();
            PgConnection con = new PgConnection();
            ResultSet r = con.getStatement().executeQuery("select * from agence order by name;");
            while (r.next()) {
                agences.add(new Agence(UUID.fromString(r.getString("id")), r.getString("name"), r.getString("host"), r.getInt("port"), r.getString("database"), r.getString("username"), r.getString("password"), r.getInt("status")));
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
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("insert into agence values(?,?,?,?,?,?,?,?);");
            p.setString(1, a.getId().toString());
            p.setString(2, a.getName());
            p.setString(3, a.getHost());
            p.setInt(4, a.getPort());
            p.setString(5, a.getDatabase());
            p.setString(6, a.getUsername());
            p.setString(7, a.getPassword());
            p.setInt(8, a.getStatus());
            p.execute();
            p = con.getStatement().getConnection().prepareStatement("insert into lastupdate values(?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'));");
            p.setString(1, a.getId().toString());
            p.setString(2, getFormatedDateAsString(getFormatedDateAsDate("1999-12-20 00:00:00")));
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
            String SQL = "update agence set name=?,host=?,port=?,database=?,username=?,password=?,status=? where id=?;";
            PreparedStatement p = con.getStatement().getConnection().prepareStatement(SQL);
            p.setString(1, a.getName());
            p.setString(2, a.getHost());
            p.setInt(3, a.getPort());
            p.setString(4, a.getDatabase());
            p.setString(5, a.getUsername());
            p.setString(6, a.getPassword());
            p.setInt(7, a.getStatus());
            p.setString(8, a.getId().toString());
            p.execute();
            p = con.getStatement().getConnection().prepareStatement("update lastupdate set last_update=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS') where id_db=?;");
            p.setString(1, getFormatedDateAsString(new Date()));
            p.setString(2, a.getId().toString());
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
            Agence a;
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("select * from agence where id=? ;");
            p.setString(1, id.toString());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                a = new Agence(id, r.getString("name"), r.getString("host"), r.getInt("port"), r.getString("database"), r.getString("username"), r.getString("password"), r.getInt("status"));
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
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("delete from agence where id=?;");
            p.setString(1, id.toString());
            p.execute();
            con.closeConnection();
            return 1;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public boolean isOnline(UUID id) {
        Agence a = getAgenceById(id);
        if (a != null) {
            try {
                //Socket socket = new Socket(a.getHost(), a.getPort());
                //socket.close();
                PgMultiConnection con = new PgMultiConnection(a.getHost(), String.valueOf(a.getPort()), a.getDatabase(), a.getUsername(), a.getPassword());
                if (con != null) {
                    con.closeCon();
                }
                return true;
            } catch (ClassNotFoundException | SQLException ex) {
                return false;
            }

        } else {
            return false;
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
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("update agence set name=? where id=?;");
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
        try {
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("select * from lastupdate where id_db=? ;");
            p.setString(1, id.toString());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                Date d = getFormatedDateAsDate(r.getString("last_update"));
                con.closeConnection();
                return d;
            } else {
                con.closeConnection();
                return null;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setLastUpdate(UUID id) {
        try {
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("update lastupdate set  last_update=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS') where id_db=?;");
            p.setString(1, getFormatedDateAsString(new Date()));
            p.setString(2, id.toString());
            p.execute();
            con.closeConnection();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
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

    public void updateAllAgenceName() {
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            PgMultiConnection con;
            System.out.println("-- Updating Agence names....");
            for (int i = 0; i < agences.size(); i++) {
                try {
                    con = new PgMultiConnection(agences.get(i).getHost(), String.valueOf(agences.get(i).getPort()), agences.get(i).getDatabase(), agences.get(i).getUsername(), agences.get(i).getPassword());
                    String SQL = "SELECT value FROM t_basic_par where name='BRANCH_NAME' ;";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    if (r.next()) {
                        updateName(agences.get(i).getId(), r.getString("value"));
                        setLastUpdate(agences.get(i).getId());
                    }
                    con.closeConnection();
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
                }
            }
            System.out.println("-- Agence names updated.");
        }

    }

    public String getFormatedDateAsString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            return format.format(date);
        } else {
            return null;
        }
    }

    public Date getFormatedDateAsDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            try {
                return format.parse(date);
            } catch (ParseException ex) {
                Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            return null;
        }
    }

    public void updateAgenceNameById(UUID id) {
        Agence a = new AgenceController().getAgenceById(id);
        if (a != null) {
            PgMultiConnection con;
            System.out.println("-- Updating Agence " + a.getName() + " :....");
            try {
                con = new PgMultiConnection(a.getHost(), String.valueOf(a.getPort()), a.getDatabase(), a.getUsername(), a.getPassword());
                String SQL = "SELECT value FROM t_basic_par where name='BRANCH_NAME' ;";
                ResultSet r = con.getStatement().executeQuery(SQL);
                if (r.next()) {
                    updateName(a.getId(), r.getString("value"));
                    setLastUpdate(a.getId());
                }
                con.closeConnection();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
            }

            System.out.println("-- Agence " + a.getName() + " updated.");
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
