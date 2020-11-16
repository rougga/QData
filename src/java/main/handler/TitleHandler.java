package main.handler;

import java.util.ArrayList;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import main.CfgHandler;
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

    
    public String getGblTitle(){
        String title =getTitle("gbl");
        if(title==null){
            return String.valueOf("Rapport Globale");
        }else{
            return title;
        }
    }
    
    
    public void setTitle(String table, String title) {

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
            }else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
