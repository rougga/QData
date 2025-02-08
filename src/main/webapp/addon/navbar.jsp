<%@page import="ma.rougga.qdata.CfgHandler"%>
<%@page import="java.util.Objects"%>
<%
    if (Objects.equals(session.getAttribute("user"), null)) {
        response.sendRedirect("/QData/index.jsp");
    }
%>
<nav class="navbar navbar-expand-lg navbar-dark " style="background-color: #14b6fa;">
    <a class="navbar-brand" href="#">QData</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active" id="home">
                <a class="nav-link font-weight-bold" href="/QData/home.jsp">
                    <span class="fas fa-home font-weight-bold"></span> ACCUEIL
                </a>
            </li>
            <li class="nav-item dropdown" id="report">
                <a class="nav-link dropdown-toggle font-weight-bold gbl emp empser gch gchserv" href="#" id="navbarDropdownR" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class=""></span> RAPPORTS
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownR" >
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_GBL_REPORT%>?d=d"">RAPPORT GLOBALE</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_EMP_REPORT %>?d=d">RAPPORT EMPLOYE</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_EMPSER_REPORT %>?d=d">RAPPORT EMPLOYE / SERVICE</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_GCH_REPORT %>?d=d">RAPPORT GUICHET</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_GCHSER_REPORT %>?d=d">RAPPORT GUICHET / SERVICE</a>
                    <a class="dropdown-item font-weight-bold navHover d d-none" href="<%=CfgHandler.PAGE_TCH_REPORT %>?d=d">RAPPORT TACHE</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_GLA_REPORT %>?d=d">GRILLE ATTENTE</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_GLT_REPORT %>?d=d">GRILLE TRAITEMENT</a>
                    </div>
            </li> 
            <li class="nav-item dropdown" id="tranche">
                <a class="nav-link dropdown-toggle font-weight-bold ndt ndtt ndta ndtsa" href="#" id="navbarDropdownRe" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class=""></span> TRANCHE HORAIRE
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownRe" >
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_REPORT%>?type=ndt&d=d">NOMBRE DE TICKETS EDITÉS</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_REPORT%>?type=ndtt&d=d">NOMBRE DE TICKETS TRAITÉS</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_REPORT%>?type=ndta&d=d">NOMBRE DE TICKETS ABSENTS</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_REPORT%>?type=ndtsa&d=d">NOMBRE DE TICKETS SANS AFFECTATION</a>
                </div>
            </li> 
            <li class="nav-item" id="topics">
                <a class="nav-link font-weight-bold" href="javascript:alert('QData v<%= CfgHandler.VERSION %>');">
                    <span class="far fa-file-alt"></span> Aide
                </a>
            </li> 
        </ul>


        <ul class="navbar-nav">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <b>${user}</b>
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item font-weight-bold navHover disabled" href="/QData/settings.jsp" >Paramètres</a>
                    <div class="dropdown-divider border-dark"></div>
                    
                    <%
                        if(Objects.equals(session.getAttribute("grade"), "adm")){
                    %>
                            <a class="dropdown-item font-weight-bold navHover" href="/QData/setting/cibles.jsp" >Cibles</a>
                            <a class="dropdown-item font-weight-bold navHover" href="/QData/setting/users.jsp" >Utilisateurs</a>
                            <a class="dropdown-item font-weight-bold navHover" href="/QData/setting/zones.jsp" >Zones</a>
                            <a class="dropdown-item font-weight-bold navHover" href="/QData/setting/agences.jsp" >Agences</a>
                            <a class="dropdown-item font-weight-bold navHover" href="/QData/setting/titles.jsp" >Titres</a>
                    <%
                        }
                    %>
                    
                    <a class="dropdown-item font-weight-bold navHover" href="/QData/setting/maj.jsp" >MaJ GBL</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item font-weight-bold navHover" href="/QData/Logoff">
                        Déconnexion</a>
                </div>
            </li>

        </ul>
    </div> 
</nav>
