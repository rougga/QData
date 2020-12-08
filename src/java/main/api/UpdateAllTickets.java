
package main.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.Updater;
import main.controller.TicketController;

public class UpdateAllTickets extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new TicketController().updateAllTickets();
        resp.sendRedirect("/QData/setting/maj.jsp?err=Tickets%20Updated");
    }
    
}
