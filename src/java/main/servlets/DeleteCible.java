package main.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.controller.AgenceController;
import main.controller.CibleController;
import org.apache.commons.lang3.StringUtils;

public class DeleteCible extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (Objects.equals(request.getSession().getAttribute("user"), null)) {
                response.sendRedirect("./index.jsp");
            } else {
                if (Objects.equals(request.getSession().getAttribute("grade"), "adm")) {
                    String id = request.getParameter("id");
                    String db_id = request.getParameter("db_id");
                    if (StringUtils.isNoneBlank(id,db_id)) {
                        if (new CibleController().deleteById(id,UUID.fromString(db_id))) {
                            response.sendRedirect("./setting/cibles.jsp?err=" + URLEncoder.encode("Cible est supprimé", "UTF-8"));
                        } else {
                            response.sendRedirect("./setting/cibles.jsp?err=" + URLEncoder.encode("Cible n'est pas supprimé", "UTF-8"));
                        }
                    } else {
                        response.sendRedirect("./setting/cibles.jsp?err=" + URLEncoder.encode("un champ est vide", "UTF-8"));
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
