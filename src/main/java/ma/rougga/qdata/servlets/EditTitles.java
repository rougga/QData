package ma.rougga.qdata.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qdata.CfgHandler;
import ma.rougga.qdata.controller.TitleController;
import ma.rougga.qdata.handler.TitleHandler;
import ma.rougga.qdata.modal.Title;
import org.apache.commons.lang3.StringUtils;

public class EditTitles extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            if (Objects.equals(request.getSession().getAttribute("user"), null)) {
                response.sendRedirect("./index.jsp");
            } else {
                if (Objects.equals(request.getSession().getAttribute("grade"), "adm")) {
                    request.setCharacterEncoding("UTF-8");
                    String gbl = request.getParameter("gbl");
                    String emp = request.getParameter("emp");
                    String empser = request.getParameter("empser");
                    String gch = request.getParameter("gch");
                    String gchser = request.getParameter("gchser");
                    String gla = request.getParameter("gla");
//                    String glt = request.getParameter("glt");
//                    String apl = request.getParameter("apl");
                    String ndt = request.getParameter("ndt");
                    String ndtt = request.getParameter("ndtt");
                    String ndta = request.getParameter("ndta");
                    String ndtsa = request.getParameter("ndtsa");
//                    String cnx = request.getParameter("cnx");
//                    String remp = request.getParameter("remp");
//                    String sgch = request.getParameter("sgch");
//                    String ser = request.getParameter("ser");
//                    String tch = request.getParameter("tch");
                    if (StringUtils.isNoneBlank(gbl, emp, empser, gch, gchser, gla,  ndt, ndtt, ndta, ndtsa)) {

                        TitleController tc = new TitleController();
                        Title t = new Title();
                        t = tc.getTitleByType("gbl");
                        t.setValue(gbl);
                        tc.updateTitle(t);
                        t = tc.getTitleByType("emp");
                        t.setValue(emp);
                        tc.updateTitle(t);
                        t = tc.getTitleByType("empser");
                        t.setValue(empser);
                        tc.updateTitle(t);
                        t = tc.getTitleByType("gch");
                        t.setValue(gch);
                        tc.updateTitle(t);
                        t = tc.getTitleByType("gchser");
                        t.setValue(gchser);
                        tc.updateTitle(t);
                        t = tc.getTitleByType("gla");
                        t.setValue(gla);
                        tc.updateTitle(t);
                        t = tc.getTitleByType("ndt");
                        t.setValue(ndt);
                        tc.updateTitle(t);
                        t = tc.getTitleByType("ndtt");
                        t.setValue(ndtt);
                        tc.updateTitle(t);
                        t = tc.getTitleByType("ndta");
                        t.setValue(ndta);
                        tc.updateTitle(t);
                        t = tc.getTitleByType("ndtsa");
                        t.setValue(ndtsa);
                        tc.updateTitle(t);
                        response.sendRedirect("/"+CfgHandler.APP+"/setting/titles.jsp?err=" + URLEncoder.encode("Modifié", "UTF-8"));
                    } else {
                        response.sendRedirect("/"+CfgHandler.APP+"/setting/titles.jsp?err=" + URLEncoder.encode("une ou plusieurs entrées vides", "UTF-8"));
                    }

                } else {
                    response.sendRedirect("/"+CfgHandler.APP+"/home.jsp");
                }
            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
