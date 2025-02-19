package ma.rougga.qdata.api.report;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qdata.controller.AgenceController;
import ma.rougga.qdata.controller.ExportExcelController;
import org.slf4j.LoggerFactory;

public class ExportExcel extends HttpServlet {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExportExcel.class);

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
                    if (new ExportExcelController().exportGblExcel(response, request, date1, date2, dbs)) {
                        response.sendRedirect("./gbl.jsp?date1=" + date1 + "&date2=" + date2 + AgenceController.getAgencesURLFromStringArray(dbs) + "&err=Erreur%20lors%20de%20limpression");
                    }
                }//GBL
                else if (Objects.equals(type, "emp")) {
                    if (new ExportExcelController().exportEmpExcel(response, request, date1, date2, dbs)) {
                        response.sendRedirect("./emp.jsp?date1=" + date1 + "&date2=" + date2 + AgenceController.getAgencesURLFromStringArray(dbs) + "&err=Erreur%20lors%20de%20limpression");
                    }
                } else if (Objects.equals(type, "empser")) {
                    if (new ExportExcelController().exportEmpSerExcel(response, request, date1, date2, dbs)) {
                        response.sendRedirect("./empser.jsp?date1=" + date1 + "&date2=" + date2 + AgenceController.getAgencesURLFromStringArray(dbs) + "&err=Erreur%20lors%20de%20limpression");
                    }
                } else if (Objects.equals(type, "gch")) {
                    if (new ExportExcelController().exportGchExcel(response, request, date1, date2, dbs)) {
                        response.sendRedirect("./gch.jsp?date1=" + date1 + "&date2=" + date2 + AgenceController.getAgencesURLFromStringArray(dbs) + "&err=Erreur%20lors%20de%20limpression");
                    }
                } else if (Objects.equals(type, "gchser")) {
                    if (new ExportExcelController().exportGchSerExcel(response, request, date1, date2, dbs)) {
                        response.sendRedirect("./gchser.jsp?date1=" + date1 + "&date2=" + date2 + AgenceController.getAgencesURLFromStringArray(dbs) + "&err=Erreur%20lors%20de%20limpression");
                    }
                } else if (Objects.equals(type, "gla")) {
                    if (new ExportExcelController().exportGlaExcel(response, request, date1, date2, dbs)) {
                        response.sendRedirect("./gla.jsp?date1=" + date1 + "&date2=" + date2 + AgenceController.getAgencesURLFromStringArray(dbs) + "&err=Erreur%20lors%20de%20limpression");
                    }
                } else if (Objects.equals(type, "glt")) {
                    if (new ExportExcelController().exportGltExcel(response, request, date1, date2, dbs)) {
                        response.sendRedirect("./glt.jsp?date1=" + date1 + "&date2=" + date2 + AgenceController.getAgencesURLFromStringArray(dbs) + "&err=Erreur%20lors%20de%20limpression");
                    }
                } else if (Objects.equals(type, "tht")) {
                    if (new ExportExcelController().exportThTExcel(response, request, date1, date2, dbs)) {
                        response.sendRedirect("./tht.jsp?date1=" + date1 + "&date2=" + date2 + AgenceController.getAgencesURLFromStringArray(dbs) + "&err=Erreur%20lors%20de%20limpression");
                    }
                } else if (Objects.equals(type, "thtt")) {
                    if (new ExportExcelController().exportThTTExcel(response, request, date1, date2, dbs)) {
                        response.sendRedirect("./thtt.jsp?date1=" + date1 + "&date2=" + date2 + AgenceController.getAgencesURLFromStringArray(dbs) + "&err=Erreur%20lors%20de%20limpression");
                    }
                } else if (Objects.equals(type, "tha")) {
                    if (new ExportExcelController().exportThAExcel(response, request, date1, date2, dbs)) {
                        response.sendRedirect("./tha.jsp?date1=" + date1 + "&date2=" + date2 + AgenceController.getAgencesURLFromStringArray(dbs) + "&err=Erreur%20lors%20de%20limpression");
                    }
                } else if (Objects.equals(type, "thsa")) {
                    if (new ExportExcelController().exportThSAExcel(response, request, date1, date2, dbs)) {
                        response.sendRedirect("./thsa.jsp?date1=" + date1 + "&date2=" + date2 + AgenceController.getAgencesURLFromStringArray(dbs) + "&err=Erreur%20lors%20de%20limpression");
                    }
                } else {
                    response.sendRedirect("./home.jsp?date1=" + date1 + "&date2=" + date2 + AgenceController.getAgencesURLFromStringArray(dbs) + "&err=Erreur%20lors%20de%20limpression");
                }

            }
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

}
