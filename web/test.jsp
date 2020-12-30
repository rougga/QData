
<%@page import="main.PgConnection"%>
<%@page import="net.sf.jasperreports.engine.JasperRunManager"%>
<%@page import="java.util.Map"%>
<%@page import="java.io.File"%>
<%@page import="java.util.HashMap"%>
<%@page import="main.Export"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><%= application.getRealPath("report3.jasper") %></h1>
        <%
            
            out.print("Chemin  : "+application.getRealPath("report3.jasper"));
            File reportFile = new File(application.getRealPath("/cfg/pdf/gbl.jasper"));//your report_name.jasper file
            Map parameters = new HashMap();
            
            byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, new PgConnection().getStatement().getConnection());
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();
            
        %>
    </body>
</html>
