package main.servlets;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.CfgHandler;
import main.PasswordAuthentication;
import main.controller.UtilisateurController;
import main.modal.Utilisateur;
import org.apache.commons.lang3.StringUtils;

public class AddUtilisateur extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        if (Objects.equals(request.getSession().getAttribute("user"), null)) {
            response.sendRedirect("./index.jsp");
        } else {
            if (Objects.equals(request.getSession().getAttribute("grade"), "adm")) {
                
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String password2 = request.getParameter("password2");
                String grade = request.getParameter("grade");
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String sponsor = request.getSession().getAttribute("user").toString();
                String id_zone = request.getParameter("zone");

                if (StringUtils.isNoneBlank(username, password, grade, firstName, lastName, sponsor, id_zone)) {
                    UtilisateurController uc = new UtilisateurController();
                    PasswordAuthentication pa = new PasswordAuthentication();
                    Utilisateur u = new Utilisateur(username,
                            pa.hash(password.toCharArray()),
                            grade,
                            firstName,
                            lastName,
                            CfgHandler.getFormatedDateAsDate(CfgHandler.getFormatedDateAsString(new Date())),
                            sponsor);

                    if (uc.AddUtilisateur(u)) {
                        uc.setZone(u.getId(), UUID.fromString(id_zone));
                        response.sendRedirect("./setting/users.jsp?err=" + URLEncoder.encode("L'utilisateur " + u.getUsername() + " est ajoutée", "UTF-8"));
                    } else {
                        response.sendRedirect("./setting/users.jsp?err=" + URLEncoder.encode("L'utilisateur n'est pas ajoutée", "UTF-8"));
                    }
                } else {
                    response.sendRedirect("./setting/users.jsp?err=" + URLEncoder.encode("un champ est vide", "UTF-8"));
                }
            } else {
                response.sendRedirect("./home.jsp?err=" + URLEncoder.encode("vous avez besoin des privilèges d'administrateur", "UTF-8"));
            }

        }
    }

}
