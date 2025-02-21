
package ma.rougga.qdata.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qdata.controller.AgenceController;
import ma.rougga.qdata.modal.Agence;

public class AgencesAPI extends HttpServlet {

  @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AgenceController ac = new AgenceController();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Agence> agences = ac.getAllAgence();
        if(agences == null){
            agences = new ArrayList<>();
        }
        String json = objectMapper.writeValueAsString(agences);
        resp.getWriter().println(json);
    }
  
}
