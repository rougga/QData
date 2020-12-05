package main.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.controller.AgenceController;
import main.modal.Agence;

public class AddDatabase extends HttpServlet {

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
                    String host = request.getParameter("host");
                    String port = request.getParameter("port");
                    String database = request.getParameter("database");
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    int status = 1;
                    if (name != null && host != null && port != null && database != null && username != null && password != null) {
                        Agence a = new Agence(name, host, Integer.parseInt(port), database, username, password, status);
                        if (new AgenceController().addAgence(a) == 1) {
                            response.sendRedirect("./setting/agences.jsp?err=" + URLEncoder.encode("la base de données est ajoutée", "UTF-8"));
                        } else {
                            response.sendRedirect("./setting/agences.jsp?err=" + URLEncoder.encode("la base de données n'est pas ajoutée", "UTF-8"));
                        }
                    } else {
                        response.sendRedirect("./setting/agences.jsp?err=" + URLEncoder.encode("un champ est vide", "UTF-8"));
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
