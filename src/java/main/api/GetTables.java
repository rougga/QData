package main.api;

import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

public class GetTables extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) {

        try {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JSONObject obj = new JSONObject();
            JSONObject obj2 = new JSONObject();
            obj.put("name", "foo");
            obj2.put("name2", "foo2");
            obj.put("obj2", obj2);
            out.print(obj);
        } catch (Exception ex) {
            
        }

    }

}
