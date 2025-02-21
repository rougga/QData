package ma.rougga.qdata.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qdata.CfgHandler;
import ma.rougga.qdata.controller.AgenceController;
import ma.rougga.qdata.controller.report.GblTableController;

public class GblAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        GblTableController gbl = new GblTableController();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        AgenceController ac = new AgenceController();
        String[] agences = req.getParameterValues("agences");
        String date1 = (req.getParameter("date1") == null) ? CfgHandler.format.format(new Date()) : req.getParameter("date1");
        String date2 = (req.getParameter("date2") == null) ? CfgHandler.format.format(new Date()) : req.getParameter("date2");
        if (agences == null || agences.length == 0) {
            agences = ac.putAgencesToStringArray(ac.getAllAgence());
        }
        List<Map> table = gbl.getTableAsList(date1, date2, agences);
        if (table == null) {
            table = new ArrayList<>();
        }
        String json = objectMapper.writeValueAsString(table);
        resp.getWriter().println(json);
    }

}
