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
    //String[] agences = (String[])session.getAttribute("agences");
    String Title = "";
    String date1 = (request.getParameter("date1") == null) ? gbl.getFormat().format(new Date()) : request.getParameter("date1");
    String date2 = (request.getParameter("date2") == null) ? gbl.getFormat().format(new Date()) : request.getParameter("date2");
    List<ArrayList> table2;
    String[] cols ;
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
        <style>
        </style>
    </head>
    <body>
        <div class="container-xl bg-dark h-100 p-0">

            <div class="head">
                <%@include file="./addon/navbar.jsp" %>
                <script>
                    $("#home").removeClass("active");
                    $(".<%=type%>").addClass("active");
                </script>
            </div>
            <div class="body">
                <%                     if (request.getParameter("err") != "" && request.getParameter("err") != null) {

                %>
                <%= "<div class='alert alert-danger alert-dismissible fade show' role='alert'><b>"
                        + request.getParameter("err")
                        + "</b><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>"%>
                <%
                    }

                    Map data = gbl.getTable(request, request.getParameter("date1"), request.getParameter("date2"), request.getParameterValues("agences"), type);
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
        empser: ".0,.1,.2,.3,.4,.5,.10,.13",
        gch: ".0,.1,.2,.3,.4,.10,.13",
        gchser: ".0,.1,.2,.3,.4,.5,.11,.14",
        ndt: ".0,.1,.8,.9,.10,.11,.12,.13,.14,.15,.16,.17,.18",
        cnx: ".0,.1,.2,.3,.4,.5",
        remp: ".0,.1,.3,.10,.11,.12,.13,.14,.15",
        ser: ".0,.1,.2,.3,.4,.5,.6,.7,.8",
        sgch: ".0,.1,.2,.3,.4,.5,.6,.7",
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
    $("#refresh").on('click', function () {
         $("#filterForm").attr("action","");
         
         $("#filterForm").submit();
    });
        
    $("#excel").on('click', function () {
         $("#filterForm").attr("action","./Print");
         $("#format").val("excel");
         $("#filterForm").submit();
    });
    $("#word").on('click', function () {
    });
    $("#pdf").on('click', function () {
        $("#filterForm").attr("action","./Print");
        $("#format").val("pdf");
        $("#filterForm").submit();
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
        addDateToSessionStorage();
        updateLinks();
    });
    $("#date2").on('change', function () {
        if ($("#date1").val() > $("#date2").val()) {
            alert("La date Du est plus gros que la date Au.");
            $("#date2").val(new Date().toLocaleDateString('en-CA'));
        }
        addDateToSessionStorage();
        updateLinks();
    });
    $("#today").on('click', function () {
        $("#date1").val(moment().format('YYYY-MM-DD'));
        $("#date2").val(moment().format('YYYY-MM-DD'));
        addDateToSessionStorage();
        updateLinks();
    });
    $("#yesterday").on('click', function () {
        $("#date1").val(moment().subtract(1, 'days').format('YYYY-MM-DD'));
        $("#date2").val(moment().subtract(1, 'days').format('YYYY-MM-DD'));
        addDateToSessionStorage();
        updateLinks();
    });
                
    $("#cWeek").on('click', function () {
        $("#date1").val(moment().startOf('week').format('YYYY-MM-DD'));
        $("#date2").val(moment().endOf('week').format('YYYY-MM-DD'));
        addDateToSessionStorage();
        updateLinks();
    });
    $("#lWeek").on('click', function () {
        $("#date1").val(moment().subtract(1, 'week').startOf('week').format('YYYY-MM-DD'));
        $("#date2").val(moment().subtract(1, 'week').endOf('week').format('YYYY-MM-DD'));
        addDateToSessionStorage();
        updateLinks();
    });
    $("#cMonth").on('click', function () {
        $("#date1").val(moment().startOf('month').format('YYYY-MM-DD'));
        $("#date2").val(moment().endOf('month').format('YYYY-MM-DD'));
        addDateToSessionStorage();
        updateLinks();
    });
    $("#lMonth").on('click', function () {
        $("#date1").val(moment().subtract(1, 'month').startOf('month').format('YYYY-MM-DD'));
        $("#date2").val(moment().subtract(1, 'month').endOf('month').format('YYYY-MM-DD'));
        addDateToSessionStorage();
        updateLinks();
    });
    $("#cYear").on('click', function () {
        $("#date1").val(moment().startOf('year').format('YYYY-MM-DD'));
        $("#date2").val(moment().endOf('year').format('YYYY-MM-DD'));
        addDateToSessionStorage();
        updateLinks();
    });
    $("#lYear").on('click', function () {
        $("#date1").val(moment().subtract(1, 'year').startOf('year').format('YYYY-MM-DD'));
        $("#date2").val(moment().subtract(1, 'year').endOf('year').format('YYYY-MM-DD'));
        addDateToSessionStorage();
        updateLinks();
    });
                
   showCols("<%=type%>");
   rowSpan();
   var getCheckedBoxes =function() {
    var searchIDs = $("input:checkbox:checked").map(function(){
            if($(this).val()!== "on"){
                return $(this).val();
            }
            
        }).get();
        return searchIDs;
    };
    var addDateToSessionStorage = function() {
        sessionStorage.setItem("date1",$("#date1").val());
        sessionStorage.setItem("date2",$("#date2").val());
    };
    var filterDate = function(date) {
        if (date){
            return date;
        }
        else{
            return moment().format("YYYY-MM-DD");
        }
    };
    var updateLinks = function(){
        var ids = getCheckedBoxes();
        var date1 =filterDate(sessionStorage.getItem("date1"));
        var date2 =filterDate(sessionStorage.getItem("date2")); 
        
        var agencesLink ="";
        for(i=0;i<ids.length;i++){
            agencesLink+= "&agences="+ids[i];
        }
        agencesLink+="&date1="+date1+"&date2="+date2;
        $.each($(".d"),function(i,v) {
            var link=$(v).attr("href");
            link= link.substring(0,link.indexOf("d=d")+3);
            link+=agencesLink;
            $(v).attr("href",link);
        });
        sessionStorage.setItem("dbs",JSON.stringify(ids));
        console.log(ids);
    };
    $(".check").on('change',function() {
        sessionStorage.setItem("dbs",JSON.stringify(getCheckedBoxes()));
        updateLinks();
    });
    $(".ck-text").on('click', function () {
        var id = $(this).attr("data-id");
        var check = $("input[value="+id+"]");
        if($(check).prop("checked")){
            $(check).prop("checked",false);
            sessionStorage.setItem("dbs",JSON.stringify(getCheckedBoxes()));
            updateLinks();
        }else{
            $(check).prop("checked",true);
            sessionStorage.setItem("dbs",JSON.stringify(getCheckedBoxes()));
            updateLinks();
        }
    });
    
    $("#selectAll").on('change',function() {
        if($(this).prop("checked")){
           $(".check").prop("checked",true);
           sessionStorage.setItem("dbs",JSON.stringify(getCheckedBoxes()));
           
        console.log(getCheckedBoxes());
           updateLinks();
        }else{
            $(".check").prop("checked",false);
            sessionStorage.setItem("dbs",JSON.stringify(getCheckedBoxes()));
            updateLinks();
            
        console.log(getCheckedBoxes());
        }
    });
   var checkCheckBoxes = function() {
       var arr =JSON.parse(sessionStorage.getItem("dbs"));
       if(arr===null){
           $(".check").prop("checked",true);
        }else{
            $(".check").prop("checked",false);
            for(var i=0;i<arr.length;i++){
                $(".check[value='"+arr[i]+"']").prop("checked",true);
            }
        }
       
    };
    var setDates = function() {
        var date1 =filterDate(sessionStorage.getItem("date1"));
        var date2 =filterDate(sessionStorage.getItem("date2"));
        $("#date1").val(date1);
        $("#date2").val(date2);
        
    };
   checkCheckBoxes();
   setDates();
   updateLinks();
});
                
        </script>
    </body>
</html>
