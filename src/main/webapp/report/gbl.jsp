<%@page import="ma.rougga.qdata.modal.GblRow"%>
<%@page import="ma.rougga.qdata.handler.TitleHandler"%>
<%@page import="ma.rougga.qdata.controller.report.GblTableController"%>
<%@page import="java.util.Map"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ma.rougga.qdata.TableGenerator"%>
<%@page import="org.w3c.dom.Element"%>
<%@page import="org.xml.sax.SAXException"%>
<%@page import="javax.xml.parsers.ParserConfigurationException"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="org.w3c.dom.Node"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="ma.rougga.qdata.CfgHandler"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.Properties"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="ma.rougga.qdata.PgConnection"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String type = "gbl";
    String[] agences = request.getParameterValues("agences");
    String Title = new TitleHandler(request).getTitle(type);
    String date1 = (request.getParameter("date1") == null) ? CfgHandler.format.format(new Date()) : request.getParameter("date1");
    String date2 = (request.getParameter("date2") == null) ? CfgHandler.format.format(new Date()) : request.getParameter("date2");
    List<Map> table = new GblTableController().getTableAsList(date1, date2, agences);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>QData - <%= Title%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="/<%= CfgHandler.APP%>/img/favicon-32x32.png">
        <script src="/<%= CfgHandler.APP%>/js/jquery.js"></script>
        <link href="/<%= CfgHandler.APP%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="/<%= CfgHandler.APP%>/js/bootstrap.bundle.min.js"></script>
        <link href="/<%= CfgHandler.APP%>/css/navbar.css" rel="stylesheet" type="text/css"/> 
        <link href="/<%= CfgHandler.APP%>/css/body.css" rel="stylesheet" type="text/css"/>
        <script src="/<%= CfgHandler.APP%>/js/echarts-en.min.js"></script>
        <script src="/<%= CfgHandler.APP%>/js/moment.min.js"></script>
        <script src="/<%= CfgHandler.APP%>/js/report.js"></script>
        <style>
            .db{
                max-width: 35%;
            }
        </style>
    </head>
    <body>
        <div class=" bg-dark container h-100 p-0">

            <div class="head">
                <%@include file="../addon/navbar.jsp" %>
                <script>
                    $("#home").removeClass("active");
                    $(".<%=type%>").addClass("active");
                </script>
            </div>
            <div class="body ">
                <%                     if (request.getParameter("err") != "" && request.getParameter("err") != null) {

                %>
                <%= "<div class='alert alert-danger alert-dismissible fade show' role='alert'><b>"
                        + request.getParameter("err")
                        + "</b><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>"%>
                <%
                    }
                %>


                <h2 class="text-center p-4"><%= Title%></h2>
                <div>
                    <%@include file="../addon/report/defaulthtml.jsp" %>
                </div>
                <div class="table-responsive">
                    <table class="table table-light table-bordered table-striped  ">
                        <a class="float-right btn btn-link text-white" id="plus">PLUS >></a>
                        <thead class="appColor">
                            <tr class="">
                                <th class="col 0 text-wrap text-center align-middle db" >Site</th>

                                <th class="col 1 text-wrap text-center align-middle" >Service</th>

                                <th class="col 2 text-wrap text-center align-middle" >Nb. Tickets</th>

                                <th class="col 3 text-wrap text-center align-middle" >Nb. Traités</th>

                                <th class="col 4 text-wrap text-center align-middle" >Nb. Absents</th>

                                <th class="col 5 text-wrap text-center align-middle" >Nb. Traités &lt;1mn</th>

                                <th class="col 6 text-wrap text-center align-middle" >Nb. Sans affectation</th>

                                <th class="col 7 text-wrap text-center align-middle" >Absents/Nb. Tickets(%)</th>

                                <th class="col 8 text-wrap text-center align-middle" >Traités&lt;1mn/Nb. Tickets(%)</th>

                                <th class="col 9 text-wrap text-center align-middle" >Sans affect/Nb. Tickets(%)</th>

                                <th class="col 10 text-wrap text-center align-middle" >Moyenne d'attente</th>

                                <th class="col 11 text-wrap text-center align-middle" >&gt;Cible</th>

                                <th class="col 12 text-wrap text-center align-middle" >%Cible</th>

                                <th class="col 13 text-wrap text-center align-middle" >Moyenne Traitement</th>

                                <th class="col 14 text-wrap text-center align-middle" >&gt;Cible</th>

                                <th class="col 15 text-wrap text-center align-middle" >%Cible</th>

                            </tr>
                        </thead>
                        <tbody  class="font-weight-bold ">

                            <%    if (!table.isEmpty() && table != null) {

                                    for (Map agence : table) {
                            %>
                            <%
                                Object servicesObj = agence.get("services");
                                if (servicesObj instanceof List<?>) {
                                    List<GblRow> services = (ArrayList<GblRow>) agence.get("services");
                                    for (GblRow service : services) {
                            %>                      <tr class="" data-id="<%= agence.get("id_agence")%>">
                                <th scope="row" class="text-center align-middle border-dark 0 db <%= agence.get("agence_name")%>" data-id="<%= agence.get("id_agence")%>"><%= agence.get("agence_name")%></th>

                                <th class="col 1 text-wrap text-center align-middle <%= service.getServiceName()%>" data-id="<%= service.getServiceName()%>"><%= service.getServiceName()%></th>

                                <th class="col 2 text-wrap text-center align-middle" ><%= service.getNbT()%></th>

                                <th class="col 3 text-wrap text-center align-middle" ><%= service.getNbTt()%></th>

                                <th class="col 4 text-wrap text-center align-middle" ><%= service.getNbA()%></th>

                                <th class="col 5 text-wrap text-center align-middle" ><%= service.getNbTl1()%></th>

                                <th class="col 6 text-wrap text-center align-middle" ><%= service.getNbSa()%></th>

                                <th class="col 7 text-wrap text-center align-middle" ><%= service.getPerApT()%>%</th>

                                <th class="col 8 text-wrap text-center align-middle" ><%= service.getPertl1Pt()%>%</th>

                                <th class="col 9 text-wrap text-center align-middle" ><%= service.getPerSaPt()%>%</th>

                                <th class="col 10 text-wrap text-center align-middle" ><%= CfgHandler.getFormatedTimeFromSeconds(service.getAvgSecA())%></th>

                                <th class="col 11 text-wrap text-center align-middle" ><%= service.getNbCa()%></th>

                                <th class="col 12 text-wrap text-center align-middle" ><%= service.getPercapt()%>%</th>

                                <th class="col 13 text-wrap text-center align-middle" ><%= CfgHandler.getFormatedTimeFromSeconds(service.getAvgSecT())%></th>

                                <th class="col 14 text-wrap text-center align-middle" ><%= service.getNbCt()%></th>

                                <th class="col 15 text-wrap text-center align-middle" ><%= service.getPerctPt()%>%</th>
                                    <%}
    }%>
                            </tr>
                            <%
                                    }
                                }
                            %>


                        </tbody>
                    </table>
                </div>
            </div>
            <div class="footer">

            </div>
        </div>
        <script>
            let type = "<%= type %>";
        </script>
    </body>
</html>
