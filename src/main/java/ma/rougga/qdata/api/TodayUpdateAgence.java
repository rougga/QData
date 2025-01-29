package ma.rougga.qdata.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qdata.Updater;
import ma.rougga.qdata.controller.AgenceController;
import ma.rougga.qdata.modal.Agence;
import org.apache.commons.lang3.StringUtils;

public class TodayUpdateAgence extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if(!StringUtils.isBlank(id)){
            new Updater().updateDatabaseById(UUID.fromString(id));
            resp.sendRedirect("/QData/setting/agences.jsp?err="+URLEncoder.encode(new AgenceController().getAgenceById(UUID.fromString(id)).getName(), "UTF-8")+"%20Updated");
        }else{
            resp.sendRedirect("/QData/setting/agences.jsp?err="+URLEncoder.encode("Erreur sur l'identifiant", "UTF-8"));
        }
    }
    
}
