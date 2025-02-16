<%@page import="ma.rougga.qdata.modal.report.GchRow"%>
<%@page import="ma.rougga.qdata.controller.TitleController"%>
<%@page import="ma.rougga.qdata.modal.Title"%>
<%@page import="ma.rougga.qdata.controller.report.GchTableController"%>
<%@page import="java.util.Map"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ma.rougga.qdata.CfgHandler"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String type = "gch";
    String[] agences = request.getParameterValues("agences");
    Title Title = new TitleController().getTitleByType(type);
    String date1 = (request.getParameter("date1") == null) ? CfgHandler.format.format(new Date()) : request.getParameter("date1");
    String date2 = (request.getParameter("date2") == null) ? CfgHandler.format.format(new Date()) : request.getParameter("date2");
    List<Map> table = new GchTableController().getTableAsList(date1, date2, agences);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>QData - <%= Title.getValue()%></title>
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
                max-width: 40%;
            }
        </style>
    </head>
    <body>
        <div class=" bg-dark h-100 p-0">

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


                <h2 class="text-center p-4"><%= Title.getValue()%></h2>
                <div>
                    <%@include file="../addon/report/defaulthtml.jsp" %>
                </div>
                <div class="px-4">
                    <a class="float-right btn btn-link text-white" id="plus">PLUS >></a>
                    <div class="table-responsive">
                        <table class="table table-light table-bordered table-striped  ">
                            <thead class="appColor">
                                <tr class="">

                                    <th class="col 0 text-wrap text-center align-middle db"  style="">Site</th>

                                    <th class="col 1 text-center align-middle" style="">Guichet</th>

                                    <th class="col 2 text-wrap text-center align-middle" style="">Nb. Tickets</th>

                                    <th class="col 3 text-wrap text-center align-middle" style="">Nb. Traités</th>

                                    <th class="col 4 text-wrap text-center align-middle" style="">Nb. Absents</th>

                                    <th class="col 5 text-wrap text-center align-middle" style="">Nb. Traités &lt;1mn</th>

                                    <th class="col 6 text-wrap text-center align-middle" style="">Nb. Sans affectation</th>

                                    <th class="col 7 text-wrap text-center align-middle" style="">Absents/Nb. Tickets(%)</th>

                                    <th class="col 8 text-wrap text-center align-middle" style="">Traités&lt;1mn/Nb. Tickets(%)</th>

                                    <th class="col 9 text-wrap text-center align-middle" style="">Sans affect/Nb. Tickets(%)</th>

                                    <th class="col 10 text-wrap text-center align-middle" style="">Moyenne d'attente</th>

                                    <th class="col 11 text-wrap text-center align-middle" style="">&gt;Cible</th>

                                    <th class="col 12 text-wrap text-center align-middle" style="">%Cible</th>

                                    <th class="col 13 text-wrap text-center align-middle" style="">Moyenne Traitement</th>

                                    <th class="col 14 text-wrap text-center align-middle" style="">&gt;Cible</th>

                                    <th class="col 15 text-wrap text-center align-middle" style="">%Cible</th>

                                </tr>
                            </thead>
                            <tbody  class="font-weight-bold ">

                                <%    if (!table.isEmpty() && table != null) {

                                        for (Map agence : table) {
                                %>
                                <%
                                    Object empsObj = agence.get("emps");
                                    if (empsObj instanceof List<?>) {
                                        List<GchRow> emps = (ArrayList<GchRow>) empsObj;
                                        for (GchRow emp : emps) {
                                %>                     
                                <tr class="" data-id="<%= agence.get("id_agence")%>">
                                    <th scope="row" class="text-center text-wrap align-middle border-dark 0 db <%= agence.get("agence_name")%>" data-id="<%= agence.get("id_agence")%>"><%= agence.get("agence_name")%></th>

                                    <th class="col 1  text-center align-middle <%= emp.getGuichetName()%>" data-id="<%= emp.getGuichetId()%>" ><%= emp.getGuichetName()%></th>
                                    <th class="col 2 text-wrap text-center align-middle" ><%= emp.getNbT()%></th>

                                    <th class="col 3 text-wrap text-center align-middle" ><%= emp.getNbTt()%></th>

                                    <th class="col 4 text-wrap text-center align-middle" ><%= emp.getNbA()%></th>

                                    <th class="col 5 text-wrap text-center align-middle" ><%= emp.getNbTl1()%></th>

                                    <th class="col 6 text-wrap text-center align-middle" ><%= emp.getNbSa()%></th>

                                    <th class="col 7 text-wrap text-center align-middle" ><%= emp.getPerApT()%>%</th>

                                    <th class="col 8 text-wrap text-center align-middle" ><%= emp.getPerTl1Pt()%>%</th>

                                    <th class="col 9 text-wrap text-center align-middle" ><%= emp.getPerSaPt()%>%</th>

                                    <th class="col 10 text-wrap text-center align-middle" ><%= CfgHandler.getFormatedTimeFromSeconds(emp.getAvgSecA())%></th>

                                    <th class="col 11 text-wrap text-center align-middle" ><%= emp.getNbCa()%></th>

                                    <th class="col 12 text-wrap text-center align-middle" ><%= emp.getPerCapt()%>%</th>

                                    <th class="col 13 text-wrap text-center align-middle" ><%= CfgHandler.getFormatedTimeFromSeconds(emp.getAvgSecT())%></th>

                                    <th class="col 14 text-wrap text-center align-middle" ><%= emp.getNbCt()%></th>

                                    <th class="col 15 text-wrap text-center align-middle" ><%= emp.getPerCtPt()%>%</th>

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
            </div>
            <div class="footer">
                <div>
                    <div class='div-wrapper d-flex justify-content-center align-items-center p-2'>
                        <a type='button' class='btn btn-success m-2' id='excel' href="/<%=CfgHandler.APP%>/exportexcel?type=<%= type%>">
                            <img src='/<%=CfgHandler.APP%>/img/icon/excel.png'/>
                            Excel
                        </a>
                         <!-- comment 
                        <a type='button' class='btn btn-danger m-2' id='pdf' href="/<%=CfgHandler.APP%>/exportpdf?type=<%= type%>" >
                            <img src='/<%=CfgHandler.APP%>/img/icon/pdf.png'/>
                            PDF
                        </a>-->
                    </div>
                </div>
            </div>
        </div>

        <script>
            let type = "<%= type%>";
        </script>
    </body>
</html>
