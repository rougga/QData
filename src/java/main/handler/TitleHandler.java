package main.handler;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import main.CfgHandler;
import main.controller.AgenceController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TitleHandler {

    HttpServletRequest request;

    public TitleHandler(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public String getGblTitle() {
        String title = getTitle("gbl");
        if (title == null) {
            return String.valueOf("Rapport Globale");
        } else {
            return title;
        }
    }

    public String getEmpTitle() {
        String title = getTitle("emp");
        if (title == null) {
            return String.valueOf("Rapport Employé");
        } else {
            return title;
        }
    }

    public String getEmpSerTitle() {
        String title = getTitle("empser");
        if (title == null) {
            return String.valueOf("Rapport Employé par service");
        } else {
            return title;
        }
    }

    public String getGchTitle() {
        String title = getTitle("gch");
        if (title == null) {
            return String.valueOf("Rapport Guichet");
        } else {
            return title;
        }
    }

    public String getGchServTitle() {
        String title = getTitle("gchserv");
        if (title == null) {
            return String.valueOf("Rapport Guichet par service");
        } else {
            return title;
        }
    }

    public String getGlaTitle() {
        String title = getTitle("gla");
        if (title == null) {
            return String.valueOf("Grille d attente");
        } else {
            return title;
        }
    }

    public String getGltTitle() {
        String title = getTitle("glt");
        if (title == null) {
            return String.valueOf("Grille de traitement");
        } else {
            return title;
        }
    }

    public String getNdtTitle() {
        String title = getTitle("ndt");
        if (title == null) {
            return String.valueOf("Nombre de tickets edités");
        } else {
            return title;
        }
    }

    public String getNdttTitle() {
        String title = getTitle("ndtt");
        if (title == null) {
            return String.valueOf("Nombre de tickets traités");
        } else {
            return title;
        }
    }

    public String getNdtaTitle() {
        String title = getTitle("ndta");
        if (title == null) {
            return String.valueOf("Nombre de tickets absents");
        } else {
            return title;
        }
    }

    public String getNdtsaTitle() {
        String title = getTitle("ndtsa");
        if (title == null) {
            return String.valueOf("Nombre de tickets sans affectation");
        } else {
            return title;
        }
    }

    public void setTitle(String table, String title) {
        try {
            CfgHandler cfg = new CfgHandler(request);
            String path = cfg.getTitleFile();
            Document doc = cfg.getXml(path);
            Node titles = doc.getFirstChild();
            NodeList nList = titles.getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (Objects.equals(eElement.getNodeName(), table)) {
                        titles.removeChild(nNode);
                    }
                }

            }

            Element tableE = doc.createElement(table);
            tableE.appendChild(doc.createTextNode(title));
            titles.appendChild(tableE);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);

        } catch (Exception e) {
            Logger.getLogger(TitleHandler.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public String getTitle(String table) {
        try {
            CfgHandler cfg = new CfgHandler(getRequest());
            String path = cfg.getTitleFile();
            Document doc = cfg.getXml(path);
            NodeList nList = doc.getElementsByTagName(table);
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                return eElement.getTextContent();
            } else {
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(TitleHandler.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public String getCnxTitle() {
       String title = getTitle("cnx");
        if (title == null) {
            return String.valueOf("Connexions");
        } else {
            return title;
        }
    }

    public String getRempTitle() {
        String title = getTitle("remp");
        if (title == null) {
            return String.valueOf("Rendement Employés");
        } else {
            return title;
        }
    }

    public String getSerTitle() {
       String title = getTitle("ser");
        if (title == null) {
            return String.valueOf("Supervision: Service");
        } else {
            return title;
        }
    }

    public String getSgchTitle() {
       String title = getTitle("sgch");
        if (title == null) {
            return String.valueOf("Supervision: Guichet");
        } else {
            return title;
        }
       
    }

    public String getAplTitle() {
        String title = getTitle("apl");
        if (title == null) {
            return String.valueOf("Détail des appels");
        } else {
            return title;
        }
    }
    
    public String getTaskTitle() {
         String title = getTitle("tch");
        if (title == null) {
            return String.valueOf("Rapport des Taches");
        } else {
            return title;
        }
    }
}
