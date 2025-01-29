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
import main.modal.Zone;

public class ZoneController {

    public ZoneController() {
    }

    public List<Zone> getAllZones() {
        try {
            List<Zone> zones = new ArrayList();
            PgConnection con = new PgConnection();
            ResultSet r = con.getStatement().executeQuery("select * from rougga_zones order by name;");
            while (r.next()) {
                zones.add(new Zone(UUID.fromString(r.getString("id")),
                        r.getString("name"),
                        r.getString("city"),
                        r.getString("code")
                ));
            }
            con.closeConnection();
            return zones;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ZoneController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public boolean addZone(Zone z) {
        try {
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("insert into rougga_zones values(?,?,?,?);");
            p.setString(1, z.getId().toString());
            p.setString(2, z.getName());
            p.setString(3, z.getCity());
            p.setString(4, z.getCode());
            p.execute();
            con.closeConnection();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(ZoneController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean editZone(Zone z) {
        try {
            PgConnection con = new PgConnection();
            String SQL = "update rougga_zones set name=?, city=?, code=? where id=?;";
            PreparedStatement p = con.getStatement().getConnection().prepareStatement(SQL);
            p.setString(1, z.getName());
            p.setString(2, z.getCity());
            p.setString(3, z.getCode());
            p.setString(4, z.getId().toString());
            p.execute();
            con.closeConnection();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(ZoneController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public Zone getZoneById(UUID id) {
        try {
            Zone a;
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("select * from rougga_zones where id=? ;");
            p.setString(1, id.toString());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                a = new Zone(UUID.fromString(r.getString("id")),
                        r.getString("name"),
                        r.getString("city"),
                        r.getString("code")
                );
                con.closeConnection();
                return a;
            } else {
                con.closeConnection();
                return null;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ZoneController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public boolean deleteZoneById(UUID id) {
        try {
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("delete from rougga_zones where id=?;");
            p.setString(1, id.toString());
            p.execute();
            con.closeConnection();
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ZoneController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    public int getAgenceCountInZone(UUID id) {
        try {
            int agenceCount = 0;
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("select count(*) as num from rougga_agence_zone where id_zone=? ;");
            p.setString(1, id.toString());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                agenceCount = r.getInt("num");
                con.closeConnection();
                return agenceCount;
            } else {
                con.closeConnection();
                return agenceCount;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ZoneController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return 0;
        }
    }
}
