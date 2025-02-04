<%@page import="ma.rougga.qdata.modal.report.EmpRow"%>
<%@page import="ma.rougga.qdata.controller.TitleController"%>
<%@page import="ma.rougga.qdata.modal.Title"%>
<%@page import="ma.rougga.qdata.controller.report.EmpTableController"%>
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
    String type = "emp";
    String[] agences = request.getParameterValues("agences");
    Title Title = new TitleController().getTitleByType(type);
    String date1 = (request.getParameter("date1") == null) ? CfgHandler.format.format(new Date()) : request.getParameter("date1");
    String date2 = (request.getParameter("date2") == null) ? CfgHandler.format.format(new Date()) : request.getParameter("date2");
    List<Map> table = new EmpTableController().getTableAsList(date1, date2, agences);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>QData - <%= Title.getValue() %></title>
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


                <h2 class="text-center p-4"><%= Title.getValue() %></h2>
                <div>
                    <%@include file="../addon/report/defaulthtml.jsp" %>
                </div>
                <div class="table-responsive">
                    <table class="table table-light table-bordered table-striped  ">
                        <a class="float-right btn btn-link text-white" id="plus">PLUS >></a>
                        <thead class="appColor">
                            <tr class="">

                                <th class="col 0 text-wrap text-center align-middle db" >Site</th>

                                <th class="col 1 text-wrap text-center align-middle" >Employé</th>

                                <th class="col 2 text-wrap text-center align-middle" >Nb. Traités</th>

                                <th class="col 3 text-wrap text-center align-middle" >Nb. Absents</th>

                                <th class="col 4 text-wrap text-center align-middle" >Nb. Traités &lt;1mn</th>

                                <th class="col 5 text-wrap text-center align-middle" >Nb. Sans affectation</th>

                                <th class="col 6 text-wrap text-center align-middle" >Absents/Nb. Tickets(%)</th>

                                <th class="col 7 text-wrap text-center align-middle" >Traités&lt;1mn/Nb. Tickets(%)</th>

                                <th class="col 8 text-wrap text-center align-middle" >Sans affect/Nb. Tickets(%)</th>

                                <th class="col 9 text-wrap text-center align-middle" >Moyenne d'attente</th>

                                <th class="col 10 text-wrap text-center align-middle" >&gt;Cible</th>

                                <th class="col 11 text-wrap text-center align-middle" >%Cible</th>

                                <th class="col 12 text-wrap text-center align-middle" >Moyenne Traitement</th>

                                <th class="col 13 text-wrap text-center align-middle" >&gt;Cible</th>

                                <th class="col 14 text-wrap text-center align-middle" >%Cible</th>

                            </tr>
                        </thead>
                        <tbody  class="font-weight-bold ">

                            <%    if (!table.isEmpty() && table != null) {

                                    for (Map agence : table) {
                            %>
                            <%
                                Object empsObj = agence.get("emps");
                                if (empsObj instanceof List<?>) {
                                    List<EmpRow> emps = (ArrayList<EmpRow>) empsObj;
                                    for (EmpRow emp : emps) {
                            %>                     
                            <tr class="" data-id="<%= agence.get("id_agence")%>">
                                <th scope="row" class="text-center align-middle border-dark 0 db <%= agence.get("agence_name")%>" data-id="<%= agence.get("id_agence")%>"><%= agence.get("agence_name")%></th>

                                <th class="col 1 text-wrap text-center align-middle <%= emp.getUserName()%>" ><%= emp.getUserName()%></th>

                                <th class="col 2 text-wrap text-center align-middle" ><%= emp.getNbTt() %></th>

                                <th class="col 3 text-wrap text-center align-middle" ><%= emp.getNbA() %></th>

                                <th class="col 4 text-wrap text-center align-middle" ><%= emp.getNbTl1() %></th>

                                <th class="col 5 text-wrap text-center align-middle" ><%= emp.getNbSa() %></th>

                                <th class="col 6 text-wrap text-center align-middle" ><%= emp.getPerApT()%>%</th>

                                <th class="col 7 text-wrap text-center align-middle" ><%= emp.getPerTl1Pt()%>%</th>

                                <th class="col 8 text-wrap text-center align-middle" ><%= emp.getPerSaPt()%>%</th>

                                <th class="col 9 text-wrap text-center align-middle" ><%= CfgHandler.getFormatedTimeFromSeconds(emp.getAvgSecA())%></th>

                                <th class="col 10 text-wrap text-center align-middle" ><%= emp.getNbCa()%></th>

                                <th class="col 11 text-wrap text-center align-middle" ><%= emp.getPerCapt()%>%</th>

                                <th class="col 12 text-wrap text-center align-middle" ><%= CfgHandler.getFormatedTimeFromSeconds(emp.getAvgSecT())%></th>

                                <th class="col 13 text-wrap text-center align-middle" ><%= emp.getNbCt()%></th>

                                <th class="col 14 text-wrap text-center align-middle" ><%= emp.getPerCtPt()%>%</th>

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
