<%@page import="ma.rougga.qdata.modal.report.ThTRow"%>
<%@page import="ma.rougga.qdata.controller.report.ThTTableController"%>
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
    String type = "tht";
    String[] agences = request.getParameterValues("agences");
    Title Title = new TitleController().getTitleByType(type);
    String date1 = (request.getParameter("date1") == null) ? CfgHandler.format.format(new Date()) : request.getParameter("date1");
    String date2 = (request.getParameter("date2") == null) ? CfgHandler.format.format(new Date()) : request.getParameter("date2");
    List<Map> table = new ThTTableController().getTableAsList(date1, date2, agences);
    ThTRow totaleRow = new ThTRow();
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

                                    <th class="col 1 text-center align-middle" style="">Service</th>

                                    <th class="col 2 text-wrap text-center align-middle" style="">00:00-00:59</th>

                                    <th class="col 3 text-wrap text-center align-middle" style="">01:00-01:59</th>

                                    <th class="col 4 text-wrap text-center align-middle" style="">02:00-02:59</th>

                                    <th class="col 5 text-wrap text-center align-middle" style="">03:00-03:59</th>

                                    <th class="col 6 text-wrap text-center align-middle" style="">04:00-04:59</th>

                                    <th class="col 7 text-wrap text-center align-middle" style="">05:00-05:59</th>

                                    <th class="col 8 text-wrap text-center align-middle" style="">06:00-06:59</th>

                                    <th class="col 9 text-wrap text-center align-middle" style="">07:00-07:59</th>

                                    <th class="col 10 text-wrap text-center align-middle" style="">08:00-08:59</th>

                                    <th class="col 11 text-wrap text-center align-middle" style="">09:00-09:59</th>

                                    <th class="col 12 text-wrap text-center align-middle" style="">10:00-10:59</th>

                                    <th class="col 13 text-wrap text-center align-middle" style="">11:00-11:59</th>

                                    <th class="col 14 text-wrap text-center align-middle" style="">12:00-12:59</th>

                                    <th class="col 15 text-wrap text-center align-middle" style="">13:00-13:59</th>

                                    <th class="col 16 text-wrap text-center align-middle" style="">14:00-14:59</th>

                                    <th class="col 17 text-wrap text-center align-middle" style="">15:00-15:59</th>

                                    <th class="col 18 text-wrap text-center align-middle" style="">16:00-16:59</th>

                                    <th class="col 19 text-wrap text-center align-middle" style="">17:00-17:59</th>

                                    <th class="col 20 text-wrap text-center align-middle" style="">18:00-18:59</th>

                                    <th class="col 21 text-wrap text-center align-middle" style="">19:00-19:59</th>

                                    <th class="col 22 text-wrap text-center align-middle" style="">20:00-20:59</th>

                                    <th class="col 23 text-wrap text-center align-middle" style="">21:00-21:59</th>

                                    <th class="col 24 text-wrap text-center align-middle" style="">22:00-22:59</th>

                                    <th class="col 25 text-wrap text-center align-middle" style="">23:00-23:59</th>

                                </tr>
                            </thead>
                            <tbody  class="font-weight-bold ">

                                <%    if (!table.isEmpty() && table != null) {

                                        for (Map agence : table) {
                                %>
                                <%
                                    Object empsObj = agence.get("emps");
                                    if (empsObj instanceof List<?>) {
                                        List<ThTRow> emps = (ArrayList<ThTRow>) empsObj;
                                        totaleRow = emps.get(emps.size() - 1);
                                        for (ThTRow emp : emps) {
                                %>


                                <tr  class="" data-id="<%= agence.get("id_agence")%>">
                                    <th scope="row" class="text-center text-wrap align-middle border-dark 0 db <%= agence.get("agence_name")%>" data-id="<%= agence.get("id_agence")%>"><%= agence.get("agence_name")%></th>

                                    <td class="text-left border-dark 1 <%= emp.getServiceName()%>" style=""><%= emp.getServiceName()%> </td>

                                    <td class="text-left border-dark 2" style=""><%= emp.getH0()%> </td>

                                    <td class="text-left border-dark 3" style=""><%= emp.getH1()%></td>

                                    <td class="text-left border-dark 4" style=""><%= emp.getH2()%></td>

                                    <td class="text-left border-dark 5" style=""><%= emp.getH3()%></td>

                                    <td class="text-left border-dark 6" style=""><%= emp.getH4()%></td>

                                    <td class="text-left border-dark 7" style=""><%= emp.getH5()%></td>

                                    <td class="text-left border-dark 8" style=""><%= emp.getH6()%></td>

                                    <td class="text-left border-dark 9" style=""><%= emp.getH7()%></td>

                                    <td class="text-left border-dark 10" style=""><%= emp.getH8()%></td>

                                    <td class="text-left border-dark 11" style=""><%= emp.getH9()%></td>

                                    <td class="text-left border-dark 12" style=""><%= emp.getH10()%></td>

                                    <td class="text-left border-dark 13" style=""><%= emp.getH11()%></td>

                                    <td class="text-left border-dark 14" style=""><%= emp.getH12()%></td>

                                    <td class="text-left border-dark 15" style=""><%= emp.getH13()%></td>

                                    <td class="text-left border-dark 16" style=""><%= emp.getH14()%></td>

                                    <td class="text-left border-dark 17" style=""><%= emp.getH15()%></td>

                                    <td class="text-left border-dark 18" style=""><%= emp.getH16()%></td>

                                    <td class="text-left border-dark 19" style=""><%= emp.getH17()%></td>

                                    <td class="text-left border-dark 20" style=""><%= emp.getH18()%></td>

                                    <td class="text-left border-dark 21" style=""><%= emp.getH19()%></td>

                                    <td class="text-left border-dark 22" style=""><%= emp.getH20()%></td>

                                    <td class="text-left border-dark 23" style=""><%= emp.getH21()%></td>

                                    <td class="text-left border-dark 24" style=""><%= emp.getH22()%></td>

                                    <td class="text-left border-dark 25" style=""><%= emp.getH23()%></td>

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
                                            data: ['00:00','01:00','02:00','03:00','04:00','05:00','06:00','07:00','08:00','09:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00','20:00','21:00','22:00','23:00'],
                                            axisLabel: {
                                                show: true,
                                                interval: 0,
                                                rotate: 30,
                                                fontWeight: 'bold'
                                            },
                                            axisTick: {
                                                alignWithLabel: false
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
                    <%= totaleRow.getH0() %>,
                    <%= totaleRow.getH1() %>,
                    <%= totaleRow.getH2() %>,
                    <%= totaleRow.getH3() %>,
                    <%= totaleRow.getH4() %>,
                    <%= totaleRow.getH5() %>,
                    <%= totaleRow.getH6() %>,
                    <%= totaleRow.getH7() %>,
                    <%= totaleRow.getH8() %>,
                    <%= totaleRow.getH9() %>,
                    <%= totaleRow.getH10() %>,
                    <%= totaleRow.getH11() %>,
                    <%= totaleRow.getH12() %>,
                    <%= totaleRow.getH13() %>,
                    <%= totaleRow.getH14() %>,
                    <%= totaleRow.getH15() %>,
                    <%= totaleRow.getH16() %>,
                    <%= totaleRow.getH17() %>,
                    <%= totaleRow.getH18() %>,
                    <%= totaleRow.getH19() %>,
                    <%= totaleRow.getH20() %>,
                    <%= totaleRow.getH21() %>,
                    <%= totaleRow.getH22() %>,
                    <%= totaleRow.getH23() %>
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
