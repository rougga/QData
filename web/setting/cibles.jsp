<%@page import="main.modal.Service"%>
<%@page import="main.modal.Cible"%>
<%@page import="main.controller.CibleController"%>
<%@page import="main.controller.ServiceController"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="main.modal.Agence"%>
<%@page import="java.util.List"%>
<%@page import="main.controller.AgenceController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
public String getFormatedTime(double Sec) {
int hours = (int) (Sec / 3600);
int minutes = (int) ((Sec % 3600) / 60);
int seconds = (int) (Sec % 60);
return String.format("%02d:%02d:%02d", hours, minutes, seconds);
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
        <script src="../js/setting/cible.js"></script>
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
                    <h1 class="text-white text_center ">
                        Les cibles :
                        <span class=" float-right">
                            <a class="btn btn-success" id="cibleAdd" data-toggle="modal" data-target="#cibleModal"><img src="/QData/img/icon/plus.png"> Ajouter</a>

                        </span>
                    </h1>
                    <table class="table table-bordered table-light   table-responsive-sm border-dark" id="dbTable">
                        <thead class="appColor border-dark">
                            <tr>
                                <th scope="col">Site</th>
                                <th scope="col">Service</th>
                                <th scope="col">Cible d'attente</th>
                                <th scope="col">Cible de traitement</th>
                                <th scope="col">depasse cible %</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>

                        <tbody class="font-weight-bold">
                            <%
                               AgenceController ac =new AgenceController();
                               ServiceController sc = new ServiceController();
                               CibleController cc = new CibleController();
                              List<Cible> table=cc.getAll();
                              if(table!=null){
                                  for(int i =0;i<table.size();i++){
                            %>
                            <tr class="clickable-row5 border-dark">
                                <td class="border-dark align-middle" data-id="<%= table.get(i).getDb_id() %>">
                                    <%= ac.getAgenceById(table.get(i).getDb_id()).getName() %>
                                </td>
                                <td class="border-dark align-middle">
                                    <%= sc.getById(table.get(i).getBiz_type_id(), table.get(i).getDb_id()).getName() %>
                                </td>
                                <td class="border-dark align-middle"><%=getFormatedTime(table.get(i).getCibleA()) %></td>
                                <td class="border-dark align-middle"><%=getFormatedTime(table.get(i).getCibleT()) %></td>
                                <td class="border-dark align-middle"><%=table.get(i).getdCible() %>%</td><td class="border-dark align-middle">
                                    <a class="btn btn-danger m-0" id="dbDlt" href="/QData/DeleteCible?id=<%= table.get(i).getBiz_type_id()%>&db_id=<%= table.get(i).getDb_id() %>"><img src="/QData/img/icon/trash.png"></a>
                                </td>
                            </tr>
                            <%
                                 }
                               }else{
                            %><%="<h4 class='text-center text-danger'>No database</h4>"%><%
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
                            <form id="cibleForm" action="/QData/Add" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title "  id="exampleModalLabel">Modal title</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <label for="site">Site:</label>
                                        <select class="form-control" id="site" name="site" required>

                                            <option selected disabled >Sélectionner Site:</option>
                                            <%
                                                List<Agence> agences = new AgenceController().getAllAgence();
                                                if(agences!=null && agences.size()>0){
                                                    for (int i = 0; i < agences.size(); i++) {
                                            %>          
                                            <option value="<%= agences.get(i).getId().toString() %>" >
                                                <%= agences.get(i).getName() %>
                                            </option>

                                            <%
                                                    }
                                                }
                                                
                                                
                                            %>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label for="service">Service:</label>
                                        <select class="form-control" id="service" name="service" required>

                                            <option selected disabled >Sélectionner Site:</option>
                                            <%
                                                List<Service> services = new ServiceController().getAll();
                                                if(services!=null && services.size()>0){
                                                    for (int i = 0; i < services.size(); i++) {
                                            %>          
                                            <option value="<%= services.get(i).getId()%>" data-db-id="<%= services.get(i).getDb_id() %>">
                                                <%= services.get(i).getName() %>
                                            </option>

                                            <%
                                                    }
                                                }
                                                
                                                
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="cibleA">Cible d'attente:</label><br>
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleAH" name="cibleAH" placeholder="Hr" min="0" max="23" value="0" required>
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleAM" name="cibleAM" placeholder="Min" min="0" max="60" value="0" required>
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleAS" name="cibleAS" placeholder="Sec" min="0" max="60" value="0" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="cibleT">Cible Traitement:</label><br>
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleTH" name="cibleTH" placeholder="Hr" min="0" max="23" value="0" required>
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleTM" name="cibleTM" placeholder="Min" min="0" max="60" value="0" required>
                                        <input type="number" class="form-control w-25 d-inline-block" id="cibleTS" name="cibleTS" placeholder="Sec" min="0" max="60" value="0" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="cibleD">%dépasse. Cible:</label>
                                        <input type="number" class="form-control" id="cibleD" name="cibleD" min="0" max="100" value="0" required>
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
            </div>
        </div>
    </body>
</html>
