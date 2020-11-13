package main.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import main.CfgHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DbHandler {

    private String URL;
    HttpServletRequest request;

    public DbHandler(HttpServletRequest request) {
        this.request = request;
    }

    public List<ArrayList> getDatabases() {
        List<ArrayList> table = new ArrayList<>();
        try {
            CfgHandler cfg = new CfgHandler(getRequest());
            String path = cfg.getDbFile();
            Document doc = cfg.getXml(path);
            NodeList nList = doc.getElementsByTagName("db");
            for (int i = 0; i < nList.getLength(); i++) {
                ArrayList row = new ArrayList();
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    row.add(eElement.getElementsByTagName("id").item(0).getTextContent());
                    row.add(eElement.getElementsByTagName("name").item(0).getTextContent());
                    row.add(eElement.getElementsByTagName("host").item(0).getTextContent());
                    row.add(eElement.getElementsByTagName("port").item(0).getTextContent());
                    row.add(eElement.getElementsByTagName("database").item(0).getTextContent());
                    row.add(eElement.getElementsByTagName("username").item(0).getTextContent());
                    row.add(eElement.getElementsByTagName("password").item(0).getTextContent());
                    table.add(row);
                }
            }
            return table;
        } catch (Exception e) {
            return null;
        }
    }

    public List getDatabaseById(String id) {
        List table = new ArrayList<>();
        if (!Objects.equals(id, null)) {
            try {
                CfgHandler cfg = new CfgHandler(getRequest());
                String path = cfg.getDbFile();
                Document doc = cfg.getXml(path);
                Node root = doc.getFirstChild();
                NodeList nList = root.getChildNodes();
                for (int i = 0; i < nList.getLength(); i++) {
                    Node nNode = nList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        if (Objects.equals(eElement.getElementsByTagName("id").item(0).getTextContent(), id.trim())) {
                            table.add(eElement.getElementsByTagName("id").item(0).getTextContent());
                            table.add(eElement.getElementsByTagName("name").item(0).getTextContent());
                            table.add(eElement.getElementsByTagName("host").item(0).getTextContent());
                            table.add(eElement.getElementsByTagName("port").item(0).getTextContent());
                            table.add(eElement.getElementsByTagName("database").item(0).getTextContent());
                            table.add(eElement.getElementsByTagName("username").item(0).getTextContent());
                            table.add(eElement.getElementsByTagName("password").item(0).getTextContent());
                        }
                    }
                }
                return table;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public long getMaxId() {
        long maxId = 1;
        try {
            CfgHandler cfg = new CfgHandler(getRequest());
            String path = cfg.getDbFile();
            Document doc = cfg.getXml(path);
            Node root = doc.getFirstChild();
            NodeList nList = root.getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (Long.parseLong(eElement.getElementsByTagName("id").item(0).getTextContent()) > maxId) {
                        maxId = Long.parseLong(eElement.getElementsByTagName("id").item(0).getTextContent());
                    }
                }
            }
            maxId++;
            return maxId;
        } catch (Exception e) {
            return 0;
        }
    }

    public int addDataBase(String name, String host, String port, String database, String username, String password) {
        long max = getMaxId();
        if (max == 0) {
            return 0;
        } else {
            try {
                CfgHandler cfg = new CfgHandler(getRequest());
                String path = cfg.getDbFile();

                Document doc = cfg.getXml(path);
                Node root = doc.getFirstChild();

                Element element = doc.createElement("db");
                root.appendChild(element);

                Element idE = doc.createElement("id");

                idE.appendChild(doc.createTextNode(max + ""));
                element.appendChild(idE);

                Element nameE = doc.createElement("name");
                nameE.appendChild(doc.createTextNode(name.toLowerCase().trim()));
                element.appendChild(nameE);

                Element hostE = doc.createElement("host");
                hostE.appendChild(doc.createTextNode(host.trim()));
                element.appendChild(hostE);

                Element portE = doc.createElement("port");
                portE.appendChild(doc.createTextNode(port.trim()));
                element.appendChild(portE);

                Element databaseE = doc.createElement("database");
                databaseE.appendChild(doc.createTextNode(database.trim()));
                element.appendChild(databaseE);

                Element usernameE = doc.createElement("username");
                usernameE.appendChild(doc.createTextNode(username.trim()));
                element.appendChild(usernameE);

                Element passwordE = doc.createElement("password");
                passwordE.appendChild(doc.createTextNode(password));
                element.appendChild(passwordE);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(path));
                transformer.transform(source, result);
                return 1;
            } catch (Exception e) {
                return 0;
            }
        }

    }

    public int deleteDatabaseById(String id) {
        try {
            CfgHandler cfg = new CfgHandler(getRequest());
            String path = cfg.getDbFile();
            Document doc = cfg.getXml(path);
            Node root = doc.getFirstChild();
            NodeList nList = root.getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (Objects.equals(eElement.getElementsByTagName("id").item(0).getTextContent(), id.trim())) {
                        root.removeChild(nNode);
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File(path));
                        transformer.transform(source, result);
                        return 1;
                    }
                }

            }
            return 0;
        }catch(Exception e){
            return 0;
        }
    }

    

    public String getURL() {
        return URL;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

}
