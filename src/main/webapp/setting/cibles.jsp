<%@page import="java.net.URLEncoder"%>
<%@page import="ma.rougga.qdata.modal.Service"%>
<%@page import="ma.rougga.qdata.modal.Cible"%>
<%@page import="ma.rougga.qdata.controller.CibleController"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="ma.rougga.qdata.modal.Agence"%>
<%@page import="java.util.List"%>
<%@page import="ma.rougga.qdata.controller.AgenceController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (!Objects.equals(session.getAttribute("grade"), "adm")) {
        response.sendRedirect("/"+CfgHandler.APP+"/home.jsp?err="+ URLEncoder.encode("vous avez besoin des privilèges d'administrateur", "UTF-8"));
        return;
    }

%>
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
        <div class="">
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

                <div class="container" id="dbTbl">
                    <h1 class="text-white text_center ">
                        Les cibles :
                    </h1>
                    <table class="table table-bordered table-light   table-responsive-sm border-dark" id="dbTable">
                        <thead class="appColor border-dark">
                            <tr>
                                <th scope="col">Site</th>
                                <th scope="col">Service</th>
                                <th scope="col">Cible d'attente</th>
                                <th scope="col">Cible de traitement</th>
                                <th scope="col">depasse cible %</th>
                            </tr>
                        </thead>

                        <tbody class="font-weight-bold">
                            <%
                               AgenceController ac =new AgenceController();
                               CibleController cc = new CibleController();
                               List<Cible> table=cc.getAll();
                              if(table!=null){
                                  for(Cible cible :table ){
                            %>
                            <tr class="clickable-row5 border-dark">
                                <td class="border-dark align-middle" data-id="<%= cible.getAgence_id() %>">
                                    <%= ac.getAgenceById(cible.getAgence_id()).getName() %>
                                </td>
                                <td class="border-dark align-middle">
                                    <%= cible.getService_name() %>
                                </td>
                                <td class="border-dark align-middle"><%= cible.getCibleA() %></td>
                                <td class="border-dark align-middle"><%= cible.getCibleT() %></td>
                                <td class="border-dark align-middle"><%= cible.getCiblePer() %>%</td>
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
        </div>
    </body>
</html>
