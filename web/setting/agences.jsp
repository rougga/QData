<%@page import="main.Listener"%>
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
        <title>QData</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="../img/favicon-32x32.png">
        <script src="../js/jquery.js"></script>
        <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="../js/bootstrap.bundle.min.js"></script>
        <link href="../css/navbar.css" rel="stylesheet" type="text/css"/> 
        <link href="../css/body.css" rel="stylesheet" type="text/css"/>
        <script src="../js/settings.js"></script>
        <script src="../js/setting/agence.js"></script>
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
                        Les agences :
                        <span class=" float-right">
                            <a class="btn btn-success" id="dbAdd" data-toggle="modal" data-target="#dbModal"><img src="/QData/img/icon/plus.png"> Ajouter</a>

                        </span>
                    </h1>
                    <table class="table table-bordered table-light table-responsive-md border-dark"  id="dbTable">
                        <thead class="appColor border-dark">
                            <tr>
                                <th scope="col">Zone</th>
                                <th scope="col">Name</th>
                                <th scope="col">Host:port</th>
                                <th scope="col">Statut</th>
                                <th scope="col">Dernière mise à jour</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>

                        <tbody class="font-weight-bold">
                            <%
                                AgenceController ac = new AgenceController();
                                List<Agence> table = ac.getAllAgence();
                                if (table != null) {
                                    for (int i = 0; i < table.size(); i++) {
                                        Zone z = ac.getAgenceZoneByAgenceId(table.get(i).getId());
                                        String zoneName , id_zone;
                                        if(z==null){
                                             zoneName = "--";
                                             id_zone = "";
                                        }else{
                                             zoneName = z.getName() ;
                                             id_zone = z.getId().toString();
                                        }
                                        String host = table.get(i).getHost() + ":" + table.get(i).getPort();
                                    
                            %>
                            <tr class="clickable-row5 border-dark">
                                <td class="border-dark align-middle agenceZone" data-zone="<%= id_zone %>"><%= zoneName %></td>
                                <td class="border-dark align-middle agenceName"><%=table.get(i).getName()%></td>
                                <td class="border-dark align-middle agenceHost"><%=host%></td>
                                <td class="border-dark align-middle status " 
                                    data-id="<%= table.get(i).getId()%>" 
                                    data-ip="<%= host %>"
                                    >
                                    <div class="spinner-border" role="status">
                                        <span class="sr-only text-center text-white bg-secondary p-1">UNK</span>
                                    </div>
                                </td>
                                <td class="border-dark align-middle"><%= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(ac.getLastUpdate(table.get(i).getId()))%></td>
                                <td class="border-dark align-middle">
                                    <a class="btn btn-secondary m-0 dbUpdateAll disabled" data-id="<%= table.get(i).getId()%>" href="/QData/setting/update.jsp?id_agence=<%= table.get(i).getId()%>" title="Mise à jour"><img src="/QData/img/icon/maj.png"></a>
                                    <a class="btn btn-warning m-0 dbEdit" data-id="<%= table.get(i).getId()%>" href="#" title="Modifier">
                                        <img src="/QData/img/icon/pencil.png">
                                    </a>
                                    <a class="btn btn-danger m-0 dbDlt" data-id="<%= table.get(i).getId()%>" href="/QData/DeleteDatabase?id=<%= table.get(i).getId()%>" title="Supprimer"><img src="/QData/img/icon/trash.png"></a>
                                </td>
                                <td class="d-none agenceDb"><%= table.get(i).getDatabase() %></td>
                                <td class="d-none agenceUser"><%= table.get(i).getUsername() %></td>
                                <td class="d-none agencePass"><%= table.get(i).getPassword() %></td>
                            </tr>
                            <%
                                }
                            } else {
                            %><%="<h4 class='text-center text-danger'>No database</h4>"%><%
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
                            <form id="dbForm" action="/QData/AddDatabase" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title "  id="exampleModalLabel">Ajouter base de donnée:</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">

                                    <div class="form-group">
                                        <label for="agence">Agence:</label>
                                        <input type="text" class="form-control" id="agence" name="name" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="agence">Zone:</label>
                                        <select class="form-control" id="zone" name="zone">
                                            <option class="disabled" selected disabled>Selectionez Zone</option>
                                            <%                                     
                                                ZoneController zc = new ZoneController();
                                                List<Zone> zones = zc.getAllZones();
                                                if (zones != null) {
                                                    for (int i = 0; i < zones.size(); i++) {
                                            %>

                                            <option class="" value="<%=zones.get(i).getId()%>"><%=zones.get(i).getName()%></option>

                                            <%
                                                }
                                            } else {
                                            %><%="<h4 class='text-center text-danger'>No Zones</h4>"%><%
                                                }

                                            %>
                                            <option class="bg-danger text-white" value="" >No Zone</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="host">Hôte:</label>
                                        <input type="text" class="form-control" id="host" name="host" placeholder="0.0.0.0" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="port">Port:</label>
                                        <input type="number" class="form-control" id="port" name="port" value="5432" required min="1">
                                    </div>
                                    <div class="form-group">
                                        <label for="db">Database:</label>
                                        <input type="text" class="form-control" id="db" name="database" value="postgres" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="user">Utilisateur:</label>
                                        <input type="text" class="form-control" id="user" name="username" value="honyi" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="pass">Mot de passe:</label>
                                        <input type="text" class="form-control" id="pass" name="password" value="honyi123" required>
                                    </div>

                                    <div class="form-group">
                                        <input type="hidden" class="form-control" id="type" name="type" value="db">
                                    </div>
                                        
                                    <div class="form-group">
                                        <input type="hidden" class="form-control" id="id" name="id_agence" value="">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Fermer</button>
                                    <button type="submit" class="btn appBg text-white hover" id="dbAddButton">Ajouter</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
