 <%@page import="ma.rougga.qdata.modal.Agence"%>
<%@page import="ma.rougga.qdata.controller.ZoneController"%>
<%@page import="java.util.List"%>
<%@page import="ma.rougga.qdata.controller.AgenceController"%>
<%@page import="ma.rougga.qdata.modal.Zone"%>
<%@page import="java.util.Date"%>
<%@page import="ma.rougga.qdata.CfgHandler"%>
<div class='div-wrapper d-flex justify-content-center align-items-center'>
                 <form class='form-inline' id='filterForm'  action=''>
                 <label class='m-1' for='date1'>Du: </label>
                 <input type='date' class='form-control mb-2 mr-sm-2' id='date1' name='date1' value='' max='<%= CfgHandler.format.format(new Date())%>'>
                 <label class='m-1' for='date2'>Au: </label>
                 <input type='date' class='form-control mb-2 mr-sm-2' id='date2' name='date2' value='' >
                 <input type='hidden' value='gbl' name='type'>
                 <div class='btn-group dropright mb-2 mr-4'>
                   <button type='button' class='btn btn-secondary dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>
                     Intervalle
                   </button>
                   <div class='dropdown-menu '>
                     <a class='dropdown-item font-weight-bold appHover' href='#' id='today'>Aujourd'hui</a>
                     <a class='dropdown-item font-weight-bold appHover' href='#' id='yesterday'>Hier</a>
                     <a class='dropdown-item font-weight-bold appHover' href='#' id='cWeek'>Sema.rougga.qdatae en cours</a>
                     <a class='dropdown-item font-weight-bold appHover' href='#' id='lWeek'>Dernier sema.rougga.qdatae</a>
                     <a class='dropdown-item font-weight-bold appHover' href='#' id='cMonth'>Mois en cours</a>
                     <a class='dropdown-item font-weight-bold appHover' href='#' id='lMonth'>Mois dernier</a>
                     <a class='dropdown-item font-weight-bold appHover' href='#' id='cYear'>Année en cours</a>
                     <a class='dropdown-item font-weight-bold appHover' href='#' id='lYear'>Année dernier</a>
                   </div>
                 </div>
                 <div class='btn-group dropright mb-2 mr-1'>
                   <button type='button' class='btn btn-light font-weight-bold dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>
                     Zones
                   </button>
                   <div class='dropdown-menu ' id='zones'>
                <span class='dropdown-item font-weight-bold pl-2 zone'>
                <input type='checkbox'  class='mr-1 form-check-input ' id='selectAllZones'><span id='textSelect'><u>Toutes les agences</u></span>
                </span>
<%
    AgenceController ac = new AgenceController();
        List<Zone> zones = new ZoneController().getAllZones();
        if (zones != null && !zones.isEmpty()) {
            for (Zone zone : zones) {
                List<Agence> dropdownAgences = ac.getAgencesByZone(zone.getId());
                if (!dropdownAgences.isEmpty()) {
%>
                    <div class='dropdown-divider border-dark'></div>
                        <span class='dropdown-item font-weight-bold appHover  pl-2 zone'>
                        <input type='checkbox' name='zones' class='mr-1 form-check-input zoneCheckbox' value='<%= zone.getId() %>'>
                            <span class='zone-ck-text' data-id='<%=zone.getId().toString() %>'><img src='../img/icon/zone.png'/> <%= zone.getName()%> </span>
                            <%
                    for (Agence agence : dropdownAgences) {
                    %>
                        <span class='dropdown-item font-weight-bold appHover  pl-2 agence' data-zoneId='<%= zone.getId() %>'>
                                <input type='checkbox' name='agences' class='mr-1 form-check-input check' data-zoneId='<%= zone.getId() %>' value='<%= agence.getId()%>'>
                                <span class='zone-ck-text' data-id='<%=agence.getId().toString() %>'><%= agence.getName()%> </span></span>
                    </span>
                        <%}
                  
                }

            }

        }
%>
        </div>

         </div>
              

        <button type='button' class='btn btn-primary mb-2' id='refresh'><img src='../img/icon/reload.png'/> Actualiser</button>
                