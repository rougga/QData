
<%@page import="main.controller.LoginLogController"%>
<%@page import="main.handler.TitleHandler"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="main.controller.TicketController"%>
<%@page import="main.Updater"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.Socket"%>
<%@page import="main.modal.Agence"%>
<%@page import="main.controller.AgenceController"%>
<%@page import="java.util.UUID"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="main.PgMultiConnection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="main.handler.DbHandler"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <div class="container-lg">
            <div class="head">
                <%@include file="./addon/navbar.jsp" %>
            </div>
            <div class="body">
                <%
                    
                 
                %>
            </div>  
        </div>
    </body>
</html>
