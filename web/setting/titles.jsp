<%@page import="main.handler.TitleHandler"%>
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
            <div class="mt-4">
                <%                     
                    String err=request.getParameter("err");
                    if (err != "" && err != null) {

                %>
                <%= "<div class='alert alert-danger alert-dismissible fade show' role='alert'><b>"
                        + err
                        + "</b><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>"%>
                <%
                    }

                    TitleHandler th = new TitleHandler(request);
                %>
                <div class="w-100">
                    <h1 class="text-white text-center">Modifier les titre des tables:</h1>
                    <form id="ttlForm" action="/QData/EditTitles" method="POST" class="bg-white p-2 my-4 border rounded w-75 mx-auto font-weight-bold" >
                        <div class="form-group row">
                            <label for="gbl" class="col-sm-3 col-form-label text-dark">Globale:</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control border border-dark" id="gbl" name="gbl" required value="<%= th.getGblTitle()%>">
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="emp" class="col-sm-3 col-form-label text-dark">Employée:</label>
                            <div class="col-sm-9">                                
                                <input type="text" class="form-control border border-dark" id="emp" name="emp" required value="<%= th.getEmpTitle()%>">       
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="empser" class="col-sm-3 col-form-label text-dark">Employée-Service:</label>
                            <div class="col-sm-9">                                
                                <input type="text" class="form-control border border-dark" id="empser" name="empser" required value="<%= th.getEmpSerTitle()%>">         
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="gch" class="col-sm-3 col-form-label text-dark">Guichet:</label>
                            <div class="col-sm-9">                                
                                <input type="text" class="form-control border border-dark" id="gch" name="gch" required value="<%= th.getGchTitle() %>">               
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="gchserv" class="col-sm-3 col-form-label text-dark">Guichet-Service:</label>
                            <div class="col-sm-9">                                
                                <input type="text" class="form-control border border-dark" id="gchserv" name="gchserv" required value="<%= th.getGchServTitle() %>">                   
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="gla" class="col-sm-3 col-form-label text-dark">Grille attente:</label>
                            <div class="col-sm-9">                              
                                <input type="text" class="form-control border border-dark" id="gla" name="gla" required value="<%= th.getGlaTitle() %>">                  
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="glt" class="col-sm-3 col-form-label text-dark">Grille traitement:</label>
                            <div class="col-sm-9">                               
                                <input type="text" class="form-control border border-dark" id="glt" name="glt" required value="<%= th.getGltTitle() %>">                  
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="apl" class="col-sm-3 col-form-label text-dark">Détail des appels:</label>
                            <div class="col-sm-9">                               
                                <input type="text" class="form-control border border-dark" id="apl" name="apl" required value="<%= th.getAplTitle() %>">                  
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="nbt" class="col-sm-3 col-form-label text-dark">Nb. edités:</label>
                            <div class="col-sm-9">                                
                                <input type="text" class="form-control border border-dark" id="nbt" name="ndt" required value="<%= th.getNdtTitle() %>">             
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="nbtt" class="col-sm-3 col-form-label text-dark">Nb. traités:</label>
                            <div class="col-sm-9">                               
                                <input type="text" class="form-control border border-dark" id="nbtt" name="ndtt" required value="<%= th.getNdttTitle() %>">               
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="nbta" class="col-sm-3 col-form-label text-dark">Nb. absents:</label>
                            <div class="col-sm-9">                                
                                <input type="text" class="form-control border border-dark" id="nbta" name="ndta" required value="<%= th.getNdtaTitle() %>">                
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="nbtsa" class="col-sm-3 col-form-label text-dark">Nb. sans affectation:</label>
                            <div class="col-sm-9">                               
                                <input type="text" class="form-control border border-dark" id="nbtsa" name="ndtsa" required value="<%= th.getNdtsaTitle() %>">                 
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="cnx" class="col-sm-3 col-form-label text-dark">Rendement - Connexions:</label>

                            <div class="col-sm-9">                               
                                <input type="text" class="form-control border border-dark" id="cnx" name="cnx" required value="<%= th.getCnxTitle() %>">                      
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="remp" class="col-sm-3 col-form-label text-dark">Rendement - Employés:</label>
                            <div class="col-sm-9">                                
                                <input type="text" class="form-control border border-dark disabled" id="remp" name="remp" required value="<%= th.getRempTitle() %>">          
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="sgch" class="col-sm-3 col-form-label text-dark">Supervision - Guichet:</label>
                            <div class="col-sm-9">                               
                                <input type="text" class="form-control border border-dark disabled" id="sgch" name="sgch" required value="<%= th.getSgchTitle() %>">            
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="ser" class="col-sm-3 col-form-label text-dark">Supervision - Services:</label>
                            <div class="col-sm-9">                               
                                <input type="text" class="form-control border border-dark disabled" id="ser" name="ser" required value="<%= th.getSerTitle() %>">                 
                            </div>
                        </div>
                            
                        <div class="form-group row">
                            <label for="tch" class="col-sm-3 col-form-label text-dark">Rapport Tache:</label>
                            <div class="col-sm-9">                               
                                <input type="text" class="form-control border border-dark disabled" id="tch" name="tch" required value="<%= th.getTaskTitle() %>">                 
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-sm-10">
                                <button type="submit" class="btn btn-success float-right" >Sauvegarder</button>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>
        <div class="footer">

        </div>
    </body>
</html>
