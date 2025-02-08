<%@page import="java.net.URLEncoder"%>
<%@page import="ma.rougga.qdata.modal.Zone"%>
<%@page import="ma.rougga.qdata.controller.ZoneController"%>
<%@page import="ma.rougga.qdata.controller.UtilisateurController"%>
<%@page import="ma.rougga.qdata.modal.Utilisateur"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (!Objects.equals(session.getAttribute("grade"), "adm")) {
        response.sendRedirect("/" + CfgHandler.APP + "/home.jsp?err=" + URLEncoder.encode("vous avez besoin des privilèges d'administrateur", "UTF-8"));
    }

%>
<%!
    public String getFormatedTime(Float Sec) {
        int hours = (int) (Sec / 3600);
        int minutes = (int) ((Sec % 3600) / 60);
        int seconds = (int) (Sec % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public String getGrade(String grd) {
        switch (grd) {
            case "adm":
                return "Administrateur";
            case "user":
                return "Utilisateur";
        }
        return "erreur!";
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>QData - UTILISATEURS</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="/QData/img/favicon-32x32.png">
        <script src="/QData/js/jquery.js"></script>
        <link href="/QData/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="/QData/js/bootstrap.bundle.min.js"></script>
        <link href="/QData/css/navbar.css" rel="stylesheet" type="text/css"/> 
        <link href="/QData/css/body.css" rel="stylesheet" type="text/css"/>
        <script src="/QData/js/settings.js"></script>
    </head>
    <body>
        <div class="">
            <div class="head">
                <%@include file="../addon/navbar.jsp" %>
                <script>
                    $("#home").removeClass("active");
                </script>
            </div>
            <div class="pt-4 mt-4">
                <div class="container" id="userTbl">
                    <%                    String err = request.getParameter("err");
                        if (err != "" && err != null) {

                    %>
                    <%= "<div class='alert alert-danger alert-dismissible fade show' role='alert'><b>"
                            + err
                            + "</b><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>"%>
                    <%
                        }
                    %>
                    <h1 class="text-white">
                        Utilisateurs : 
                        <span class="  float-right">
                            <a class="btn btn-success" id="userAdd" data-toggle="modal" data-target="#userModal"><img src="/QData/img/icon/plus.png"> Ajouter</a>
                        </span>
                    </h1>
                    <table class="table table-bordered table-light   table-responsive-sm " id="userTable">
                        <thead class="appColor ">
                            <tr>
                                <th scope="col">Nom D'utilisateur</th>
                                <th scope="col">Nom Complet</th>
                                <th scope="col">Grade</th>
                                <th scope="col">Ajouté par</th>
                                <th scope="col">Zone</th>
                                <th></th>
                            </tr>
                        </thead>

                        <tbody class="">


                            <%
                                UtilisateurController uc = new UtilisateurController();
                                List<Utilisateur> utilisateur = uc.getAllUtilisateur();

                                if (utilisateur != null) {
                                    for (int i = 0; i < utilisateur.size(); i++) {
                                        String zoneName = "-";
                                        if (uc.getUtilisateurZone(utilisateur.get(i).getId()) != null) {
                                            zoneName = uc.getUtilisateurZone(utilisateur.get(i).getId()).getName();
                                        } else {
                                            continue;
                                        }
                            %>
                            <tr class=' clickable-row3 '>
                                <th scope='row'  class="border-dark align-middle" data-userId="<%=utilisateur.get(i).getId()%>"> <%=utilisateur.get(i).getUsername()%></th>
                                <td class="border-dark align-middle" ><b><%=utilisateur.get(i).getLastName() + " " + utilisateur.get(i).getFirstName()%></b></td>
                                <td class="border-dark align-middle" data-grade="<%=utilisateur.get(i).getGrade()%>"><b> <%=getGrade(utilisateur.get(i).getGrade())%></b></td>
                                <td class="border-dark align-middle" data-sponsor="<%=utilisateur.get(i).getSponsor()%>"><b> <%= (utilisateur.get(i).getSponsor() != null) ? utilisateur.get(i).getSponsor() : "-"%></b></td>
                                <td class="border-dark align-middle" data-zone><b> <%= zoneName%></b></td>
                                <td class="border-dark align-middle">
                                    <a class="btn btn-danger m-0" href="/QData/DeleteUtilisateur?id=<%= utilisateur.get(i).getId()%>"><img src="/QData/img/icon/trash.png"></a>
                                </td>
                            </tr>
                            <%
                                    }
                                }
                            %>

                        </tbody>
                    </table>
                </div>
            </div>
            <div class="footer">
                <div class="modal fade text-dark" id="userModal" tabindex="-1" aria-labelledby="userModalaria" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form id="userForm" action="/QData/AddUtilisateur" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title "  id="userModalLabel">Modal title</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">

                                    <div class="form-group">
                                        <label for="username">Nom D'utilisateur <span class="text-danger">*</span>:</label>
                                        <input type="text" class="form-control" id="username" name="username" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="password">Mot de passe <span class="text-danger">*</span>:</label>
                                        <input type="password" class="form-control" id="password" name="password" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="password">Mot de passe <span class="text-danger">*</span>:</label>
                                        <input type="password" class="form-control" id="password2" name="password2" required>
                                    </div>

                                    <div class="form-group">
                                        <label for="grade">Grade <span class="text-danger">*</span>:</label>
                                        <select class="form-control" id="grade" name="grade" required>
                                            <option value="0" selected  disabled> Selectioner Grade:</option>
                                            <option value="adm">ADMINISTATEUR</option>
                                            <option value="user">UTILISATEUR</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="firstName">Nom:</label>
                                        <input type="text" class="form-control" id="firstName" name="firstName">
                                    </div>
                                    <div class="form-group">
                                        <label for="lastName">Prenom:</label>
                                        <input type="text" class="form-control" id="lastName" name="lastName">
                                    </div>
                                    <div class="form-group">
                                        <label for="agence">Zone <span class="text-danger">*</span>:</label>
                                        <select class="form-control" id="zone" name="zone">

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
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <input type="hidden" class="form-control" id="type" name="type" value="user">
                                    </div>

                                    <p> <span class="text-danger">*</span> = champ obligatoire.</p>

                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Fermer</button>
                                    <button type="submit" class="btn btn-success" id="userSubmit">Ajouter</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
