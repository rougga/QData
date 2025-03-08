package ma.rougga.qdata.api;

import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qdata.controller.AgenceController;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

public class GetAgenceStatus extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) {

        try {
            PrintWriter out = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            req.setCharacterEncoding("UTF-8");
            String id = req.getParameter("id");
            if (StringUtils.isBlank(id)) {
                JSONObject all = new JSONObject();
                all.put("status", "false");
                all.put("error", "blank id");
                out.print(all);
            } else {
                AgenceController ac = new AgenceController();
//                boolean status = ac.isOnline(UUID.fromString(id));
                JSONObject agence = ac.getAgenceInfoJson(UUID.fromString(id));
//                JSONObject all = new JSONObject();
//                all.put("status", agence);
                out.print(agence);
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

}
