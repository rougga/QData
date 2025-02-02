<%@page import="java.net.URLEncoder"%>
<%@page import="main.modal.Zone"%>
<%@page import="main.controller.ZoneController"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="main.modal.Agence"%>
<%@page import="java.util.List"%>
<%@page import="main.controller.AgenceController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (!Objects.equals(session.getAttribute("grade"), "adm")) {
        response.sendRedirect("/"+CfgHandler.APP+"/home.jsp?err="+ URLEncoder.encode("vous avez besoin des privilèges d'administrateur", "UTF-8"));
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>QData - Zones</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="../img/favicon-32x32.png">
        <script src="../js/jquery.js"></script>
        <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="../js/bootstrap.bundle.min.js"></script>
        <link href="../css/navbar.css" rel="stylesheet" type="text/css"/> 
        <link href="../css/body.css" rel="stylesheet" type="text/css"/>
        <script src="../js/settings.js"></script>
        <script src="../js/setting/zone.js"></script>
    </head>
    <body>
        <div class="container-lg p-0 ">
            <div class="head">
                <%@include file="../addon/navbar.jsp" %>
                <script>
                    $("#home").removeClass("active");
                </script>
            </div>
            <div class="mt-4 pt-4">
                <%                    String err = request.getParameter("err");
                    if (err != "" && err != null) {

                %>
                <%= "<div class='alert alert-danger alert-dismissible fade show' role='alert'><b>"
                        + err
                        + "</b><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>"%>
                <%
                    }
                %>

                <div class="w-100" id="dbTbl">
                    <h1 class="text-white text_center pl-2">
                        Les Zones :
                        <span class=" float-right">
                            <a class="btn btn-success" id="dbAdd"><img src="/QData/img/icon/plus.png"> Ajouter</a>
                        </span>
                    </h1>
                    <table class="table table-bordered table-light table-responsive-md border-dark"  id="dbTable">
                        <thead class="appColor border-dark">
                            <tr>
                                <th scope="col">Name</th>
                                <th scope="col">Ville</th>
                                <th scope="col">Code</th>
                                <th scope="col">Nb. Agences</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>

                        <tbody class="font-weight-bold">
                            <%
                                ZoneController zc = new ZoneController();
                                List<Zone> table = zc.getAllZones();
                                if (table != null) {
                                    for (int i = 0; i < table.size(); i++) {
                            %>
                            <tr class="clickable-row5 border-dark">
                                <td class="border-dark align-middle zoneName"><%=table.get(i).getName()%></td>
                                <td class="border-dark align-middle zoneCity"><%=table.get(i).getCity()%></td>
                                <td class="border-dark align-middle zoneCode"><%=table.get(i).getCode()%></td>
                                <td class="border-dark align-middle"><%= zc.getAgenceCountInZone(table.get(i).getId()) %></td>
                                <td class="border-dark align-middle">
                                    <a class="btn btn-secondary m-0 dbEdit" data-id="<%= table.get(i).getId()%>" href="#" title="Modifier">
                                        <img src="/QData/img/icon/pencil.png">
                                    </a>
                                    <a class="btn btn-danger m-0 dbDlt" data-id="<%= table.get(i).getId()%>" href="/QData/DeleteZone?id=<%= table.get(i).getId()%>" title="Supprimer"><img src="/QData/img/icon/trash.png"></a>
                                
                                </td>
                            </tr>
                            <%
                                }
                            } else {
                            %><%="<h4 class='text-center text-danger'>No Zones</h4>"%><%
                                }

                            %>

                        </tbody>
                    </table>
                </div>
            </div>
            <div class="footer">
                <div class="modal fade text-dark" id="dbModal" tabindex="-1" aria-labelledby="dbModalaria" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form id="dbForm" action="/QData/AddZone" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title "  id="exampleModalLabel">Ajouter une Zone:</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">

                                    <div class="form-group">
                                        <label for="agence">Nom</label>
                                        <input type="text" class="form-control" id="zoneName" name="name" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="host">Ville:</label>

                                        <input type="text" class="form-control" id="zoneCity" name="city" required> 
                                    </div>
                                    <div class="form-group">
                                        <label for="port">Code:</label>
                                        <input type="text" class="form-control" id="zoneCode" name="code" required>
                                    </div>
                                    <div class="form-group">
                                        <input type="hidden" class="form-control" id="type" name="type" value="zone">
                                    </div>
                                    <div class="form-group">
                                        <input type="hidden" class="form-control" id="zoneId" name="id_zone" value="">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Fermer</button>
                                    <button type="submit" class="btn appBg text-white hover">Ajouter</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                            
                           
    </body>
     <script>
       history.replaceState({page:1},'title',"?err=");
    </script>
</html>
