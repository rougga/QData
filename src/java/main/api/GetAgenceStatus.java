package main.api;

import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.controller.AgenceController;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

public class GetAgenceStatus extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) {

        try {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String id = req.getParameter("id");
            if (StringUtils.isBlank(id)) {
                JSONObject all = new JSONObject();
                all.put("error", "blank id");
                out.print(all);
            } else {
                boolean status= new AgenceController().isOnline(UUID.fromString(id));
                JSONObject all = new JSONObject();
                all.put("status", status);
                out.print(all);
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

}
