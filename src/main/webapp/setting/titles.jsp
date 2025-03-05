<%@page import="ma.rougga.qdata.controller.TitleController"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="ma.rougga.qdata.modal.Agence"%>
<%@page import="java.util.List"%>
<%@page import="ma.rougga.qdata.controller.AgenceController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (!Objects.equals(session.getAttribute("grade"), "adm")) {
        response.sendRedirect("/" + CfgHandler.APP + "/home.jsp?err=" + URLEncoder.encode("vous avez besoin des privilèges d'administrateur", "UTF-8"));
        return;
    }
    TitleController tc = new TitleController();
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
        <div class="">
            <div class="head">
                <%@include file="../addon/navbar.jsp" %>
                <script>
                    $("#home").removeClass("active");
                </script>
            </div>
            <div class="mt-4">
                <%                    String err = request.getParameter("err");
                    if (err != "" && err != null) {

                %>
                <%= "<div class='alert alert-danger alert-dismissible fade show' role='alert'><b>"
                        + err
                        + "</b><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>"%>
                <%
                    }

                %>
                <div class="container-lg">
                    <h1 class="text-white text-center">Modifier les titre des tables:</h1>
                    <form id="ttlForm" action="/QData/EditTitles" method="POST" class="my-4 py-4" >
                        <table class="table  table-light table-responsive-sm" id="dbTable">
                            <thead class="appColor border-dark">
                                <tr>
                                    <th scope="col" class="w-50 text-right">Rapport</th>
                                    <th scope="col" class="w-50">Titre à afficher</th>
                                </tr>
                            </thead>
                            <tbody class="font-weight-bold">
                                <tr class="border-dark">
                                    <td class="border-dark align-middle text-right" >
                                        Globale:
                                    </td>
                                    <td class="border-dark align-middle">
                                        <input type="text" class="form-control border border-dark" id="gbl" name="gbl" required value="<%= tc.getTitleByType("gbl").getValue()%>">
                                    </td>
                                </tr>
                                <tr class="border-dark">
                                    <td class="border-dark align-middle text-right" >
                                        Employée:
                                    </td>
                                    <td class="border-dark align-middle">
                                        <input type="text" class="form-control border border-dark" id="emp" name="emp" required value="<%= tc.getTitleByType("emp").getValue()%>">     
                                    </td>
                                </tr>
                                <tr class="border-dark">
                                    <td class="border-dark align-middle text-right" >
                                        Employée-Service:
                                    </td>
                                    <td class="border-dark align-middle">
                                        <input type="text" class="form-control border border-dark" id="empser" name="empser" required value="<%= tc.getTitleByType("empser").getValue()%>">    
                                    </td>
                                </tr>
                                <tr class="border-dark">
                                    <td class="border-dark align-middle text-right" >
                                        Guichet:
                                    </td>
                                    <td class="border-dark align-middle ">
                                        <input type="text" class="form-control border border-dark" id="gch" name="gch" required value="<%= tc.getTitleByType("gch").getValue()%>">     
                                    </td>
                                </tr>
                                <tr class="border-dark">
                                    <td class="border-dark align-middle text-right" >
                                        Guichet-Service:
                                    </td>
                                    <td class="border-dark align-middle">
                                        <input type="text" class="form-control border border-dark" id="gchserv" name="gchser" required value="<%= tc.getTitleByType("gchser").getValue()%>">    
                                    </td>
                                </tr>
                                <tr class="border-dark">
                                    <td class="border-dark align-middle text-right" >
                                        Grille attente:
                                    </td>
                                    <td class="border-dark align-middle ">
                                        <input type="text" class="form-control border border-dark" id="gla" name="gla" required value="<%= tc.getTitleByType("gla").getValue()%>">    
                                    </td>
                                </tr>
                                <tr class="border-dark">
                                    <td class="border-dark align-middle text-right" >
                                        Grille traitement:
                                    </td>
                                    <td class="border-dark align-middle">
                                        <input type="text" class="form-control border border-dark" id="glt" name="glt" required value="<%= tc.getTitleByType("glt").getValue()%>">        
                                    </td>
                                </tr>
                                <tr class="border-dark">
                                    <td class="border-dark align-middle text-right" >
                                        Tranche horaire Nb. edités:
                                    </td>
                                    <td class="border-dark align-middle">
                                        <input type="text" class="form-control border border-dark" id="nbt" name="ndt" required value="<%= tc.getTitleByType("tht").getValue()%>">          
                                    </td>
                                </tr>
                                <tr class="border-dark">
                                    <td class="border-dark align-middle text-right" >
                                        Tranche horaire Nb. traités:
                                    </td>
                                    <td class="border-dark align-middle">
                                        <input type="text" class="form-control border border-dark" id="nbtt" name="ndtt" required value="<%= tc.getTitleByType("thtt").getValue()%>">           
                                    </td>
                                </tr>
                                <tr class="border-dark">
                                    <td class="border-dark align-middle text-right" >
                                        Tranche horaire Nb. absents:
                                    </td>
                                    <td class="border-dark align-middle">
                                        <input type="text" class="form-control border border-dark" id="nbta" name="ndta" required value="<%= tc.getTitleByType("tha").getValue()%>">            
                                    </td>
                                </tr>
                                <tr class="border-dark">
                                    <td class="border-dark align-middle text-right" >
                                        Tranche horaire Nb. sans affectation:
                                    </td>
                                    <td class="border-dark align-middle">
                                        <input type="text" class="form-control border border-dark" id="nbtsa" name="ndtsa" required value="<%= tc.getTitleByType("thsa").getValue()%>">           
                                    </td>
                                </tr>
                            </tbody>
                        </table>

                        <div class="text-right">
                            <button type="submit" class="btn btn-success " >
                                <img src="../img/icon/done.png"/>
                                Sauvegarder
                            </button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="footer">

            </div>
        </div>
    </body>
</html>
