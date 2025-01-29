package ma.rougga.qdata.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qdata.PasswordAuthentication;
import ma.rougga.qdata.controller.UtilisateurController;
import ma.rougga.qdata.modal.Utilisateur;
import org.apache.commons.lang3.StringUtils;

public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String err = "";
            boolean isFound = false;
            boolean pwFalse = true;
            PasswordAuthentication pa = new PasswordAuthentication();
            UtilisateurController uc = new UtilisateurController();
            if (StringUtils.isNoneBlank(username, password)) {
                String hashedPass;
                username = username.trim().toLowerCase();
                Utilisateur u = uc.getUtilisateurByUsername(username);
                if (u != null) {
                    isFound = true;
                    if (pa.authenticate(password.toCharArray(), u.getPassword())) {
                        request.getSession().setAttribute("user", username);
                        request.getSession().setAttribute("grade", u.getGrade());
                        response.sendRedirect("./home.jsp");
                        pwFalse = false;
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
