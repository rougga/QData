package ma.rougga.qdata.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ma.rougga.qdata.controller.ZoneController;
import ma.rougga.qdata.modal.Zone;
import org.apache.commons.lang3.StringUtils;

public class AddZone extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (Objects.equals(request.getSession().getAttribute("user"), null)) {
                response.sendRedirect("./index.jsp");
            } else {
                if (Objects.equals(request.getSession().getAttribute("grade"), "adm")) {
                    request.setCharacterEncoding("UTF-8");
                    String name = request.getParameter("name");
                    String city = request.getParameter("city");
                    String code = request.getParameter("code");
                    int status = 1;
                    if (StringUtils.isNoneBlank(name,city,code)) {
                        Zone z = new Zone(name, city, code);
                        if (new ZoneController().addZone(z)) {
                            response.sendRedirect("./setting/zones.jsp?err=" + URLEncoder.encode("la zone est ajoutée", "UTF-8"));
                        } else {
                            response.sendRedirect("./setting/zones.jsp?err=" + URLEncoder.encode("la zone n'est pas ajoutée", "UTF-8"));
                        }
                    } else {
                        response.sendRedirect("./setting/zone.jsp?err=" + URLEncoder.encode("un champ est vide", "UTF-8"));
                    }
                }else{
                    response.sendRedirect("./home.jsp?err=" + URLEncoder.encode("vous avez besoin des privilèges d'administrateur", "UTF-8"));
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
