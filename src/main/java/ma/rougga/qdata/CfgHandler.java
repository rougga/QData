package ma.rougga.qdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public static final String APP = "QData";
    public static final String VERSION = "6.0";
    public static final String COMPANY = "ROUGGA";
    public static final String CLIENT = "NST-Maroc";
    public static final int APP_PORT = 8888;
    public static final String APP_NODE = "QStates";
    public static final int APP_NODE_PORT = 8888;
    public static long AUTOUPDATE_REFRESHTIME = 10;

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
    public static final String PAGE_HOME = "/" + APP + "/home.jsp";
    public static final String PAGE_REPORT = "/" + APP + "/report.jsp";
    public static final String PAGE_TASK = "/" + APP + "/setting/taches.jsp";
    public static final String PAGE_GBL_REPORT = "/" + APP + "/report/gbl.jsp";
    public static final String PAGE_EMP_REPORT = "/" + APP + "/report/emp.jsp";
    public static final String PAGE_EMPSER_REPORT = "/" + APP + "/report/empser.jsp";
    public static final String PAGE_GCH_REPORT = "/" + APP + "/report/gch.jsp";
    public static final String PAGE_GCHSER_REPORT = "/" + APP + "/report/gchser.jsp";
    public static final String PAGE_GLA_REPORT = "/" + APP + "/report/gla.jsp";
    public static final String PAGE_GLT_REPORT = "/" + APP + "/report/glt.jsp";
    public static final String PAGE_TCH_REPORT = "/" + APP + "/report/tch.jsp";

    //API URL
    public static String API_GBL_TABLE_JSON = APP_NODE + "/getgbltable";
    public static String API_EMP_TABLE_JSON = APP_NODE + "/getemptable";
    public static String API_EMPSER_TABLE_JSON = APP_NODE + "/getempsertable";
    public static String API_GCH_TABLE_JSON = APP_NODE + "/getgchtable";
    public static String API_GCHSER_TABLE_JSON = APP_NODE + "/getgchsertable";
    public static String API_GLA_TABLE_JSON = APP_NODE + "/getglatable";
    public static String API_GLT_TABLE_JSON = APP_NODE + "/getglttable";
    public static String API_TCH_TABLE_JSON = APP_NODE + "/gettchtable";
    public static String API_CHECK_STATUS = APP_NODE + "/onlinecheck";
    //Pars
    private Properties prop = null;
    private String url;
    private String appPath;
    private HttpServletRequest request;
    private OutputStream output;
    private FileReader FR = null;

    public final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static String getFormatedDateAsString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            return format.format(date);
        } else {
            return null;
        }

    }

    public static Date getFormatedDateAsDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            try {
                return format.parse(date);
            } catch (ParseException ex) {
                Logger.getLogger(CfgHandler.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getFormatedTimeFromSeconds(Double Sec) {
        if (Sec == null) {
            Sec = Double.valueOf("0");
        }
        int hours = (int) (Sec / 3600);
        int minutes = (int) ((Sec % 3600) / 60);
        int seconds = (int) (Sec % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public CfgHandler(HttpServletRequest r) throws FileNotFoundException, IOException {
        this.request = r;
    }

    public static String prepareTableJsonUrl(String host, int port, String apiPoint, String date1, String date2) {
        String url = "http://" + host
                + ":" + port
                + "/" + apiPoint
                + "?date1=" + date1
                + "&date2=" + date2;
        return url;
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
        String path = getCibleFile();
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
        String path = getCibleFile();
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
        return request.getServletContext().getRealPath(dbFile);
    }

    public String getTitleFile() {
        return request.getServletContext().getRealPath(titleFile);
    }

}
