package ma.rougga.qdata.api.agence;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qdata.controller.AgenceController;
import ma.rougga.qdata.controller.UpdateController;
import org.apache.commons.lang3.StringUtils;

public class RestoreAgence extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id_agence");
        if (Objects.equals(req.getSession().getAttribute("user"), null)) {
            resp.sendRedirect("./index.jsp");
        } else if (StringUtils.isNoneBlank(id)) {
            if (new UpdateController().restoreAllAgenceDataById(UUID.fromString(id))) {
                resp.sendRedirect("/QData/setting/update.jsp?id_agence=" + id + "&err=" + URLEncoder.encode(new AgenceController().getAgenceById(UUID.fromString(id)).getName() + " Updated (Globale)", "UTF-8"));

            } else {
                resp.sendRedirect("/QData/setting/update.jsp?id_agence=" + id + "&err=" + URLEncoder.encode(new AgenceController().getAgenceById(UUID.fromString(id)).getName() + " Erreur lors de la restauration des données", "UTF-8"));

            }
        } else {
            resp.sendRedirect("/QData/setting/update.jsp?id_agence=" + id + "&err=" + URLEncoder.encode("Erreur sur l'identifiant", "UTF-8"));
        }
    }

}
