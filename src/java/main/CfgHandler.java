package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CfgHandler {
    
    //MetaData
    public  static final String APP = "QData";
    public static final String VERSION = "0.4";
    public static final String COMPANY = "ROUGGA";
    public static final String CLIENT = "NST-Maroc";
    public static final int APP_PORT=8989;
    public static String APP_NODE="QStates";
    
    //Data
    private final String cfgFile = "\\cfg\\cfg.properties";
    private final String userFile = "\\cfg\\db\\users.xml";
    private final String cibleFile = "\\cfg\\db\\cible.xml";
    private final String extraFile = "\\cfg\\db\\extra.xml";
    private final String dbFile = "\\cfg\\db\\db.xml";
    private final String titleFile = "\\cfg\\db\\title.xml";
    
    //Excel
    private final String tempxls = "\\cfg\\excel\\temp.xls";
    private final String gblTempExcel = "\\cfg\\excel\\gbltemp.xlsx";
    private final String empTempExcel = "\\cfg\\excel\\emptemp.xlsx";
    private final String empServTempExcel = "\\cfg\\excel\\empservtemp.xlsx";
    private final String GchTempExcel = "\\cfg\\excel\\gchtemp.xlsx";
    private final String GchservTempExcel = "\\cfg\\excel\\gchservtemp.xlsx";
    private final String ndtTempExcel = "\\cfg\\excel\\ndttemp.xlsx";
    private final String glaTempExcel = "\\cfg\\excel\\glatemp.xlsx";
    private final String gltTempExcel = "\\cfg\\excel\\glttemp.xlsx";
    
    //Pages

    public static String PAGE_HOME = "/QData/home.jsp";
    public static String PAGE_REPORT = "/QData/report.jsp";
    public static String PAGE_TASK = "/QData/setting/taches.jsp";
    
    //Pars
    private Properties prop = null;
    private String url;
    private String appPath;
    private HttpServletRequest request;
    private OutputStream output;
    private FileReader FR = null;

    public CfgHandler(HttpServletRequest r) throws FileNotFoundException, IOException {
        this.request = r;
    }

    public String getPropertie(String name) throws IOException {

        prop = new Properties();
        FR = new FileReader(getCfgFile());
        prop.load(FR);
        String val = prop.getProperty(name);
        return val;

    }

    public void closeFR() throws IOException {
        FR.close();
    }

    public void addPropertie(String name, String val) throws FileNotFoundException, IOException {
        Properties p = new Properties();
        p.setProperty(name, val);
        FileOutputStream f = new FileOutputStream(getCfgFile());
        p.store(f, "daasdasd");
        f.close();
        p.clear();
    }

    public Document getXml(String path) throws ParserConfigurationException, SAXException, IOException {
        File xml = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xml);
        doc.getDocumentElement().normalize();
        return doc;
    }

    public int getCibleA(String id) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {

        int cibleA = 0;
        String path= getCibleFile();
        Document doc = getXml(path);
        Node cibles = doc.getFirstChild();
        NodeList nList = cibles.getChildNodes();
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (Objects.equals(eElement.getElementsByTagName("id").item(0).getTextContent(), id)) {
                    cibleA = Integer.parseInt(eElement.getElementsByTagName("cibleA").item(0).getTextContent());
                }
            }

        }
        return cibleA;
    }

    public int getCibleT(String id) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {
        
        int cibleT = 0;
        String path= getCibleFile();
        Document doc = getXml(path);
        Node cibles = doc.getFirstChild();
        NodeList nList = cibles.getChildNodes();
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (Objects.equals(eElement.getElementsByTagName("id").item(0).getTextContent(), id)) {
                    cibleT = Integer.parseInt(eElement.getElementsByTagName("cibleT").item(0).getTextContent());
                }
            }

        }
        return cibleT;
    }

    public String getUrl() {
        return url;
    }

    public String getAppPath() {
        return appPath;
    }

    public String getTempxls() {
        return tempxls;
    }
    public String getCfgFile() {
        return request.getServletContext().getRealPath(cfgFile);
    }

    public String getUserFile() {
        return request.getServletContext().getRealPath(userFile);
    }

    public String getCibleFile() {
        return request.getServletContext().getRealPath(cibleFile);
    }

    public String getExtraFile() {
        return request.getServletContext().getRealPath(extraFile);
    }
     public String getGblTempExcel() {
        return request.getServletContext().getRealPath(gblTempExcel);
    }

    public String getEmpTempExcel() {
        return request.getServletContext().getRealPath(empTempExcel);
    }

    public String getGchTempExcel() {
        return request.getServletContext().getRealPath(GchTempExcel);
    }

    public String getEmpServTempExcel() {
        return request.getServletContext().getRealPath(empServTempExcel);
    }

    public String getGchServTempExcel() {
        return request.getServletContext().getRealPath(GchservTempExcel);
    }

    public String getNdtTempExcel() {
        return request.getServletContext().getRealPath(ndtTempExcel);
    }

    public String getGlaTempExcel() {
        return request.getServletContext().getRealPath(glaTempExcel);
    }

    public String getGltTempExcel() {
        return request.getServletContext().getRealPath(gltTempExcel);
    }

    public String getDbFile() {
        return  request.getServletContext().getRealPath(dbFile);
    }

    public String getTitleFile() {
        return request.getServletContext().getRealPath(titleFile);
    }


}
