package main.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import main.CfgHandler;
import main.PgConnection;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Edit extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (Objects.equals(request.getSession().getAttribute("user"), null)) {
                response.sendRedirect("./index.jsp");
            } else {
                String type = request.getParameter("type").trim();
                if (Objects.equals(type, "cible")) {
                    String id = request.getParameter("service").trim();
                    String cibleAH = request.getParameter("cibleAH");
                    String cibleAM = request.getParameter("cibleAM");
                    String cibleAS = request.getParameter("cibleAS");
                    String cibleTH = request.getParameter("cibleTH");
                    String cibleTM = request.getParameter("cibleTM");
                    String cibleTS = request.getParameter("cibleTS");
                    String cibleD = request.getParameter("cibleD");
                    try {
                        CfgHandler cfg = new CfgHandler(request);
                       String path= cfg.getCibleFile();
                        Document doc = cfg.getXml(path);
                        Node cibles = doc.getFirstChild();
                        NodeList nList = cibles.getChildNodes();
                        for (int i = 0; i < nList.getLength(); i++) {
                            Node nNode = nList.item(i);
                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element eElement = (Element) nNode;
                                if (Objects.equals(eElement.getElementsByTagName("id").item(0).getTextContent(), id)) {
                                    cibles.removeChild(nNode);
                                }
                            }

                        }
                        out.println("Deleted");
                        int cibleA = (Integer.parseInt(cibleAH) * 3600) + (Integer.parseInt(cibleAM) * 60) + Integer.parseInt(cibleAS);
                        int cibleT = (Integer.parseInt(cibleTH) * 3600) + (Integer.parseInt(cibleTM) * 60) + Integer.parseInt(cibleTS);

                        Element service = doc.createElement("service");
                        cibles.appendChild(service);

                        Element idE = doc.createElement("id");
                        idE.appendChild(doc.createTextNode(id));
                        service.appendChild(idE);

                        Element nameE = doc.createElement("name");
                        ResultSet r = new PgConnection().getStatement().executeQuery("SELECT name FROM t_biz_type where id ='" + id + "';");
                        if (r.next()) {

                            nameE.appendChild(doc.createTextNode(r.getString("name")));

                        } else {
                            nameE.appendChild(doc.createTextNode("ERREUR"));
                        }
                        service.appendChild(nameE);

                        Element cibleAE = doc.createElement("cibleA");
                        cibleAE.appendChild(doc.createTextNode(cibleA + ""));
                        service.appendChild(cibleAE);

                        Element cibleTE = doc.createElement("cibleT");
                        cibleTE.appendChild(doc.createTextNode(cibleT + ""));
                        service.appendChild(cibleTE);

                        Element cibleDE = doc.createElement("dcible");
                        cibleDE.appendChild(doc.createTextNode(cibleD + ""));
                        service.appendChild(cibleDE);

                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File(path));
                        transformer.transform(source, result);
                        response.sendRedirect("./settings.jsp");
                        
                    } catch (IOException | ParserConfigurationException | TransformerException | DOMException | SAXException | ClassNotFoundException | SQLException  e) {
                        out.print(e.getMessage());
                    }

                }

            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
