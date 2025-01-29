
package main.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.controller.AgenceController;
import main.controller.UpdateController;
import org.apache.commons.lang3.StringUtils;

public class UpdateAgencesAllData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id_agence");
        if(StringUtils.isNoneBlank(id)){
            //new Updater().restoreAllAgenceDataById(UUID.fromString(id));
            new UpdateController().restoreAllAgenceDataById(UUID.fromString(id));
            resp.sendRedirect("/QData/setting/update.jsp?id_agence="+id+"&err="+URLEncoder.encode(new AgenceController().getAgenceById(UUID.fromString(id)).getName() + " Updated (Globale)", "UTF-8"));
        }else{
            resp.sendRedirect("/QData/setting/update.jsp?id_agence="+id+"&err="+URLEncoder.encode("Erreur sur l'identifiant", "UTF-8"));
        }
    }
   
   
}
