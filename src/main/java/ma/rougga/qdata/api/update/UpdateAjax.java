
package ma.rougga.qdata.api.update;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qdata.controller.UpdateController;

public class UpdateAjax extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (Objects.equals(req.getSession().getAttribute("user"), null)) {
            resp.sendRedirect("./index.jsp");
        } else {
            new UpdateController().update();
            resp.getWriter().print("done");
        }
    }
}
