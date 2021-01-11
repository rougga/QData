package main.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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
import main.CfgHandler;
import main.PgConnection;
import main.modal.Agence;
import main.modal.Cible;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class CibleController {

    public CibleController() {
    }

    public List<Cible> getAll() {
        try {
            List<Cible> cibles = new ArrayList();
            PgConnection con = new PgConnection();
            ResultSet r = con.getStatement().executeQuery("select * from cible;");
            while (r.next()) {
                cibles.add(
                        new Cible(
                                r.getString("biz_type_id"),
                                UUID.fromString(r.getString("db_id")),
                                r.getDouble("cibleA"),
                                r.getDouble("cibleT"),
                                r.getFloat("dCible")
                        )
                );
            }
            con.closeConnection();
            return cibles;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CibleController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Cible getOne(String id, UUID db_id) {
        Cible dc = new Cible(id, db_id, 0, 0, 0);
        try {
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("select * from cible where biz_type_id=? and db_id=?");
            p.setString(1, id);
            p.setString(2, db_id.toString());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                Cible c = new Cible(
                        r.getString("biz_type_id"),
                        UUID.fromString(r.getString("db_id")),
                        r.getDouble("cibleA"),
                        r.getDouble("cibleT"),
                        r.getFloat("dCible")
                );
                con.closeConnection();
                return c;
            } else {
                con.closeConnection();
                return dc;
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CibleController.class.getName()).log(Level.SEVERE, null, ex);
            return dc;
        }
    }

    public boolean isUnique(String id, String db_id) {
        try {
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("select * from cible where biz_type_id=? and db_id=?");
            p.setString(1, id);
            p.setString(2, db_id);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                con.closeConnection();
                return false;
            } else {
                con.closeConnection();
                return true;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CibleController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int addCible(Cible c) {
        try {
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("insert into cible values(?,?,?,?,?);");
            p.setString(1, c.getBiz_type_id());
            p.setString(2, c.getDb_id().toString());
            p.setDouble(3, c.getCibleA());
            p.setDouble(4, c.getCibleT());
            p.setFloat(5, c.getdCible());
            p.execute();
            con.closeConnection();
            return 1;

        } catch (Exception ex) {
            Logger.getLogger(CibleController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int addCibleXml(Cible c) {
        try {
            Agence a = new AgenceController().getAgenceById(c.getDb_id());
            String path = "http://" + a.getHost() + ":" + CfgHandler.APP_PORT + "/" + CfgHandler.APP + "/cfg/cible.xml";
            //File xml = new File(path);
            System.out.println(path);
            //modified code
            URL url = new URL(path);
            URLConnection urlConnection = url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            //your code
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(in);
            doc.getDocumentElement().normalize();
            Node cibles = doc.getFirstChild();

            Element service = doc.createElement("service");
            cibles.appendChild(service);

            Element idE = doc.createElement("id");
            idE.appendChild(doc.createTextNode(c.getBiz_type_id()));
            service.appendChild(idE);

            Element nameE = doc.createElement("name");
            nameE.appendChild(doc.createTextNode(new ServiceController().getById(c.getBiz_type_id(), c.getDb_id()).getName()));

            service.appendChild(nameE);

            Element cibleAE = doc.createElement("cibleA");
            cibleAE.appendChild(doc.createTextNode(String.valueOf(c.getCibleA())));
            service.appendChild(cibleAE);

            Element cibleTE = doc.createElement("cibleT");
            cibleTE.appendChild(doc.createTextNode(String.valueOf(c.getCibleT())));
            service.appendChild(cibleTE);

            Element cibleDE = doc.createElement("dcible");
            cibleDE.appendChild(doc.createTextNode(String.valueOf(c.getdCible())));
            service.appendChild(cibleDE);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            URLConnection urlConnection2 = url.openConnection();
            urlConnection2.setDoOutput(true);
            StreamResult result = new StreamResult(urlConnection2.getOutputStream());
            transformer.transform(source, result);
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(CibleController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    public boolean deleteById(String id, UUID db_id) {
        try {
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("delete from cible where biz_type_id=? and db_id=? ;");
            p.setString(1, id);
            p.setString(2, db_id.toString());
            p.execute();
            con.closeConnection();
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CibleController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
