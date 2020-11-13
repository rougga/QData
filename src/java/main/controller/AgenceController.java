package main.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.PgConnection;
import main.modal.Agence;

public class AgenceController {

    public AgenceController() {
    }

    public List<Agence> getAllAgence() {
        try {
            List<Agence> agences = new ArrayList();
            PgConnection con = new PgConnection();
            ResultSet r = con.getStatement().executeQuery("select * from agence;");
            while (r.next()) {
                agences.add(new Agence(UUID.fromString(r.getString("id")), r.getString("name"), r.getString("host"), r.getInt("port"), r.getString("database"), r.getString("username"), r.getString("password"),r.getInt("status")));
            }
            con.closeConnection();
            return agences;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, null, ex);
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
            con.closeConnection();
            return 1;

        } catch (ClassNotFoundException | SQLException ex) {
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
                a = new Agence(id, r.getString("name"), r.getString("host"), r.getInt("port"), r.getString("database"), r.getString("username"), r.getString("password"),r.getInt("status"));
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
}
