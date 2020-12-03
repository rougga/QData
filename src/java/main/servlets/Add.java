package main.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;
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
import main.PasswordAuthentication;
import main.PgConnection;
import main.controller.CibleController;
import main.modal.Cible;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Add extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (Objects.equals(request.getSession().getAttribute("user"), null)) {
                response.sendRedirect("./index.jsp");
            } else {
                if (Objects.equals(request.getSession().getAttribute("grade"), "adm")) {
                    String type = request.getParameter("type").trim();

                    if (Objects.equals(type, "cible")) {
                        String db_id = request.getParameter("site");
                        String id = request.getParameter("service");
                        String cibleAH = request.getParameter("cibleAH");
                        String cibleAM = request.getParameter("cibleAM");
                        String cibleAS = request.getParameter("cibleAS");
                        String cibleTH = request.getParameter("cibleTH");
                        String cibleTM = request.getParameter("cibleTM");
                        String cibleTS = request.getParameter("cibleTS");
                        String cibleD = request.getParameter("cibleD");
                        if (StringUtils.isNoneBlank(db_id, id, cibleAH, cibleAM, cibleAS, cibleTH, cibleTM, cibleTS, cibleD)) {

                            if (!Objects.equals(id, "0")) {
                                double cibleA = (Integer.parseInt(cibleAH) * 3600) + (Integer.parseInt(cibleAM) * 60) + Integer.parseInt(cibleAS);
                                double cibleT = (Integer.parseInt(cibleTH) * 3600) + (Integer.parseInt(cibleTM) * 60) + Integer.parseInt(cibleTS);
                                float dCible = Float.parseFloat(cibleD);
                                try {
                                    CibleController cc = new CibleController();
                                    if (cc.isUnique(id, db_id)) {
                                        Cible c = new Cible(id, UUID.fromString(db_id), cibleA, cibleT, dCible);
                                        cc.addCible(c);
                                        cc.addCibleXml(c);
                                        response.sendRedirect("./setting/cibles.jsp?err=Cible%20ajoute.");
                                    } else {
                                        response.sendRedirect("./setting/cibles.jsp?err=" + URLEncoder.encode("Le cible existe déjà", "UTF-8"));
                                    }

                                } catch (Exception e) {
                                    response.sendRedirect("./setting/cibles.jsp?err=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
                                }

                            }

                        }

                    }
                    if (Objects.equals(type, "user")) {
                        String username = request.getParameter("username");
                        String password = request.getParameter("password");
                        String password2 = request.getParameter("password2");
                        String grade = request.getParameter("grade");
                        String firstName = request.getParameter("firstName");
                        String lastName = request.getParameter("lastName");
                        if (Objects.equals(password, password2)) {
                            try {
                                CfgHandler cfg = new CfgHandler(request);
                                String path = cfg.getUserFile();

                                Document doc = cfg.getXml(path);
                                Node users = doc.getFirstChild();

                                Element user = doc.createElement("user");
                                users.appendChild(user);

                                Element usernameE = doc.createElement("username");
                                usernameE.appendChild(doc.createTextNode(username));
                                user.appendChild(usernameE);

                                PasswordAuthentication pa = new PasswordAuthentication();
                                Element passwordE = doc.createElement("password");
                                passwordE.appendChild(doc.createTextNode(pa.hash(password.toCharArray())));
                                user.appendChild(passwordE);

                                Element gradeE = doc.createElement("grade");
                                gradeE.appendChild(doc.createTextNode(grade));
                                user.appendChild(gradeE);

                                Element firstNameE = doc.createElement("firstName");
                                firstNameE.appendChild(doc.createTextNode(firstName));
                                user.appendChild(firstNameE);

                                Element lastNameE = doc.createElement("lastName");
                                lastNameE.appendChild(doc.createTextNode(lastName));
                                user.appendChild(lastNameE);

                                Element dateAdded = doc.createElement("date");
                                dateAdded.appendChild(doc.createTextNode(new Date().toString()));
                                user.appendChild(dateAdded);

                                Element sponsor = doc.createElement("sponsor");
                                sponsor.appendChild(doc.createTextNode(request.getSession().getAttribute("user").toString()));
                                user.appendChild(sponsor);

                                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                                Transformer transformer = transformerFactory.newTransformer();
                                DOMSource source = new DOMSource(doc);
                                StreamResult result = new StreamResult(new File(path));
                                transformer.transform(source, result);
                                response.sendRedirect("./settings.jsp?err=Utilisateur%20ajoute.#userBtn");

                            } catch (IOException | ParserConfigurationException | DOMException | SAXException | TransformerException e) {
                                response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
                            }

                        } else {
                            response.sendRedirect("./settings.jsp?err=les%20mots%20de%20passe%20ne%20sont%20pas%20les%20memes.");
                        }

                    }
                    if (Objects.equals(type, "extra")) {
                        String id = request.getParameter("serviceNameExtra");
                        String extraH = request.getParameter("extraH");
                        String extraM = request.getParameter("extraM");
                        String extraS = request.getParameter("extraS");

                        if (id != null && extraH != null && extraM != null && extraS != null) {
                            if (!Objects.equals(id, "0")) {
                                int extra = (Integer.parseInt(extraH) * 3600) + (Integer.parseInt(extraM) * 60) + Integer.parseInt(extraS);
                                try {
                                    CfgHandler cfg = new CfgHandler(request);
                                    String path = cfg.getExtraFile();

                                    Document doc = cfg.getXml(path);
                                    Node extras = doc.getFirstChild();

                                    Element service = doc.createElement("service");
                                    extras.appendChild(service);

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

                                    Element extraE = doc.createElement("extra");
                                    extraE.appendChild(doc.createTextNode(extra + ""));
                                    service.appendChild(extraE);

                                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                                    Transformer transformer = transformerFactory.newTransformer();
                                    DOMSource source = new DOMSource(doc);
                                    StreamResult result = new StreamResult(new File(path));
                                    transformer.transform(source, result);
                                    response.sendRedirect("./settings.jsp?err=Extra%20ajoute.");

                                } catch (IOException | ClassNotFoundException | SQLException | ParserConfigurationException | DOMException | SAXException | TransformerException e) {
                                    response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
                                }

                            }

                        }

                    }
                    if (Objects.equals(type, "goal")) {
                        String maxA = request.getParameter("maxA");
                        String goalT = request.getParameter("goalT");
                        CfgHandler cfg = new CfgHandler(request);
                        Properties p = new Properties();
                        p.setProperty("maxA", maxA);
                        p.setProperty("goalT", goalT);
                        FileOutputStream f = new FileOutputStream(cfg.getCfgFile());
                        p.store(f, "daasdasd");
                        f.close();
                        p.clear();
                        response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("L'objectif est modifiée", "UTF-8"));
                    }
                } else {
                    response.sendRedirect("./home.jsp");
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
