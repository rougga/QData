package ma.rougga.qdata.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qdata.PasswordAuthentication;
import ma.rougga.qdata.controller.UtilisateurController;
import ma.rougga.qdata.modal.Utilisateur;
import org.apache.commons.lang3.StringUtils;

public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String err = "";
            PasswordAuthentication pa = new PasswordAuthentication();
            UtilisateurController uc = new UtilisateurController();
            if (StringUtils.isNoneBlank(username, password)) {
                username = username.trim().toLowerCase();
                Utilisateur u = uc.getUtilisateurByUsername(username);
                if (u == null) {
                    response.sendRedirect("/QData/index.jsp?err=" + URLEncoder.encode("1", "UTF-8"));
                    return;
                }
                if (pa.authenticate(password.toCharArray(), u.getPassword())) {
                    request.getSession().setAttribute("user", username);
                    request.getSession().setAttribute("grade", u.getGrade());
                    response.sendRedirect("./home.jsp");
                    return;
                } else {
                    err = "2";
                    response.sendRedirect("/QData/index.jsp?err=" + err);
                    return;
                }

            } else {
                response.sendRedirect("/QData/index.jsp?err=" + URLEncoder.encode("un champ est vide", "UTF-8"));
                return;
            }
        } catch (Exception e) {
            response.sendRedirect("/QData/index.jsp?err=" + URLEncoder.encode("Erreur! ", "UTF-8") + e.getMessage());
            return;
        }
    }

}
