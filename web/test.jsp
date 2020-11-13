
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
                <h1>Agence names:</h1>
                <%
                    List<ArrayList> table = new DbHandler(request).getDatabases();
                    for(int i=0;i<table.size();i++){
                        try{
                        PgMultiConnection con = new PgMultiConnection(table.get(i).get(2).toString(), table.get(i).get(3).toString(), table.get(i).get(4).toString(), table.get(i).get(5).toString(), table.get(i).get(6).toString());
                        ResultSet r=con.getStatement().executeQuery("SELECT value FROM t_basic_par where name='BRANCH_NAME' ;");
                        if(r.next()){
                %><h6><%=  r.getString("value")  %></h6><%
                        }
                        con.closeConnection();
                        }catch(Exception e){
                        %><%= e.getMessage() %> <%
                        }   
                    }
                %>
            </div>
            
            
                <h1 class="text-white"> <%= UUID.randomUUID() %></h1>
        </div>
    </body>
</html>
