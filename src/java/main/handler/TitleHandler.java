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
                    if (Objects.equals(eElement.getTextContent(), table)) {
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
             Logger.getLogger(AgenceController.class.getName()).log(Level.SEVERE, null, e);
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
            return null;
        }
    }
}
