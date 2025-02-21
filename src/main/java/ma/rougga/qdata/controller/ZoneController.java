package ma.rougga.qdata.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import ma.rougga.qdata.CPConnection;
import ma.rougga.qdata.modal.Zone;
import org.slf4j.LoggerFactory;

public class ZoneController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ZoneController.class);

    public ZoneController() {
    }

    public List<Zone> getAllZones() {
        try {
            List<Zone> zones = new ArrayList();
            Connection con = new CPConnection().getConnection();
            ResultSet r = con.createStatement().executeQuery("select * from rougga_zones order by name;");
            while (r.next()) {
                zones.add(new Zone(UUID.fromString(r.getString("id")),
                        r.getString("name"),
                        r.getString("city"),
                        r.getString("code")
                ));
            }
            con.close();
            return zones;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public boolean addZone(Zone z) {
        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement p = con.prepareStatement("insert into rougga_zones values(?,?,?,?);");
            p.setString(1, z.getId().toString());
            p.setString(2, z.getName());
            p.setString(3, z.getCity());
            p.setString(4, z.getCode());
            p.execute();
            con.close();
            return true;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    public boolean editZone(Zone z) {
        try {
            Connection con = new CPConnection().getConnection();
            String SQL = "update rougga_zones set name=?, city=?, code=? where id=?;";
            PreparedStatement p = con.prepareStatement(SQL);
            p.setString(1, z.getName());
            p.setString(2, z.getCity());
            p.setString(3, z.getCode());
            p.setString(4, z.getId().toString());
            p.execute();
            con.close();
            return true;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    public Zone getZoneById(UUID id) {
        try {
            Zone a;
            Connection con = new CPConnection().getConnection();
            PreparedStatement p = con.prepareStatement("select * from rougga_zones where id=? ;");
            p.setString(1, id.toString());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                a = new Zone(UUID.fromString(r.getString("id")),
                        r.getString("name"),
                        r.getString("city"),
                        r.getString("code")
                );
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

    public boolean deleteZoneById(UUID id) {
        try {
            Connection con = new CPConnection().getConnection();
            PreparedStatement p = con.prepareStatement("delete from rougga_zones where id=?;");
            p.setString(1, id.toString());
            p.execute();
            con.close();
            return true;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    public int getAgenceCountInZone(UUID id) {
        try {
            int agenceCount = 0;
            Connection con = new CPConnection().getConnection();
            PreparedStatement p = con.prepareStatement("select count(*) as num from rougga_agence_zone where id_zone=? ;");
            p.setString(1, id.toString());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                agenceCount = r.getInt("num");
                con.close();
                return agenceCount;
            } else {
                con.close();
                return agenceCount;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return 0;
        }
    }
}
