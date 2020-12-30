<%@page import="main.controller.UtilisateurController"%>
<%@page import="main.modal.Utilisateur"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            case "sv":
                return "Superviseur";
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
            <div class="pt-4 mt-4">
                <div class="" id="userTbl">
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
                                <th></th>
                            </tr>
                        </thead>

                        <tbody class="">


                            <%
                                List<Utilisateur> utilisateur = new UtilisateurController(request).getAllUtilisateur();
                                if(utilisateur!=null){
                                    for(int i=0;i<utilisateur.size();i++){
                            %>
                            <tr class=' clickable-row3 '>
                                <th scope='row'  class="border-dark align-middle"> <%=utilisateur.get(i).getUsername() %></th>
                                <td class="border-dark align-middle" ><b><%=utilisateur.get(i).getLastName() + " " + utilisateur.get(i).getFirstName() %></b></td>
                                <td class="border-dark align-middle" data-grade="<%=utilisateur.get(i).getGrade() %>"><b> <%=getGrade(utilisateur.get(i).getGrade()) %></b></td>
                                <td class="border-dark align-middle">
                                    <a class="btn btn-danger m-0" href="/QData/Delete?type=user&username=<%=utilisateur.get(i).getUsername()%>"><img src="/QData/img/icon/trash.png"></a>
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
                            <form id="userForm" action="/QData/Add" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title "  id="userModalLabel">Modal title</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">

                                    <div class="form-group">
                                        <label for="username">Nom D'utilisateur:*</label>
                                        <input type="text" class="form-control" id="username" name="username" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="password">Mot de passe:*</label>
                                        <input type="password" class="form-control" id="password" name="password" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="password">Mot de passe:*</label>
                                        <input type="password" class="form-control" id="password2" name="password2" required>
                                    </div>

                                    <div class="form-group">
                                        <label for="grade">Grade:*</label>
                                        <select class="form-control" id="grade" name="grade" required>
                                            <option value="0" selected  disabled> Selectioner Grade:</option>
                                            <option value="adm">ADMINISTATEUR</option>
                                            <option value="sv">SUPERVISEUR</option>
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
                                        <input type="hidden" class="form-control" id="type" name="type" value="user">
                                    </div>


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
