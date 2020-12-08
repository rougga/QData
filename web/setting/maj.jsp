<%@page import="main.modal.Agence"%>
<%@page import="java.util.List"%>
<%@page import="main.controller.AgenceController"%>
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
        <script src="../js/bootstrap.min.js"></script>
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
                        Mise a jour globale :
                    </h1>

                    <div class="w-50 mx-auto ">
                        <form action="">
                            <a class="btn btn-secondary mx-auto" id="majNowBtn"><img src="/QData/img/icon/maj.png"> Mise à jour maintenant</a>
                            <a class="btn btn-secondary mx-auto m-4" id="majTBtn" ><img src="/QData/img/icon/maj.png"> Mise à jour tous les tickets</a><br>
                            <a class="btn btn-secondary mx-auto" id="majLBtn"><img src="/QData/img/icon/maj.png"> Mise à jour tout le journal de connexion</a>

                        </form>
                    </div>
                </div>
            </div>
            <div class="footer">
                <script>
                    $(document).ready(function() {
                        $("#majTBtn").on('click',function() {
                            $("form").attr("action","/QData/api/updatealltickets");
                            $("form").submit();
                        });
                        $("#majLBtn").on('click',function() {
                            $("form").attr("action","/QData/api/updateallloginlog");
                            $("form").submit();
                        });
                        $("#majNowBtn").on('click',function() {
                            $("form").attr("action","/QData/api/updatenow");
                            $("form").submit();
                        });
                    });
                    
                </script>
            </div>
    </body>
</html>