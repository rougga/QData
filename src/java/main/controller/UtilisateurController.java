package main.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import main.CfgHandler;
import main.PasswordAuthentication;
import main.PgConnection;
import main.modal.Agence;
import main.modal.Utilisateur;
import main.modal.Zone;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UtilisateurController {

    HttpServletRequest request;

    public UtilisateurController(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public List<Utilisateur> getAllUtilisateur() {
        try {
            List<Utilisateur> utilisateurs = new ArrayList();
            PgConnection con = new PgConnection();
            ResultSet r = con.getStatement().executeQuery("select * from rougga_user order by date;");
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
            con.closeConnection();
            return utilisateurs;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }

    }

    public int AddUtilisateur(Utilisateur utilisateur) {
        try {
            CfgHandler cfg = new CfgHandler(request);
            String path = cfg.getUserFile();

            Document doc = cfg.getXml(path);
            Node users = doc.getFirstChild();

            Element user = doc.createElement("user");
            users.appendChild(user);

            Element usernameE = doc.createElement("username");
            usernameE.appendChild(doc.createTextNode(utilisateur.getUsername()));
            user.appendChild(usernameE);

            PasswordAuthentication pa = new PasswordAuthentication();
            Element passwordE = doc.createElement("password");
            passwordE.appendChild(doc.createTextNode(utilisateur.getPassword()));
            user.appendChild(passwordE);

            Element gradeE = doc.createElement("grade");
            gradeE.appendChild(doc.createTextNode(utilisateur.getGrade()));
            user.appendChild(gradeE);

            Element firstNameE = doc.createElement("firstName");
            firstNameE.appendChild(doc.createTextNode(utilisateur.getFirstName()));
            user.appendChild(firstNameE);

            Element lastNameE = doc.createElement("lastName");
            lastNameE.appendChild(doc.createTextNode(utilisateur.getLastName()));
            user.appendChild(lastNameE);

            Element dateAdded = doc.createElement("date");
            dateAdded.appendChild(doc.createTextNode(new SimpleDateFormat("yyyy-MM-dd").format(utilisateur.getDate())));
            user.appendChild(dateAdded);

            Element sponsor = doc.createElement("sponsor");
            sponsor.appendChild(doc.createTextNode(utilisateur.getSponsor()));
            user.appendChild(sponsor);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);
            return 1;

        } catch (IOException | ParserConfigurationException | DOMException | SAXException | TransformerException e) {
            Logger.getLogger(AgenceController.class
                    .getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int deleteUtilisateurByUsername(String username) {
        try {
            CfgHandler cfg = new CfgHandler(request);
            String path = cfg.getUserFile();
            Document doc = cfg.getXml(path);
            Node users = doc.getFirstChild();
            NodeList nList = users.getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (Objects.equals(eElement.getElementsByTagName("username").item(0).getTextContent(), username)) {
                        users.removeChild(nNode);
                    }
                }

            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);
            return 1;

        } catch (IOException | ParserConfigurationException | TransformerException | DOMException | SAXException e) {
            Logger.getLogger(AgenceController.class
                    .getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public Zone getUtilisateurZone(UUID id) {
        try {
            Zone z = null;
            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("select id_zone from rougga_user_zone where id_user=?;");
            p.setString(1, id.toString());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                z = new ZoneController().getZoneById(UUID.fromString(r.getString("id_zone")));
            }
            con.closeConnection();
            return z;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UtilisateurController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

}
