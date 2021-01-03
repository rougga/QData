<%@page import="java.util.Map"%>
<%@page import="javax.swing.text.TabExpander"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="main.Stats"%>
<%@page import="main.TableGenerator"%>
<%@page import="java.util.Date"%>
<%@page import="main.CfgHandler"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="main.PgConnection"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
Stats stat=new Stats();
Map chart= stat.getTotalDealChart();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>QData</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="./img/favicon-32x32.png">
        <script src="./js/jquery.js"></script>
        <link href="./css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="./js/bootstrap.bundle.min.js"></script>
        <script src="./js/moment.min.js"></script>
        <script src="./js/Chart.min.js"></script>
        <link href="./css/Chart.min.css" rel="stylesheet" type="text/css"/>
        <link href="./css/body.css" rel="stylesheet" type="text/css"/>
        <link href="./css/navbar.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container-lg">
            <div>
                <%@include file="./addon/navbar.jsp" %>
            </div>

            <div class="row">
                <div class="col-12 col-md-6 mt-4 pt-4">
                    <div class="border border-white rounded p-2">
                        <h4 class="text-white mx-1 text-center">Pourcentage de ticket traité par Agence:</h4>
                        <canvas id="myChart"  height="260"></canvas>
                    </div>

                    <script>
                        var ctx = document.getElementById('myChart').getContext('2d');
                        var myChart = new Chart(ctx, {
                            type: 'doughnut',
                            data : {
                                labels: <%= chart.get("lables") %>,
                                datasets: [{
                                        label: '# of Votes',
                                        data: <%= chart.get("data") %>,
                                        backgroundColor: [
                                            'rgba(255, 99, 132, 0.2)',
                                            'rgba(54, 162, 235, 0.2)',
                                            'rgba(255, 206, 86, 0.2)',
                                            'rgba(75, 192, 192, 0.2)',
                                            'rgba(153, 102, 255, 0.2)',
                                            'rgba(255, 159, 64, 0.2)'
                                        ],
                                        borderColor: [
                                            'rgba(255, 99, 132, 1)',
                                            'rgba(54, 162, 235, 1)',
                                            'rgba(255, 206, 86, 1)',
                                            'rgba(75, 192, 192, 1)',
                                            'rgba(153, 102, 255, 1)',
                                            'rgba(255, 159, 64, 1)'
                                        ],
                                        borderWidth: 1
                                    }]
                            },
                            options: {
                                    legend: {
                                        labels: {
                                            fontColor: 'lightgrey'
                                        }
                                    }
                            }       
                        });
                    </script>

                </div>
                <div class="col-12 col-md-6 mt-4 pt-4">
                    <form class="text-white border border-white rounded" >

                        <h4 class="text-center"><img src="./img/agence.png" class="img-fluid mx-auto my-3"> TOTALE d'aujourd'hui
                            <div class='spinner-grow text-white' role='status'>
                                <span class='sr-only'>Chargement...</span>
                            </div>
                        </h4>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column mx-auto">
                            <label for="validationDefaultUsername" class="col-12 col-md-5 text-md-right">Tickets édités:</label>
                            <div class="input-group col-12 col-md-6">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/ticket.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getTotalTicket(null, null)%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column mx-auto">
                            <label for="validationDefaultUsername" class="col-md-5  text-md-right">Tickets traités:</label>
                            <div class="input-group  col-md-6">
                                <div class="input-group-prepend">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/done.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right " id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getDealTicket(null, null)%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Tickets absents:</label>
                            <div class="input-group  col-md-6">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/x.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getAbsentTicket(null, null)%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Tickets sans affectation:</label>
                            <div class="input-group  col-md-6">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/ticket.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right " id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getWaitingTicket(null, null)%>">
                            </div>
                        </div>
                        <div class="form-group  d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Moyenne d'attente:</label>
                            <div class="input-group col-md-6 ">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark " id="inputGroupPrepend2"><img src="img/icon/wait.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getAvgWaitTime(null, null)%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Moyenne de traitement:</label>
                            <div class="input-group  col-md-6">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/done.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getAvgDealTime(null, null)%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Max. Attente:</label>
                            <div class="input-group  col-md-6">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/max.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getMaxWaitTime(null, null)%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Max. Traitement:</label>
                            <div class="input-group  col-md-6 ">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/max.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getMaxDealTime(null, null)%>">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script>
             var filterDate = function(date) {
                if (date){
                     return date;
                }
             else{
                return moment().format("YYYY-MM-DD");
                }
            };
             var updateLinks = function(){
                var ids = JSON.parse(sessionStorage.getItem("dbs"));
                var date1 = filterDate(sessionStorage.getItem("date1"));
                var date2 =filterDate(sessionStorage.getItem("date2")); 
                var agencesLink ="";
                if(ids){ 
                    for(i=0;i<ids.length;i++){
                        agencesLink+= "&agences="+ids[i];
                    }
                }
                agencesLink+="&date1="+date1+"$date2="+date2;
                $.each($(".d"),function(i,v) {
                    var link=$(v).attr("href");
                    link= link.substring(0,link.indexOf("d=d")+3);
                    link+=agencesLink;
                    $(v).attr("href",link);
                });
                console.log(ids);
            };
            updateLinks();
        </script>
    </body>
</html>
