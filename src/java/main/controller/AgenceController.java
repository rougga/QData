package main.controller;

import java.io.IOException;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.PgConnection;
import main.PgMultiConnection;
import main.modal.Agence;

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

            System.out.println("-- Agence "+a.getName()+" updated.");
        }
    }
}
