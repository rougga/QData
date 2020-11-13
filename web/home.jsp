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
            <%                    String err = "", data = "[", lable = "[";
                String data2 = "[", labels2 = "[";
                long wait = 0;
                long goal = 0;
                String waitPer = "0";
                String goalPer = "0";
                String waitCss = "bg-success";
                String goalCss = "bg-danger";
                String waitClass = "is-valid";
                String goalClass = "is-invalid";
                CfgHandler cfg = new CfgHandler(request);
                PgConnection con=null;
                try {
                     con = new PgConnection();
                }
                
                catch(Exception e){
            %><%= err=e.toString()%><%
                }
                try{
                    ResultSet biz_tik = con.getStatement().executeQuery("select count(*) as wait from t_ticket where status=0 and to_date(to_char(ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')=TO_DATE('" + new TableGenerator().getFormat().format(new Date()) + "','YYYY-MM-DD')");
                    if (biz_tik.next()) {
                        wait = biz_tik.getLong("wait");
                        float per;
                        if(Objects.equals(cfg.getPropertie("maxA"), "0")) {
                            per=(float)100; 
                        }
                        else{ 
                           per = ((float) goal / Long.parseLong(cfg.getPropertie("maxA"))) * 100;
                        }
                        if (per >= 100) {
                            waitPer = "100";
                            waitCss = "bg-danger";
                            waitClass = "is-invalid";
                        } else {
                            if (per > 70) {
                                waitCss = "bg-danger";
                                waitClass = "is-invalid";
                            } else if (per > 50) {
                                waitCss = "bg-warning";
                                waitClass = "is-invalid";
                            }
                            waitPer = Math.floor(per) + "";
                        }

                    } else {
                        wait = 0;

                    }
                    biz_tik = con.getStatement().executeQuery("select count(*) as wait from t_ticket where status=4 and to_date(to_char(ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')=TO_DATE('" + new TableGenerator().getFormat().format(new Date()) + "','YYYY-MM-DD')");
                    if (biz_tik.next()) {
                        goal = biz_tik.getLong("wait");
                        float per;
                        if(Objects.equals(cfg.getPropertie("goalT"), "0")) {
                            per=(float)100; 
                        }
                        else{ 
                           per = ((float) goal / Long.parseLong(cfg.getPropertie("goalT"))) * 100;
                        }
                        if (per >= 100) {
                            goalPer = "100";
                            goalCss = "bg-success";
                            goalClass = "is-valid";
                        } else {
                            if (per > 70) {
                                goalCss = "bg-success";
                                goalClass = "is-valid";
                            } else if (per > 50) {
                                goalCss = "bg-warning";
                                goalClass = "is-valid";
                            }
                            goalPer = Math.floor(per) + "";
                        }
                    } else {
                        goal = 0;
                    }
                    List<ArrayList<String>> table = new TableGenerator().generateGblTable(request, null, null, session.getAttribute("db")+"");
                    for(int i =0; i< table.size()-1;i++){
                        lable+="'"+table.get(i).get(0)+"',";
                        data+=table.get(i).get(2)+",";
                    }
                    if(lable.lastIndexOf(",")>=0){
                        lable=lable.substring(0, lable.lastIndexOf(","));
                    }
                    if(data.lastIndexOf(",")>=0){
                        data=data.substring(0, data.lastIndexOf(","));
                    }
                    lable+="]";
                    data+="]";
                    con.closeConnection();
                } 
                catch (Exception e) {
                con.closeConnection();
            %><%= e.toString()%><%
                }
                
                Stats stat = new Stats();
            %>
            <div class="d-flex justify-content-center  justify-content-md-between flex-md-row flex-column align-items-start">
                <div class="col-12 col-md-6">
                    <div class="mt-4  mb-md-5">
                        <h3 class="text-white">Ticket en attente <span class="badge badge-pill appColor"><%= wait + " / " + cfg.getPropertie("maxA")%></span>:</h3>
                        <div class="progress" style="height: 40px;">
                            <div class="progress-bar <%= waitCss%> font-weight-bold progress-bar-striped progress-bar-animated " role="progressbar" style="width: <%= waitPer%>%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"><%= wait + " / " + cfg.getPropertie("maxA")%></div>
                        </div>
                    </div>
                    <form class="text-white border border-white rounded my-1" style="min-height: 232px" >
                        <h4 class="text-center p-1 my-0">Nb. Tickets en attente:</h4>
                        <%
                            try{
                            List<ArrayList> table = stat.getWaitingTicketsByService(null, null,response);
                            
                            for(int i =0;i<table.size();i++){
                        %>
                        <div class="form-group d-flex justify-content-center align-items-center flex-md-row flex-column my-0">
                            <label for="validationDefaultUsername" class="col-12 col-md-5 text-md-right font-weight-bold my-0"><%=  table.get(i).get(0) %>:</label>
                            <div class="input-group col-12 col-md-6 ">
                                <input type="text" class="form-control bg-dark text-white text-md-right  font-weight-bold my-1"   disabled value="<%=  table.get(i).get(1) %>">
                            </div>
                        </div>

                        <%
                    }
                    }catch(Exception e){
                        %><%=e.getMessage()%><%
                        }
                            
                        %>
                    </form>
                    <div class="border border-white rounded p-2">
                        <h4 class="text-white mx-1 text-center">Pourcentage de ticket traité par service:</h4>
                        <canvas id="myChart" height="133" ></canvas>
                    </div>

                    <script>
                        var ctx = document.getElementById('myChart').getContext('2d');
                        var myChart = new Chart(ctx, {
                            type: 'doughnut',
                            data: {
                                labels: <%= lable%>,
                                datasets: [{
                                        label: '# of Votes',
                                        data: <%= data %>,
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
                            }
                        });
                    </script>

                </div>
                <div class="col-12 col-md-6 ">

                    <div class="mt-4 mb-md-5">
                        <h3 class="text-white">Totale de ticket Traité <span class="badge badge-pill appColor"><%= goal + " / " + cfg.getPropertie("goalT")%></span>:</h3>
                        <div class="progress " style="height: 40px;">
                            <div class="progress-bar <%= goalCss%> font-weight-bold  progress-bar-striped progress-bar-animated"" role="progressbar" style="width: <%= goalPer%>%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"> <%= goal + " / " + cfg.getPropertie("goalT")%></div>
                        </div>
                    </div>
                    <form class="text-white border border-white rounded" >

                        <h4 class="text-center"><img src="./img/agence.png" class="img-fluid mx-auto my-3"> <%= session.getAttribute("db")%></h4>

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
                                <input type="text" class="form-control bg-dark text-white text-md-right <%= goalClass %>" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getDealTicket(null, null)%>">
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
                                <input type="text" class="form-control bg-dark text-white text-md-right <%= waitClass %>" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" disabled value="<%= stat.getWaitingTicket(null, null)%>">
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
    </body>
</html>
