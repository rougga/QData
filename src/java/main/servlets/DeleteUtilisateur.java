package main.servlets;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.controller.AgenceController;
import main.controller.UtilisateurController;
import org.apache.commons.lang3.StringUtils;

public class DeleteUtilisateur extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        if (Objects.equals(request.getSession().getAttribute("user"), null)) {
            response.sendRedirect("./index.jsp");
        } else {
            if (Objects.equals(request.getSession().getAttribute("grade"), "adm")) {
                UtilisateurController uc = new UtilisateurController(request);
                String id = request.getParameter("id");
                if (StringUtils.isNotBlank(id)) {
                    if (uc.deleteUtilisateurById(UUID.fromString(id))) {
                        response.sendRedirect("./setting/users.jsp?err=" + URLEncoder.encode("L'utilisateur est supprimé", "UTF-8"));
                    } else {
                        response.sendRedirect("./settings/users.jsp?err=" + URLEncoder.encode("L'utilisateur n'est pas supprimé", "UTF-8"));
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
