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
        <title>QData</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="./img/favicon-32x32.png">
        <script src="./js/jquery.js"></script>
        <link href="./css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="./js/bootstrap.bundle.min.js"></script>
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
            <div>
                <div class="jumbotron mt-4">
                    <h1 class="display-4">Erreur</h1>
                    <p class="lead">OUPS Y A RIEN À VOIR ICI !

                    </p>
                    <hr class="my-4">
                    <p>La page que vous demandez n’existe pas.</p>
                    <p class="lead">
                        <a class="btn btn-primary btn-lg" href="/QData/home.jsp" role="button">ALLER A LA PAGE D'ACCUEIL</a>
                    </p>
                </div>
            </div>
    </body>
</html>
