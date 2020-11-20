<%@page import="main.TableGenerator"%>
<%@page import="java.util.Map"%>
<%@page import="main.handler.TitleHandler"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="main.controller.report.GblController"%>
<%@page import="main.modal.report.GblRow"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    TableGenerator gen = new TableGenerator();
    GblController gblc = new GblController();
    String date1 = (request.getParameter("date1") == null) ? gen.getFormat().format(new Date()) : request.getParameter("date1");
    String date2 = (request.getParameter("date2") == null) ? gen.getFormat().format(new Date()) : request.getParameter("date2");
    String toDay = gen.getFormat().format(new Date());
    String Title = new TitleHandler(request).getGblTitle();
    gen.setDefaultHTML();
    String Top = gen.getTopHTML();
    String Bottom = gen.getBottomHTML();
    List<GblRow> table = gblc.getTable(request, date1, date2);
    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>OffReport</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="../img/favicon-32x32.png">
        <script src="../js/jquery.js"></script>
        <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="../js/bootstrap.bundle.min.js"></script>
        <link href="../css/navbar.css" rel="stylesheet" type="text/css"/> 
        <link href="../css/body.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container-xl">

            <div class="head">
                <%@include file="../addon/navbar.jsp" %>
                <script>
                    $("#home").removeClass("active");
                    $(".gbl").addClass("active");
                </script>
            </div>
            <div class="body">
                <%                    
                    
                    if (request.getParameter("err") != "" && request.getParameter("err") != null) {

                %>
                <%= "<div class='alert alert-danger alert-dismissible fade show' role='alert'><b>"
                        + request.getParameter("err")
                        + "</b><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>"%>
                <%
                    }
                    
                %>


                <h2 class="text-center p-4"><%= Title%></h2>
                <div class='div-wrapper d-flex justify-content-center align-items-center'>
                    <form class='form-inline'>
                        <label class='m-1' for='date1'>Du: </label>
                        <input type='date' class='form-control mb-2 mr-sm-2' id='date1' name='date1' value='<%= date1 %>' max='<%= toDay %>'>
                        <label class='m-1' for='date2'>Au: </label>
                        <input type='date' class='form-control mb-2 mr-sm-2' id='date2' name='date2' value='<%= date2 %>' >
                        <input type='hidden' value='" + getType() ' name='type'>
                        <div class='btn-group dropright mb-2 mr-2'>
                            <button type='button' class='btn btn-secondary dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>
                                Intervalle
                            </button>
                            <div class='dropdown-menu '>
                                <a class='dropdown-item font-weight-bold appHover' href='#' id='today'>Aujourd'hui</a>
                                <a class='dropdown-item font-weight-bold appHover' href='#' id='yesterday'>Hier</a>
                                <a class='dropdown-item font-weight-bold appHover' href='#' id='cWeek'>Semaine en cours</a>
                                <a class='dropdown-item font-weight-bold appHover' href='#' id='lWeek'>Dernier semaine</a>
                                <a class='dropdown-item font-weight-bold appHover' href='#' id='cMonth'>Mois en cours</a>
                                <a class='dropdown-item font-weight-bold appHover' href='#' id='lMonth'>Mois dernier</a>
                                <a class='dropdown-item font-weight-bold appHover' href='#' id='cYear'>Année en cours</a>
                                <a class='dropdown-item font-weight-bold appHover' href='#' id='lYear'>Année dernier</a>
                            </div>
                        </div>
                        <button type='submit' class='btn btn-primary mb-2'><img src='../img/icon/reload.png'/> Actualiser</button>
                    </form>
                    <script>
                    </script>
                </div>
                <table class="table table-light table-bordered table-striped  table-responsive">
                    <a class="float-right btn btn-link text-white" id="plus">PLUS >></a>
                    <thead class="appColor ">
                        <tr class="">
                            <%
                                for(int i=0;i<gblc.getCols().length;i++){
                                    
                            %>
                            <th class="<%= i%> col"><%= gblc.getCols()[i] %></th>
                                <%
                                    }
                                
                                %>
                        </tr>
                    </thead>
                    <tbody  class="font-weight-bold ">
                        <%
                         if (!table.isEmpty() && table!=null) {
                             for (int i = 0; i < table.size(); i++) {
                        %>
                        <tr class="">
                            <th scope="row" class="text-center align-middle border-dark db 0" data-id="<%= table.get(i).getId_db() %>"><%= table.get(i).getName_db() %></th>
                            <td class="text-left border-dark service 1 <%= table.get(i).getService_name() %>"><%= table.get(i).getService_name() %></td>
                            <td class="text-left border-dark 2" ><%= table.get(i).getNb_t() %></td>
                            <td class="text-left border-dark 3"><%= table.get(i).getNb_tt() %></td>
                            <td class="text-left border-dark 4"><%= table.get(i).getNb_ta() %></td>
                            <td class="text-left border-dark 5"><%= table.get(i).getNb_ttl1() %></td>
                            <td class="text-left border-dark 6"><%= table.get(i).getNb_tsa() %></td>
                            <td class="text-left border-dark 7"><%= table.get(i).getPerApt() %></td>
                            <td class="text-left border-dark 8"><%= table.get(i).getPerTl1pt() %></td>
                            <td class="text-left border-dark 9"><%= table.get(i).getPerSapt() %></td>
                            <td class="text-left border-dark 10"><%= gen.getFormatedTime(table.get(i).getAvgA()) %></td>
                            <td class="text-left border-dark 11"><%= table.get(i).getgCibleA() %></td>
                            <td class="text-left border-dark 12"><%= table.get(i).getPerCibleA() %></td>
                            <td class="text-left border-dark 13"><%= gen.getFormatedTime(table.get(i).getAvgT()) %></td>
                            <td class="text-left border-dark 14"><%= table.get(i).getgCibleT() %></td>
                            <td class="text-left border-dark 15"><%= table.get(i).getPerCibleT() %></td>
                        </tr>
                        <%
                                }
                            }
                        %>



                    </tbody>
                </table>

            </div>
            <div class="footer">
                <div class='div-wrapper d-flex justify-content-center align-items-center p-2'>
                    <form class='' id='printForm' action='./Print' method='GET'>
                        <div class='form-group'>
                            <input type='hidden' class='form-control' id='date1' name='type' value='gbl'>
                        </div>
                        <div class='form-group'>
                            <input type='hidden' class='form-control' id='date1' name='date1' value='<%=date1%>'>
                        </div>
                        <div class='form-group'>
                            <input type='hidden' class='form-control'  id='date2' name='date2' value='<%=date2%>'>
                        </div>
                        <div class='form-group'>
                            <input type='hidden' class='form-control'  id='format' name='format' value='excel'>
                        </div>
                        <button type='button' class='btn btn-success m-2' id='excel'><img src='../img/icon/excel.png'/> Excel</button>
                        <button type='button' class='btn btn-danger m-2' id='pdf' disabled><img src='../img/icon/pdf.png'/> PDF</button>
                    </form>
                </div>
            </div>
        </div>
        <script>
           $(document).ready(function () {
                var colsToShow = ".0,.1,.2,.3,.4,.10,.13";
                $("td").hide();
                $("th").hide();
                $(colsToShow).show();
                $(".Sous-Totale").parent("tr").addClass("bg-warning");
                $("#excel").on('click', function () {
                    $("#format").val("excel");
                    $("#printForm").submit();
                });
                $("#word").on('click', function () {
                    $("#format").val("word");
                    $("#printForm").submit();
                });
                $("#pdf").on('click', function () {
                    $("#format").val("pdf");
                    $("#printForm").submit();
                });
                $("#plus").on('click', function () {
                    if ($(this).text() === "PLUS >>") {
                        $(this).text("MOIN <<");
                        $("td").show();
                        $("th").show();
                    } else {
                        $("td").hide();
                        $("th").hide();
                        $(colsToShow).show();
                        $(this).text("PLUS >>");
                    }
                });
                $("#date1").on('change', function () {
                    if ($(this).val() > $("#date2").val()) {
                        alert("La date Du est plus gros que la date Au.");
                        $("#date2").val(new Date().toLocaleDateString('en-CA'));
                    }
                });
                $("#date2").on('change', function () {
                    if ($("#date1").val() > $("#date2").val()) {
                        alert("La date Du est plus gros que la date Au.");
                        $("#date2").val(new Date().toLocaleDateString('en-CA'));
                    }
                });
                
                var elements = $('.db');
                var ids = [];
                for (var i = 0; i < elements.length; i++) {
                    if (ids.indexOf($(elements[i]).attr("data-id")) === -1) {
                        ids.push($(elements[i]).attr("data-id"));
                    }
                    else {
                        $(elements[i]).hide();
                    }
                }
                for (var i = 0; i < ids.length; i++) {
                    $('.db[data-id='+ids[i]+']:first').attr("rowspan", $('.db[data-id='+ids[i]+']').length);
                }
            });
        </script>
    </body>
</html>
