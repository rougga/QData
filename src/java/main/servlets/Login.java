package main.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import main.CfgHandler;
import main.PasswordAuthentication;
import main.PgConnection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author bouga
 */
public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String err = "";
            boolean isFound = false;
            boolean pwFalse = true;
            if (!(username == null) && !(password == null)) {
                String hashedPass;
                username = username.trim().toLowerCase();
                try {
                    CfgHandler cfg = new CfgHandler(request);
                    String path = cfg.getUserFile();
                    File xml = new File(path);
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(xml);
                    doc.getDocumentElement().normalize();
                    NodeList nList = doc.getElementsByTagName("user");
                    for (int i = 0; i < nList.getLength(); i++) {
                        Node nNode = nList.item(i);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element eElement = (Element) nNode;
                            if (username.equalsIgnoreCase(eElement.getElementsByTagName("username").item(0).getTextContent().trim())) {
                                isFound = true;
                                out.println("<br>USER FOUND<br>");
                                PasswordAuthentication pa = new PasswordAuthentication();
                                if (pa.authenticate(password.toCharArray(), eElement.getElementsByTagName("password").item(0).getTextContent())) {
                                    out.println("<br>PASS CORRECT<br>");
                                    request.getSession().setAttribute("user", username);
                                    request.getSession().setAttribute("grade", eElement.getElementsByTagName("grade").item(0).getTextContent());
                                    response.sendRedirect("./home.jsp");
                                } else {
                                    pwFalse = true;
                                }
                                break;
                            } else {
                                isFound = false;
                            }

                        }
                    }
                    if (!isFound) {
                        err = "1";
                        response.sendRedirect("/QData/index.jsp?err=" + err);
                    } else {
                        if (pwFalse) {
                            err = "2";
                            response.sendRedirect("/QData/index.jsp?err=" + err);
                        }
                    }

                    out.println("<br>" + err + "<br>");

                } catch (Exception e) {

                }

            } else {
                response.sendRedirect("/QData/index.jsp?err=" + "");
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
