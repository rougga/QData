package ma.rougga.qdata.api.report;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;

public class ExportPdf extends HttpServlet {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExportPdf.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (Objects.equals(request.getSession().getAttribute("user"), null)) {
                response.sendRedirect("./index.jsp");
            } else {
                String type = request.getParameter("type").trim();
                String date1 = request.getParameter("date1").trim();
                String date2 = request.getParameter("date2").trim();
                String[] dbs = request.getParameterValues("agences");
                if (Objects.equals(type, "gbl")) {

//                    if (new ExportPdfController().exportGblPDF(response, request, date1, date2, dbs)) {
//                        response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=Erreur%20lors%20de%20limpression");
//                    }
                }//GBL

            }
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

}
