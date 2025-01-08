<%@page import="main.CfgHandler"%>
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
                    <span class="fas fa-home font-weight-bold"></span> Accueil
                </a>
            </li>
            <li class="nav-item dropdown" id="report">
                <a class="nav-link dropdown-toggle font-weight-bold gbl emp empser gch gchserv" href="#" id="navbarDropdownR" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class=""></span> Rapport
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownR" >
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_GBL_REPORT%>?d=d"">RAPPORT GLOBALE</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_REPORT%>?type=emp&d=d">RAPPORT EMPLOYE</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_REPORT%>?type=empser&d=d">RAPPORT EMPLOYE (service)</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_REPORT%>?type=gch&d=d">RAPPORT GUICHET</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_REPORT%>?type=gchserv&d=d">RAPPORT GUICHET (service)</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_REPORT%>?type=tch&d=d">RAPPORT TACHE</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_REPORT%>?type=gla&d=d">Grille attente</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_REPORT%>?type=glt&d=d">Grille traitement</a>
                    </div>
            </li> 
            <li class="nav-item dropdown" id="tranche">
                <a class="nav-link dropdown-toggle font-weight-bold ndt ndtt ndta ndtsa" href="#" id="navbarDropdownRe" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class=""></span> Tranche horaire
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownRe" >
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_REPORT%>?type=ndt&d=d">Nombre de tickets edités</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_REPORT%>?type=ndtt&d=d">Nombre de tickets traités</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_REPORT%>?type=ndta&d=d">Nombre de tickets absents</a>
                    <a class="dropdown-item font-weight-bold navHover d" href="<%=CfgHandler.PAGE_REPORT%>?type=ndtsa&d=d">Nombre de tickets sans affectation</a>
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
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
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
