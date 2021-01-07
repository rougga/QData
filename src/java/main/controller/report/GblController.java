package main.controller.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import main.CfgHandler;
import main.PgConnection;
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

    public String[] getCols() {
        return gblCols;
    }

    public List<GblRow> getTable(HttpServletRequest request, String d1, String d2) {
        String date1 = (d1 == null) ? format.format(new Date()) : d1;
        String date2 = (d2 == null) ? format.format(new Date()) : d2;
        List<GblRow> table = new ArrayList<>();
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences!=null) {
            for (int i = 0; i < agences.size(); i++) {
                
                try {
                    Agence a = agences.get(i);
                    PgConnection con = new PgConnection();
                    
                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='"+a.getId()+"'";

                    String SQL = "SELECT "
                            + " g1.BIZ_TYPE_ID,"
                            + " G1.NAME,"
                            + " G1.NB_T,"
                            + " G1.NB_TT, "
                            + "G1.NB_A,"
                            + " G1.NB_TL1,"
                            + " G1.NB_SA,"
                            + " CASE "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + "ELSE CAST((G1.NB_A::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERAPT,"
                            + " CASE"
                            + " WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric"
                            + " ELSE CAST((G1.NB_TL1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERTL1pt,"
                            + " CASE WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + "ELSE CAST((G1.NB_SA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERSAPT , "
                            + "G1.AVGSEC_A, G1.avgsec_T "
                            + "from "
                            + "( select "
                            + "t1.biz_type_id,"
                            + " b.name, "
                            + "(SELECT COUNT(*) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID " + dateCon + " ) AS NB_T,"
                            + " (SELECT COUNT(*) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND T2.STATUS = 4 " + dateCon + " ) AS NB_TT,"
                            + " (SELECT COUNT(*) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND T2.STATUS = 2 " + dateCon + " ) AS NB_A,"
                            + " (SELECT COUNT(*) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 AND T2.STATUS = 4 " + dateCon + " ) AS NB_TL1,"
                            + " (SELECT COUNT(*) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND T2.STATUS = 0 " + dateCon + " ) AS NB_SA,"
                            + " (SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID and T2.call_time is not null  " + dateCon + " ) AS AVGSEC_A, "
                            + " (SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND T2.STATUS = 4 " + dateCon + " ) AS AVGSEC_T FROM T_TICKET T1, T_BIZ_TYPE B WHERE T1.BIZ_TYPE_ID = B.ID  and b.db_id='"+a.getId()+"' AND TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') GROUP BY T1.BIZ_TYPE_ID, B.NAME ) G1 ;";

                    String subSQL = "SELECT G1.NB_T, "
                            + "G1.NB_TT, "
                            + "G1.NB_A, "
                            + "G1.NB_TL1, "
                            + "G1.NB_SA, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + "ELSE CAST((G1.NB_A::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERAPT, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + "ELSE CAST((G1.NB_TL1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERTL1PT, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + "ELSE CAST((G1.NB_SA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERSAPT, "
                            + "G1.AVGSEC_A, "
                            + "G1.AVGSEC_T "
                            + "FROM "
                            + "(SELECT "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE 1 = 1 "
                            + " " + dateCon + " ) AS NB_T, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + " ) AS NB_TT, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 2 "
                            + " " + dateCon + " ) AS NB_A, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                            + "AND T2.STATUS = 4 "
                            + " " + dateCon + " ) AS NB_TL1, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 0 "
                            + " " + dateCon + " ) AS NB_SA, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.call_time is not null "
                            + " " + dateCon + " ) AS AVGSEC_A, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + " ) AS AVGSEC_T "
                            + "FROM T_TICKET T1 "
                            + "WHERE TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') limit 1 ) G1 ;";
                    
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        GblRow row = new GblRow(a.getId(),
                                a.getName(),
                                r.getString("biz_type_id"),
                                r.getString("name"),
                                r.getLong("nb_t"),
                                r.getLong("nb_tt"),
                                r.getLong("nb_a"),
                                r.getLong("nb_tl1"),
                                r.getLong("nb_sa"),
                                r.getFloat("perApT")+"",
                                r.getFloat("PERTL1pt")+"",
                                r.getFloat("perSApT")+"",
                                r.getFloat("avgSec_A")+"",
                                0,
                                "0",
                                r.getFloat("avgSec_T")+"",
                                0,
                                "0");
                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        GblRow row = new GblRow(a.getId(),
                                a.getName(),
                                null,
                                "Sous-Totale",
                                r.getLong("nb_t"),
                                r.getLong("nb_tt"),
                                r.getLong("nb_a"),
                                r.getLong("nb_tl1"),
                                r.getLong("nb_sa"),
                                r.getFloat("perApT")+"",
                                r.getFloat("PERTL1pt")+"",
                                r.getFloat("perSApT")+"",
                                r.getFloat("avgSec_A")+"",
                                0,
                                "0",
                                r.getFloat("avgSec_T")+"",
                                0,
                                "0");
                        table.add(row);
                    }

                    con.closeConnection();

                } catch (Exception ex) {
                    Logger.getLogger(GblController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return table;
        } else {
            return null;
        }
        
    }
}
