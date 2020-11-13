<%@page import="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="main.TableGenerator"%>
<%@page import="org.w3c.dom.Element"%>
<%@page import="org.xml.sax.SAXException"%>
<%@page import="javax.xml.parsers.ParserConfigurationException"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="org.w3c.dom.Node"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="main.CfgHandler"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.Properties"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="main.PgConnection"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    TableGenerator gbl = new TableGenerator();
    String type = (request.getParameter("type") == null) ? "gbl" : request.getParameter("type").toLowerCase().trim();
    String Title = "";
    String date1 = (request.getParameter("date1") == null) ? gbl.getFormat().format(new Date()) : request.getParameter("date1");
    String date2 = (request.getParameter("date2") == null) ? gbl.getFormat().format(new Date()) : request.getParameter("date2");
    List<ArrayList<String>> table2;
    String[] cols;
    String toDay = gbl.getFormat().format(new Date());
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>OffReport</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="./img/favicon-32x32.png">
        <script src="js/jquery.js"></script>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/bootstrap.min.js"></script>
        <link href="css/navbar.css" rel="stylesheet" type="text/css"/> 
        <link href="css/body.css" rel="stylesheet" type="text/css"/>
        <script src="js/echarts-en.min.js"></script>
    </head>
    <body>
        <div class="container-xl">

            <div class="head">
                <%@include file="./addon/navbar.jsp" %>
                <script>
                    $("#home").removeClass("active");
                    $(".<%=type%>").addClass("active");
                </script>
                <%=type%>
            </div>
            <div class="body">
                <%                     if (request.getParameter("err") != "" && request.getParameter("err") != null) {

                %>
                <%= "<div class='alert alert-danger alert-dismissible fade show' role='alert'><b>"
                        + request.getParameter("err")
                        + "</b><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>"%>
                <%
                    }

                    Map data = gbl.getTable(request, request.getParameter("date1"), request.getParameter("date2"), request.getSession().getAttribute("db") + "", type);
                    table2 = (List) data.get("table");
                    Title = (String) data.get("title");
                    cols = (String[]) data.get("cols");


                %>


                <h2 class="text-center p-4"><%= Title%></h2>

                <%= data.get("top") %>
                <table class="table table-light table-bordered table-striped  table-responsive">
                    <a class="float-right btn btn-link text-white" id="plus">PLUS >></a>
                    <thead class="appColor ">
                        <tr class="">
                            <c:forEach var="col" items="<%=cols%>" varStatus="status">
                                <th class="col ${status.index} text-wrap text-center align-middle"><c:out value="${col}"/></th>
                                </c:forEach>
                        </tr>
                    </thead>
                    <tbody  class="font-weight-bold ">

                        <%
                            if (!table2.isEmpty() && table2!=null) {
                                for (int i = 0; i < table2.size(); i++) {
                        %>
                        <tr class="db1">
                            <th scope="row" class="text-center align-middle border-dark 0"><%= gbl.getDB()%></th>
                                <% for (int j = 0; j < table2.get(i).size(); j++) {%>
                            <td class="text-left border-dark <%= j + 1%>" class=""><%= table2.get(i).get(j)%></td>
                            <%}%>
                        </tr>
                        <%
                                }
                            }
                        %>


                    </tbody>
                </table>
                
            </div>
            <div class="footer">
                <%= data.get("bottom") %>
            </div>
        </div>
        <script>
            $(document).ready(function () {
                var colsToShow = {
                    gbl: ".0,.1,.2,.3,.4,.10,.13",
                    emp: ".0,.1,.2,.3,.4,.10,.13",
                    empser: ".0,.1,.2,.3,.4,.5,.11,.14",
                    gch: ".0,.1,.2,.3,.4,.10,.13",
                    gchser: ".0,.1,.2,.3,.4,.5,.11,.14",
                    ndt: ".0,.8,.9,.10,.11,.12,.13,.14,.15,.16,.17,.18",
                    cnx: ".0,.1,.2,.3,.4,.5",
                    remp: ".0,.1,.2,.10,.11,.12,.13,.14,.15",
                    ser: ".0,.1,.2,.3,.4,.5,.6,.7,.8",
                    sgch: ".0,.1,.2,.3,.4",
                    apl: ".0,.1,.2,.7,.8,.9,.10,.11",
                    gla: ".0,.1,.8,.9,.10,.11,.12,.13,.14,.15",
                    glt: ".0,.1,.2,.3,.4,.5,.6,.7,.8,.15"
                };
                var colsToHide = {
                    glt: ".8",
                    gla: ".8"
                };
                var t;
                var showCols = function (type) {
                    t = type;

                    switch (type) {
                        case "gbl":
                            $(colsToShow.gbl).show();
                            break;
                        case "emp" :
                            $(colsToShow.emp).show();
                            break;
                        case "empser":
                            $(colsToShow.empser).show();
                            break;
                        case "gch":
                            $(colsToShow.gch).show();
                            break;
                        case "gchserv":
                            $(colsToShow.gchser).show();
                            break;
                        case "ndt":
                            $(colsToShow.ndt).show();
                            break;
                        case "ndtt":
                            $(colsToShow.ndt).show();
                            break;
                        case "ndta":
                            $(colsToShow.ndt).show();
                            break;
                        case "ndtsa":
                            $(colsToShow.ndt).show();
                            break;
                        case "cnx":
                            $(colsToShow.cnx).show();
                            break;
                        case "remp":
                            $(colsToShow.remp).show();
                            break;
                        case "ser":
                            $(colsToShow.ser).show();
                            break;
                        case "sgch":
                            $(colsToShow.sgch).show();
                            break;
                        case "apl":
                            $(colsToShow.apl).show();
                            break;
                        case "gla":
                            $(colsToShow.gla).show();
                            break;
                        case "glt":
                            $(colsToShow.glt).show();
                            break;
                        default :
                            $(colsToShow.gbl).show();
                            break;
                    }
                    ;

                    $(".db1 .0").hide();
                    $(".db1 th:first").show().attr("rowspan", "20000");
                };
                var hideCols = function (type) {
                    t = type;
                    switch (type) {
                        case "glt":
                            $(colsToHide.glt).hide();
                            break;
                        case "gla":
                            $(colsToHide.gla).hide();
                            break;
                    }

                };
                $(".db1 td,thead tr th").hide();


                $(".db1:last").addClass("bg-warning");
                $("#excel").on('click', function () {
                    $("#format").val("excel");
                    $("#printForm").submit();
                });
                $("#word").on('click', function () {
                    $("#format").val("word");
                    $("#printForm").submit();
                });
                $("#pdf").on('click', function () {
                    $("#format").val("pdf");
                    $("#printForm").submit();
                });
                $("#plus").on('click', function () {
                    if ($(this).text() === "PLUS >>") {
                        $(".db1,thead tr th,.db1 tr,.db1 td").show();
                        $(this).text("MOIN <<");
                        $(".db1 th").hide();
                        hideCols(t);
                        $(".db1 th:first").show().attr("rowspan", "2000000");

                    } else {
                        $(".db1 td,thead tr th").hide();
                        showCols(t);
                        $(".db1 th").hide();
                        $(".db1 th:first").show().attr("rowspan", "2000000");
                        $(this).text("PLUS >>");
                    }
                });
                $("#date1").on('change', function () {
                    if ($(this).val() > $("#date2").val()) {
                        alert("La date Du est plus gros que la date Au.");
                        $("#date2").val(new Date().toLocaleDateString('en-CA'));
                    }
                });
                $("#date2").on('change', function () {
                    if ($("#date1").val() > $("#date2").val()) {
                        alert("La date Du est plus gros que la date Au.");
                        $("#date2").val(new Date().toLocaleDateString('en-CA'));
                    }
                });
                showCols("<%= type%>");
            });

        </script>
    </body>
</html>
