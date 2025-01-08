<%@page import="java.util.UUID"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="main.modal.Agence"%>
<%@page import="java.util.List"%>
<%@page import="main.controller.AgenceController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
  String id_agence = request.getParameter("id_agence");
  Agence agence = new Agence();
  AgenceController ac = new AgenceController();
  if(StringUtils.isNotBlank(id_agence)){
    agence = ac.getAgenceById(UUID.fromString(id_agence)); 
  }
    
    
    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>QData</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="../img/favicon-32x32.png">
        <script src="../js/jquery.js"></script>
        <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="../js/bootstrap.bundle.min.js"></script>
        <link href="../css/navbar.css" rel="stylesheet" type="text/css"/> 
        <link href="../css/body.css" rel="stylesheet" type="text/css"/>
        <script src="../js/settings.js"></script>
    </head>
    <body>
        <div class="container-lg">
            <div class="head">
<%@include file="../addon/navbar.jsp" %>
                <script>
                    $("#home").removeClass("active");
                </script>
            </div>
            <div class="mt-4 pt-4">
<%                     
    String err=request.getParameter("err");
    if (err != "" && err != null) {

%>
<%= "<div class='alert alert-danger alert-dismissible fade show' role='alert'><b>"
        + err
        + "</b><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>"%>
<%
    }
%>

                <div class="w-100" id="dbTbl">
                    <h1 class="text-white text-center">
                        Mise a jour de l'agence: <%= agence.getName()%>
                    </h1>
                    <h5 class="text-white text-center">
                        Dernière mise à jour: <%= CfgHandler.getFormatedDateAsString(ac.getLastUpdate(agence.getId())) %>
                    </h5>
                    <div class="w-50 mx-auto mt-4">
                        <form action="" class="d-flex justify-content-center flex-column">
                            <a class="btn btn-secondary m-1" id="majNowBtn" href="/<%=CfgHandler.APP%>/updateagencestodaydata?id_agence=<%=agence.getId().toString()%>">
                                <img src="/QData/img/icon/maj.png">
                                Forcer la mise à jour d'aujourd'hui
                            </a>
                            <a class="btn btn-secondary m-1" id="majTBtn"  href="/<%=CfgHandler.APP%>/updateagencesalldata?id_agence=<%=agence.getId().toString()%>" >
                                <img src="/QData/img/icon/maj.png">
                                Mettre à jour toutes les données de l'agence
                            </a>
                        </form>
                    </div>
                </div>
            </div>
            <div class="footer">
            </div>
    </body>
</html>
