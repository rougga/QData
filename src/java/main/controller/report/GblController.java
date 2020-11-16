package main.controller.report;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import main.CfgHandler;
import main.PgMultiConnection;
import main.controller.AgenceController;
import main.modal.Agence;
import main.modal.report.GblRow;

public class GblController {

    private String[] gblCols = new String[]{"Site", "Service", "Nb. Tickets", "Nb. Traités", "Nb. Absents", "Nb. Traités <1mn", "Nb. Sans affectation", "Absents/Nb. Tickets(%)", "Traités<1mn/Nb. Tickets(%)", "Sans affect/Nb. Tickets(%)", "Moyenne d'attente", ">Cible", "%Cible", "Moyenne Traitement", ">Cible", "%Cible"};
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private String SQL;
    private String totaleSQL;

    public GblController() {
    }

    public String[] getGblCols() {
        return gblCols;
    }

    public List<GblRow> getTable(HttpServletRequest request, String d1, String d2) {
        String date1 = (d1 == null) ? format.format(new Date()) : d1;
        String date2 = (d2 == null) ? format.format(new Date()) : d2;
        List<GblRow> table = new ArrayList<>();
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences.size() > 0) {
            for (int i = 0; i < agences.size(); i++) {
                try {
                    Agence a = agences.get(i);
                    PgMultiConnection con = new PgMultiConnection(a.getHost(), String.valueOf(a.getPort()), a.getDatabase(), a.getUsername(), a.getPassword());

                    CfgHandler cfg = new CfgHandler(request);
                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') ";

                    String gblSQL = "";

                    String subTotalSQL = "";
                    ResultSet r = con.getStatement().executeQuery(gblSQL);
                    while (r.next()) {
                        GblRow row = new GblRow(a.getId(),
                                r.getString("biz_type_id"),
                                r.getString("name"),
                                r.getLong("nb_t"),
                                r.getLong("nb_tt"),
                                r.getLong("nb_a"),
                                r.getLong("nb_tl1"),
                                r.getLong("nb_sa"),
                                r.getFloat("perApT"),
                                r.getFloat("PERTL1pt"),
                                r.getFloat("perSApT"),
                                r.getFloat("avgSec_A"),
                                0,
                                0,
                                r.getFloat("avgSec_T"),
                                0,
                                0);
                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subTotalSQL);
                    while (r.next()) {
                        GblRow row = new GblRow(a.getId(),
                                null,
                                "Sous-Totale",
                                r.getLong("nb_t"),
                                r.getLong("nb_tt"),
                                r.getLong("nb_a"),
                                r.getLong("nb_tl1"),
                                r.getLong("nb_sa"),
                                r.getFloat("perApT"),
                                r.getFloat("PERTL1pt"),
                                r.getFloat("perSApT"),
                                r.getFloat("avgSec_A"),
                                0,
                                0,
                                r.getFloat("avgSec_T"),
                                0,
                                0);
                        table.add(row);
                    }

                    con.closeConnection();

                } catch (Exception ex) {
                    Logger.getLogger(GblController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {

        }
        return table;
    }

}
