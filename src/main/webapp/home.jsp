<%@page import="java.util.Map"%>
<%@page import="javax.swing.text.TabExpander"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="ma.rougga.qdata.Stats"%>
<%@page import="java.util.Date"%>
<%@page import="ma.rougga.qdata.CfgHandler"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="ma.rougga.qdata.PgConnection"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
        <style>
            .min-hieght{
                min-height: 510px;
            }
        </style>
    </head>
    <body>
        <div class="">
            <div>
                <%@include file="./addon/navbar.jsp" %>
            </div>
            <%                Stats stat = new Stats();
                Map chart = stat.getTotalDealChart();
            %>
            <div>
                <%                    String err = request.getParameter("err");
                    if (err != "" && err != null) {

                %>
                <%= "<div class='alert alert-danger alert-dismissible fade show' role='alert'><b>"
                        + err
                        + "</b><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>"%>
                <%
                    }
                %>
            </div>
            <div class="d-md-flex justify-content-center mt-2">
                <button  class="btn btn-success" id="majNowBtn">
                    <img src="./img/icon/reload.png" class="" >
                    Forcer la Mise à jour d'aujourd'hui maintenant
                </button>
                <div class="spinner-border loadingSpinner text-white" role="status">
                    <span class="sr-only">Loading...</span>
                </div>
            </div>
            <div class="d-md-flex justify-content-center px-4  mt-4">
                <div class="col-12 col-md-6 min-hieght">
                    <div class="border border-white rounded p-2 min-hieght">
                        <h4 class="text-white mx-1 text-center">Pourcentage de ticket traité par Agence:</h4>
                        <canvas id="myChart"  height=""></canvas>
                    </div>
                    <script>
                        var ctx = document.getElementById('myChart').getContext('2d');
                        var myChart = new Chart(ctx, {
                            type: 'doughnut',
                            data: {
                                labels: <%= chart.get("lables")%>,
                                datasets: [{
                                        label: '# of Votes',
                                        data: <%= chart.get("data")%>,
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
                <div class="col-12 col-md-6 min-hieght">
                    <form class="text-white border border-white rounded min-hieght" >

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
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getTotalTicket()%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column mx-auto">
                            <label for="validationDefaultUsername" class="col-md-5  text-md-right">Tickets traités:</label>
                            <div class="input-group  col-md-6">
                                <div class="input-group-prepend">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/done.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right " id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getDealTicket()%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Tickets absents:</label>
                            <div class="input-group  col-md-6">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/x.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getAbsentTicket()%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Tickets sans affectation:</label>
                            <div class="input-group  col-md-6">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/ticket.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right " id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getWaitingTicket()%>">
                            </div>
                        </div>
                        <div class="form-group  d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Moyenne d'attente:</label>
                            <div class="input-group col-md-6 ">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark " id="inputGroupPrepend2"><img src="img/icon/wait.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= CfgHandler.getFormatedTimeFromSeconds(stat.getAvgWaitTime())%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Moyenne de traitement:</label>
                            <div class="input-group  col-md-6">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/done.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= CfgHandler.getFormatedTimeFromSeconds(stat.getAvgDealTime())%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Max. Attente:</label>
                            <div class="input-group  col-md-6">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/max.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= CfgHandler.getFormatedTimeFromSeconds(stat.getMaxWaitTime())%>">
                            </div>
                        </div>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column">
                            <label for="validationDefaultUsername" class="col-md-5 text-md-right ">Max. Traitement:</label>
                            <div class="input-group  col-md-6 ">
                                <div class="input-group-prepend ">
                                    <span class="input-group-text bg-dark" id="inputGroupPrepend2"><img src="img/icon/max.png"/></span>
                                </div>
                                <input type="text" class="form-control bg-dark text-white text-md-right" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= CfgHandler.getFormatedTimeFromSeconds(stat.getMaxDealTime())%>">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script>
            var filterDate = function (date) {
                if (date) {
                    return date;
                } else {
                    return moment().format("YYYY-MM-DD");
                }
            };
            var updateLinks = function () {
                var ids = JSON.parse(sessionStorage.getItem("dbs"));
                var date1 = filterDate(sessionStorage.getItem("date1"));
                var date2 = filterDate(sessionStorage.getItem("date2"));
                var agencesLink = "";
                if (ids) {
                    for (i = 0; i < ids.length; i++) {
                        agencesLink += "&agences=" + ids[i];
                    }
                }
                agencesLink += "&date1=" + date1 + "&date2=" + date2;
                $.each($(".d"), function (i, v) {
                    var link = $(v).attr("href");
                    link = link.substring(0, link.indexOf("d=d") + 3);
                    link += agencesLink;
                    $(v).attr("href", link);
                });
                console.log(ids);
            };
            history.replaceState({page: 1}, 'title', "?err=");
            function initLoader() {
                $("#majNowBtn").addClass("disabled");
                $("#majNowBtn").removeClass("btn-success").addClass("btn-dark");
                $(".loadingSpinner").removeClass("d-none").addClass("d-flex");
                console.log("loading");
            }
            function removeLoader() {
                $("#majNowBtn").removeClass("disabled");
                $("#majNowBtn").removeClass("btn-dark").addClass("btn-success");
                $(".loadingSpinner").removeClass("d-flex").addClass("d-none");
            }
            $(".majBtn").on("click", function () {
                initLoader();
            });
            $(document).ready(function () {
                removeLoader();
                updateLinks();
                
                $("#majNowBtn").on('click', function () {
                    initLoader();
                    $.get("/QData/updateajax", function (data) {
                        removeLoader();
                    });
                });
                
            });
            
            
            
        </script>
    </body>
</html>
