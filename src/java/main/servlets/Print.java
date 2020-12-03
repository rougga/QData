package main.servlets;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.Export;
import main.controller.AgenceController;

public class Print extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            if (Objects.equals(request.getSession().getAttribute("user"), 0)) {
                response.sendRedirect("./index.jsp");
            } else {
                String type = request.getParameter("type").trim();
                String format = request.getParameter("format").trim().toLowerCase();
                String date1 = request.getParameter("date1").trim();
                String date2 = request.getParameter("date2").trim();
                String[] dbs = request.getParameterValues("agences");
                if (Objects.equals(type, "gbl")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportGblExcel(response, request, date1, date2,dbs) == 0) {
                            response.sendRedirect("./report.jsp?err=Table%20vide");
                        }
                    }//GBL XL

                    if (Objects.equals(format, "pdf")) {

                    }//GBL PDF
                }//GBL

                if (Objects.equals(type, "emp") || Objects.equals(type, "remp")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportEmpExcel(response, request, date1, date2,dbs) == 0) {
                            response.sendRedirect("./report.jsp?err=Table%20vide");
                        }
                    }//EMP XL

                    if (Objects.equals(format, "pdf")) {

                    }//EMP PDF
                }//EMP
                if (Objects.equals(type, "empser")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportEmpServiceExcel(response, request, date1, date2,dbs) == 0) {
                            response.sendRedirect("./report.jsp?err=Table%20vide");
                        }
                    }//EMP XL

                    if (Objects.equals(format, "pdf")) {

                    }//EMP PDF
                }//EMPSER
                if (Objects.equals(type, "gch")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportGchExcel(response, request, date1, date2,dbs) == 0) {
                            response.sendRedirect("./report.jsp?err=Table%20vide");
                        }
                    }//GCH XL

                    if (Objects.equals(format, "pdf")) {

                    }//GCH PDF
                }//GCH
                if (Objects.equals(type, "gchserv")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportGchServiceExcel(response, request, date1, date2,dbs) == 0) {
                            response.sendRedirect("./report.jsp?err=Table%20vide");
                        }
                    }//EMP XL

                    if (Objects.equals(format, "pdf")) {

                    }//EMP PDF
                }//GCHSER

                if (Objects.equals(type, "ndt")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportNdtExcel(response, request, date1, date2,dbs) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&err=erreur%20dans%20impression");
                        }
                    }//NDT XL

                    if (Objects.equals(format, "pdf")) {

                    }//NDT PDF

                }//NDT

                if (Objects.equals(type, "ndtt")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportNdttExcel(response, request, date1, date2,dbs) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&err=erreur%20dans%20impression");
                        }
                    }//NDTT XL

                    if (Objects.equals(format, "pdf")) {

                    }//NDTT PDF

                }//NDTT

                if (Objects.equals(type, "ndta")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportNdtaExcel(response, request, date1, date2,dbs) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&err=erreur%20dans%20impression");
                        }
                    }//NDTA XL

                    if (Objects.equals(format, "pdf")) {

                    }//NDTA PDF

                }//NDTA
                if (Objects.equals(type, "ndtsa")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportNdtsaExcel(response, request, date1, date2,dbs) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&err=erreur%20dans%20impression");
                        }
                    }//NDTSA XL

                    if (Objects.equals(format, "pdf")) {

                    }//NDTSA PDF

                }//NDTSA
                if (Objects.equals(type, "gla")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportGlaExcel(response, request, date1, date2,dbs) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&err=erreur%20dans%20impression");
                        }
                    }//NDTSA XL

                    if (Objects.equals(format, "pdf")) {

                    }//NDTSA PDF

                }//GLA

                if (Objects.equals(type, "glt")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportGltExcel(response, request, date1, date2,dbs) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&err=erreur%20dans%20impression");
                        }
                    }//NDTSA XL

                    if (Objects.equals(format, "pdf")) {

                    }//NDTSA PDF

                }//GLT

                response.sendRedirect("./report.jsp?type=" + type + "&err=Type%20inconnue");
            }
        } catch (Exception e) {
            response.sendRedirect("./report.jsp?err=" + e.getMessage());
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, e.getMessage(), e);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, "", ex);
        }

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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, "", ex);
        }
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
