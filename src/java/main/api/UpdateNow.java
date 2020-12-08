
package main.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.Updater;

public class UpdateNow extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new Updater().updateDatabase();
        resp.sendRedirect("/QData/setting/maj.jsp?err=Force%20Updated");
    }
    
}
