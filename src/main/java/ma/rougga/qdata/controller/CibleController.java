package ma.rougga.qdata.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import ma.rougga.qdata.CPConnection;
import ma.rougga.qdata.CfgHandler;
import ma.rougga.qdata.PgConnection;
import ma.rougga.qdata.modal.Agence;
import ma.rougga.qdata.modal.Cible;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class CibleController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AgenceController.class);

    public CibleController() {
    }

    public List<Cible> getAll() {
        try {
            List<Cible> cibles = new ArrayList();
            Connection con = new CPConnection().getConnection();
            ResultSet r = con.createStatement().executeQuery("select * from rougga_cibles;");
            while (r.next()) {
                Cible cible = new Cible();
                cible.setService_id(r.getString("service_id"));
                cible.setService_name(r.getString("service_name"));
                cible.setCibleA(0);
                cible.setCibleT(0);
                cible.setAgence_id(UUID.fromString(r.getString("agence_id")));
                cibles.add(cible);
            }
            con.close();
            return cibles;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return null;
        }
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

//    public int addCible(Cible c) {
//        try {
//            PgConnection con = new PgConnection();
//            PreparedStatement p = con.getStatement().getConnection().prepareStatement("insert into cible values(?,?,?,?,?);");
//            p.setString(1, c.getBiz_type_id());
//            p.setString(2, c.getDb_id().toString());
//            p.setDouble(3, c.getCibleA());
//            p.setDouble(4, c.getCibleT());
//            p.setFloat(5, c.getdCible());
//            p.execute();
//            con.closeConnection();
//            return 1;
//
//        } catch (Exception ex) {
//            Logger.getLogger(CibleController.class.getName()).log(Level.SEVERE, null, ex);
//            return 0;
//        }
//    }

//    public boolean deleteById(String id, UUID db_id) {
//        try {
//            PgConnection con = new PgConnection();
//            PreparedStatement p = con.getStatement().getConnection().prepareStatement("delete from cible where biz_type_id=? and db_id=? ;");
//            p.setString(1, id);
//            p.setString(2, db_id.toString());
//            p.execute();
//            con.closeConnection();
//            return true;
//
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(CibleController.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        }
//    }
}
