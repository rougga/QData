
<%@page import="main.controller.report.GblTableController"%>
<%@page import="main.Updater"%>
<%@page import="java.nio.file.FileSystems"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="main.SQLiteConnection"%>
<%@page import="main.controller.UpdateController"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="main.CfgHandler"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>QData</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="./img/favicon-32x32.png">
        <script src="js/jquery.js"></script>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/bootstrap.bundle.min.js"></script>
        <link href="css/body.css" rel="stylesheet" type="text/css"/> 
        <link href="css/login.css" rel="stylesheet" type="text/css"/> 
        <link href="css/loginSS.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="wrapper text-white">
           
                
<% new GblTableController().updateFromJson(null, null); %>
              
            
        </div>
    </body>
</html>