<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.UUID"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="ma.rougga.qdata.modal.Agence"%>
<%@page import="java.util.List"%>
<%@page import="ma.rougga.qdata.controller.AgenceController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <link href="../css/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        <link href="../css/navbar.css" rel="stylesheet" type="text/css"/> 
        <link href="../css/body.css" rel="stylesheet" type="text/css"/>
        <script src="../js/settings.js"></script>
    </head>
    <body>
        <div class="">
            <div class="head">
                <%@include file="../addon/navbar.jsp" %>
                <script>
                    $("#home").removeClass("active");
                </script>
            </div>
            <div class="mt-4 pt-4">
                <%    String err = request.getParameter("err");
                    if (err != "" && err != null) {

                %>
                <%= "<div class='alert alert-danger alert-dismissible fade show' role='alert'><b>"
                        + err
                        + "</b><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>"%>
                <%
                    }
                %>

                <div class="container-lg" id="dbTbl">
                    <h1 class="text-white text-center">
                        MISE À JOUR GLOBALE :
                    </h1>
                    <div class="d-flex justify-content-center text-white loadingSpinner p-4" >
                        <div class="spinner-border" role="status">
                            <span class="sr-only">Loading...</span>
                        </div>
                    </div>
                    <div class="col-md-6 d-flex mt-4 flex-column mx-auto align-items-center">
                        <a class="btn btn-secondary m-1 majBtn" id="majNowBtn" href="/<%=CfgHandler.APP%>/update">
                            <i class="fa fa-cloud-download" aria-hidden="true"></i>
                            <span>Forcer la Mise à jour d'aujourd'hui maintenant</span>
                        </a>
                        <a class="btn btn-secondary m-1 majBtn" id="majTBtn"  href="/<%=CfgHandler.APP%>/restore" >
                            <i class="fa fa-cloud-download" aria-hidden="true"></i>
                            Restaurer toutes les données maintenant
                        </a>
                        <a class="btn btn-danger mt-5 col-4 " id=""  href="/<%=CfgHandler.APP%>/home.jsp" >
                            <i class="fa fa-arrow-left" aria-hidden="true"></i>
                            RETOURNER
                        </a>
                    </div>
                </div>

            </div>
            <div class="footer">
            </div>
        </div>
    </body>
    <script>
        history.replaceState({page: 1}, 'title', "?err=");
        function initLoader() {
            $("#majNowBtn").addClass("disabled");
            $("#majTBtn").addClass("disabled");
            $("#majNowBtn").removeClass("btn-secondary").addClass("btn-dark");
            $("#majTBtn").removeClass("btn-secondary").addClass("btn-dark");
            $(".loadingSpinner").removeClass("d-none").addClass("d-flex");
            console.log("loading");
        }
        $(".majBtn").on("click", function () {
            initLoader();
        });
        $(document).ready(function () {
            $("#majNowBtn").removeClass("disabled");
            $("#majTBtn").removeClass("disabled");
            $("#majNowBtn").removeClass("btn-dark").addClass("btn-secondary");
            $("#majTBtn").removeClass("btn-dark").addClass("btn-secondary");
            $(".loadingSpinner").removeClass("d-flex").addClass("d-none");
        });

    </script>
</html>
