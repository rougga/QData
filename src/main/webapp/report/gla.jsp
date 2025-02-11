<%@page import="ma.rougga.qdata.controller.report.GlaTableController"%>
<%@page import="ma.rougga.qdata.modal.report.GlaRow"%>
<%@page import="ma.rougga.qdata.controller.TitleController"%>
<%@page import="ma.rougga.qdata.modal.Title"%>
<%@page import="java.util.Map"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ma.rougga.qdata.CfgHandler"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String type = "gla";
    String[] agences = request.getParameterValues("agences");
    Title Title = new TitleController().getTitleByType(type);
    String date1 = (request.getParameter("date1") == null) ? CfgHandler.format.format(new Date()) : request.getParameter("date1");
    String date2 = (request.getParameter("date2") == null) ? CfgHandler.format.format(new Date()) : request.getParameter("date2");
    List<Map> table = new GlaTableController().getTableAsList(date1, date2, agences);
    GlaRow totaleRow = new GlaRow();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>QData - <%= Title.getValue()%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="/<%= CfgHandler.APP%>/img/favicon-32x32.png">
        <script src="/<%= CfgHandler.APP%>/js/jquery.js"></script>
        <link href="/<%= CfgHandler.APP%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="/<%= CfgHandler.APP%>/js/bootstrap.bundle.min.js"></script>
        <link href="/<%= CfgHandler.APP%>/css/navbar.css" rel="stylesheet" type="text/css"/> 
        <link href="/<%= CfgHandler.APP%>/css/body.css" rel="stylesheet" type="text/css"/>
        <script src="/<%= CfgHandler.APP%>/js/echarts-en.min.js"></script>
        <script src="/<%= CfgHandler.APP%>/js/moment.min.js"></script>
        <script src="/<%= CfgHandler.APP%>/js/report.js"></script>
        <style>
            .db{
                max-width: 40%;
            }
        </style>
    </head>
    <body>
        <div class=" bg-dark  h-100 p-0">

            <div class="head">
                <%@include file="../addon/navbar.jsp" %>
                <script>
                    $("#home").removeClass("active");
                    $(".<%=type%>").addClass("active");
                </script>
            </div>
            <div class="body ">
                <%                     if (request.getParameter("err") != "" && request.getParameter("err") != null) {

                %>
                <%= "<div class='alert alert-danger alert-dismissible fade show' role='alert'><b>"
                        + request.getParameter("err")
                        + "</b><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>"%>
                <%
                    }
                %>


                <h2 class="text-center p-4"><%= Title.getValue()%></h2>
                <div>
                    <%@include file="../addon/report/defaulthtml.jsp" %>
                </div>
                <div class="px-4">
                    <a class="float-right btn btn-link text-white" id="plus">PLUS >></a>
                    <div class="table-responsive">
                        <table class="table table-light table-bordered table-striped  ">
                            <thead class="appColor">
                                <tr class="">
                                    <th class="col 0 text-wrap text-center align-middle db" style="">Site</th>

                                    <th class="col 1  text-center align-middle" style="">Service</th>

                                    <th class="col 2 text-wrap text-center align-middle" style="">0-15s</th>

                                    <th class="col 3 text-wrap text-center align-middle" style="">15s-30s</th>

                                    <th class="col 4 text-wrap text-center align-middle" style="">30s-1min</th>

                                    <th class="col 5 text-wrap text-center align-middle" style="">1min-1min30s</th>

                                    <th class="col 6 text-wrap text-center align-middle" style="">1min30s-2min</th>

                                    <th class="col 7 text-wrap text-center align-middle" style="">2-5min</th>

                                    <th class="col 8 text-wrap text-center align-middle" style="display: none;">0-5min</th>

                                    <th class="col 9 text-wrap text-center align-middle" style="">5-10min</th>

                                    <th class="col 10 text-wrap text-center align-middle" style="">10-20min</th>

                                    <th class="col 11 text-wrap text-center align-middle" style="">20-30min</th>

                                    <th class="col 12 text-wrap text-center align-middle" style="">30-45min</th>

                                    <th class="col 13 text-wrap text-center align-middle" style="">45-50min</th>

                                    <th class="col 14 text-wrap text-center align-middle" style="">&gt; 50min</th>

                                    <th class="col 15 text-wrap text-center align-middle" style="">Total</th>

                                </tr>
                            </thead>
                            <tbody  class="font-weight-bold ">

                                <%    if (!table.isEmpty() && table != null) {

                                        for (Map agence : table) {
                                %>
                                <%
                                    Object empsObj = agence.get("emps");
                                    if (empsObj instanceof List<?>) {
                                        List<GlaRow> emps = (ArrayList<GlaRow>) empsObj;
                                        totaleRow = emps.get(emps.size() - 1);
                                        for (GlaRow emp : emps) {
                                %>


                                <tr class="" data-id="<%= agence.get("agence_id")%>">
                                    <th scope="row" class="text-center text-wrap align-middle border-dark 0 db <%= agence.get("agence_name")%>" data-id="<%= agence.get("agence_id")%>"><%= agence.get("agence_name")%></th>

                                    <td class="text-left border-dark 1 <%= emp.getServiceName()%>" style=""><%= emp.getServiceName()%> </td>

                                    <td class="text-left border-dark 2" style=""><%= emp.getS0_15()%></td>

                                    <td class="text-left border-dark 3" style=""><%= emp.getS15_30()%></td>

                                    <td class="text-left border-dark 4" style=""><%= emp.getS30_60()%></td>

                                    <td class="text-left border-dark 5" style=""><%= emp.getS60_90()%></td>

                                    <td class="text-left border-dark 6" style=""><%= emp.getS90_120()%></td>

                                    <td class="text-left border-dark 7" style=""><%= emp.getS120()%></td>

                                    <td class="text-left border-dark 8" style=""><%= emp.getM0_5()%></td>

                                    <td class="text-left border-dark 9" style=""><%= emp.getM5_10()%></td>

                                    <td class="text-left border-dark 10" style=""><%= emp.getM10_20()%></td>

                                    <td class="text-left border-dark 11" style=""><%= emp.getM20_30()%></td>

                                    <td class="text-left border-dark 12" style=""><%= emp.getM30_45()%></td>

                                    <td class="text-left border-dark 13" style=""><%= emp.getM45_50()%></td>

                                    <td class="text-left border-dark 14" style=""><%= emp.getM50()%></td>

                                    <td class="text-left border-dark 15" style=""><%= emp.getTotal()%></td>

                                </tr>

                                <%}
                                            }
                                        }
                                    }
                                %>


                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="footer  px-4 pb-4">
                <div id='graphs' class='text-white bg-white ' style='height:400px'></div>

                <script type='text/javascript'>

                    var myChart = echarts.init(document.getElementById('graphs'));
                    var option = {
                        title: {
                            text: "<%= Title.getValue()%>  De  <%= date1%>  Au  <%= date2%>"
                                    },
                                    tooltip: {
                                        trigger: 'axis'
                                    },
                                    legend: {

                                    },
                                    toolbox: {
                                        show: true,
                                        itemSize: '20',
                                        itemGrap: '20',
                                        feature: {
                                            mark: {show: true},
                                            magicType: {show: true, type: ['line', 'bar']},
                                            saveAsImage: {show: true, title: 'Sauvegarder'}
                                        },
                                        iconStyle: {color: 'black'}
                                    },
                                    calculable: true,
                                    xAxis: [
                                        {
                                            type: 'category',
                                            interval: 0,
                                            nameLocation: 'center',
                                            data: ["0-15s", "15s-30s", "30s-1min", "1min-1min30s", "1min30s-2min", "2-5min", "5-10min", "10-20min", "20-30min", "30-45min", "45-50min", "> 50min"],
                                            axisLabel: {
                                                show: true,
                                                interval: 0,
                                                rotate: 30,
                                                fontWeight: 'bold'
                                            },
                                            axisTick: {
                                                alignWithLabel: true
                                            }
                                        }
                                    ],
                                    yAxis: [
                                        {
                                            type: 'value',
                                            name: 'Nb. Tickets',
                                            nameTextStyle: {
                                                fontWeight: 'bold'
                                            },
                                            axisLabel: {
                                                fontWeight: 'bold'
                                            }
                                        }
                                    ],
                                    series: [
                                        {
                                            name: '',
                                            type: 'bar',
                                            data: [
                    <%= totaleRow.getS0_15()%>,
                    <%= totaleRow.getS15_30()%>,
                    <%= totaleRow.getS30_60()%>,
                    <%= totaleRow.getS60_90()%>,
                    <%= totaleRow.getS90_120()%>,
                    <%= totaleRow.getS120()%>,
                    <%= totaleRow.getM5_10()%>,
                    <%= totaleRow.getM10_20()%>,
                    <%= totaleRow.getM20_30()%>,
                    <%= totaleRow.getM30_45()%>,
                    <%= totaleRow.getM45_50()%>,
                    <%= totaleRow.getM50()%>
                                            ],
                                            itemStyle: {
                                                normal: {
                                                    color: '#14b6fa'
                                                }
                                            }
                                        }
                                    ]
                                };
                                myChart.setOption(option);
                                $(window).on('resize', function () {
                                    myChart.resize();
                                });

                </script>
                <div>
                    <div class='div-wrapper d-flex justify-content-center align-items-center p-2'>
                        <a type='button' class='btn btn-success m-2' id='excel' href="/<%=CfgHandler.APP%>/exportexcel?type=<%= type%>&date1=<%= date1%>&date2=<%= date2%>&agence_id=">
                            <img src='/<%=CfgHandler.APP%>/img/icon/excel.png'/>
                            Excel
                        </a>
                        <a type='button' class='btn btn-danger m-2' id='pdf' href="/<%=CfgHandler.APP%>/exportpdf?type=<%= type%>&date1=<%= date1%>&date2=<%= date2%>&agence_id=" >
                            <img src='/<%=CfgHandler.APP%>/img/icon/pdf.png'/>
                            PDF
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <script>
            let type = "<%= type%>";
        </script>
    </body>
</html>
