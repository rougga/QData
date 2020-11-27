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
    List<ArrayList> table2;
    String[] cols;
    String toDay = gbl.getFormat().format(new Date());
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>QData</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="./img/favicon-32x32.png">
        <script src="js/jquery.js"></script>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/bootstrap.bundle.min.js"></script>
        <link href="css/navbar.css" rel="stylesheet" type="text/css"/> 
        <link href="css/body.css" rel="stylesheet" type="text/css"/>
        <script src="js/echarts-en.min.js"></script>
        <script src="js/moment.min.js"></script>
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
                        <tr class="">
                            <th scope="row" class="text-center align-middle border-dark 0 db col" data-id="<%= table2.get(i).get(0)%>"><%= table2.get(i).get(2)%></th>
                            <td class="text-left border-dark 1 <%= table2.get(i).get(3)%>"><%= table2.get(i).get(3)%></td>
                            <% for (int j = 4; j < table2.get(i).size(); j++) {%>
                            <td class="text-left border-dark <%= j-2 %>" class=""><%= table2.get(i).get(j)%></td>
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
$(document).ready(function() {
    var colsToShow = {
        gbl: ".0,.1,.2,.3,.4,.10,.13",
        emp: ".0,.1,.2,.3,.4,.5,.9,.12",
        empser: ".0,.1,.2,.3,.4,.5,.11,.14",
        gch: ".0,.1,.2,.3,.4,.10,.13",
        gchser: ".0,.1,.2,.3,.4,.5,.11,.14",
        ndt: ".0,.1,.8,.9,.10,.11,.12,.13,.14,.15,.16,.17,.18",
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
    var rowSpan = function() {
    var elements = $('.db');
    var ids = [];
    for (var i = 0; i < elements.length; i++) {
        if (ids.indexOf($(elements[i]).attr("data-id")) === -1) {
            ids.push($(elements[i]).attr("data-id"));
        } else {
            $(elements[i]).hide();
        }
    }
    for (var i = 0; i < ids.length; i++) {
        $('.db[data-id=' + ids[i] + ']:first').attr("rowspan", $('.db[data-id=' + ids[i] + ']').length);
    }
    };
    
    
    $("td").hide();
    $("th").hide();
    $(".Sous-Totale").parent("tr").addClass("bg-warning");
    $(".Totale").parent("tr").addClass("appColor").addClass("text-white").removeClass("border-dark").children(".Totale").removeClass("border-dark");
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
            $(this).text("MOIN <<");
            $("td").show();
            $("th").show();
            rowSpan();
            hideCols(t);
        } else {
            $("td").hide();
            $("th").hide();
            showCols(t);
            rowSpan();
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
    $("#today").on('click', function () {
                    $("#date1").val(moment().format('YYYY-MM-DD'));
                    $("#date2").val(moment().format('YYYY-MM-DD'));
                });
                $("#yesterday").on('click', function () {
                    $("#date1").val(moment().subtract(1, 'days').format('YYYY-MM-DD'));
                    $("#date2").val(moment().subtract(1, 'days').format('YYYY-MM-DD'));
                });
                
                $("#cWeek").on('click', function () {
                    $("#date1").val(moment().startOf('week').format('YYYY-MM-DD'));
                    $("#date2").val(moment().endOf('week').format('YYYY-MM-DD'));
                });
                $("#lWeek").on('click', function () {
                    $("#date1").val(moment().subtract(1, 'week').startOf('week').format('YYYY-MM-DD'));
                    $("#date2").val(moment().subtract(1, 'week').endOf('week').format('YYYY-MM-DD'));
                });
                $("#cMonth").on('click', function () {
                    $("#date1").val(moment().startOf('month').format('YYYY-MM-DD'));
                    $("#date2").val(moment().endOf('month').format('YYYY-MM-DD'));
                });
                $("#lMonth").on('click', function () {
                    $("#date1").val(moment().subtract(1, 'month').startOf('month').format('YYYY-MM-DD'));
                    $("#date2").val(moment().subtract(1, 'month').endOf('month').format('YYYY-MM-DD'));
                });
                $("#cYear").on('click', function () {
                    $("#date1").val(moment().startOf('year').format('YYYY-MM-DD'));
                    $("#date2").val(moment().endOf('year').format('YYYY-MM-DD'));
                });
                $("#lYear").on('click', function () {
                    $("#date1").val(moment().subtract(1, 'year').startOf('year').format('YYYY-MM-DD'));
                    $("#date2").val(moment().subtract(1, 'year').endOf('year').format('YYYY-MM-DD'));
                });
   showCols("<%=type%>");
   rowSpan();
});
                
        </script>
    </body>
</html>
