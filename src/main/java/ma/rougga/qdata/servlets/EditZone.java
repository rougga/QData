package ma.rougga.qdata.servlets;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qdata.controller.ZoneController;
import ma.rougga.qdata.modal.Zone;
import org.apache.commons.lang3.StringUtils;


public class EditZone extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        ZoneController zc = new ZoneController();
        if (Objects.equals(request.getSession().getAttribute("user"), null)) {
                response.sendRedirect("./index.jsp");
            } else {
                if (Objects.equals(request.getSession().getAttribute("grade"), "adm")) {
                    request.setCharacterEncoding("UTF-8");
                    String id_zone = request.getParameter("id_zone");
                    String name = request.getParameter("name");
                    String city = request.getParameter("city");
                    String code = request.getParameter("code");
                    if (StringUtils.isNoneBlank(name,city,code,id_zone)) {
                        Zone z = new Zone(UUID.fromString(id_zone),name, city, code);
                        if (zc.editZone(z)) {
                            response.sendRedirect("./setting/zones.jsp?err=" + URLEncoder.encode("la zone est Modifié", "UTF-8"));
                        } else {
                            response.sendRedirect("./setting/zones.jsp?err=" + URLEncoder.encode("la zone n'est pas Modifié", "UTF-8"));
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
