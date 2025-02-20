package ma.rougga.qdata.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.rougga.qdata.controller.ZoneController;
import ma.rougga.qdata.modal.Zone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ZoneAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ZoneController zoneController = new ZoneController();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Zone> zones = zoneController.getAllZones();
        String json = objectMapper.writeValueAsString(zones);
        resp.getWriter().println(json);
    }
}
