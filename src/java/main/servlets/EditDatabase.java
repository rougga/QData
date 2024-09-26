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
import main.modal.Agence;
import org.apache.commons.lang3.StringUtils;

public class EditDatabase extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                    String zoneId = request.getParameter("zone");
                    String id_agence = request.getParameter("id_agence");
                    int status = 1;
                    if (StringUtils.isNoneBlank(name, host, port, database, username, password, id_agence)) {
                        Agence a = new Agence(UUID.fromString(id_agence), name, host, Integer.parseInt(port), database, username, password, status);
                        AgenceController ac = new AgenceController();
                        if (ac.editAgence(a)) {
                            if (StringUtils.isNotBlank(zoneId)) {
                                ac.editZone(a.getId(), UUID.fromString(zoneId));
                            }else{
                                ac.editZone(a.getId(), null);
                            }
                            response.sendRedirect("./setting/agences.jsp?err=" + URLEncoder.encode("la base de données est modifié", "UTF-8"));
                        } else {
                            response.sendRedirect("./setting/agences.jsp?err=" + URLEncoder.encode("la base de données n'est pas modifié", "UTF-8"));
                        }
                    } else {
                        response.sendRedirect("./setting/agences.jsp?err=" + URLEncoder.encode("un champ est vide", "UTF-8"));
                    }
                } else {
                    response.sendRedirect("./home.jsp?err=" + URLEncoder.encode("vous avez besoin des privilèges d'administrateur", "UTF-8"));
                }
            }

        }

    }
}
