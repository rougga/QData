package ma.rougga.qdata.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qdata.controller.AgenceController;
import ma.rougga.qdata.controller.UpdateController;
import org.apache.commons.lang3.StringUtils;

public class UpdateAgencesTodayData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id_agence");
        if(StringUtils.isNoneBlank(id)){
            //new Updater().restoreAllAgenceDataById(UUID.fromString(id));
            new UpdateController().updateAgencesTodayData(UUID.fromString(id));
            resp.sendRedirect("/QData/setting/update.jsp?id_agence="+id+"&err="+URLEncoder.encode(new AgenceController().getAgenceById(UUID.fromString(id)).getName() + " Updated (today)", "UTF-8"));
        }else{
            resp.sendRedirect("/QData/setting/update.jsp?id_agence="+id+"&err="+URLEncoder.encode("Erreur sur l'identifiant", "UTF-8"));
        }
    }
   
}
