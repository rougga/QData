<%@page import="main.controller.AgenceController"%>
<%@page import="main.modal.Agence"%>
<%@page import="main.handler.DbHandler"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="main.PgConnection"%>
<%@page import="org.w3c.dom.Element"%>
<%@page import="org.w3c.dom.Node"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="main.CfgHandler"%>
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
        <title>OffReport</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="./img/favicon-32x32.png">
        <script src="./js/jquery.js"></script>
        <link href="./css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="./js/bootstrap.min.js"></script>
        <link href="css/navbar.css" rel="stylesheet" type="text/css"/> 
        <link href="./css/body.css" rel="stylesheet" type="text/css"/>
        <script src="./js/settings.js"></script>
        <style>
            table{
                white-space:nowrap; 
            }
        </style>
    </head>
    <body>
        <div class="container-lg">
            <div class="head">
                <%@include file="./addon/navbar.jsp" %>
                <script>
                    $("#home").removeClass("active");
                </script>
            </div>
            <div class="body p-3">
                <%                     if (request.getParameter("err") != "" && request.getParameter("err") != null) {

                %>
                <%= "<div class='alert alert-danger alert-dismissible fade show' role='alert'><b>"
                        + request.getParameter("err")
                        + "</b><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>"%>
                <%
                    }
                %>
                <div class="row">
                    <div class="btn-group btn-group-lg p-4 mx-auto" role="group" aria-label="Basic example">
                        <a class="btn btn-primary" id="cibleBtn">Cible</a>
                        <a class="btn btn-secondary" id="userBtn">Utilisateurs</a>
                        <a class="btn btn-secondary" id="extraBtn">Extra</a>
                        <a class="btn btn-secondary" id="goalBtn">Objectif</a>
                        <a class="btn btn-secondary" id="dbBtn">Agences</a>
                    </div>
                </div>
                <div class="" id="cibleTbl">
                    <h1>
                        Cible : 
                        <span class="  float-right">
                            <a class="btn btn-success" id="cibleAdd" data-toggle="modal" data-target="#cibleModal"><img src="./img/icon/plus.png"> Ajouter</a>
                            <a class="btn btn-info " id="cibleEdit" ><img src="./img/icon/pencil.png"> Editer</a>
                            <a class="btn btn-danger" id="cibleDlt" href="#"><img src="./img/icon/trash.png"> Supprimer</a>
                        </span>
                    </h1>
                    <table class="table table-bordered table-light   table-responsive-sm " id="cibleTable">
                        <thead class="appColor ">
                            <tr>
                                <th scope="col">Service</th>
                                <th scope="col">Cible d'attente</th>
                                <th scope="col">Cible Traitement</th>
                                <th scope="col">%dépasse. Cible</th>
                            </tr>
                        </thead>

                        <tbody class="">


                            <%
                                CfgHandler cfg = new CfgHandler(request);
                                String path =  cfg.getCibleFile();

                                Document doc = cfg.getXml(path);
                                NodeList nList = doc.getElementsByTagName("service");
                                for (int i = 0; i < nList.getLength(); i++) {
                                    Node nNode = nList.item(i);
                                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                        Element eElement = (Element) nNode;

                            %><%="<tr class=' clickable-row '>"
                                    + "<th scope='row'  data-id='" + eElement.getElementsByTagName("id").item(0).getTextContent() + "'>" + eElement.getElementsByTagName("name").item(0).getTextContent() + "</th>"
                                    + "<td ><b>" + getFormatedTime(Float.parseFloat(eElement.getElementsByTagName("cibleA").item(0).getTextContent())) + "</b></td>"
                                    + "<td><b>" + getFormatedTime(Float.parseFloat(eElement.getElementsByTagName("cibleT").item(0).getTextContent())) + "</b></td>"
                                    + "<td><b>" + eElement.getElementsByTagName("dcible").item(0).getTextContent() + "</b></td>"
                                    + "</tr>"%><%

                                            }
                                        }


                            %>

                        </tbody>
                    </table>
                </div>
                <div class="" id="userTbl">
                    <h1>
                        Utilisateurs : 
                        <span class="  float-right">
                            <a class="btn btn-success" id="userAdd" data-toggle="modal" data-target="#userModal"><img src="./img/icon/plus.png"> Ajouter</a>
                            <a class="btn btn-info disabled" id="userEdit"><img src="./img/icon/pencil.png"> Editer</a>
                            <a class="btn btn-danger" id="userDlt" href="#"><img src="./img/icon/trash.png"> Supprimer</a>
                        </span>
                    </h1>
                    <table class="table table-bordered table-light   table-responsive-sm " id="userTable">
                        <thead class="appColor ">
                            <tr>
                                <th scope="col">Nom D'utilisateur</th>
                                <th scope="col">Nom Complet</th>
                                <th scope="col">Grade</th>
                            </tr>
                        </thead>

                        <tbody class="">


                            <%
                                String path3 = cfg.getUserFile();
                                Document doc3 = cfg.getXml(path3);
                                NodeList nList3 = doc3.getElementsByTagName("user");
                                for (int i = 0;
                                        i < nList3.getLength();
                                        i++) {
                                    Node nNode3 = nList3.item(i);
                                    if (nNode3.getNodeType() == Node.ELEMENT_NODE) {
                                        Element eElement3 = (Element) nNode3;

                            %><%="<tr class=' clickable-row3 '>"
                                    + "<th scope='row' >" + eElement3.getElementsByTagName("username").item(0).getTextContent() + "</th>"
                                    + "<td ><b>" + eElement3.getElementsByTagName("lastName").item(0).getTextContent() + " " + eElement3.getElementsByTagName("firstName").item(0).getTextContent() + "</b></td>"
                                    + "<td data-grade='" + eElement3.getElementsByTagName("grade").item(0).getTextContent() + "'><b>" + getGrade(eElement3.getElementsByTagName("grade").item(0).getTextContent().toLowerCase().trim()) + "</b></td>"
                                    + "</tr>"%><%

                                            }
                                        }


                            %>

                        </tbody>
                    </table>
                </div>
                <div class="w-100" id="extraTbl">
                    <h1>
                        Extra : 
                        <span class="  float-right">
                            <a class="btn btn-success" id="extraAdd" data-toggle="modal" data-target="#extraModal"><img src="./img/icon/plus.png"> Ajouter</a>
                            <a class="btn btn-info disabled" id="extraEdit"><img src="./img/icon/pencil.png"> Editer</a>
                            <a class="btn btn-danger" id="extraDlt" href="#"><img src="./img/icon/trash.png"> Supprimer</a>
                        </span>
                    </h1>
                    <table class="table table-bordered table-light   table-responsive-sm " id="extraTable">
                        <thead class="appColor ">
                            <tr>
                                <th scope="col">Service</th>
                                <th scope="col">Extra</th>
                            </tr>
                        </thead>

                        <tbody class="">


                            <%
                                String path2 =  cfg.getExtraFile();
                                //String path2 = "C:\\Users\\bouga\\Desktop\\OffReport\\web\\cfg\\extra.xml";

                                Document doc2 = cfg.getXml(path2);
                                NodeList nList2 = doc2.getElementsByTagName("service");
                                for (int i = 0; i < nList2.getLength(); i++) {
                                    Node nNode2 = nList2.item(i);
                                    if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
                                        Element eElement2 = (Element) nNode2;

                            %><%="<tr class=' clickable-row2 '>"
                                    + "<th scope='row'  data-id='" + eElement2.getElementsByTagName("id").item(0).getTextContent() + "'>" + eElement2.getElementsByTagName("name").item(0).getTextContent() + "</th>"
                                    + "<td ><b>" + getFormatedTime(Float.parseFloat(eElement2.getElementsByTagName("extra").item(0).getTextContent())) + "</b></td>"
                                    + "</tr>"%><%

                                            }
                                        }


                            %>

                        </tbody>
                    </table>
                </div>
                <div class="w-100" id="goalTbl">
                    <h1>
                        Objectif : 
                        <span class="  float-right">
                            <a class="btn btn-info " id="goalEdit" data-toggle="modal" data-target="#goalModal"><img src="./img/icon/pencil.png"> Editer</a>
                        </span>
                    </h1>
                    <table class="table table-bordered table-light   table-responsive-sm " id="goalTable">
                        <thead class="appColor">
                            <tr>
                                <th scope="col">Objectif</th>
                                <th scope="col">Valeur</th>
                            </tr>
                        </thead>

                        <tbody class="font-weight-bold">

                            <tr class="clickable-row4">
                                <td>Max Attente</td>
                                <td><%= cfg.getPropertie("maxA")%></td>
                            </tr>
                            <tr class="clickable-row4">
                                <td>But Traitement</td>
                                <td><%= cfg.getPropertie("goalT")%></td>
                            </tr>

                        </tbody>
                    </table>
                </div>
                <div class="w-100" id="dbTbl">
                    <h1>
                        Les bases de données  : 
                        <span class=" float-right">
                            <a class="btn btn-success" id="dbAdd" data-toggle="modal" data-target="#dbModal"><img src="./img/icon/plus.png"> Ajouter</a>

                        </span>
                    </h1>
                    <table class="table table-bordered table-light   table-responsive-sm border-dark" id="dbTable">
                        <thead class="appColor border-dark">
                            <tr>
                                <th scope="col">Name</th>
                                <th scope="col">Host:port</th>
                                <th scope="col">Base de donnée</th>
                                <th scope="col">Utilisateur</th>
                                <th scope="col">Mot de passe</th>
                                <th></th>
                            </tr>
                        </thead>

                        <tbody class="font-weight-bold">
                            <%
                              List<Agence> table=new AgenceController().getAllAgence();
                              if(table!=null){
                                  for(int i =0;i<table.size();i++){
                            %>
                            <tr class="clickable-row5 border-dark">
                                <td class="border-dark align-middle"><%=table.get(i).getName() %></td>
                                <td class="border-dark align-middle"><%=table.get(i).getHost()+":"+table.get(i).getPort() %></td>
                                <td class="border-dark align-middle"><%=table.get(i).getDatabase() %></td>
                                <td class="border-dark align-middle"><%=table.get(i).getUsername() %></td>
                                <td class="border-dark align-middle"><%=table.get(i).getPassword() %></td>
                                <td class="border-dark align-middle">
                                    <a class="btn btn-danger m-0" id="dbDlt" href="./DeleteDatabase?id=<%= table.get(i).getId() %>"><img src="./img/icon/trash.png"></a>
                                    
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
                <div class="modal fade text-dark" id="cibleModal" tabindex="-1" aria-labelledby="cibleModalaria" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form id="cibleForm" action="./Add" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title "  id="exampleModalLabel">Modal title</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">

                                    <div class="form-group">
                                        <label for="serviceName">Service:</label>
                                        <input class="form-control" type="hidden" id="servicePlace">
                                        <select class="form-control" id="serviceName" name="service" required>

                                            <option selected disabled value="0">Sélectionner service:</option>
                                            <%                              
                                               /* ResultSet r = new PgConnection().getStatement().executeQuery("SELECT id,name FROM t_biz_type;");
                                                while (r.next()) {
                                            %><%="<option value='" + r.getString("id") + "'>"
                                                    + r.getString("name")
                                                    + "</option>"%><%

                                                                }
                                                */
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="cibleA">Cible d'attente:</label><br>
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleAH" name="cibleAH" placeholder="Hr" min="0" max="24" required>
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleAM" name="cibleAM" placeholder="Min" min="0" max="60" required>
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleAS" name="cibleAS" placeholder="Sec" min="0" max="60" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="cibleT">Cible Traitement:</label><br>
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleTH" name="cibleTH" placeholder="Hr" min="0" max="24" required>
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleTM" name="cibleTM" placeholder="Min" min="0" max="60" required>
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleTS" name="cibleTS" placeholder="Sec" min="0" max="60" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="cibleD">%dépasse. Cible:</label>
                                        <input type="number" class="form-control" id="cibleD" name="cibleD" min="0" max="100" required>
                                    </div>
                                    <div class="form-group">
                                        <input type="hidden" class="form-control" name="type" value="cible">
                                    </div>

                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Fermer</button>
                                    <button type="submit" class="btn btn-success  " id="cibleSubmit" >Enregistrer</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal fade text-dark" id="userModal" tabindex="-1" aria-labelledby="userModalaria" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form id="userForm" action="./Add" method="POST">
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
                <div class="modal fade text-dark" id="extraModal" tabindex="-1" aria-labelledby="extraModalaria" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form id="extraForm" action="./Add" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title "  id="exampleModalLabel">Modal title</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">

                                    <div class="form-group">
                                        <label for="serviceName">Service:</label>
                                        <select class="form-control" id="serviceNameExtra" name="serviceNameExtra" required>>
                                            <option selected disabled value="0">Sélectionner service:</option>
                                            <%   
                                              /*  r = new PgConnection().getStatement().executeQuery("SELECT id,name FROM t_biz_type;");
                                                while (r.next()) {
                                            %><%="<option value='" + r.getString("id") + "'>"
                                                    + r.getString("name")
                                                    + "</option>"%><%

                                                        }*/
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="extra">Extra:</label><br>
                                        <input type="number" class="form-control w-25 d-inline-block" id="extraH" name="extraH" placeholder="Hr" required max="23" min="0">
                                        <input type="number" class="form-control w-25 d-inline-block" id="extraM" name="extraM" placeholder="Min" required max="60" min="0">
                                        <input type="number" class="form-control w-25 d-inline-block" id="extraS" name="extraS" placeholder="Sec" required max="60" min="0">
                                    </div>
                                    <div class="form-group">

                                        <input type="hidden" class="form-control" id="type" name="type" value="extra">
                                    </div>

                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Fermer</button>
                                    <button type="submit" class="btn appBg text-white hover">Enregistrer</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal fade text-dark" id="goalModal" tabindex="-1" aria-labelledby="goalModalaria" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form id="goalForm" action="./Add" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title "  id="exampleModalLabel">Modifier Objectif:</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">

                                    <div class="form-group">
                                        <label for="maxA">Max Attente:</label><br>
                                        <input type="number" class="form-control" id="goalAH" name="maxA" placeholder="Nb." min="0" value="<%= cfg.getPropertie("maxA") %>" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="goalT">But Traitement:</label><br>
                                        <input type="number" class="form-control" id="goalTH" name="goalT" placeholder="Nb." min="0" value="<%= cfg.getPropertie("goalT") %>" required>
                                    </div>
                                    <div class="">
                                        <input type="hidden" class="form-control" name="type" value="goal">
                                    </div>

                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Fermer</button>
                                    <button type="submit" class="btn btn-success  " id="goalSubmit" >Enregistrer</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="modal fade text-dark" id="dbModal" tabindex="-1" aria-labelledby="dbModalaria" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form id="dbForm" action="./AddDatabase" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title "  id="exampleModalLabel">Ajouter base de donnée:</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">

                                    <div class="form-group">
                                        <label for="db">Agence:</label>
                                        <input type="text" class="form-control" id="" name="name" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="db">Hôte:</label>
                                        <input type="text" class="form-control" id="" name="host" placeholder="0.0.0.0" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="db">Port:</label>
                                        <input type="number" class="form-control" id="" name="port" required min="1">
                                    </div>
                                    <div class="form-group">
                                        <label for="db">Database:</label>
                                        <input type="text" class="form-control" id="" name="database"  required>
                                    </div>
                                    <div class="form-group">
                                        <label for="db">Utilisateur:</label>
                                        <input type="text" class="form-control" id="" name="username"  required>
                                    </div>
                                    <div class="form-group">
                                        <label for="db">Mot de passe:</label>
                                        <input type="text" class="form-control" id="" name="password"  required>
                                    </div>

                                    <div class="form-group">
                                        <input type="hidden" class="form-control" id="type" name="type" value="db">
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
                                    <script>
                                        $(document).ready(function (){
                                            $("#<%=request.getParameter("type") %>Btn").click();
                                        });
                                        
                                        
                                    </script>
    </body>

</html>
