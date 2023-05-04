package main.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.handler.TitleHandler;
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
                    String gchserv = request.getParameter("gchserv");
                    String gla = request.getParameter("gla");
                    String glt = request.getParameter("glt");
                    String apl = request.getParameter("apl");
                    String ndt = request.getParameter("ndt");
                    String ndtt = request.getParameter("ndtt");
                    String ndta = request.getParameter("ndta");
                    String ndtsa = request.getParameter("ndtsa");
                    String cnx = request.getParameter("cnx");
                    String remp = request.getParameter("remp");
                    String sgch = request.getParameter("sgch");
                    String ser = request.getParameter("ser");
                    String tch = request.getParameter("tch");
                    if (StringUtils.isNoneBlank(gbl,emp,empser,gch,gchserv,gla,glt,apl,ndt,ndtt,ndta,ndtsa,cnx,remp,sgch,ser,tch)) {
                        TitleHandler th = new TitleHandler(request);
                        th.setTitle("gbl", gbl);
                        th.setTitle("emp", emp);
                        th.setTitle("empser", empser);
                        th.setTitle("gch", gch);
                        th.setTitle("gchserv", gchserv);
                        th.setTitle("gla", gla);
                        th.setTitle("glt", glt);
                        th.setTitle("apl", apl);
                        th.setTitle("ndt", ndt);
                        th.setTitle("ndtt", ndtt);
                        th.setTitle("ndta", ndta);
                        th.setTitle("ndtsa", ndtsa);
                        th.setTitle("cnx", cnx);
                        th.setTitle("remp", remp);
                        th.setTitle("sgch", sgch);
                        th.setTitle("ser", ser);
                        th.setTitle("tch", tch);
                        response.sendRedirect("/QData/setting/titles.jsp?err="+URLEncoder.encode("Modifié", "UTF-8"));
                    } else {
                        response.sendRedirect("/QData/setting/titles.jsp?err="+URLEncoder.encode("une ou plusieurs entrées vides", "UTF-8"));
                    }

                }else{
                    response.sendRedirect("/QData/home.jsp");
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
