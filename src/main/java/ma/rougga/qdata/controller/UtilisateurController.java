package ma.rougga.qdata.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.rougga.qdata.CPConnection;
import ma.rougga.qdata.CfgHandler;
import ma.rougga.qdata.PgConnection;
import ma.rougga.qdata.modal.Utilisateur;
import ma.rougga.qdata.modal.Zone;
import org.slf4j.LoggerFactory;

public class UtilisateurController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UtilisateurController.class);

    public List<Utilisateur> getAllUtilisateur() {
        try {
            List<Utilisateur> utilisateurs = new ArrayList();
            Connection con = new CPConnection().getConnection();
            ResultSet r = con.createStatement().executeQuery("select * from rougga_users order by date;");
            while (r.next()) {
                utilisateurs.add(
                        new Utilisateur(
                                UUID.fromString(r.getString("id")),
                                r.getString("username"),
                                r.getString("password"),
                                r.getString("grade"),
                                r.getString("first_name"),
                                r.getString("last_name"),
                                CfgHandler.getFormatedDateAsDate(r.getString("date")),
                                r.getString("sponsor"))
                );
            }
            con.close();
            return utilisateurs;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return null;
        }

    }

    public Utilisateur getUtilisateurById(UUID id) {
        Utilisateur u = null;
        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement ps = con.prepareStatement("select * from rougga_users where id=?;");
            ps.setString(1, id.toString());
            ResultSet r = ps.executeQuery();
            if (r.next()) {
                u = new Utilisateur(
                        UUID.fromString(r.getString("id")),
                        r.getString("username"),
                        r.getString("password"),
                        r.getString("grade"),
                        r.getString("first_name"),
                        r.getString("last_name"),
                        CfgHandler.getFormatedDateAsDate(r.getString("date")),
                        r.getString("sponsor"));
            }
            con.close();
            return u;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public Utilisateur getUtilisateurByUsername(String username) {
        Utilisateur u = null;
        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement ps = con.prepareStatement("select id from rougga_users where username=?;");
            ps.setString(1, username);
            ResultSet r = ps.executeQuery();
            if (r.next()) {
                u = getUtilisateurById(UUID.fromString(r.getString("id")));
            }
            con.close();
            return u;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public boolean AddUtilisateur(Utilisateur u) {
        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement p = con.prepareStatement("insert into rougga_users (id,username,password,grade,first_name,last_name,sponsor) values(?,?,?,?,?,?,?);");
            p.setString(1, u.getId().toString());
            p.setString(2, u.getUsername());
            p.setString(3, u.getPassword());
            p.setString(4, u.getGrade());
            p.setString(5, u.getFirstName());
            p.setString(6, u.getLastName());
            p.setString(7, u.getSponsor());
            p.execute();
            con.close();
            return true;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    public boolean deleteUtilisateurById(UUID id) {
        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement p = con.prepareStatement("delete from rougga_users where id=?;");
            p.setString(1, id.toString());
            p.execute();
            con.close();
            return true;

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    public boolean setZone(UUID id_user, UUID id_zone) {
        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement p = con.prepareStatement("insert into rougga_user_zone values(?,?);");
            p.setString(1, id_user.toString());
            p.setString(2, id_zone.toString());
            p.execute();
            con.close();
            return true;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    public Zone getUtilisateurZone(UUID id) {
        try {
            Zone z = null;
            Connection con = new CPConnection().getConnection();
            PreparedStatement p = con.prepareStatement("select id_zone from rougga_user_zone where id_user=?;");
            p.setString(1, id.toString());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                z = new ZoneController().getZoneById(UUID.fromString(r.getString("id_zone")));
            }
            con.close();
            return z;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public Zone getUtilisateurZoneByUsername(String username) {
        Zone z = this.getUtilisateurZone(this.getUtilisateurByUsername(username).getId());
        return z;
    }
}
