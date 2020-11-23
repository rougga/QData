<%@page import="javax.swing.text.TabExpander"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="main.Stats"%>
<%@page import="main.TableGenerator"%>
<%@page import="java.util.Date"%>
<%@page import="main.CfgHandler"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="main.PgConnection"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>QStates</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="./img/favicon-32x32.png">
        <script src="./js/jquery.js"></script>
        <link href="./css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="./js/bootstrap.min.js"></script>
        <script src="./js/Chart.min.js"></script>
        <link href="./css/Chart.min.css" rel="stylesheet" type="text/css"/>
        <link href="./css/body.css" rel="stylesheet" type="text/css"/>
        <link href="./css/navbar.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container-lg">
            <div>
                <%@include file="./addon/navbar.jsp" %>
            </div>
            <%   
                String err = "", data = "[", lable = "[";
                String data2 = "[", labels2 = "[";
                long wait = 0;
                long goal = 0;
                String waitPer = "0";
                String goalPer = "0";
                String waitCss = "bg-success";
                String goalCss = "bg-danger";
                String waitClass = "is-valid";
                String goalClass = "is-invalid";
                
            %>
           
    </body>
</html>
