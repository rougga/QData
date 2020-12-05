
package main.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.Updater;
import main.controller.LoginLogController;

public class UpdateAllLoginLog extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new LoginLogController().updateAllLoginLogs();
        resp.sendRedirect("/QData/setting/maj.jsp?err=LL%20Updated");
    }
    
}
