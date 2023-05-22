package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import main.controller.AgenceController;
import main.controller.CibleController;
import main.handler.TitleHandler;
import main.modal.Agence;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

public class TableGenerator {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
    private final SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private String DB;
    private String[] dbs;
    private String date1;
    private String date2;
    private String type;
    private String title;
    private List<ArrayList<String>> table;
    private ArrayList<String> subTotal;
    private String topHTML;
    private String bottomHTML;
    private String[] cols;
    private String chartLables;
    private String chartData;
    public String[] gblCols;
    private String[] empCols;
    private String[] empServiceCols;
    private String[] gchCols;
    private String[] gchServiceCols;
    private String[] ndtCols;
    private String[] cnxCols;
    private String[] serCols;
    private String[] sgchCols;
    private String[] aplCols;
    private String[] glaCols;
    private String[] gltCols;
    private String[] ndtChartCols;
    private String[] taskCols;

    public TableGenerator() {

        this.gblCols = new String[]{"Site", "Service", "Nb. Tickets", "Nb. Traités", "Nb. Absents", "Nb. Traités <1mn", "Nb. Sans affectation", "Absents/Nb. Tickets(%)", "Traités<1mn/Nb. Tickets(%)", "Sans affect/Nb. Tickets(%)", "Moyenne d'attente", ">Cible", "%Cible", "Moyenne Traitement", ">Cible", "%Cible"};
        this.empCols = new String[]{"Site", "Employé", "Nb. Traités", "Nb. Absents", "Nb. Traités <1mn", "Nb. Sans affectation", "Absents/Nb. Tickets(%)", "Traités<1mn/Nb. Tickets(%)", "Sans affect/Nb. Tickets(%)", "Moyenne d'attente", ">Cible", "%Cible", "Moyenne Traitement", ">Cible", "%Cible"};
        this.empServiceCols = new String[]{"Site", "Employé", "Service", "Nb. Traités", "Nb. Absents", "Nb. Traités <1mn", "Nb. Sans affectation", "Absents/Nb. Tickets(%)", "Traités<1mn/Nb. Tickets(%)", "Sans affect/Nb. Tickets(%)", "Moyenne d'attente", ">Cible", "%Cible", "Moyenne Traitement", ">Cible", "%Cible"};
        this.gchCols = new String[]{"Site", "Guichet", "Nb. Tickets", "Nb. Traités", "Nb. Absents", "Nb. Traités <1mn", "Nb. Sans affectation", "Absents/Nb. Tickets(%)", "Traités<1mn/Nb. Tickets(%)", "Sans affect/Nb. Tickets(%)", "Moyenne d'attente", ">Cible", "%Cible", "Moyenne Traitement", ">Cible", "%Cible"};
        this.gchServiceCols = new String[]{"Site", "Guichet", "Service", "Nb. Tickets", "Nb. Traités", "Nb. Absents", "Nb. Traités <1mn", "Nb. Sans affectation", "Absents/Nb. Tickets(%)", "Traités<1mn/Nb. Tickets(%)", "Sans affect/Nb. Tickets(%)", "Moyenne d'attente", ">Cible", "%Cible", "Moyenne Traitement", ">Cible", "%Cible"};
        this.ndtCols = new String[]{"Site", "Service", "00:00-00:59", "01:00-01:59", "02:00-02:59", "03:00-03:59", "04:00-04:59", "05:00-05:59", "06:00-06:59", "07:00-07:59", "08:00-08:59", "09:00-09:59", "10:00-10:59", "11:00-11:59", "12:00-12:59", "13:00-13:59", "14:00-14:59", "15:00-15:59", "16:00-16:59", "17:00-17:59", "18:00-18:59", "19:00-19:59", "20:00-20:59", "21:00-21:59", "22:00-22:59", "23:00-23:59"};
        this.ndtChartCols = new String[]{"Site", "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};

        this.cnxCols = new String[]{"Site", "Employé", "Heure Connexion", "Heure Déconnexion", "Durée", "IP"};
        this.serCols = new String[]{"Site", "Prefix", "Service", "Tickets en attente", "Durée maximale d'attente", "Moyenne d'attente", "Tickets en cours de traitement", "Moyenne de traitement", "Statut", "Code", "Num. de départ", "Commentaire", "Délai d'appel", "Temp traitement warning", "Sort"};
        this.sgchCols = new String[]{"Site", "Code", "Guichet", "Employée", "Etat", "Temp d'accee", "Pause", "Ticket current"};
        this.aplCols = new String[]{"Site", "Service", "Numéro", "Heure edition ticket", "Heure appel", "Heure début de traitement", "Heure fin traitement", "Guichet", "Employé", "Durée attente", "Durée traitement", "Statut"};
        this.glaCols = new String[]{"Site", "Service", "0-15s", "15s-30s", "30s-1min", "1min-1min30s", "1min30s-2min", "2-5min", "0-5min", "5-10min", "10-20min", "20-30min", "30-45min", "45-50min", "> 50min", "Total"};
        this.gltCols = new String[]{"Site", "Service", "0-15s", "15s-30s", "30s-1min", "1min-1min30s", "1min30s-2min", "2-5min", ">5min", "5-10min", "10-20min", "20-30min", "30-45min", "45-50min", "> 50min", "Total"};
        this.taskCols = new String[]{"Site", "Service", "Tache", "Nb. Tickets Traités", "Qte. Traités"};
        this.table = new ArrayList<>();
        this.subTotal = new ArrayList<>();
    }

    public String getFormatedTime(Float Sec) {
        int hours = (int) (Sec / 3600);
        int minutes = (int) ((Sec % 3600) / 60);
        int seconds = (int) (Sec % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void template(String d1, String d2) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);

        List<ArrayList> table = new ArrayList<>();
        AgenceController ac = new AgenceController();
        CibleController cc = new CibleController();
        if (this.dbs.length > 0) {
            for (int i = 0; i < this.dbs.length; i++) {
                try {
                    Agence a = ac.getAgenceById(UUID.fromString(this.dbs[i]));
                    PgConnection con = new PgConnection();

                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'";
                    String SQL = "";
                    String subSQL = "";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {

                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {

                    }
                    con.closeConnection();
                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        try {
            PgConnection con = new PgConnection();
            String totalSQL = "";
            ResultSet r = con.getStatement().executeQuery(totalSQL);
            while (r.next()) {
                ArrayList row = new ArrayList<>();
                row.add("Totale");
                row.add("");
                row.add("Totale");
                row.add("Totale");
                table.add(row);
            }

            con.closeConnection();

        } catch (Exception ex) {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ArrayList> generateGblTable2(String d1, String d2) {

        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        List<ArrayList> table = new ArrayList<>();
        List<Agence> agences = new AgenceController().getAllAgence();
        CibleController cc = new CibleController();
        if (agences != null) {
            for (int i = 0; i < agences.size(); i++) {
                try {
                    Agence a = agences.get(i);
                    PgConnection con = new PgConnection();

                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'";

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
                            + " (SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND T2.STATUS = 4 " + dateCon + " ) AS AVGSEC_T FROM T_TICKET T1, T_BIZ_TYPE B WHERE T1.BIZ_TYPE_ID = B.ID  and b.db_id='" + a.getId() + "' and t1.db_id='" + a.getId() + "' AND TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') GROUP BY T1.BIZ_TYPE_ID, B.NAME ) G1 ;";

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
                            + "WHERE TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t1.db_id='" + a.getId() + "' limit 1 ) G1 ;";

                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId().toString());
                        String id = r.getString("biz_type_id");
                        row.add(id);
                        row.add(a.getName());
                        row.add(r.getString("name"));
                        row.add(r.getLong("nb_t"));
                        row.add(r.getLong("nb_tt"));
                        row.add(r.getLong("nb_a"));
                        row.add(r.getLong("nb_tl1"));
                        row.add(r.getLong("nb_sa"));
                        row.add(String.format("%.2f", r.getFloat("perApT")) + "%");
                        row.add(String.format("%.2f", r.getFloat("PERTL1pt")) + "%");
                        row.add(String.format("%.2f", r.getFloat("perSApT")) + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));

                        String cibleSQL = "SELECT G1.BIZ_TYPE_ID, "
                                + "G1.NAME, "
                                + "G1.NB_TT, "
                                + "G1.NB_CA, "
                                + "CASE "
                                + "WHEN G1.NB_TT::numeric = 0 "
                                + "OR G1.NB_CA::numeric = 0 THEN 0 "
                                + "ELSE CAST((G1.NB_CA::numeric / G1.NB_TT::numeric) * 100::numeric AS DECIMAL(10,2)) "
                                + "END AS PERCAPT, "
                                + "G1.NB_CT, "
                                + "CASE "
                                + "WHEN G1.NB_TT::numeric = 0 "
                                + "OR G1.NB_CT::numeric = 0 THEN 0 "
                                + "ELSE CAST((G1.NB_CT::numeric / G1.NB_TT::numeric) * 100::numeric AS DECIMAL(10,2)) "
                                + "END AS PERCTPT "
                                + "FROM "
                                + "(SELECT B.NAME, "
                                + "T1.BIZ_TYPE_ID, "
                                + "(SELECT COUNT(*) "
                                + "FROM T_TICKET T2 "
                                + "WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID "
                                + "AND T2.STATUS = 4  " + dateCon + " ) AS NB_TT, "
                                + " "
                                + "(SELECT COUNT(*) "
                                + "FROM T_TICKET T2 "
                                + "WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID "
                                + "AND DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric >  " + cc.getOne(id, a.getId()).getCibleA() + " "
                                + "AND T2.STATUS = 4  " + dateCon + " ) AS NB_CA, "
                                + " "
                                + "(SELECT COUNT(*) "
                                + "FROM T_TICKET T2 "
                                + "WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID "
                                + "AND DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric >  " + cc.getOne(id, a.getId()).getCibleT() + " "
                                + "AND T2.STATUS = 4  " + dateCon + ") AS NB_CT "
                                + "FROM T_TICKET T1, "
                                + "T_BIZ_TYPE B "
                                + "WHERE T1.BIZ_TYPE_ID = B.ID "
                                + " AND TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') "
                                + "AND T1.BIZ_TYPE_ID = '" + id + "' and t1.db_id='" + a.getId() + "' and b.db_id='" + a.getId() + "'"
                                + "GROUP BY T1.BIZ_TYPE_ID, "
                                + "B.NAME) G1 ; "
                                + "";
                        ResultSet cib = con.getStatement().executeQuery(cibleSQL);
                        if (cib.next()) {
                            row.add(cib.getLong("nb_ca") + "");
                            row.add(String.format("%.2f", cib.getFloat("percapt")) + "%");
                            row.add(getFormatedTime(r.getFloat("avgSec_T")));
                            row.add(cib.getLong("nb_ct") + "");
                            row.add(String.format("%.2f", cib.getFloat("perctpt")) + "%");
                        } else {
                            row.add("--");
                            row.add("--%");
                            row.add(getFormatedTime(r.getFloat("avgSec_T")));
                            row.add("--");
                            row.add("-%");
                        }

                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId().toString());
                        row.add("");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getLong("nb_t"));
                        row.add(r.getLong("nb_tt"));
                        row.add(r.getLong("nb_a"));
                        row.add(r.getLong("nb_tl1"));
                        row.add(r.getLong("nb_sa"));
                        row.add(String.format("%.2f", r.getFloat("perApT")) + "%");
                        row.add(String.format("%.2f", r.getFloat("PERTL1pt")) + "%");
                        row.add(String.format("%.2f", r.getFloat("perSApT")) + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("--");
                        table.add(row);
                    }

                    con.closeConnection();

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        try {
            PgConnection con = new PgConnection();

            String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') ";

            String totalSQL = "SELECT G1.NB_T, "
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

            ResultSet r = con.getStatement().executeQuery(totalSQL);
            while (r.next()) {
                ArrayList row = new ArrayList<>();
                row.add("Totale");
                row.add("");
                row.add("Totale");
                row.add("Totale");
                row.add(r.getLong("nb_t"));
                row.add(r.getLong("nb_tt"));
                row.add(r.getLong("nb_a"));
                row.add(r.getLong("nb_tl1"));
                row.add(r.getLong("nb_sa"));
                row.add(String.format("%.2f", r.getFloat("perApT")) + "%");
                row.add(String.format("%.2f", r.getFloat("PERTL1pt")) + "%");
                row.add(String.format("%.2f", r.getFloat("perSApT")) + "%");
                row.add(getFormatedTime(r.getFloat("avgSec_A")));
                row.add("--");
                row.add("--");
                row.add(getFormatedTime(r.getFloat("avgSec_T")));
                row.add("--");
                row.add("--");
                table.add(row);
            }

            con.closeConnection();

        } catch (Exception ex) {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }

    public List<ArrayList> generateGblTable(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        List<ArrayList> table = new ArrayList<>();
        AgenceController ac = new AgenceController();
        CibleController cc = new CibleController();
        if (this.dbs.length > 0) {
            for (int i = 0; i < this.dbs.length; i++) {
                try {
                    Agence a = ac.getAgenceById(UUID.fromString(this.dbs[i]));
                    PgConnection con = new PgConnection();

                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'";

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
                            + " (SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND T2.STATUS = 4 " + dateCon + " ) AS AVGSEC_T FROM T_TICKET T1, T_BIZ_TYPE B WHERE T1.BIZ_TYPE_ID = B.ID  and b.db_id='" + a.getId() + "' and t1.db_id='" + a.getId() + "' AND TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') GROUP BY T1.BIZ_TYPE_ID, B.NAME ) G1 ;";

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
                            + "WHERE TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t1.db_id='" + a.getId() + "' limit 1 ) G1 ;";

                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId().toString());
                        String id = r.getString("biz_type_id");
                        row.add(id);
                        row.add(a.getName());
                        row.add(r.getString("name"));
                        row.add(r.getLong("nb_t"));
                        row.add(r.getLong("nb_tt"));
                        row.add(r.getLong("nb_a"));
                        row.add(r.getLong("nb_tl1"));
                        row.add(r.getLong("nb_sa"));
                        row.add(String.format("%.2f", r.getFloat("perApT")) + "%");
                        row.add(String.format("%.2f", r.getFloat("PERTL1pt")) + "%");
                        row.add(String.format("%.2f", r.getFloat("perSApT")) + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));

                        String cibleSQL = "SELECT G1.BIZ_TYPE_ID, "
                                + "G1.NAME, "
                                + "G1.NB_TT, "
                                + "G1.NB_CA, "
                                + "CASE "
                                + "WHEN G1.NB_TT::numeric = 0 "
                                + "OR G1.NB_CA::numeric = 0 THEN 0 "
                                + "ELSE CAST((G1.NB_CA::numeric / G1.NB_TT::numeric) * 100::numeric AS DECIMAL(10,2)) "
                                + "END AS PERCAPT, "
                                + "G1.NB_CT, "
                                + "CASE "
                                + "WHEN G1.NB_TT::numeric = 0 "
                                + "OR G1.NB_CT::numeric = 0 THEN 0 "
                                + "ELSE CAST((G1.NB_CT::numeric / G1.NB_TT::numeric) * 100::numeric AS DECIMAL(10,2)) "
                                + "END AS PERCTPT "
                                + "FROM "
                                + "(SELECT B.NAME, "
                                + "T1.BIZ_TYPE_ID, "
                                + "(SELECT COUNT(*) "
                                + "FROM T_TICKET T2 "
                                + "WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID "
                                + "AND T2.STATUS = 4  " + dateCon + " ) AS NB_TT, "
                                + " "
                                + "(SELECT COUNT(*) "
                                + "FROM T_TICKET T2 "
                                + "WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID "
                                + "AND DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric >  " + cc.getOne(id, a.getId()).getCibleA() + " "
                                + "AND T2.STATUS = 4  " + dateCon + " ) AS NB_CA, "
                                + " "
                                + "(SELECT COUNT(*) "
                                + "FROM T_TICKET T2 "
                                + "WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID "
                                + "AND DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric >  " + cc.getOne(id, a.getId()).getCibleT() + " "
                                + "AND T2.STATUS = 4  " + dateCon + ") AS NB_CT "
                                + "FROM T_TICKET T1, "
                                + "T_BIZ_TYPE B "
                                + "WHERE T1.BIZ_TYPE_ID = B.ID "
                                + " AND TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') "
                                + "AND T1.BIZ_TYPE_ID = '" + id + "' and t1.db_id='" + a.getId() + "' and b.db_id='" + a.getId() + "'"
                                + "GROUP BY T1.BIZ_TYPE_ID, "
                                + "B.NAME) G1 ; "
                                + "";
                        ResultSet cib = con.getStatement().executeQuery(cibleSQL);
                        if (cib.next()) {
                            row.add(cib.getLong("nb_ca") + "");
                            row.add(String.format("%.2f", cib.getFloat("percapt")) + "%");
                            row.add(getFormatedTime(r.getFloat("avgSec_T")));
                            row.add(cib.getLong("nb_ct") + "");
                            row.add(String.format("%.2f", cib.getFloat("perctpt")) + "%");
                        } else {
                            row.add("--");
                            row.add("--%");
                            row.add(getFormatedTime(r.getFloat("avgSec_T")));
                            row.add("--");
                            row.add("-%");
                        }

                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId().toString());
                        row.add("");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getLong("nb_t"));
                        row.add(r.getLong("nb_tt"));
                        row.add(r.getLong("nb_a"));
                        row.add(r.getLong("nb_tl1"));
                        row.add(r.getLong("nb_sa"));
                        row.add(String.format("%.2f", r.getFloat("perApT")) + "%");
                        row.add(String.format("%.2f", r.getFloat("PERTL1pt")) + "%");
                        row.add(String.format("%.2f", r.getFloat("perSApT")) + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("--");
                        table.add(row);
                    }

                    con.closeConnection();

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                PgConnection con = new PgConnection();
                String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and ( " + getDbsSql("t2") + " )";

                String totalSQL = "SELECT G1.NB_T, "
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
                        + "WHERE TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and (" + getDbsSql("t1") + ") limit 1 ) G1 ;";

                ResultSet r = con.getStatement().executeQuery(totalSQL);
                while (r.next()) {
                    ArrayList row = new ArrayList<>();
                    row.add("Totale");
                    row.add("");
                    row.add("Totale");
                    row.add("Totale");
                    row.add(r.getLong("nb_t"));
                    row.add(r.getLong("nb_tt"));
                    row.add(r.getLong("nb_a"));
                    row.add(r.getLong("nb_tl1"));
                    row.add(r.getLong("nb_sa"));
                    row.add(String.format("%.2f", r.getFloat("perApT")) + "%");
                    row.add(String.format("%.2f", r.getFloat("PERTL1pt")) + "%");
                    row.add(String.format("%.2f", r.getFloat("perSApT")) + "%");
                    row.add(getFormatedTime(r.getFloat("avgSec_A")));
                    row.add("--");
                    row.add("--");
                    row.add(getFormatedTime(r.getFloat("avgSec_T")));
                    row.add("--");
                    row.add("--");
                    table.add(row);
                }

                con.closeConnection();

            } catch (Exception ex) {
                Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return generateGblTable2(getDate1(), getDate2());
        }
        return table;
    }

    public List<ArrayList> generateEmpTable2(String d1, String d2) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        List<ArrayList> table = new ArrayList<>();
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            for (int i = 0; i < agences.size(); i++) {
                try {
                    Agence a = agences.get(i);
                    PgConnection con = new PgConnection();

                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'";

                    String SQL = "select  "
                            + "g1.deal_user, "
                            + "g1.name, "
                            + "g1.nb_t, "
                            + "g1.nb_tt, "
                            + "g1.nb_ta, "
                            + "g1.nb_ttl1, "
                            + "g1.nb_tsa, "
                            + "g1.avgsec_a, "
                            + "g1.avgsec_t, "
                            + "CASE  "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric  "
                            + " else  CAST((G1.NB_tA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2))  "
                            + "END AS PERAPT,  "
                            + "   CASE  "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric  "
                            + " else  CAST((G1.NB_tTL1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2))  "
                            + "END AS PERTL1PT,  "
                            + "CASE  "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric  "
                            + " else  CAST((G1.NB_tSA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2))  "
                            + "END AS PERSAPT "
                            + " "
                            + "from "
                            + " "
                            + "(select  "
                            + "t1.deal_user, "
                            + "u.name,  "
                            + "(select count(*) from t_ticket t2 where t2.deal_user = t1.deal_user " + dateCon + ") as nb_t, "
                            + "(select count(*) from t_ticket t2 where t2.deal_user = t1.deal_user and t2.status=4 " + dateCon + ") as nb_tt, "
                            + "(select count(*) from t_ticket t2 where t2.deal_user = t1.deal_user and t2.status=2 " + dateCon + ") as nb_ta, "
                            + "(select count(*) from t_ticket t2 where t2.deal_user = t1.deal_user and DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 and t2.status=4 " + dateCon + ") as nb_ttl1, "
                            + "(select count(*) from t_ticket t2 where t2.deal_user = t1.deal_user and t2.status=0 " + dateCon + ") as nb_tsa, "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) FROM T_TICKET T2 WHERE t2.deal_user = t1.deal_user and T2.call_time is not null  " + dateCon + ") AS AVGSEC_A, "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) FROM T_TICKET T2 WHERE t2.deal_user = t1.deal_user and T2.STATUS = 4  " + dateCon + ") AS AVGSEC_T  "
                            + "from t_ticket t1, t_user u "
                            + "where t1.deal_user= u.id and to_date(to_char(t1.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t1.db_id='" + a.getId() + "'  and u.db_id='" + a.getId() + "' "
                            + "group by u.name,t1.deal_user) g1 order by g1.name;";
                    String subSQL = "SELECT G1.NB_T, "
                            + "G1.NB_TT, "
                            + "G1.NB_A, "
                            + "G1.NB_TL1, "
                            + "G1.NB_SA, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + " else  CAST((G1.NB_A::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERAPT, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + " else  CAST((G1.NB_TL1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERTL1PT, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + " else  CAST((G1.NB_SA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERSAPT, "
                            + "G1.AVGSEC_A, "
                            + "G1.AVGSEC_T "
                            + "FROM "
                            + "(SELECT "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE 1 = 1 "
                            + " " + dateCon + " and t2.deal_user is not null) AS NB_T, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_TT, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 2 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_A, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                            + "AND T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_TL1, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 0 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_SA, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.call_time is not null "
                            + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_A, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_T "
                            + "FROM T_TICKET T1 "
                            + "WHERE t1.deal_user is not null and TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t1.db_id='" + a.getId() + "'  limit 1 ) G1 ;";

                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId().toString());
                        row.add(r.getString("deal_user"));
                        row.add(a.getName());
                        row.add(r.getString("name"));
                        row.add(r.getLong("nb_tt") + "");
                        row.add(r.getLong("nb_ta"));
                        row.add(r.getLong("nb_ttl1") + "");
                        row.add(r.getLong("nb_tsa") + "");
                        row.add(r.getFloat("perApT") + "%");
                        row.add(r.getFloat("PERTL1pt") + "%");
                        row.add(r.getFloat("perSApT") + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--%");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("-%");
                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId().toString());
                        row.add("");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getLong("nb_tt"));
                        row.add(r.getLong("nb_a"));
                        row.add(r.getLong("nb_tl1"));
                        row.add(r.getLong("nb_sa"));
                        row.add(r.getFloat("perApT") + "%");
                        row.add(r.getFloat("PERTL1pt") + "%");
                        row.add(r.getFloat("perSApT") + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--%");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("-%");
                        table.add(row);
                    }

                    con.closeConnection();

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        try {
            PgConnection con = new PgConnection();
            String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') ";
            String totalSQL = "SELECT G1.NB_T, "
                    + "G1.NB_TT, "
                    + "G1.NB_A, "
                    + "G1.NB_TL1, "
                    + "G1.NB_SA, "
                    + "CASE "
                    + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                    + " else  CAST((G1.NB_A::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                    + "END AS PERAPT, "
                    + "CASE "
                    + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                    + " else  CAST((G1.NB_TL1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                    + "END AS PERTL1PT, "
                    + "CASE "
                    + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                    + " else  CAST((G1.NB_SA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                    + "END AS PERSAPT, "
                    + "G1.AVGSEC_A, "
                    + "G1.AVGSEC_T "
                    + "FROM "
                    + "(SELECT "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE 1 = 1 "
                    + " " + dateCon + " and t2.deal_user is not null) AS NB_T, "
                    + " "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.STATUS = 4 "
                    + " " + dateCon + "  and t2.deal_user is not null) AS NB_TT, "
                    + " "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.STATUS = 2 "
                    + " " + dateCon + "  and t2.deal_user is not null) AS NB_A, "
                    + " "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                    + "AND T2.STATUS = 4 "
                    + " " + dateCon + "  and t2.deal_user is not null) AS NB_TL1, "
                    + " "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.STATUS = 0 "
                    + " " + dateCon + "  and t2.deal_user is not null) AS NB_SA, "
                    + " "
                    + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.call_time is not null "
                    + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_A, "
                    + " "
                    + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.STATUS = 4 "
                    + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_T "
                    + "FROM T_TICKET T1 "
                    + "WHERE t1.deal_user is not null and TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') limit 1 ) G1 ;";

            ResultSet r = con.getStatement().executeQuery(totalSQL);
            while (r.next()) {
                ArrayList row = new ArrayList<>();
                row.add("Totale");
                row.add("");
                row.add("Totale");
                row.add("Totale");
                row.add(r.getLong("nb_tt"));
                row.add(r.getLong("nb_a"));
                row.add(r.getLong("nb_tl1"));
                row.add(r.getLong("nb_sa"));
                row.add(r.getFloat("perApT") + "%");
                row.add(r.getFloat("PERTL1pt") + "%");
                row.add(r.getFloat("perSApT") + "%");
                row.add(getFormatedTime(r.getFloat("avgSec_A")));
                row.add("--");
                row.add("--%");
                row.add(getFormatedTime(r.getFloat("avgSec_T")));
                row.add("--");
                row.add("-%");
                table.add(row);
            }

            con.closeConnection();

        } catch (Exception ex) {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }

    public List<ArrayList> generateEmpTable(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        List<ArrayList> table = new ArrayList<>();
        AgenceController ac = new AgenceController();
        CibleController cc = new CibleController();
        if (this.dbs.length > 0) {
            for (int i = 0; i < this.dbs.length; i++) {
                try {
                    Agence a = ac.getAgenceById(UUID.fromString(this.dbs[i]));
                    PgConnection con = new PgConnection();

                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'";

                    String SQL = "select  "
                            + "g1.deal_user, "
                            + "g1.name, "
                            + "g1.nb_t, "
                            + "g1.nb_tt, "
                            + "g1.nb_ta, "
                            + "g1.nb_ttl1, "
                            + "g1.nb_tsa, "
                            + "g1.avgsec_a, "
                            + "g1.avgsec_t, "
                            + "CASE  "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric  "
                            + " else  CAST((G1.NB_tA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2))  "
                            + "END AS PERAPT,  "
                            + "   CASE  "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric  "
                            + " else  CAST((G1.NB_tTL1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2))  "
                            + "END AS PERTL1PT,  "
                            + "CASE  "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric  "
                            + " else  CAST((G1.NB_tSA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2))  "
                            + "END AS PERSAPT "
                            + " "
                            + "from "
                            + " "
                            + "(select  "
                            + "t1.deal_user, "
                            + "u.name,  "
                            + "(select count(*) from t_ticket t2 where t2.deal_user = t1.deal_user " + dateCon + ") as nb_t, "
                            + "(select count(*) from t_ticket t2 where t2.deal_user = t1.deal_user and t2.status=4 " + dateCon + ") as nb_tt, "
                            + "(select count(*) from t_ticket t2 where t2.deal_user = t1.deal_user and t2.status=2 " + dateCon + ") as nb_ta, "
                            + "(select count(*) from t_ticket t2 where t2.deal_user = t1.deal_user and DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 and t2.status=4 " + dateCon + ") as nb_ttl1, "
                            + "(select count(*) from t_ticket t2 where t2.deal_user = t1.deal_user and t2.status=0 " + dateCon + ") as nb_tsa, "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) FROM T_TICKET T2 WHERE t2.deal_user = t1.deal_user and T2.call_time is not null  " + dateCon + ") AS AVGSEC_A, "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) FROM T_TICKET T2 WHERE t2.deal_user = t1.deal_user and T2.STATUS = 4  " + dateCon + ") AS AVGSEC_T  "
                            + "from t_ticket t1, t_user u "
                            + "where t1.deal_user= u.id and to_date(to_char(t1.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t1.db_id='" + a.getId() + "'  and u.db_id='" + a.getId() + "' "
                            + "group by u.name,t1.deal_user) g1 order by g1.name;";
                    String subSQL = "SELECT G1.NB_T, "
                            + "G1.NB_TT, "
                            + "G1.NB_A, "
                            + "G1.NB_TL1, "
                            + "G1.NB_SA, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + " else  CAST((G1.NB_A::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERAPT, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + " else  CAST((G1.NB_TL1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERTL1PT, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + " else  CAST((G1.NB_SA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERSAPT, "
                            + "G1.AVGSEC_A, "
                            + "G1.AVGSEC_T "
                            + "FROM "
                            + "(SELECT "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE 1 = 1 "
                            + " " + dateCon + " and t2.deal_user is not null) AS NB_T, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_TT, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 2 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_A, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                            + "AND T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_TL1, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 0 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_SA, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.call_time is not null "
                            + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_A, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_T "
                            + "FROM T_TICKET T1 "
                            + "WHERE t1.deal_user is not null and TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t1.db_id='" + a.getId() + "'  limit 1 ) G1 ;";

                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId().toString());
                        row.add(r.getString("deal_user"));
                        row.add(a.getName());
                        row.add(r.getString("name"));
                        row.add(r.getLong("nb_tt") + "");
                        row.add(r.getLong("nb_ta"));
                        row.add(r.getLong("nb_ttl1") + "");
                        row.add(r.getLong("nb_tsa") + "");
                        row.add(r.getFloat("perApT") + "%");
                        row.add(r.getFloat("PERTL1pt") + "%");
                        row.add(r.getFloat("perSApT") + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--%");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("-%");
                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId().toString());
                        row.add("");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getLong("nb_tt"));
                        row.add(r.getLong("nb_a"));
                        row.add(r.getLong("nb_tl1"));
                        row.add(r.getLong("nb_sa"));
                        row.add(r.getFloat("perApT") + "%");
                        row.add(r.getFloat("PERTL1pt") + "%");
                        row.add(r.getFloat("perSApT") + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--%");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("-%");
                        table.add(row);
                    }

                    con.closeConnection();

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                PgConnection con = new PgConnection();
                String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and (" + getDbsSql("t2") + ")";
                String totalSQL = "SELECT G1.NB_T, "
                        + "G1.NB_TT, "
                        + "G1.NB_A, "
                        + "G1.NB_TL1, "
                        + "G1.NB_SA, "
                        + "CASE "
                        + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                        + " else  CAST((G1.NB_A::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                        + "END AS PERAPT, "
                        + "CASE "
                        + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                        + " else  CAST((G1.NB_TL1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                        + "END AS PERTL1PT, "
                        + "CASE "
                        + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                        + " else  CAST((G1.NB_SA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                        + "END AS PERSAPT, "
                        + "G1.AVGSEC_A, "
                        + "G1.AVGSEC_T "
                        + "FROM "
                        + "(SELECT "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE 1 = 1 "
                        + " " + dateCon + " and t2.deal_user is not null) AS NB_T, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.STATUS = 4 "
                        + " " + dateCon + "  and t2.deal_user is not null) AS NB_TT, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.STATUS = 2 "
                        + " " + dateCon + "  and t2.deal_user is not null) AS NB_A, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                        + "AND T2.STATUS = 4 "
                        + " " + dateCon + "  and t2.deal_user is not null) AS NB_TL1, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.STATUS = 0 "
                        + " " + dateCon + "  and t2.deal_user is not null) AS NB_SA, "
                        + " "
                        + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.call_time is not null "
                        + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_A, "
                        + " "
                        + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.STATUS = 4 "
                        + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_T "
                        + "FROM T_TICKET T1 "
                        + "WHERE t1.deal_user is not null and TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and (" + getDbsSql("t1") + ")limit 1 ) G1 ;";

                ResultSet r = con.getStatement().executeQuery(totalSQL);
                while (r.next()) {
                    ArrayList row = new ArrayList<>();
                    row.add("Totale");
                    row.add("");
                    row.add("Totale");
                    row.add("Totale");
                    row.add(r.getLong("nb_tt"));
                    row.add(r.getLong("nb_a"));
                    row.add(r.getLong("nb_tl1"));
                    row.add(r.getLong("nb_sa"));
                    row.add(r.getFloat("perApT") + "%");
                    row.add(r.getFloat("PERTL1pt") + "%");
                    row.add(r.getFloat("perSApT") + "%");
                    row.add(getFormatedTime(r.getFloat("avgSec_A")));
                    row.add("--");
                    row.add("--%");
                    row.add(getFormatedTime(r.getFloat("avgSec_T")));
                    row.add("--");
                    row.add("-%");
                    table.add(row);
                }

                con.closeConnection();

            } catch (Exception ex) {
                Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return generateEmpTable2(getDate1(), getDate2());
        }
        return table;
    }

    public List<ArrayList> generateEmpServiceTable2(String d1, String d2) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        List<ArrayList> table = new ArrayList<>();
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            for (int i = 0; i < agences.size(); i++) {
                try {
                    Agence a = agences.get(i);
                    PgConnection con = new PgConnection();

                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'";
                    String SQL = "select g1.deal_user, g1.username, g1.service, g1.biz_type_id, g1.nb_t, g1.nb_tt, g1.nb_ta, g1.nb_ttl1, g1.nb_tsa, g1.avgsec_a, g1.avgsec_t,  CASE WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric  ELSE CAST((G1.NB_tA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) END AS PERAPT, CASE WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric                   ELSE CAST((G1.NB_tTL1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) END AS PERTL1PT, CASE WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric ELSE CAST((G1.NB_tSA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2))  END AS PERSAPT from "
                            + " (select t1.deal_user,  u.name as username, t1.biz_type_id, b.name as service,"
                            + "(select count(*) from t_ticket t2 where t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id " + dateCon + ") as nb_t, "
                            + "(select count(*) from t_ticket t2 where t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and t2.status=4    " + dateCon + "  ) as nb_tt, "
                            + "(select count(*) from t_ticket t2 where t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and t2.status=2   " + dateCon + "   ) as nb_ta, "
                            + "(select count(*) from t_ticket t2 where t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 and t2.status=4   " + dateCon + "  ) as nb_ttl1,"
                            + "(select count(*) from t_ticket t2 where t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and t2.status=0   " + dateCon + "   ) as nb_tsa,"
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) FROM T_TICKET T2 WHERE t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and T2.call_time is not null    " + dateCon + "   ) AS AVGSEC_A,"
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) FROM T_TICKET T2 WHERE t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and T2.STATUS = 4   " + dateCon + " ) AS AVGSEC_T "
                            + "  from t_ticket t1 , t_biz_type b,t_user u "
                            + "where t1.deal_user is not null and t1.biz_type_id=b.id and t1.deal_user=u.id and to_date(to_char(t1.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t1.db_id='" + a.getId() + "' and b.db_id='" + a.getId() + "' and u.db_id='" + a.getId() + "'"
                            + "group by t1.deal_user,u.name,t1.biz_type_id,b.name) g1 order by g1.username;";
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
                            + " " + dateCon + " and t2.deal_user is not null) AS NB_T, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_TT, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 2 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_A, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                            + "AND T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_TL1, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 0 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_SA, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.call_time is not null "
                            + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_A, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_T "
                            + "FROM T_TICKET T1 "
                            + "WHERE t1.deal_user is not null and TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t1.db_id='" + a.getId() + "' limit 1 ) G1 ;";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId().toString());
                        row.add(r.getString("biz_type_id"));
                        row.add(a.getName());
                        row.add(r.getString("username"));
                        row.add(r.getString("service"));
                        row.add(r.getLong("nb_tt") + "");
                        row.add(r.getLong("nb_ta") + "");
                        row.add(r.getLong("nb_ttl1") + "");
                        row.add(r.getLong("nb_tsa") + "");
                        row.add(r.getFloat("perApT") + "%");
                        row.add(r.getFloat("PERTL1pt") + "%");
                        row.add(r.getFloat("perSApT") + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--%");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("-%");

                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId().toString());
                        row.add("Sous-Totale");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add("Sous-Totale");
                        row.add(r.getLong("nb_tt") + "");
                        row.add(r.getLong("nb_a") + "");
                        row.add(r.getLong("nb_tl1") + "");
                        row.add(r.getLong("nb_sa") + "");
                        row.add(r.getFloat("perApT") + "%");
                        row.add(r.getFloat("PERTL1pt") + "%");
                        row.add(r.getFloat("perSApT") + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--%");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("-%");
                        table.add(row);
                    }
                    con.closeConnection();
                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        try {
            PgConnection con = new PgConnection();
            String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') ";
            String totalSQL = "SELECT G1.NB_T, "
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
                    + " " + dateCon + " and t2.deal_user is not null) AS NB_T, "
                    + " "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.STATUS = 4 "
                    + " " + dateCon + "  and t2.deal_user is not null) AS NB_TT, "
                    + " "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.STATUS = 2 "
                    + " " + dateCon + "  and t2.deal_user is not null) AS NB_A, "
                    + " "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                    + "AND T2.STATUS = 4 "
                    + " " + dateCon + "  and t2.deal_user is not null) AS NB_TL1, "
                    + " "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.STATUS = 0 "
                    + " " + dateCon + "  and t2.deal_user is not null) AS NB_SA, "
                    + " "
                    + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.call_time is not null "
                    + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_A, "
                    + " "
                    + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.STATUS = 4 "
                    + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_T "
                    + "FROM T_TICKET T1 "
                    + "WHERE t1.deal_user is not null and TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') limit 1 ) G1 ;";

            ResultSet r = con.getStatement().executeQuery(totalSQL);
            while (r.next()) {
                ArrayList row = new ArrayList<>();
                row.add("Totale");
                row.add("Totale");
                row.add("Totale");
                row.add("Totale");
                row.add("Totale");
                row.add(r.getLong("nb_tt"));
                row.add(r.getLong("nb_a"));
                row.add(r.getLong("nb_tl1"));
                row.add(r.getLong("nb_sa"));
                row.add(r.getFloat("perApT") + "%");
                row.add(r.getFloat("PERTL1pt") + "%");
                row.add(r.getFloat("perSApT") + "%");
                row.add(getFormatedTime(r.getFloat("avgSec_A")));
                row.add("--");
                row.add("--%");
                row.add(getFormatedTime(r.getFloat("avgSec_T")));
                row.add("--");
                row.add("-%");
                table.add(row);
            }

            con.closeConnection();

        } catch (Exception ex) {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return table;
    }

    public List<ArrayList> generateEmpServiceTable(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        List<ArrayList> table = new ArrayList<>();
        AgenceController ac = new AgenceController();
        CibleController cc = new CibleController();
        if (this.dbs.length > 0) {
            for (int i = 0; i < this.dbs.length; i++) {
                try {
                    Agence a = ac.getAgenceById(UUID.fromString(this.dbs[i]));
                    PgConnection con = new PgConnection();

                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'";
                    String SQL = "select g1.deal_user, g1.username, g1.service, g1.biz_type_id, g1.nb_t, g1.nb_tt, g1.nb_ta, g1.nb_ttl1, g1.nb_tsa, g1.avgsec_a, g1.avgsec_t,  CASE WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric  ELSE CAST((G1.NB_tA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) END AS PERAPT, CASE WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric                   ELSE CAST((G1.NB_tTL1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) END AS PERTL1PT, CASE WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric ELSE CAST((G1.NB_tSA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2))  END AS PERSAPT from "
                            + " (select t1.deal_user,  u.name as username, t1.biz_type_id, b.name as service,"
                            + "(select count(*) from t_ticket t2 where t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id " + dateCon + ") as nb_t, "
                            + "(select count(*) from t_ticket t2 where t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and t2.status=4    " + dateCon + "  ) as nb_tt, "
                            + "(select count(*) from t_ticket t2 where t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and t2.status=2   " + dateCon + "   ) as nb_ta, "
                            + "(select count(*) from t_ticket t2 where t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 and t2.status=4   " + dateCon + "  ) as nb_ttl1,"
                            + "(select count(*) from t_ticket t2 where t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and t2.status=0   " + dateCon + "   ) as nb_tsa,"
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) FROM T_TICKET T2 WHERE t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and T2.call_time is not null    " + dateCon + "   ) AS AVGSEC_A,"
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) FROM T_TICKET T2 WHERE t2.deal_user=t1.deal_user and t2.biz_type_id=t1.biz_type_id and T2.STATUS = 4   " + dateCon + " ) AS AVGSEC_T "
                            + "  from t_ticket t1 , t_biz_type b,t_user u "
                            + "where t1.deal_user is not null and t1.biz_type_id=b.id and t1.deal_user=u.id and to_date(to_char(t1.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t1.db_id='" + a.getId() + "' and b.db_id='" + a.getId() + "' and u.db_id='" + a.getId() + "'"
                            + "group by t1.deal_user,u.name,t1.biz_type_id,b.name) g1 order by g1.username;";
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
                            + " " + dateCon + " and t2.deal_user is not null) AS NB_T, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_TT, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 2 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_A, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                            + "AND T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_TL1, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 0 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_SA, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.call_time is not null "
                            + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_A, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_T "
                            + "FROM T_TICKET T1 "
                            + "WHERE t1.deal_user is not null and TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t1.db_id='" + a.getId() + "' limit 1 ) G1 ;";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId().toString());
                        row.add(r.getString("biz_type_id"));
                        row.add(a.getName());
                        row.add(r.getString("username"));
                        row.add(r.getString("service"));
                        row.add(r.getLong("nb_tt") + "");
                        row.add(r.getLong("nb_ta") + "");
                        row.add(r.getLong("nb_ttl1") + "");
                        row.add(r.getLong("nb_tsa") + "");
                        row.add(r.getFloat("perApT") + "%");
                        row.add(r.getFloat("PERTL1pt") + "%");
                        row.add(r.getFloat("perSApT") + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--%");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("-%");

                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId().toString());
                        row.add("Sous-Totale");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add("Sous-Totale");
                        row.add(r.getLong("nb_tt") + "");
                        row.add(r.getLong("nb_a") + "");
                        row.add(r.getLong("nb_tl1") + "");
                        row.add(r.getLong("nb_sa") + "");
                        row.add(r.getFloat("perApT") + "%");
                        row.add(r.getFloat("PERTL1pt") + "%");
                        row.add(r.getFloat("perSApT") + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--%");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("-%");
                        table.add(row);
                    }
                    con.closeConnection();
                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                PgConnection con = new PgConnection();
                String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and (" + getDbsSql("t2") + ")";
                String totalSQL = "SELECT G1.NB_T, "
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
                        + " " + dateCon + " and t2.deal_user is not null) AS NB_T, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.STATUS = 4 "
                        + " " + dateCon + "  and t2.deal_user is not null) AS NB_TT, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.STATUS = 2 "
                        + " " + dateCon + "  and t2.deal_user is not null) AS NB_A, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                        + "AND T2.STATUS = 4 "
                        + " " + dateCon + "  and t2.deal_user is not null) AS NB_TL1, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.STATUS = 0 "
                        + " " + dateCon + "  and t2.deal_user is not null) AS NB_SA, "
                        + " "
                        + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.call_time is not null "
                        + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_A, "
                        + " "
                        + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.STATUS = 4 "
                        + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_T "
                        + "FROM T_TICKET T1 "
                        + "WHERE t1.deal_user is not null and TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and (" + getDbsSql("t1") + ") limit 1 ) G1 ;";

                ResultSet r = con.getStatement().executeQuery(totalSQL);
                while (r.next()) {
                    ArrayList row = new ArrayList<>();
                    row.add("Totale");
                    row.add("Totale");
                    row.add("Totale");
                    row.add("Totale");
                    row.add("Totale");
                    row.add(r.getLong("nb_tt"));
                    row.add(r.getLong("nb_a"));
                    row.add(r.getLong("nb_tl1"));
                    row.add(r.getLong("nb_sa"));
                    row.add(r.getFloat("perApT") + "%");
                    row.add(r.getFloat("PERTL1pt") + "%");
                    row.add(r.getFloat("perSApT") + "%");
                    row.add(getFormatedTime(r.getFloat("avgSec_A")));
                    row.add("--");
                    row.add("--%");
                    row.add(getFormatedTime(r.getFloat("avgSec_T")));
                    row.add("--");
                    row.add("-%");
                    table.add(row);
                }

                con.closeConnection();

            } catch (Exception ex) {
                Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return generateEmpServiceTable2(getDate1(), getDate2());
        }
        return table;
    }

    public List<ArrayList> generateGchTable2(String d1, String d2) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        List<ArrayList> table = new ArrayList<>();
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            for (int i = 0; i < agences.size(); i++) {
                try {
                    Agence a = agences.get(i);
                    PgConnection con = new PgConnection();

                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'";
                    String SQL = "select "
                            + "g1.deal_win, "
                            + "g1.guichet, "
                            + "g1.nb_t, "
                            + "g1.nb_tt, "
                            + "g1.nb_ta, "
                            + "g1.nb_ttl1, "
                            + "g1.nb_tsa, "
                            + "g1.avgsec_a, "
                            + "g1.avgsec_t, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric=0::numeric then 0::numeric "
                            + " else  CAST((G1.NB_tA::numeric/G1.NB_T::numeric)*100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERAPT, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric=0::numeric then 0::numeric "
                            + " else  CAST((G1.NB_tTL1::numeric/G1.NB_T::numeric)*100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERTL1PT, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric=0::numeric then 0::numeric "
                            + " else  CAST((G1.NB_tSA::numeric/G1.NB_T::numeric)*100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERSAPT "
                            + "from "
                            + "(SELECT "
                            + "t1.deal_win, "
                            + "w.name as guichet, "
                            + "( select count(*)from t_ticket t2 where t2.deal_win=t1.deal_win " + dateCon + ") as nb_t, "
                            + "( select count(*)from t_ticket t2 where t2.deal_win=t1.deal_win and t2.status=4 " + dateCon + ") as  nb_tt, "
                            + "( select count(*)from t_ticket t2 where t2.deal_win=t1.deal_win and t2.status=2 " + dateCon + ") as  nb_ta, "
                            + "( select count(*)from t_ticket t2 where t2.deal_win=t1.deal_win and DATE_PART('epoch'::text,T2.FINISH_TIME-T2.START_TIME)::numeric/60::numeric<=1 and t2.status=4 " + dateCon + ") as nb_ttl1, "
                            + "( select count(*)from t_ticket t2 where t2.deal_win=t1.deal_win and t2.status=0 " + dateCon + ") as  nb_tsa, "
                            + "(SELECT AVG(DATE_PART('epoch'::text,T2.CALL_TIME-T2.TICKET_TIME)::numeric) from T_TICKET T2 WHERE t2.deal_win=t1.deal_win and T2.call_time is not null " + dateCon + ") AS AVGSEC_A, "
                            + "(SELECT AVG(DATE_PART('epoch'::text,T2.FINISH_TIME-T2.START_TIME)::numeric) from T_TICKET T2 WHERE t2.deal_win=t1.deal_win and T2.STATUS=4 " + dateCon + ") AS AVGSEC_T "
                            + "from "
                            + "t_ticket t1, "
                            + "t_window w "
                            + "where t1.deal_win=w.id and to_date(to_char(t1.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t1.db_id='" + a.getId() + "' and w.db_id='" + a.getId() + "' "
                            + "group by t1.deal_win,w.name)g1 "
                            + ";";
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
                            + " " + dateCon + " and t2.deal_user is not null) AS NB_T, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_TT, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 2 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_A, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                            + "AND T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_TL1, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 0 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_SA, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.call_time is not null "
                            + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_A, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_T "
                            + "FROM T_TICKET T1 "
                            + "WHERE t1.deal_user is not null and TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t1.db_id='" + a.getId() + "' limit 1 ) G1 ;";

                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList<String> row = new ArrayList<>();
                        row.add(a.getId().toString());
                        row.add(r.getString("deal_win"));
                        row.add(a.getName());
                        row.add(r.getString("guichet"));
                        row.add(r.getLong("nb_t") + "");
                        row.add(r.getLong("nb_tt") + "");
                        row.add(r.getLong("nb_ta") + "");
                        row.add(r.getLong("nb_ttl1") + "");
                        row.add(r.getLong("nb_tsa") + "");
                        row.add(r.getFloat("perApT") + "%");
                        row.add(r.getFloat("PERTL1pt") + "%");
                        row.add(r.getFloat("perSApT") + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--%");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("--%");

                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList<String> row = new ArrayList<>();
                        row.add(a.getId().toString());
                        row.add("Sous-Totale");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getLong("nb_t") + "");
                        row.add(r.getLong("nb_tt") + "");
                        row.add(r.getLong("nb_a") + "");
                        row.add(r.getLong("nb_tl1") + "");
                        row.add(r.getLong("nb_sa") + "");
                        row.add(r.getFloat("perApT") + "%");
                        row.add(r.getFloat("PERTL1pt") + "%");
                        row.add(r.getFloat("perSApT") + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--%");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("--%");
                        table.add(row);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        try {
            PgConnection con = new PgConnection();
            String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') ";
            String totalSQL = "SELECT G1.NB_T, "
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
                    + " " + dateCon + " and t2.deal_user is not null) AS NB_T, "
                    + " "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.STATUS = 4 "
                    + " " + dateCon + "  and t2.deal_user is not null) AS NB_TT, "
                    + " "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.STATUS = 2 "
                    + " " + dateCon + "  and t2.deal_user is not null) AS NB_A, "
                    + " "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                    + "AND T2.STATUS = 4 "
                    + " " + dateCon + "  and t2.deal_user is not null) AS NB_TL1, "
                    + " "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.STATUS = 0 "
                    + " " + dateCon + "  and t2.deal_user is not null) AS NB_SA, "
                    + " "
                    + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.call_time is not null "
                    + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_A, "
                    + " "
                    + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.STATUS = 4 "
                    + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_T "
                    + "FROM T_TICKET T1 "
                    + "WHERE t1.deal_user is not null and TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') limit 1 ) G1 ;";

            ResultSet r = con.getStatement().executeQuery(totalSQL);
            while (r.next()) {
                ArrayList row = new ArrayList<>();
                row.add("Totale");
                row.add("");
                row.add("Totale");
                row.add("Totale");
                row.add(r.getLong("nb_t") + "");
                row.add(r.getLong("nb_tt") + "");
                row.add(r.getLong("nb_a") + "");
                row.add(r.getLong("nb_tl1") + "");
                row.add(r.getLong("nb_sa") + "");
                row.add(r.getFloat("perApT") + "%");
                row.add(r.getFloat("PERTL1pt") + "%");
                row.add(r.getFloat("perSApT") + "%");
                row.add(getFormatedTime(r.getFloat("avgSec_A")));
                row.add("--");
                row.add("--%");
                row.add(getFormatedTime(r.getFloat("avgSec_T")));
                row.add("--");
                row.add("--%");
                table.add(row);
            }

            con.closeConnection();

        } catch (Exception ex) {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return table;
    }

    public List<ArrayList> generateGchTable(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        List<ArrayList> table = new ArrayList<>();
        AgenceController ac = new AgenceController();
        CibleController cc = new CibleController();
        if (this.dbs.length > 0) {
            for (int i = 0; i < this.dbs.length; i++) {
                try {
                    Agence a = ac.getAgenceById(UUID.fromString(this.dbs[i]));
                    PgConnection con = new PgConnection();

                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'";
                    String SQL = "select "
                            + "g1.deal_win, "
                            + "g1.guichet, "
                            + "g1.nb_t, "
                            + "g1.nb_tt, "
                            + "g1.nb_ta, "
                            + "g1.nb_ttl1, "
                            + "g1.nb_tsa, "
                            + "g1.avgsec_a, "
                            + "g1.avgsec_t, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric=0::numeric then 0::numeric "
                            + " else  CAST((G1.NB_tA::numeric/G1.NB_T::numeric)*100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERAPT, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric=0::numeric then 0::numeric "
                            + " else  CAST((G1.NB_tTL1::numeric/G1.NB_T::numeric)*100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERTL1PT, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric=0::numeric then 0::numeric "
                            + " else  CAST((G1.NB_tSA::numeric/G1.NB_T::numeric)*100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERSAPT "
                            + "from "
                            + "(SELECT "
                            + "t1.deal_win, "
                            + "w.name as guichet, "
                            + "( select count(*)from t_ticket t2 where t2.deal_win=t1.deal_win " + dateCon + ") as nb_t, "
                            + "( select count(*)from t_ticket t2 where t2.deal_win=t1.deal_win and t2.status=4 " + dateCon + ") as  nb_tt, "
                            + "( select count(*)from t_ticket t2 where t2.deal_win=t1.deal_win and t2.status=2 " + dateCon + ") as  nb_ta, "
                            + "( select count(*)from t_ticket t2 where t2.deal_win=t1.deal_win and DATE_PART('epoch'::text,T2.FINISH_TIME-T2.START_TIME)::numeric/60::numeric<=1 and t2.status=4 " + dateCon + ") as nb_ttl1, "
                            + "( select count(*)from t_ticket t2 where t2.deal_win=t1.deal_win and t2.status=0 " + dateCon + ") as  nb_tsa, "
                            + "(SELECT AVG(DATE_PART('epoch'::text,T2.CALL_TIME-T2.TICKET_TIME)::numeric) from T_TICKET T2 WHERE t2.deal_win=t1.deal_win and T2.call_time is not null " + dateCon + ") AS AVGSEC_A, "
                            + "(SELECT AVG(DATE_PART('epoch'::text,T2.FINISH_TIME-T2.START_TIME)::numeric) from T_TICKET T2 WHERE t2.deal_win=t1.deal_win and T2.STATUS=4 " + dateCon + ") AS AVGSEC_T "
                            + "from "
                            + "t_ticket t1, "
                            + "t_window w "
                            + "where t1.deal_win=w.id and to_date(to_char(t1.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t1.db_id='" + a.getId() + "' and w.db_id='" + a.getId() + "' "
                            + "group by t1.deal_win,w.name)g1 "
                            + ";";
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
                            + " " + dateCon + " and t2.deal_user is not null) AS NB_T, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_TT, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 2 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_A, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                            + "AND T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_TL1, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 0 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS NB_SA, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.call_time is not null "
                            + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_A, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_T "
                            + "FROM T_TICKET T1 "
                            + "WHERE t1.deal_user is not null and TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t1.db_id='" + a.getId() + "' limit 1 ) G1 ;";

                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList<String> row = new ArrayList<>();
                        row.add(a.getId().toString());
                        row.add(r.getString("deal_win"));
                        row.add(a.getName());
                        row.add(r.getString("guichet"));
                        row.add(r.getLong("nb_t") + "");
                        row.add(r.getLong("nb_tt") + "");
                        row.add(r.getLong("nb_ta") + "");
                        row.add(r.getLong("nb_ttl1") + "");
                        row.add(r.getLong("nb_tsa") + "");
                        row.add(r.getFloat("perApT") + "%");
                        row.add(r.getFloat("PERTL1pt") + "%");
                        row.add(r.getFloat("perSApT") + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--%");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("--%");

                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList<String> row = new ArrayList<>();
                        row.add(a.getId().toString());
                        row.add("Sous-Totale");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getLong("nb_t") + "");
                        row.add(r.getLong("nb_tt") + "");
                        row.add(r.getLong("nb_a") + "");
                        row.add(r.getLong("nb_tl1") + "");
                        row.add(r.getLong("nb_sa") + "");
                        row.add(r.getFloat("perApT") + "%");
                        row.add(r.getFloat("PERTL1pt") + "%");
                        row.add(r.getFloat("perSApT") + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--%");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("--%");
                        table.add(row);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            try {
                PgConnection con = new PgConnection();
                String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and (" + getDbsSql("t2") + ") ";
                String totalSQL = "SELECT G1.NB_T, "
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
                        + " " + dateCon + " and t2.deal_user is not null) AS NB_T, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.STATUS = 4 "
                        + " " + dateCon + "  and t2.deal_user is not null) AS NB_TT, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.STATUS = 2 "
                        + " " + dateCon + "  and t2.deal_user is not null) AS NB_A, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                        + "AND T2.STATUS = 4 "
                        + " " + dateCon + "  and t2.deal_user is not null) AS NB_TL1, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.STATUS = 0 "
                        + " " + dateCon + "  and t2.deal_user is not null) AS NB_SA, "
                        + " "
                        + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.call_time is not null "
                        + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_A, "
                        + " "
                        + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.STATUS = 4 "
                        + " " + dateCon + "  and t2.deal_user is not null) AS AVGSEC_T "
                        + "FROM T_TICKET T1 "
                        + "WHERE t1.deal_user is not null and TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and (" + getDbsSql("t1") + ") limit 1 ) G1 ;";

                ResultSet r = con.getStatement().executeQuery(totalSQL);
                while (r.next()) {
                    ArrayList row = new ArrayList<>();
                    row.add("Totale");
                    row.add("");
                    row.add("Totale");
                    row.add("Totale");
                    row.add(r.getLong("nb_t") + "");
                    row.add(r.getLong("nb_tt") + "");
                    row.add(r.getLong("nb_a") + "");
                    row.add(r.getLong("nb_tl1") + "");
                    row.add(r.getLong("nb_sa") + "");
                    row.add(r.getFloat("perApT") + "%");
                    row.add(r.getFloat("PERTL1pt") + "%");
                    row.add(r.getFloat("perSApT") + "%");
                    row.add(getFormatedTime(r.getFloat("avgSec_A")));
                    row.add("--");
                    row.add("--%");
                    row.add(getFormatedTime(r.getFloat("avgSec_T")));
                    row.add("--");
                    row.add("--%");
                    table.add(row);
                }

                con.closeConnection();

            } catch (Exception ex) {
                Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return generateGchTable2(getDate1(), getDate2());
        }
        return table;
    }

    public List<ArrayList> generateGchServiceTable2(String d1, String d2) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        List<ArrayList> table = new ArrayList<>();
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            for (int i = 0; i < agences.size(); i++) {
                try {
                    Agence a = agences.get(i);
                    PgConnection con = new PgConnection();

                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'";
                    String SQL = "SELECT G1.DEAL_WIN, "
                            + "G1.GUICHET, "
                            + "g1.service, "
                            + "G1.NB_T, "
                            + "G1.NB_TT, "
                            + "G1.NB_TA, "
                            + "G1.NB_TTL1, "
                            + "G1.NB_TSA, "
                            + "G1.AVGSEC_A, "
                            + "G1.AVGSEC_T, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + "ELSE CAST((G1.NB_TA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERAPT, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + "ELSE CAST((G1.NB_TTL1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERTL1PT, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + "ELSE CAST((G1.NB_TSA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERSAPT "
                            + "FROM "
                            + "(SELECT T1.DEAL_WIN, "
                            + " t1.biz_type_id, "
                            + "W.NAME AS GUICHET, "
                            + " b.name as service, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.DEAL_WIN = T1.DEAL_WIN and T2.biz_type_id=t1.biz_type_id " + dateCon + ") AS NB_T, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.DEAL_WIN = T1.DEAL_WIN "
                            + "AND T2.STATUS = 4 and T2.biz_type_id=t1.biz_type_id  " + dateCon + ") AS NB_TT, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.DEAL_WIN = T1.DEAL_WIN and T2.biz_type_id=t1.biz_type_id "
                            + "AND T2.STATUS = 2  " + dateCon + ") AS NB_TA, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.DEAL_WIN = T1.DEAL_WIN "
                            + "AND DATE_PART('epoch'::text, "
                            + " "
                            + "T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                            + "AND T2.STATUS = 4 and T2.biz_type_id=t1.biz_type_id " + dateCon + ") AS NB_TTL1, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.DEAL_WIN = T1.DEAL_WIN "
                            + "AND T2.STATUS = 0 and T2.biz_type_id=t1.biz_type_id " + dateCon + ") AS NB_TSA, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, "
                            + " "
                            + "T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.DEAL_WIN = T1.DEAL_WIN "
                            + "AND T2.CALL_TIME IS NOT NULL and T2.biz_type_id=t1.biz_type_id " + dateCon + ") AS AVGSEC_A, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text,T2.FINISH_TIME - T2.START_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.DEAL_WIN = T1.DEAL_WIN "
                            + "AND T2.STATUS = 4 and T2.biz_type_id=t1.biz_type_id " + dateCon + ") AS AVGSEC_T "
                            + "FROM T_TICKET T1, "
                            + "T_WINDOW W , t_biz_type b "
                            + "WHERE T1.DEAL_WIN = W.ID and T1.biz_type_id = b.id  and to_date(to_char(t1.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t1.db_id='" + a.getId() + "' and b.db_id='" + a.getId() + "' and w.db_id='" + a.getId() + "' "
                            + "GROUP BY T1.DEAL_WIN, "
                            + "W.NAME,b.name,t1.biz_type_id) G1;";
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
                            + " " + dateCon + " and t2.deal_win is not null) AS NB_T, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_win is not null) AS NB_TT, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 2 "
                            + " " + dateCon + "  and t2.deal_win is not null) AS NB_A, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                            + "AND T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_win is not null) AS NB_TL1, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 0 "
                            + " " + dateCon + "  and t2.deal_win is not null) AS NB_SA, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.call_time is not null "
                            + " " + dateCon + "  and t2.deal_win is not null) AS AVGSEC_A, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_win is not null) AS AVGSEC_T "
                            + "FROM T_TICKET T1 "
                            + "WHERE t1.deal_win is not null and TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t1.db_id='" + a.getId() + "'  limit 1 ) G1 ;";

                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId());
                        row.add(r.getString("deal_win"));
                        row.add(a.getName());
                        row.add(r.getString("guichet"));
                        row.add(r.getString("service"));
                        row.add(r.getLong("nb_t") + "");
                        row.add(r.getLong("nb_tt") + "");
                        row.add(r.getLong("nb_ta") + "");
                        row.add(r.getLong("nb_ttl1") + "");
                        row.add(r.getLong("nb_tsa") + "");
                        row.add(r.getFloat("perApT") + "%");
                        row.add(r.getFloat("PERTL1pt") + "%");
                        row.add(r.getFloat("perSApT") + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--%");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("--%");

                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId());
                        row.add("Sous-Totale");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add("Sous-Totale");
                        row.add(r.getLong("nb_t") + "");
                        row.add(r.getLong("nb_tt") + "");
                        row.add(r.getLong("nb_a") + "");
                        row.add(r.getLong("nb_tl1") + "");
                        row.add(r.getLong("nb_sa") + "");
                        row.add(r.getFloat("perApT") + "%");
                        row.add(r.getFloat("PERTL1pt") + "%");
                        row.add(r.getFloat("perSApT") + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--%");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("--%");
                        table.add(row);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        try {
            PgConnection con = new PgConnection();
            String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') ";

            String totalSQL = "SELECT G1.NB_T, "
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
                    + " " + dateCon + " and t2.deal_win is not null) AS NB_T, "
                    + " "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.STATUS = 4 "
                    + " " + dateCon + "  and t2.deal_win is not null) AS NB_TT, "
                    + " "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.STATUS = 2 "
                    + " " + dateCon + "  and t2.deal_win is not null) AS NB_A, "
                    + " "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                    + "AND T2.STATUS = 4 "
                    + " " + dateCon + "  and t2.deal_win is not null) AS NB_TL1, "
                    + " "
                    + "(SELECT COUNT(*) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.STATUS = 0 "
                    + " " + dateCon + "  and t2.deal_win is not null) AS NB_SA, "
                    + " "
                    + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.call_time is not null "
                    + " " + dateCon + "  and t2.deal_win is not null) AS AVGSEC_A, "
                    + " "
                    + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                    + "FROM T_TICKET T2 "
                    + "WHERE T2.STATUS = 4 "
                    + " " + dateCon + "  and t2.deal_win is not null) AS AVGSEC_T "
                    + "FROM T_TICKET T1 "
                    + "WHERE t1.deal_win is not null and TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') limit 1 ) G1 ;";

            ResultSet r = con.getStatement().executeQuery(totalSQL);
            while (r.next()) {
                ArrayList row = new ArrayList<>();
                row.add("Totale");
                row.add("");
                row.add("Totale");
                row.add("Totale");
                row.add("Totale");
                row.add(r.getLong("nb_t") + "");
                row.add(r.getLong("nb_tt") + "");
                row.add(r.getLong("nb_a") + "");
                row.add(r.getLong("nb_tl1") + "");
                row.add(r.getLong("nb_sa") + "");
                row.add(r.getFloat("perApT") + "%");
                row.add(r.getFloat("PERTL1pt") + "%");
                row.add(r.getFloat("perSApT") + "%");
                row.add(getFormatedTime(r.getFloat("avgSec_A")));
                row.add("--");
                row.add("--%");
                row.add(getFormatedTime(r.getFloat("avgSec_T")));
                row.add("--");
                row.add("--%");
                table.add(row);
            }

            con.closeConnection();

        } catch (Exception ex) {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return table;
    }

    public List<ArrayList> generateGchServiceTable(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        List<ArrayList> table = new ArrayList<>();
        AgenceController ac = new AgenceController();
        CibleController cc = new CibleController();
        if (this.dbs.length > 0) {
            for (int i = 0; i < this.dbs.length; i++) {
                try {
                    Agence a = ac.getAgenceById(UUID.fromString(this.dbs[i]));
                    PgConnection con = new PgConnection();

                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'";
                    String SQL = "SELECT G1.DEAL_WIN, "
                            + "G1.GUICHET, "
                            + "g1.service, "
                            + "G1.NB_T, "
                            + "G1.NB_TT, "
                            + "G1.NB_TA, "
                            + "G1.NB_TTL1, "
                            + "G1.NB_TSA, "
                            + "G1.AVGSEC_A, "
                            + "G1.AVGSEC_T, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + "ELSE CAST((G1.NB_TA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERAPT, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + "ELSE CAST((G1.NB_TTL1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERTL1PT, "
                            + "CASE "
                            + "WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric "
                            + "ELSE CAST((G1.NB_TSA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) "
                            + "END AS PERSAPT "
                            + "FROM "
                            + "(SELECT T1.DEAL_WIN, "
                            + " t1.biz_type_id, "
                            + "W.NAME AS GUICHET, "
                            + " b.name as service, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.DEAL_WIN = T1.DEAL_WIN and T2.biz_type_id=t1.biz_type_id " + dateCon + ") AS NB_T, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.DEAL_WIN = T1.DEAL_WIN "
                            + "AND T2.STATUS = 4 and T2.biz_type_id=t1.biz_type_id  " + dateCon + ") AS NB_TT, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.DEAL_WIN = T1.DEAL_WIN and T2.biz_type_id=t1.biz_type_id "
                            + "AND T2.STATUS = 2  " + dateCon + ") AS NB_TA, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.DEAL_WIN = T1.DEAL_WIN "
                            + "AND DATE_PART('epoch'::text, "
                            + " "
                            + "T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                            + "AND T2.STATUS = 4 and T2.biz_type_id=t1.biz_type_id " + dateCon + ") AS NB_TTL1, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.DEAL_WIN = T1.DEAL_WIN "
                            + "AND T2.STATUS = 0 and T2.biz_type_id=t1.biz_type_id " + dateCon + ") AS NB_TSA, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, "
                            + " "
                            + "T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.DEAL_WIN = T1.DEAL_WIN "
                            + "AND T2.CALL_TIME IS NOT NULL and T2.biz_type_id=t1.biz_type_id " + dateCon + ") AS AVGSEC_A, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text,T2.FINISH_TIME - T2.START_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.DEAL_WIN = T1.DEAL_WIN "
                            + "AND T2.STATUS = 4 and T2.biz_type_id=t1.biz_type_id " + dateCon + ") AS AVGSEC_T "
                            + "FROM T_TICKET T1, "
                            + "T_WINDOW W , t_biz_type b "
                            + "WHERE T1.DEAL_WIN = W.ID and T1.biz_type_id = b.id  and to_date(to_char(t1.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t1.db_id='" + a.getId() + "' and b.db_id='" + a.getId() + "' and w.db_id='" + a.getId() + "' "
                            + "GROUP BY T1.DEAL_WIN, "
                            + "W.NAME,b.name,t1.biz_type_id) G1;";
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
                            + " " + dateCon + " and t2.deal_win is not null) AS NB_T, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_win is not null) AS NB_TT, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 2 "
                            + " " + dateCon + "  and t2.deal_win is not null) AS NB_A, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                            + "AND T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_win is not null) AS NB_TL1, "
                            + " "
                            + "(SELECT COUNT(*) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 0 "
                            + " " + dateCon + "  and t2.deal_win is not null) AS NB_SA, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.call_time is not null "
                            + " " + dateCon + "  and t2.deal_win is not null) AS AVGSEC_A, "
                            + " "
                            + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                            + "FROM T_TICKET T2 "
                            + "WHERE T2.STATUS = 4 "
                            + " " + dateCon + "  and t2.deal_win is not null) AS AVGSEC_T "
                            + "FROM T_TICKET T1 "
                            + "WHERE t1.deal_win is not null and TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t1.db_id='" + a.getId() + "'  limit 1 ) G1 ;";

                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId());
                        row.add(r.getString("deal_win"));
                        row.add(a.getName());
                        row.add(r.getString("guichet"));
                        row.add(r.getString("service"));
                        row.add(r.getLong("nb_t") + "");
                        row.add(r.getLong("nb_tt") + "");
                        row.add(r.getLong("nb_ta") + "");
                        row.add(r.getLong("nb_ttl1") + "");
                        row.add(r.getLong("nb_tsa") + "");
                        row.add(r.getFloat("perApT") + "%");
                        row.add(r.getFloat("PERTL1pt") + "%");
                        row.add(r.getFloat("perSApT") + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--%");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("--%");

                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId());
                        row.add("Sous-Totale");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add("Sous-Totale");
                        row.add(r.getLong("nb_t") + "");
                        row.add(r.getLong("nb_tt") + "");
                        row.add(r.getLong("nb_a") + "");
                        row.add(r.getLong("nb_tl1") + "");
                        row.add(r.getLong("nb_sa") + "");
                        row.add(r.getFloat("perApT") + "%");
                        row.add(r.getFloat("PERTL1pt") + "%");
                        row.add(r.getFloat("perSApT") + "%");
                        row.add(getFormatedTime(r.getFloat("avgSec_A")));
                        row.add("--");
                        row.add("--%");
                        row.add(getFormatedTime(r.getFloat("avgSec_T")));
                        row.add("--");
                        row.add("--%");
                        table.add(row);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            try {
                PgConnection con = new PgConnection();
                String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and (" + getDbsSql("t2") + ")";

                String totalSQL = "SELECT G1.NB_T, "
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
                        + " " + dateCon + " and t2.deal_win is not null) AS NB_T, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.STATUS = 4 "
                        + " " + dateCon + "  and t2.deal_win is not null) AS NB_TT, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.STATUS = 2 "
                        + " " + dateCon + "  and t2.deal_win is not null) AS NB_A, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 "
                        + "AND T2.STATUS = 4 "
                        + " " + dateCon + "  and t2.deal_win is not null) AS NB_TL1, "
                        + " "
                        + "(SELECT COUNT(*) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.STATUS = 0 "
                        + " " + dateCon + "  and t2.deal_win is not null) AS NB_SA, "
                        + " "
                        + "(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.call_time is not null "
                        + " " + dateCon + "  and t2.deal_win is not null) AS AVGSEC_A, "
                        + " "
                        + "(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) "
                        + "FROM T_TICKET T2 "
                        + "WHERE T2.STATUS = 4 "
                        + " " + dateCon + "  and t2.deal_win is not null) AS AVGSEC_T "
                        + "FROM T_TICKET T1 "
                        + "WHERE t1.deal_win is not null and TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and (" + getDbsSql("t1") + ") limit 1 ) G1 ;";

                ResultSet r = con.getStatement().executeQuery(totalSQL);
                while (r.next()) {
                    ArrayList row = new ArrayList<>();
                    row.add("Totale");
                    row.add("");
                    row.add("Totale");
                    row.add("Totale");
                    row.add("Totale");
                    row.add(r.getLong("nb_t") + "");
                    row.add(r.getLong("nb_tt") + "");
                    row.add(r.getLong("nb_a") + "");
                    row.add(r.getLong("nb_tl1") + "");
                    row.add(r.getLong("nb_sa") + "");
                    row.add(r.getFloat("perApT") + "%");
                    row.add(r.getFloat("PERTL1pt") + "%");
                    row.add(r.getFloat("perSApT") + "%");
                    row.add(getFormatedTime(r.getFloat("avgSec_A")));
                    row.add("--");
                    row.add("--%");
                    row.add(getFormatedTime(r.getFloat("avgSec_T")));
                    row.add("--");
                    row.add("--%");
                    table.add(row);
                }

                con.closeConnection();

            } catch (Exception ex) {
                Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return generateGchServiceTable2(getDate1(), getDate2());
        }
        return table;
    }

    public void generateNdtChart(String d1, String d2) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')";

        String SQL = "Select "
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                + ";";
        try {
            PgConnection con = new PgConnection();
            ResultSet r = con.getStatement().executeQuery(SQL);
            if (r.next()) {
                ArrayList row = new ArrayList();
                row.add(r.getString("h0"));
                row.add(r.getString("h1"));
                row.add(r.getString("h2"));
                row.add(r.getString("h3"));
                row.add(r.getString("h4"));
                row.add(r.getString("h5"));
                row.add(r.getString("h6"));
                row.add(r.getString("h7"));
                row.add(r.getString("h8"));
                row.add(r.getString("h9"));
                row.add(r.getString("h10"));
                row.add(r.getString("h11"));
                row.add(r.getString("h12"));
                row.add(r.getString("h13"));
                row.add(r.getString("h14"));
                row.add(r.getString("h15"));
                row.add(r.getString("h16"));
                row.add(r.getString("h17"));
                row.add(r.getString("h18"));
                row.add(r.getString("h19"));
                row.add(r.getString("h20"));
                row.add(r.getString("h21"));
                row.add(r.getString("h22"));
                row.add(r.getString("h23"));

                setChartData(row, 0, 24);
            }

            //filling chart lable
            setChartLables(getNdtChartCols(), 1, getNdtChartCols().length);

            con.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateNdtChart2(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        if (this.dbs.length > 0) {
            String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and (" + getDbsSql("t2") + ")";

            String SQL = "Select "
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                    + ";";
            try {
                PgConnection con = new PgConnection();
                ResultSet r = con.getStatement().executeQuery(SQL);
                if (r.next()) {
                    ArrayList row = new ArrayList();
                    row.add(r.getString("h0"));
                    row.add(r.getString("h1"));
                    row.add(r.getString("h2"));
                    row.add(r.getString("h3"));
                    row.add(r.getString("h4"));
                    row.add(r.getString("h5"));
                    row.add(r.getString("h6"));
                    row.add(r.getString("h7"));
                    row.add(r.getString("h8"));
                    row.add(r.getString("h9"));
                    row.add(r.getString("h10"));
                    row.add(r.getString("h11"));
                    row.add(r.getString("h12"));
                    row.add(r.getString("h13"));
                    row.add(r.getString("h14"));
                    row.add(r.getString("h15"));
                    row.add(r.getString("h16"));
                    row.add(r.getString("h17"));
                    row.add(r.getString("h18"));
                    row.add(r.getString("h19"));
                    row.add(r.getString("h20"));
                    row.add(r.getString("h21"));
                    row.add(r.getString("h22"));
                    row.add(r.getString("h23"));

                    setChartData(row, 0, 24);
                }

                //filling chart lable
                setChartLables(getNdtChartCols(), 1, getNdtChartCols().length);

                con.closeConnection();
            } catch (Exception ex) {
                Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            generateNdtChart(getDate1(), getDate2());
        }

    }

    public void generateNdttChart(String d1, String d2) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.status=4 ";

        String SQL = "Select "
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                + ";";
        try {
            PgConnection con = new PgConnection();
            ResultSet r = con.getStatement().executeQuery(SQL);
            if (r.next()) {
                ArrayList row = new ArrayList();
                row.add(r.getString("h0"));
                row.add(r.getString("h1"));
                row.add(r.getString("h2"));
                row.add(r.getString("h3"));
                row.add(r.getString("h4"));
                row.add(r.getString("h5"));
                row.add(r.getString("h6"));
                row.add(r.getString("h7"));
                row.add(r.getString("h8"));
                row.add(r.getString("h9"));
                row.add(r.getString("h10"));
                row.add(r.getString("h11"));
                row.add(r.getString("h12"));
                row.add(r.getString("h13"));
                row.add(r.getString("h14"));
                row.add(r.getString("h15"));
                row.add(r.getString("h16"));
                row.add(r.getString("h17"));
                row.add(r.getString("h18"));
                row.add(r.getString("h19"));
                row.add(r.getString("h20"));
                row.add(r.getString("h21"));
                row.add(r.getString("h22"));
                row.add(r.getString("h23"));

                setChartData(row, 0, 24);
            }

            //filling chart lable
            setChartLables(getNdtChartCols(), 1, getNdtChartCols().length);

            con.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateNdttChart2(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        if (this.dbs.length > 0) {
            String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.status=4 and (" + getDbsSql("t2") + ")";

            String SQL = "Select "
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                    + ";";
            try {
                PgConnection con = new PgConnection();
                ResultSet r = con.getStatement().executeQuery(SQL);
                if (r.next()) {
                    ArrayList row = new ArrayList();
                    row.add(r.getString("h0"));
                    row.add(r.getString("h1"));
                    row.add(r.getString("h2"));
                    row.add(r.getString("h3"));
                    row.add(r.getString("h4"));
                    row.add(r.getString("h5"));
                    row.add(r.getString("h6"));
                    row.add(r.getString("h7"));
                    row.add(r.getString("h8"));
                    row.add(r.getString("h9"));
                    row.add(r.getString("h10"));
                    row.add(r.getString("h11"));
                    row.add(r.getString("h12"));
                    row.add(r.getString("h13"));
                    row.add(r.getString("h14"));
                    row.add(r.getString("h15"));
                    row.add(r.getString("h16"));
                    row.add(r.getString("h17"));
                    row.add(r.getString("h18"));
                    row.add(r.getString("h19"));
                    row.add(r.getString("h20"));
                    row.add(r.getString("h21"));
                    row.add(r.getString("h22"));
                    row.add(r.getString("h23"));

                    setChartData(row, 0, 24);
                }

                //filling chart lable
                setChartLables(getNdtChartCols(), 1, getNdtChartCols().length);

                con.closeConnection();
            } catch (Exception ex) {
                Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            generateNdttChart(getDate1(), getDate2());
        }
    }

    public void generateNdtaChart(String d1, String d2) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t2.status=2 ";

        String SQL = "Select "
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                + ";";
        try {
            PgConnection con = new PgConnection();
            ResultSet r = con.getStatement().executeQuery(SQL);
            if (r.next()) {
                ArrayList row = new ArrayList();
                row.add(r.getString("h0"));
                row.add(r.getString("h1"));
                row.add(r.getString("h2"));
                row.add(r.getString("h3"));
                row.add(r.getString("h4"));
                row.add(r.getString("h5"));
                row.add(r.getString("h6"));
                row.add(r.getString("h7"));
                row.add(r.getString("h8"));
                row.add(r.getString("h9"));
                row.add(r.getString("h10"));
                row.add(r.getString("h11"));
                row.add(r.getString("h12"));
                row.add(r.getString("h13"));
                row.add(r.getString("h14"));
                row.add(r.getString("h15"));
                row.add(r.getString("h16"));
                row.add(r.getString("h17"));
                row.add(r.getString("h18"));
                row.add(r.getString("h19"));
                row.add(r.getString("h20"));
                row.add(r.getString("h21"));
                row.add(r.getString("h22"));
                row.add(r.getString("h23"));

                setChartData(row, 0, 24);
            }

            //filling chart lable
            setChartLables(getNdtChartCols(), 1, getNdtChartCols().length);

            con.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateNdtaChart2(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        if (this.dbs.length > 0) {
            String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t2.status=2 and (" + getDbsSql("t2") + ") ";

            String SQL = "Select "
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                    + ";";
            try {
                PgConnection con = new PgConnection();
                ResultSet r = con.getStatement().executeQuery(SQL);
                if (r.next()) {
                    ArrayList row = new ArrayList();
                    row.add(r.getString("h0"));
                    row.add(r.getString("h1"));
                    row.add(r.getString("h2"));
                    row.add(r.getString("h3"));
                    row.add(r.getString("h4"));
                    row.add(r.getString("h5"));
                    row.add(r.getString("h6"));
                    row.add(r.getString("h7"));
                    row.add(r.getString("h8"));
                    row.add(r.getString("h9"));
                    row.add(r.getString("h10"));
                    row.add(r.getString("h11"));
                    row.add(r.getString("h12"));
                    row.add(r.getString("h13"));
                    row.add(r.getString("h14"));
                    row.add(r.getString("h15"));
                    row.add(r.getString("h16"));
                    row.add(r.getString("h17"));
                    row.add(r.getString("h18"));
                    row.add(r.getString("h19"));
                    row.add(r.getString("h20"));
                    row.add(r.getString("h21"));
                    row.add(r.getString("h22"));
                    row.add(r.getString("h23"));

                    setChartData(row, 0, 24);
                }

                //filling chart lable
                setChartLables(getNdtChartCols(), 1, getNdtChartCols().length);

                con.closeConnection();
            } catch (Exception ex) {
                Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            generateNdtaChart(getDate1(), getDate2());
        }
    }

    public void generateNdtsaChart(String d1, String d2) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t2.status=0 ";

        String SQL = "Select "
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                + ";";
        try {
            PgConnection con = new PgConnection();
            ResultSet r = con.getStatement().executeQuery(SQL);
            if (r.next()) {
                ArrayList row = new ArrayList();
                row.add(r.getString("h0"));
                row.add(r.getString("h1"));
                row.add(r.getString("h2"));
                row.add(r.getString("h3"));
                row.add(r.getString("h4"));
                row.add(r.getString("h5"));
                row.add(r.getString("h6"));
                row.add(r.getString("h7"));
                row.add(r.getString("h8"));
                row.add(r.getString("h9"));
                row.add(r.getString("h10"));
                row.add(r.getString("h11"));
                row.add(r.getString("h12"));
                row.add(r.getString("h13"));
                row.add(r.getString("h14"));
                row.add(r.getString("h15"));
                row.add(r.getString("h16"));
                row.add(r.getString("h17"));
                row.add(r.getString("h18"));
                row.add(r.getString("h19"));
                row.add(r.getString("h20"));
                row.add(r.getString("h21"));
                row.add(r.getString("h22"));
                row.add(r.getString("h23"));

                setChartData(row, 0, 24);
            }

            //filling chart lable
            setChartLables(getNdtChartCols(), 1, getNdtChartCols().length);

            con.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateNdtsaChart2(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        if (this.dbs.length > 0) {
            String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t2.status=0 and (" + getDbsSql("t2") + ")";

            String SQL = "Select "
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                    + ";";
            try {
                PgConnection con = new PgConnection();
                ResultSet r = con.getStatement().executeQuery(SQL);
                if (r.next()) {
                    ArrayList row = new ArrayList();
                    row.add(r.getString("h0"));
                    row.add(r.getString("h1"));
                    row.add(r.getString("h2"));
                    row.add(r.getString("h3"));
                    row.add(r.getString("h4"));
                    row.add(r.getString("h5"));
                    row.add(r.getString("h6"));
                    row.add(r.getString("h7"));
                    row.add(r.getString("h8"));
                    row.add(r.getString("h9"));
                    row.add(r.getString("h10"));
                    row.add(r.getString("h11"));
                    row.add(r.getString("h12"));
                    row.add(r.getString("h13"));
                    row.add(r.getString("h14"));
                    row.add(r.getString("h15"));
                    row.add(r.getString("h16"));
                    row.add(r.getString("h17"));
                    row.add(r.getString("h18"));
                    row.add(r.getString("h19"));
                    row.add(r.getString("h20"));
                    row.add(r.getString("h21"));
                    row.add(r.getString("h22"));
                    row.add(r.getString("h23"));

                    setChartData(row, 0, 24);
                }

                //filling chart lable
                setChartLables(getNdtChartCols(), 1, getNdtChartCols().length);

                con.closeConnection();
            } catch (Exception ex) {
                Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            generateNdtsaChart(getDate1(), getDate2());
        }

    }

    public List<ArrayList> generateNdtTable2(String d1, String d2) {

        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        List<ArrayList> table = new ArrayList<>();
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            for (int i = 0; i < agences.size(); i++) {
                try {
                    Agence a = agences.get(i);
                    PgConnection con = new PgConnection();
                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.biz_type_id=b.id and t2.db_id='" + a.getId() + "' ";
                    String dateCon2 = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "' ";

                    String SQL = "Select "
                            + "b.name ,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                            + " from t_biz_type b where b.db_id='" + a.getId() + "';";
                    String subSQL = "Select "
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h0,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h1,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h2,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h3,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h4,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h5,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h6,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h7,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h8,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h9,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h10,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h11,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h12,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h13,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h14,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h15,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h16,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h17,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h18,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h19,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h20,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h21,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h22,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h23"
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add(r.getString("name"));
                        row.add(r.getString("h0"));
                        row.add(r.getString("h1"));
                        row.add(r.getString("h2"));
                        row.add(r.getString("h3"));
                        row.add(r.getString("h4"));
                        row.add(r.getString("h5"));
                        row.add(r.getString("h6"));
                        row.add(r.getString("h7"));
                        row.add(r.getString("h8"));
                        row.add(r.getString("h9"));
                        row.add(r.getString("h10"));
                        row.add(r.getString("h11"));
                        row.add(r.getString("h12"));
                        row.add(r.getString("h13"));
                        row.add(r.getString("h14"));
                        row.add(r.getString("h15"));
                        row.add(r.getString("h16"));
                        row.add(r.getString("h17"));
                        row.add(r.getString("h18"));
                        row.add(r.getString("h19"));
                        row.add(r.getString("h20"));
                        row.add(r.getString("h21"));
                        row.add(r.getString("h22"));
                        row.add(r.getString("h23"));
                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getString("h0"));
                        row.add(r.getString("h1"));
                        row.add(r.getString("h2"));
                        row.add(r.getString("h3"));
                        row.add(r.getString("h4"));
                        row.add(r.getString("h5"));
                        row.add(r.getString("h6"));
                        row.add(r.getString("h7"));
                        row.add(r.getString("h8"));
                        row.add(r.getString("h9"));
                        row.add(r.getString("h10"));
                        row.add(r.getString("h11"));
                        row.add(r.getString("h12"));
                        row.add(r.getString("h13"));
                        row.add(r.getString("h14"));
                        row.add(r.getString("h15"));
                        row.add(r.getString("h16"));
                        row.add(r.getString("h17"));
                        row.add(r.getString("h18"));
                        row.add(r.getString("h19"));
                        row.add(r.getString("h20"));
                        row.add(r.getString("h21"));
                        row.add(r.getString("h22"));
                        row.add(r.getString("h23"));
                        table.add(row);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        try {
            PgConnection con = new PgConnection();
            String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') ";

            String totalSQL = "Select "
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                    + ";";
            ResultSet r = con.getStatement().executeQuery(totalSQL);
            while (r.next()) {
                ArrayList row = new ArrayList<>();
                row.add("Totale");
                row.add("");
                row.add("Totale");
                row.add("Totale");
                row.add(r.getString("h0"));
                row.add(r.getString("h1"));
                row.add(r.getString("h2"));
                row.add(r.getString("h3"));
                row.add(r.getString("h4"));
                row.add(r.getString("h5"));
                row.add(r.getString("h6"));
                row.add(r.getString("h7"));
                row.add(r.getString("h8"));
                row.add(r.getString("h9"));
                row.add(r.getString("h10"));
                row.add(r.getString("h11"));
                row.add(r.getString("h12"));
                row.add(r.getString("h13"));
                row.add(r.getString("h14"));
                row.add(r.getString("h15"));
                row.add(r.getString("h16"));
                row.add(r.getString("h17"));
                row.add(r.getString("h18"));
                row.add(r.getString("h19"));
                row.add(r.getString("h20"));
                row.add(r.getString("h21"));
                row.add(r.getString("h22"));
                row.add(r.getString("h23"));
                table.add(row);
            }

            con.closeConnection();

        } catch (Exception ex) {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return table;
    }

    public List<ArrayList> generateNdtTable(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        List<ArrayList> table = new ArrayList<>();
        AgenceController ac = new AgenceController();
        CibleController cc = new CibleController();
        if (this.dbs.length > 0) {
            for (int i = 0; i < this.dbs.length; i++) {
                try {
                    Agence a = ac.getAgenceById(UUID.fromString(this.dbs[i]));
                    PgConnection con = new PgConnection();
                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.biz_type_id=b.id and t2.db_id='" + a.getId() + "' ";
                    String dateCon2 = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "' ";

                    String SQL = "Select "
                            + "b.name ,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                            + " from t_biz_type b where b.db_id='" + a.getId() + "';";
                    String subSQL = "Select "
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h0,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h1,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h2,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h3,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h4,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h5,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h6,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h7,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h8,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h9,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h10,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h11,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h12,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h13,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h14,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h15,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h16,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h17,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h18,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h19,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h20,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h21,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h22,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h23"
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add(r.getString("name"));
                        row.add(r.getString("h0"));
                        row.add(r.getString("h1"));
                        row.add(r.getString("h2"));
                        row.add(r.getString("h3"));
                        row.add(r.getString("h4"));
                        row.add(r.getString("h5"));
                        row.add(r.getString("h6"));
                        row.add(r.getString("h7"));
                        row.add(r.getString("h8"));
                        row.add(r.getString("h9"));
                        row.add(r.getString("h10"));
                        row.add(r.getString("h11"));
                        row.add(r.getString("h12"));
                        row.add(r.getString("h13"));
                        row.add(r.getString("h14"));
                        row.add(r.getString("h15"));
                        row.add(r.getString("h16"));
                        row.add(r.getString("h17"));
                        row.add(r.getString("h18"));
                        row.add(r.getString("h19"));
                        row.add(r.getString("h20"));
                        row.add(r.getString("h21"));
                        row.add(r.getString("h22"));
                        row.add(r.getString("h23"));
                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getString("h0"));
                        row.add(r.getString("h1"));
                        row.add(r.getString("h2"));
                        row.add(r.getString("h3"));
                        row.add(r.getString("h4"));
                        row.add(r.getString("h5"));
                        row.add(r.getString("h6"));
                        row.add(r.getString("h7"));
                        row.add(r.getString("h8"));
                        row.add(r.getString("h9"));
                        row.add(r.getString("h10"));
                        row.add(r.getString("h11"));
                        row.add(r.getString("h12"));
                        row.add(r.getString("h13"));
                        row.add(r.getString("h14"));
                        row.add(r.getString("h15"));
                        row.add(r.getString("h16"));
                        row.add(r.getString("h17"));
                        row.add(r.getString("h18"));
                        row.add(r.getString("h19"));
                        row.add(r.getString("h20"));
                        row.add(r.getString("h21"));
                        row.add(r.getString("h22"));
                        row.add(r.getString("h23"));
                        table.add(row);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            try {
                PgConnection con = new PgConnection();
                String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and (" + getDbsSql("t2") + ")";

                String totalSQL = "Select "
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                        + ";";
                ResultSet r = con.getStatement().executeQuery(totalSQL);
                while (r.next()) {
                    ArrayList row = new ArrayList<>();
                    row.add("Totale");
                    row.add("");
                    row.add("Totale");
                    row.add("Totale");
                    row.add(r.getString("h0"));
                    row.add(r.getString("h1"));
                    row.add(r.getString("h2"));
                    row.add(r.getString("h3"));
                    row.add(r.getString("h4"));
                    row.add(r.getString("h5"));
                    row.add(r.getString("h6"));
                    row.add(r.getString("h7"));
                    row.add(r.getString("h8"));
                    row.add(r.getString("h9"));
                    row.add(r.getString("h10"));
                    row.add(r.getString("h11"));
                    row.add(r.getString("h12"));
                    row.add(r.getString("h13"));
                    row.add(r.getString("h14"));
                    row.add(r.getString("h15"));
                    row.add(r.getString("h16"));
                    row.add(r.getString("h17"));
                    row.add(r.getString("h18"));
                    row.add(r.getString("h19"));
                    row.add(r.getString("h20"));
                    row.add(r.getString("h21"));
                    row.add(r.getString("h22"));
                    row.add(r.getString("h23"));
                    table.add(row);
                }

                con.closeConnection();

            } catch (Exception ex) {
                Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return generateNdtTable2(getDate1(), getDate2());
        }
        return table;
    }

    public List<ArrayList> generateNdttTable2(String d1, String d2) {

        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        List<ArrayList> table = new ArrayList<>();
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            for (int i = 0; i < agences.size(); i++) {
                try {
                    Agence a = agences.get(i);
                    PgConnection con = new PgConnection();
                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.biz_type_id=b.id and t2.db_id='" + a.getId() + "'  and t2.status=4 ";
                    String dateCon2 = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'  and t2.status=4";

                    String SQL = "Select "
                            + "b.name ,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                            + " from t_biz_type b where b.db_id='" + a.getId() + "';";
                    String subSQL = "Select "
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h0,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h1,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h2,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h3,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h4,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h5,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h6,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h7,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h8,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h9,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h10,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h11,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h12,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h13,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h14,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h15,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h16,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h17,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h18,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h19,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h20,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h21,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h22,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h23"
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add(r.getString("name"));
                        row.add(r.getString("h0"));
                        row.add(r.getString("h1"));
                        row.add(r.getString("h2"));
                        row.add(r.getString("h3"));
                        row.add(r.getString("h4"));
                        row.add(r.getString("h5"));
                        row.add(r.getString("h6"));
                        row.add(r.getString("h7"));
                        row.add(r.getString("h8"));
                        row.add(r.getString("h9"));
                        row.add(r.getString("h10"));
                        row.add(r.getString("h11"));
                        row.add(r.getString("h12"));
                        row.add(r.getString("h13"));
                        row.add(r.getString("h14"));
                        row.add(r.getString("h15"));
                        row.add(r.getString("h16"));
                        row.add(r.getString("h17"));
                        row.add(r.getString("h18"));
                        row.add(r.getString("h19"));
                        row.add(r.getString("h20"));
                        row.add(r.getString("h21"));
                        row.add(r.getString("h22"));
                        row.add(r.getString("h23"));
                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getString("h0"));
                        row.add(r.getString("h1"));
                        row.add(r.getString("h2"));
                        row.add(r.getString("h3"));
                        row.add(r.getString("h4"));
                        row.add(r.getString("h5"));
                        row.add(r.getString("h6"));
                        row.add(r.getString("h7"));
                        row.add(r.getString("h8"));
                        row.add(r.getString("h9"));
                        row.add(r.getString("h10"));
                        row.add(r.getString("h11"));
                        row.add(r.getString("h12"));
                        row.add(r.getString("h13"));
                        row.add(r.getString("h14"));
                        row.add(r.getString("h15"));
                        row.add(r.getString("h16"));
                        row.add(r.getString("h17"));
                        row.add(r.getString("h18"));
                        row.add(r.getString("h19"));
                        row.add(r.getString("h20"));
                        row.add(r.getString("h21"));
                        row.add(r.getString("h22"));
                        row.add(r.getString("h23"));
                        table.add(row);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        try {
            PgConnection con = new PgConnection();
            String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t2.status=4 ";

            String totalSQL = "Select "
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                    + ";";
            ResultSet r = con.getStatement().executeQuery(totalSQL);
            while (r.next()) {
                ArrayList row = new ArrayList<>();
                row.add("Totale");
                row.add("");
                row.add("Totale");
                row.add("Totale");
                row.add(r.getString("h0"));
                row.add(r.getString("h1"));
                row.add(r.getString("h2"));
                row.add(r.getString("h3"));
                row.add(r.getString("h4"));
                row.add(r.getString("h5"));
                row.add(r.getString("h6"));
                row.add(r.getString("h7"));
                row.add(r.getString("h8"));
                row.add(r.getString("h9"));
                row.add(r.getString("h10"));
                row.add(r.getString("h11"));
                row.add(r.getString("h12"));
                row.add(r.getString("h13"));
                row.add(r.getString("h14"));
                row.add(r.getString("h15"));
                row.add(r.getString("h16"));
                row.add(r.getString("h17"));
                row.add(r.getString("h18"));
                row.add(r.getString("h19"));
                row.add(r.getString("h20"));
                row.add(r.getString("h21"));
                row.add(r.getString("h22"));
                row.add(r.getString("h23"));
                table.add(row);
            }

            con.closeConnection();

        } catch (Exception ex) {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return table;
    }

    public List<ArrayList> generateNdttTable(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        List<ArrayList> table = new ArrayList<>();
        AgenceController ac = new AgenceController();
        CibleController cc = new CibleController();
        if (this.dbs.length > 0) {
            for (int i = 0; i < this.dbs.length; i++) {
                try {
                    Agence a = ac.getAgenceById(UUID.fromString(this.dbs[i]));
                    PgConnection con = new PgConnection();
                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.biz_type_id=b.id and t2.db_id='" + a.getId() + "'  and t2.status=4 ";
                    String dateCon2 = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'  and t2.status=4";

                    String SQL = "Select "
                            + "b.name ,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                            + " from t_biz_type b where b.db_id='" + a.getId() + "';";
                    String subSQL = "Select "
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h0,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h1,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h2,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h3,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h4,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h5,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h6,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h7,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h8,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h9,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h10,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h11,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h12,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h13,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h14,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h15,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h16,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h17,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h18,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h19,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h20,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h21,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h22,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h23"
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add(r.getString("name"));
                        row.add(r.getString("h0"));
                        row.add(r.getString("h1"));
                        row.add(r.getString("h2"));
                        row.add(r.getString("h3"));
                        row.add(r.getString("h4"));
                        row.add(r.getString("h5"));
                        row.add(r.getString("h6"));
                        row.add(r.getString("h7"));
                        row.add(r.getString("h8"));
                        row.add(r.getString("h9"));
                        row.add(r.getString("h10"));
                        row.add(r.getString("h11"));
                        row.add(r.getString("h12"));
                        row.add(r.getString("h13"));
                        row.add(r.getString("h14"));
                        row.add(r.getString("h15"));
                        row.add(r.getString("h16"));
                        row.add(r.getString("h17"));
                        row.add(r.getString("h18"));
                        row.add(r.getString("h19"));
                        row.add(r.getString("h20"));
                        row.add(r.getString("h21"));
                        row.add(r.getString("h22"));
                        row.add(r.getString("h23"));
                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getString("h0"));
                        row.add(r.getString("h1"));
                        row.add(r.getString("h2"));
                        row.add(r.getString("h3"));
                        row.add(r.getString("h4"));
                        row.add(r.getString("h5"));
                        row.add(r.getString("h6"));
                        row.add(r.getString("h7"));
                        row.add(r.getString("h8"));
                        row.add(r.getString("h9"));
                        row.add(r.getString("h10"));
                        row.add(r.getString("h11"));
                        row.add(r.getString("h12"));
                        row.add(r.getString("h13"));
                        row.add(r.getString("h14"));
                        row.add(r.getString("h15"));
                        row.add(r.getString("h16"));
                        row.add(r.getString("h17"));
                        row.add(r.getString("h18"));
                        row.add(r.getString("h19"));
                        row.add(r.getString("h20"));
                        row.add(r.getString("h21"));
                        row.add(r.getString("h22"));
                        row.add(r.getString("h23"));
                        table.add(row);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            try {
                PgConnection con = new PgConnection();
                String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t2.status=4 and (" + getDbsSql("t2") + ") ";

                String totalSQL = "Select "
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                        + ";";
                ResultSet r = con.getStatement().executeQuery(totalSQL);
                while (r.next()) {
                    ArrayList row = new ArrayList<>();
                    row.add("Totale");
                    row.add("");
                    row.add("Totale");
                    row.add("Totale");
                    row.add(r.getString("h0"));
                    row.add(r.getString("h1"));
                    row.add(r.getString("h2"));
                    row.add(r.getString("h3"));
                    row.add(r.getString("h4"));
                    row.add(r.getString("h5"));
                    row.add(r.getString("h6"));
                    row.add(r.getString("h7"));
                    row.add(r.getString("h8"));
                    row.add(r.getString("h9"));
                    row.add(r.getString("h10"));
                    row.add(r.getString("h11"));
                    row.add(r.getString("h12"));
                    row.add(r.getString("h13"));
                    row.add(r.getString("h14"));
                    row.add(r.getString("h15"));
                    row.add(r.getString("h16"));
                    row.add(r.getString("h17"));
                    row.add(r.getString("h18"));
                    row.add(r.getString("h19"));
                    row.add(r.getString("h20"));
                    row.add(r.getString("h21"));
                    row.add(r.getString("h22"));
                    row.add(r.getString("h23"));
                    table.add(row);
                }

                con.closeConnection();

            } catch (Exception ex) {
                Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return generateNdttTable2(getDate1(), getDate2());
        }
        return table;
    }

    public List<ArrayList> generateNdtaTable2(String d1, String d2) {

        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        List<ArrayList> table = new ArrayList<>();
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            for (int i = 0; i < agences.size(); i++) {
                try {
                    Agence a = agences.get(i);
                    PgConnection con = new PgConnection();
                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.biz_type_id=b.id and t2.db_id='" + a.getId() + "'  and t2.status=2 ";
                    String dateCon2 = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'  and t2.status=2";

                    String SQL = "Select "
                            + "b.name ,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                            + " from t_biz_type b where b.db_id='" + a.getId() + "';";
                    String subSQL = "Select "
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h0,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h1,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h2,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h3,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h4,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h5,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h6,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h7,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h8,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h9,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h10,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h11,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h12,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h13,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h14,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h15,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h16,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h17,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h18,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h19,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h20,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h21,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h22,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h23"
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add(r.getString("name"));
                        row.add(r.getString("h0"));
                        row.add(r.getString("h1"));
                        row.add(r.getString("h2"));
                        row.add(r.getString("h3"));
                        row.add(r.getString("h4"));
                        row.add(r.getString("h5"));
                        row.add(r.getString("h6"));
                        row.add(r.getString("h7"));
                        row.add(r.getString("h8"));
                        row.add(r.getString("h9"));
                        row.add(r.getString("h10"));
                        row.add(r.getString("h11"));
                        row.add(r.getString("h12"));
                        row.add(r.getString("h13"));
                        row.add(r.getString("h14"));
                        row.add(r.getString("h15"));
                        row.add(r.getString("h16"));
                        row.add(r.getString("h17"));
                        row.add(r.getString("h18"));
                        row.add(r.getString("h19"));
                        row.add(r.getString("h20"));
                        row.add(r.getString("h21"));
                        row.add(r.getString("h22"));
                        row.add(r.getString("h23"));
                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getString("h0"));
                        row.add(r.getString("h1"));
                        row.add(r.getString("h2"));
                        row.add(r.getString("h3"));
                        row.add(r.getString("h4"));
                        row.add(r.getString("h5"));
                        row.add(r.getString("h6"));
                        row.add(r.getString("h7"));
                        row.add(r.getString("h8"));
                        row.add(r.getString("h9"));
                        row.add(r.getString("h10"));
                        row.add(r.getString("h11"));
                        row.add(r.getString("h12"));
                        row.add(r.getString("h13"));
                        row.add(r.getString("h14"));
                        row.add(r.getString("h15"));
                        row.add(r.getString("h16"));
                        row.add(r.getString("h17"));
                        row.add(r.getString("h18"));
                        row.add(r.getString("h19"));
                        row.add(r.getString("h20"));
                        row.add(r.getString("h21"));
                        row.add(r.getString("h22"));
                        row.add(r.getString("h23"));
                        table.add(row);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        try {
            PgConnection con = new PgConnection();
            String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t2.status=2 ";

            String totalSQL = "Select "
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                    + ";";
            ResultSet r = con.getStatement().executeQuery(totalSQL);
            while (r.next()) {
                ArrayList row = new ArrayList<>();
                row.add("Totale");
                row.add("");
                row.add("Totale");
                row.add("Totale");
                row.add(r.getString("h0"));
                row.add(r.getString("h1"));
                row.add(r.getString("h2"));
                row.add(r.getString("h3"));
                row.add(r.getString("h4"));
                row.add(r.getString("h5"));
                row.add(r.getString("h6"));
                row.add(r.getString("h7"));
                row.add(r.getString("h8"));
                row.add(r.getString("h9"));
                row.add(r.getString("h10"));
                row.add(r.getString("h11"));
                row.add(r.getString("h12"));
                row.add(r.getString("h13"));
                row.add(r.getString("h14"));
                row.add(r.getString("h15"));
                row.add(r.getString("h16"));
                row.add(r.getString("h17"));
                row.add(r.getString("h18"));
                row.add(r.getString("h19"));
                row.add(r.getString("h20"));
                row.add(r.getString("h21"));
                row.add(r.getString("h22"));
                row.add(r.getString("h23"));
                table.add(row);
            }

            con.closeConnection();

        } catch (Exception ex) {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return table;
    }

    public List<ArrayList> generateNdtaTable(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        List<ArrayList> table = new ArrayList<>();
        AgenceController ac = new AgenceController();
        CibleController cc = new CibleController();
        if (this.dbs.length > 0) {
            for (int i = 0; i < this.dbs.length; i++) {
                try {
                    Agence a = ac.getAgenceById(UUID.fromString(this.dbs[i]));
                    PgConnection con = new PgConnection();
                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.biz_type_id=b.id and t2.db_id='" + a.getId() + "'  and t2.status=2 ";
                    String dateCon2 = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'  and t2.status=2";

                    String SQL = "Select "
                            + "b.name ,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                            + " from t_biz_type b where b.db_id='" + a.getId() + "';";
                    String subSQL = "Select "
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h0,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h1,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h2,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h3,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h4,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h5,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h6,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h7,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h8,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h9,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h10,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h11,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h12,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h13,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h14,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h15,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h16,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h17,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h18,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h19,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h20,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h21,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h22,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h23"
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add(r.getString("name"));
                        row.add(r.getString("h0"));
                        row.add(r.getString("h1"));
                        row.add(r.getString("h2"));
                        row.add(r.getString("h3"));
                        row.add(r.getString("h4"));
                        row.add(r.getString("h5"));
                        row.add(r.getString("h6"));
                        row.add(r.getString("h7"));
                        row.add(r.getString("h8"));
                        row.add(r.getString("h9"));
                        row.add(r.getString("h10"));
                        row.add(r.getString("h11"));
                        row.add(r.getString("h12"));
                        row.add(r.getString("h13"));
                        row.add(r.getString("h14"));
                        row.add(r.getString("h15"));
                        row.add(r.getString("h16"));
                        row.add(r.getString("h17"));
                        row.add(r.getString("h18"));
                        row.add(r.getString("h19"));
                        row.add(r.getString("h20"));
                        row.add(r.getString("h21"));
                        row.add(r.getString("h22"));
                        row.add(r.getString("h23"));
                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getString("h0"));
                        row.add(r.getString("h1"));
                        row.add(r.getString("h2"));
                        row.add(r.getString("h3"));
                        row.add(r.getString("h4"));
                        row.add(r.getString("h5"));
                        row.add(r.getString("h6"));
                        row.add(r.getString("h7"));
                        row.add(r.getString("h8"));
                        row.add(r.getString("h9"));
                        row.add(r.getString("h10"));
                        row.add(r.getString("h11"));
                        row.add(r.getString("h12"));
                        row.add(r.getString("h13"));
                        row.add(r.getString("h14"));
                        row.add(r.getString("h15"));
                        row.add(r.getString("h16"));
                        row.add(r.getString("h17"));
                        row.add(r.getString("h18"));
                        row.add(r.getString("h19"));
                        row.add(r.getString("h20"));
                        row.add(r.getString("h21"));
                        row.add(r.getString("h22"));
                        row.add(r.getString("h23"));
                        table.add(row);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            try {
                PgConnection con = new PgConnection();
                String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t2.status=2 and (" + getDbsSql("t2") + ") ";

                String totalSQL = "Select "
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                        + ";";
                ResultSet r = con.getStatement().executeQuery(totalSQL);
                while (r.next()) {
                    ArrayList row = new ArrayList<>();
                    row.add("Totale");
                    row.add("");
                    row.add("Totale");
                    row.add("Totale");
                    row.add(r.getString("h0"));
                    row.add(r.getString("h1"));
                    row.add(r.getString("h2"));
                    row.add(r.getString("h3"));
                    row.add(r.getString("h4"));
                    row.add(r.getString("h5"));
                    row.add(r.getString("h6"));
                    row.add(r.getString("h7"));
                    row.add(r.getString("h8"));
                    row.add(r.getString("h9"));
                    row.add(r.getString("h10"));
                    row.add(r.getString("h11"));
                    row.add(r.getString("h12"));
                    row.add(r.getString("h13"));
                    row.add(r.getString("h14"));
                    row.add(r.getString("h15"));
                    row.add(r.getString("h16"));
                    row.add(r.getString("h17"));
                    row.add(r.getString("h18"));
                    row.add(r.getString("h19"));
                    row.add(r.getString("h20"));
                    row.add(r.getString("h21"));
                    row.add(r.getString("h22"));
                    row.add(r.getString("h23"));
                    table.add(row);
                }

                con.closeConnection();

            } catch (Exception ex) {
                Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return generateNdtaTable2(getDate1(), getDate2());
        }
        return table;
    }

    public List<ArrayList> generateNdtsaTable2(String d1, String d2) {

        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        List<ArrayList> table = new ArrayList<>();
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            for (int i = 0; i < agences.size(); i++) {
                try {
                    Agence a = agences.get(i);
                    PgConnection con = new PgConnection();
                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.biz_type_id=b.id and t2.db_id='" + a.getId() + "'  and t2.status=0 ";
                    String dateCon2 = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'  and t2.status=0 ";

                    String SQL = "Select "
                            + "b.name ,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                            + " from t_biz_type b where b.db_id='" + a.getId() + "';";
                    String subSQL = "Select "
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h0,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h1,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h2,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h3,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h4,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h5,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h6,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h7,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h8,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h9,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h10,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h11,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h12,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h13,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h14,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h15,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h16,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h17,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h18,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h19,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h20,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h21,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h22,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h23"
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add(r.getString("name"));
                        row.add(r.getString("h0"));
                        row.add(r.getString("h1"));
                        row.add(r.getString("h2"));
                        row.add(r.getString("h3"));
                        row.add(r.getString("h4"));
                        row.add(r.getString("h5"));
                        row.add(r.getString("h6"));
                        row.add(r.getString("h7"));
                        row.add(r.getString("h8"));
                        row.add(r.getString("h9"));
                        row.add(r.getString("h10"));
                        row.add(r.getString("h11"));
                        row.add(r.getString("h12"));
                        row.add(r.getString("h13"));
                        row.add(r.getString("h14"));
                        row.add(r.getString("h15"));
                        row.add(r.getString("h16"));
                        row.add(r.getString("h17"));
                        row.add(r.getString("h18"));
                        row.add(r.getString("h19"));
                        row.add(r.getString("h20"));
                        row.add(r.getString("h21"));
                        row.add(r.getString("h22"));
                        row.add(r.getString("h23"));
                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getString("h0"));
                        row.add(r.getString("h1"));
                        row.add(r.getString("h2"));
                        row.add(r.getString("h3"));
                        row.add(r.getString("h4"));
                        row.add(r.getString("h5"));
                        row.add(r.getString("h6"));
                        row.add(r.getString("h7"));
                        row.add(r.getString("h8"));
                        row.add(r.getString("h9"));
                        row.add(r.getString("h10"));
                        row.add(r.getString("h11"));
                        row.add(r.getString("h12"));
                        row.add(r.getString("h13"));
                        row.add(r.getString("h14"));
                        row.add(r.getString("h15"));
                        row.add(r.getString("h16"));
                        row.add(r.getString("h17"));
                        row.add(r.getString("h18"));
                        row.add(r.getString("h19"));
                        row.add(r.getString("h20"));
                        row.add(r.getString("h21"));
                        row.add(r.getString("h22"));
                        row.add(r.getString("h23"));
                        table.add(row);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        try {
            PgConnection con = new PgConnection();
            String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t2.status=0 ";

            String totalSQL = "Select "
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                    + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                    + ";";
            ResultSet r = con.getStatement().executeQuery(totalSQL);
            while (r.next()) {
                ArrayList row = new ArrayList<>();
                row.add("Totale");
                row.add("");
                row.add("Totale");
                row.add("Totale");
                row.add(r.getString("h0"));
                row.add(r.getString("h1"));
                row.add(r.getString("h2"));
                row.add(r.getString("h3"));
                row.add(r.getString("h4"));
                row.add(r.getString("h5"));
                row.add(r.getString("h6"));
                row.add(r.getString("h7"));
                row.add(r.getString("h8"));
                row.add(r.getString("h9"));
                row.add(r.getString("h10"));
                row.add(r.getString("h11"));
                row.add(r.getString("h12"));
                row.add(r.getString("h13"));
                row.add(r.getString("h14"));
                row.add(r.getString("h15"));
                row.add(r.getString("h16"));
                row.add(r.getString("h17"));
                row.add(r.getString("h18"));
                row.add(r.getString("h19"));
                row.add(r.getString("h20"));
                row.add(r.getString("h21"));
                row.add(r.getString("h22"));
                row.add(r.getString("h23"));
                table.add(row);
            }

            con.closeConnection();

        } catch (Exception ex) {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return table;
    }

    public List<ArrayList> generateNdtsaTable(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        List<ArrayList> table = new ArrayList<>();
        AgenceController ac = new AgenceController();
        CibleController cc = new CibleController();
        if (this.dbs.length > 0) {
            for (int i = 0; i < this.dbs.length; i++) {
                try {
                    Agence a = ac.getAgenceById(UUID.fromString(this.dbs[i]));
                    PgConnection con = new PgConnection();
                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.biz_type_id=b.id and t2.db_id='" + a.getId() + "'  and t2.status=0 ";
                    String dateCon2 = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'  and t2.status=0 ";

                    String SQL = "Select "
                            + "b.name ,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                            + " from t_biz_type b where b.db_id='" + a.getId() + "';";
                    String subSQL = "Select "
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h0,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h1,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h2,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h3,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h4,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h5,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h6,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h7,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h8,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h9,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h10,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h11,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h12,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h13,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h14,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h15,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h16,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h17,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h18,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h19,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h20,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h21,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h22,"
                            + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon2 + ") as h23"
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add(r.getString("name"));
                        row.add(r.getString("h0"));
                        row.add(r.getString("h1"));
                        row.add(r.getString("h2"));
                        row.add(r.getString("h3"));
                        row.add(r.getString("h4"));
                        row.add(r.getString("h5"));
                        row.add(r.getString("h6"));
                        row.add(r.getString("h7"));
                        row.add(r.getString("h8"));
                        row.add(r.getString("h9"));
                        row.add(r.getString("h10"));
                        row.add(r.getString("h11"));
                        row.add(r.getString("h12"));
                        row.add(r.getString("h13"));
                        row.add(r.getString("h14"));
                        row.add(r.getString("h15"));
                        row.add(r.getString("h16"));
                        row.add(r.getString("h17"));
                        row.add(r.getString("h18"));
                        row.add(r.getString("h19"));
                        row.add(r.getString("h20"));
                        row.add(r.getString("h21"));
                        row.add(r.getString("h22"));
                        row.add(r.getString("h23"));
                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getString("h0"));
                        row.add(r.getString("h1"));
                        row.add(r.getString("h2"));
                        row.add(r.getString("h3"));
                        row.add(r.getString("h4"));
                        row.add(r.getString("h5"));
                        row.add(r.getString("h6"));
                        row.add(r.getString("h7"));
                        row.add(r.getString("h8"));
                        row.add(r.getString("h9"));
                        row.add(r.getString("h10"));
                        row.add(r.getString("h11"));
                        row.add(r.getString("h12"));
                        row.add(r.getString("h13"));
                        row.add(r.getString("h14"));
                        row.add(r.getString("h15"));
                        row.add(r.getString("h16"));
                        row.add(r.getString("h17"));
                        row.add(r.getString("h18"));
                        row.add(r.getString("h19"));
                        row.add(r.getString("h20"));
                        row.add(r.getString("h21"));
                        row.add(r.getString("h22"));
                        row.add(r.getString("h23"));
                        table.add(row);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            try {
                PgConnection con = new PgConnection();
                String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t2.status=0 and (" + getDbsSql("t2") + ") ";

                String totalSQL = "Select "
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('00:00:00','HH24:MI:SS')::time and to_timestamp('00:59:59','HH24:MI:SS')::time " + dateCon + ") as h0,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('01:00:00','HH24:MI:SS')::time and to_timestamp('01:59:59','HH24:MI:SS')::time " + dateCon + ") as h1,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('02:00:00','HH24:MI:SS')::time and to_timestamp('02:59:59','HH24:MI:SS')::time " + dateCon + ") as h2,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('03:00:00','HH24:MI:SS')::time and to_timestamp('03:59:59','HH24:MI:SS')::time " + dateCon + ") as h3,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('04:00:00','HH24:MI:SS')::time and to_timestamp('04:59:59','HH24:MI:SS')::time " + dateCon + ") as h4,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('05:00:00','HH24:MI:SS')::time and to_timestamp('05:59:59','HH24:MI:SS')::time " + dateCon + ") as h5,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('06:00:00','HH24:MI:SS')::time and to_timestamp('06:59:59','HH24:MI:SS')::time " + dateCon + ") as h6,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('07:00:00','HH24:MI:SS')::time and to_timestamp('07:59:59','HH24:MI:SS')::time " + dateCon + ") as h7,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('08:00:00','HH24:MI:SS')::time and to_timestamp('08:59:59','HH24:MI:SS')::time " + dateCon + ") as h8,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('09:00:00','HH24:MI:SS')::time and to_timestamp('09:59:59','HH24:MI:SS')::time " + dateCon + ") as h9,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('10:00:00','HH24:MI:SS')::time and to_timestamp('10:59:59','HH24:MI:SS')::time " + dateCon + ") as h10,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('11:00:00','HH24:MI:SS')::time and to_timestamp('11:59:59','HH24:MI:SS')::time " + dateCon + ") as h11,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('12:00:00','HH24:MI:SS')::time and to_timestamp('12:59:59','HH24:MI:SS')::time " + dateCon + ") as h12,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('13:00:00','HH24:MI:SS')::time and to_timestamp('13:59:59','HH24:MI:SS')::time " + dateCon + ") as h13,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('14:00:00','HH24:MI:SS')::time and to_timestamp('14:59:59','HH24:MI:SS')::time " + dateCon + ") as h14,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('15:00:00','HH24:MI:SS')::time and to_timestamp('15:59:59','HH24:MI:SS')::time " + dateCon + ") as h15,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('16:00:00','HH24:MI:SS')::time and to_timestamp('16:59:59','HH24:MI:SS')::time " + dateCon + ") as h16,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('17:00:00','HH24:MI:SS')::time and to_timestamp('17:59:59','HH24:MI:SS')::time " + dateCon + ") as h17,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('18:00:00','HH24:MI:SS')::time and to_timestamp('18:59:59','HH24:MI:SS')::time " + dateCon + ") as h18,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('19:00:00','HH24:MI:SS')::time and to_timestamp('19:59:59','HH24:MI:SS')::time " + dateCon + ") as h19,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('20:00:00','HH24:MI:SS')::time and to_timestamp('20:59:59','HH24:MI:SS')::time " + dateCon + ") as h20,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('21:00:00','HH24:MI:SS')::time and to_timestamp('21:59:59','HH24:MI:SS')::time " + dateCon + ") as h21,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('22:00:00','HH24:MI:SS')::time and to_timestamp('22:59:59','HH24:MI:SS')::time " + dateCon + ") as h22,"
                        + "(select count(*) from t_ticket t2 where to_timestamp(to_char(t2.ticket_time,'HH24:MI:SS'),'HH24:MI:SS')::time between to_timestamp('23:00:00','HH24:MI:SS')::time and to_timestamp('23:59:59','HH24:MI:SS')::time " + dateCon + ") as h23"
                        + ";";
                ResultSet r = con.getStatement().executeQuery(totalSQL);
                while (r.next()) {
                    ArrayList row = new ArrayList<>();
                    row.add("Totale");
                    row.add("");
                    row.add("Totale");
                    row.add("Totale");
                    row.add(r.getString("h0"));
                    row.add(r.getString("h1"));
                    row.add(r.getString("h2"));
                    row.add(r.getString("h3"));
                    row.add(r.getString("h4"));
                    row.add(r.getString("h5"));
                    row.add(r.getString("h6"));
                    row.add(r.getString("h7"));
                    row.add(r.getString("h8"));
                    row.add(r.getString("h9"));
                    row.add(r.getString("h10"));
                    row.add(r.getString("h11"));
                    row.add(r.getString("h12"));
                    row.add(r.getString("h13"));
                    row.add(r.getString("h14"));
                    row.add(r.getString("h15"));
                    row.add(r.getString("h16"));
                    row.add(r.getString("h17"));
                    row.add(r.getString("h18"));
                    row.add(r.getString("h19"));
                    row.add(r.getString("h20"));
                    row.add(r.getString("h21"));
                    row.add(r.getString("h22"));
                    row.add(r.getString("h23"));
                    table.add(row);
                }

                con.closeConnection();

            } catch (Exception ex) {
                Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return generateNdtsaTable2(getDate1(), getDate2());
        }
        return table;
    }

    public List<ArrayList> generateCnxTable2(String d1, String d2) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        List<ArrayList> table = new ArrayList<>();
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            for (int i = 0; i < agences.size(); i++) {
                try {
                    Agence a = agences.get(i);
                    PgConnection con = new PgConnection();
                    String SQL = "select  "
                            + " l1.user_id, "
                            + " to_char(l1.login_time,'YYYY-MM-DD HH24:MI:SS') as cnx, "
                            + " to_char(l2.login_time,'YYYY-MM-DD HH24:MI:SS') as dcnx, "
                            + " DATE_PART('epoch'::text, l2.login_time - l1.login_time)::numeric as dure, "
                            + " l1.login_ip "
                            + " from t_login_log l1 "
                            + " left join t_login_log l2 on to_date(to_char(l1.login_time,'YYYY-MM-DD'),'YYYY-MM-DD') = to_date(to_char(l2.login_time,'YYYY-MM-DD'),'YYYY-MM-DD') and l1.user_id=l2.user_id and l2.login_time > l1.login_time  "
                            + " where  "
                            + " l1.successed=1  "
                            + " and l2.successed=1  "
                            + " and l1.login_type='in'  "
                            + " and l2.login_type='out'  "
                            + " and to_date(to_char(l1.login_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')"
                            + " and to_date(to_char(l2.login_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')"
                            + " and l1.db_id='" + a.getId() + "' and l2.db_id='" + a.getId() + "'"
                            + " order by l1.user_id,cnx  "
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    float sum = (float) 0;
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add(r.getString("user_id"));
                        row.add(a.getName());
                        row.add(r.getString("user_id"));
                        row.add(r.getString("cnx"));
                        row.add(r.getString("dcnx"));
                        float d = r.getFloat("dure");
                        sum += d;
                        row.add(getFormatedTime(d));
                        row.add(r.getString("login_ip"));
                        table.add(row);

                    }
                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, "FUCK!!!!");
        }
        return table;
    }

    public List<ArrayList> generateCnxTable(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        List<ArrayList> table = new ArrayList<>();
        AgenceController ac = new AgenceController();
        CibleController cc = new CibleController();
        if (this.dbs.length > 0) {
            for (int i = 0; i < this.dbs.length; i++) {
                try {
                    Agence a = ac.getAgenceById(UUID.fromString(this.dbs[i]));
                    PgConnection con = new PgConnection();
                    String SQL = "select  "
                            + " l1.user_id, "
                            + " to_char(l1.login_time,'YYYY-MM-DD HH24:MI:SS') as cnx, "
                            + " to_char(l2.login_time,'YYYY-MM-DD HH24:MI:SS') as dcnx, "
                            + " DATE_PART('epoch'::text, l2.login_time - l1.login_time)::numeric as dure, "
                            + " l1.login_ip "
                            + " from t_login_log l1 "
                            + " left join t_login_log l2 on to_date(to_char(l1.login_time,'YYYY-MM-DD'),'YYYY-MM-DD') = to_date(to_char(l2.login_time,'YYYY-MM-DD'),'YYYY-MM-DD') and l1.user_id=l2.user_id and l2.login_time > l1.login_time  "
                            + " where  "
                            + " l1.successed=1  "
                            + " and l2.successed=1  "
                            + " and l1.login_type='in'  "
                            + " and l2.login_type='out'  "
                            + " and to_date(to_char(l1.login_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')"
                            + " and to_date(to_char(l2.login_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')"
                            + " and l1.db_id='" + a.getId() + "' and l2.db_id='" + a.getId() + "'"
                            + " order by l1.user_id,cnx  "
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    float sum = (float) 0;
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add(r.getString("user_id"));
                        row.add(a.getName());
                        row.add(r.getString("user_id"));
                        row.add(r.getString("cnx"));
                        row.add(r.getString("dcnx"));
                        float d = r.getFloat("dure");
                        sum += d;
                        row.add(getFormatedTime(d));
                        row.add(r.getString("login_ip"));
                        table.add(row);

                    }
                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else {
            return generateCnxTable2(getDate1(), getDate2());
        }
        return table;
    }

    public List<ArrayList> generateSerTable2(String d1, String d2) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        List<ArrayList> table = new ArrayList<>();
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            for (int i = 0; i < agences.size(); i++) {
                try {
                    Agence a = agences.get(i);
                    PgConnection con = new PgConnection();
                    String dateCon = " and t.db_id='" + a.getId() + "' ";
                    String SQL = "select "
                            + "b.id, "
                            + "b.biz_prefix, "
                            + "b.name, "
                            + "b.deal_time_warning,"
                            + "b.status, "
                            + "b.comments, "
                            + "b.start_num, "
                            + "b.call_delay, "
                            + "b.biz_code, "
                            + "b.sort, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and t.status=0 " + dateCon + " and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')) as nb_tea, "
                            + "(select max(DATE_PART('epoch'::text, t.CALL_TIME - t.TICKET_TIME)::numeric) from t_ticket t where t.biz_type_id=b.id and t.call_time is not null " + dateCon + ") as maxA, "
                            + "(select avg(DATE_PART('epoch'::text, t.CALL_TIME - t.TICKET_TIME)::numeric) from t_ticket t where t.biz_type_id=b.id and t.call_time is not null " + dateCon + ") as avgA, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and t.status=3 " + dateCon + "  and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')) as nb_tet, "
                            + "(select avg(DATE_PART('epoch'::text, t.finish_time - t.start_time)::numeric) from t_ticket t where t.biz_type_id=b.id " + dateCon + ") as avgt "
                            + "from t_biz_type b "
                            + " where b.db_id='" + a.getId() + "' "
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add(a.getId());
                        row.add(a.getName());
                        row.add(r.getString("biz_prefix"));
                        row.add(r.getString("name"));
                        row.add(r.getLong("nb_tea"));
                        row.add(getFormatedTime(r.getFloat("maxa")));
                        row.add(getFormatedTime(r.getFloat("avga")));
                        row.add(r.getLong("nb_tet"));
                        row.add(getFormatedTime(r.getFloat("avgt")));
                        String stat = "";
                        switch (r.getString("status")) {
                            case "1":
                                stat = "<span class='text-center text-white bg-success p-1'>NORMAL</span>";
                                break;
                            case "0":
                                stat = "<span class='text-center text-white bg-danger p-1'>PAUSE</span>";
                                break;
                        }
                        row.add(stat);
                        row.add(r.getString("biz_code"));
                        row.add(r.getString("start_num"));
                        row.add(r.getString("comments"));
                        row.add(r.getString("call_delay"));
                        row.add(r.getString("deal_time_warning"));
                        row.add(r.getString("sort"));
                        table.add(row);
                    }
                    con.closeConnection();

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return table;
    }

    public List<ArrayList> generateSerTable(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        List<ArrayList> table = new ArrayList<>();
        AgenceController ac = new AgenceController();
        CibleController cc = new CibleController();
        if (this.dbs.length > 0) {
            for (int i = 0; i < this.dbs.length; i++) {
                try {
                    Agence a = ac.getAgenceById(UUID.fromString(this.dbs[i]));
                    PgConnection con = new PgConnection();
                    String dateCon = " and t.db_id='" + a.getId() + "' ";
                    String SQL = "select "
                            + "b.id, "
                            + "b.biz_prefix, "
                            + "b.name, "
                            + "b.deal_time_warning,"
                            + "b.status, "
                            + "b.comments, "
                            + "b.start_num, "
                            + "b.call_delay, "
                            + "b.biz_code, "
                            + "b.sort, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and t.status=0 " + dateCon + " and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')) as nb_tea, "
                            + "(select max(DATE_PART('epoch'::text, t.CALL_TIME - t.TICKET_TIME)::numeric) from t_ticket t where t.biz_type_id=b.id and t.call_time is not null " + dateCon + ") as maxA, "
                            + "(select avg(DATE_PART('epoch'::text, t.CALL_TIME - t.TICKET_TIME)::numeric) from t_ticket t where t.biz_type_id=b.id and t.call_time is not null " + dateCon + ") as avgA, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and t.status=3 " + dateCon + "  and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')) as nb_tet, "
                            + "(select avg(DATE_PART('epoch'::text, t.finish_time - t.start_time)::numeric) from t_ticket t where t.biz_type_id=b.id " + dateCon + ") as avgt "
                            + "from t_biz_type b "
                            + " where b.db_id='" + a.getId() + "' "
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add(a.getId());
                        row.add(a.getName());
                        row.add(r.getString("biz_prefix"));
                        row.add(r.getString("name"));
                        row.add(r.getLong("nb_tea"));
                        row.add(getFormatedTime(r.getFloat("maxa")));
                        row.add(getFormatedTime(r.getFloat("avga")));
                        row.add(r.getLong("nb_tet"));
                        row.add(getFormatedTime(r.getFloat("avgt")));
                        String stat = "";
                        switch (r.getString("status")) {
                            case "1":
                                stat = "<span class='text-center text-white bg-success p-1'>NORMAL</span>";
                                break;
                            case "0":
                                stat = "<span class='text-center text-white bg-danger p-1'>PAUSE</span>";
                                break;
                        }
                        row.add(stat);
                        row.add(r.getString("biz_code"));
                        row.add(r.getString("start_num"));
                        row.add(r.getString("comments"));
                        row.add(r.getString("call_delay"));
                        row.add(r.getString("deal_time_warning"));
                        row.add(r.getString("sort"));
                        table.add(row);
                    }
                    con.closeConnection();

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else {
            return generateSerTable2(getDate1(), getDate2());
        }
        return table;
    }

    public List<ArrayList> generateSgchTable2() {
        List<ArrayList> table = new ArrayList<>();
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            for (int i = 0; i < agences.size(); i++) {
                try {
                    Agence a = agences.get(i);
                    PgConnection con = new PgConnection();
                    String SQL = "SELECT w.name ,"
                            + "w.win_number, "
                            + "u.name as username, "
                            + "ws.status, "
                            + "ws.access_time, "
                            + "ws.pause_time, "
                            + "ws.current_ticket ,"
                            + "(select t.ticket_id from t_ticket t where t.id= ws.current_ticket ) as ticket_id"
                            + " "
                            + "FROM t_window_status ws , t_window w, t_user u  "
                            + "where ws.window_id=w.id and ws.user_id=u.id  and ws.db_id='" + a.getId() + "' and  w.db_id='" + a.getId() + "' and  u.db_id='" + a.getId() + "'"
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add(r.getString("win_number"));
                        row.add(r.getString("name"));
                        row.add(r.getString("username"));
                        String stat = "";
                        switch (r.getString("status")) {
                            case "1":
                                stat = "<span class='text-center text-white bg-success p-1'>ONLINE</span>";
                                break;
                            case "2":
                                stat = "<span class='text-center text-white bg-danger p-1'>HORS SERVICE</span>";
                                break;
                            case "3":
                                stat = "<span class='text-center text-white bg-secondary p-1'>OFFLINE</span>";
                                break;
                        }

                        row.add(stat);
                        row.add(r.getString("access_time"));
                        row.add(r.getString("pause_time"));
                        row.add(r.getString("ticket_id"));
                        table.add(row);
                    }
                    //divider
                    ArrayList row = new ArrayList();
                    row.add(a.getId());
                    row.add("");
                    row.add(a.getName());
                    row.add("Sous-Totale");
                    row.add("");
                    row.add("");
                    row.add("");
                    row.add("");
                    row.add("");
                    row.add("");
                    table.add(row);

                    con.closeConnection();
                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return table;
    }

    public List<ArrayList> generateGlaTable2(String d1, String d2) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        List<ArrayList> table = new ArrayList<>();
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            for (int i = 0; i < agences.size(); i++) {
                try {
                    Agence a = agences.get(i);
                    PgConnection con = new PgConnection();

                    String dateCon = "and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t.db_id='" + a.getId() + "' ";
                    String SQL = "SELECT  "
                            + "b.name,"
                            + "b.id, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=300 and t.call_time is not null " + dateCon + ") as m0_5, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>300 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=600  and t.call_time is not null  " + dateCon + ") as m5_10, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>600 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=1200 and t.call_time is not null  " + dateCon + ") as m10_20, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>1200 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=1800  and t.call_time is not null  " + dateCon + ") as m20_30, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>1800 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=2700  and t.call_time is not null  " + dateCon + ") as m30_45, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>2700 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=3000  and t.call_time is not null  " + dateCon + ") as m45_50, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>3000  and t.call_time is not null  " + dateCon + ") as m50, "
                            + " "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=15 and t.call_time is not null  " + dateCon + ") as s0_15, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>15 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=30  and t.call_time is not null  " + dateCon + ") as s15_30, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>30 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=60 and t.call_time is not null  " + dateCon + ") as s30_60, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>60 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=90  and t.call_time is not null  " + dateCon + ") as s60_90, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>90 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=120 and t.call_time is not null  " + dateCon + ") as s90_120, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>120 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=300  and t.call_time is not null  " + dateCon + ") as s120, "
                            + " "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and t.call_time is not null  " + dateCon + ") as total "
                            + "FROM t_biz_type b  where b.db_id='" + a.getId() + "' "
                            + ";";
                    String subSQL = "SELECT  "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=300 and t.call_time is not null  " + dateCon + ") as m0_5, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>300 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=600  and t.call_time is not null  " + dateCon + ") as m5_10, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>600 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=1200 and t.call_time is not null " + dateCon + " ) as m10_20, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>1200 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=1800  and t.call_time is not null  " + dateCon + ") as m20_30, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>1800 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=2700  and t.call_time is not null  " + dateCon + ") as m30_45, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>2700 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=3000  and t.call_time is not null  " + dateCon + ") as m45_50, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>3000  and t.call_time is not null  " + dateCon + ") as m50, "
                            + " "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=15 and t.call_time is not null  " + dateCon + ") as s0_15, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>15 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=30  and t.call_time is not null  " + dateCon + ") as s15_30, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>30 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=60 and t.call_time is not null  " + dateCon + ") as s30_60, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>60 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=90  and t.call_time is not null  " + dateCon + ") as s60_90, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>90 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=120 and t.call_time is not null  " + dateCon + ") as s90_120, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>120 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=300  and t.call_time is not null  " + dateCon + ") as s120, "
                            + " "
                            + "(select count(*) from t_ticket t where  t.call_time is not null " + dateCon + " ) as total "
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add(r.getString("id"));
                        row.add(a.getName());
                        row.add(r.getString("name"));
                        row.add(r.getLong("s0_15") + "");
                        row.add(r.getLong("s15_30") + "");
                        row.add(r.getLong("s30_60") + "");
                        row.add(r.getLong("s60_90") + "");
                        row.add(r.getLong("s90_120") + "");
                        row.add(r.getLong("s120") + "");

                        row.add(r.getLong("m0_5") + "");
                        row.add(r.getLong("m5_10") + "");
                        row.add(r.getLong("m10_20") + "");
                        row.add(r.getLong("m20_30") + "");
                        row.add(r.getLong("m30_45") + "");
                        row.add(r.getLong("m45_50") + "");
                        row.add(r.getLong("m50") + "");
                        row.add(r.getLong("total") + "");
                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("Sous-Totale");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getLong("s0_15") + "");
                        row.add(r.getLong("s15_30") + "");
                        row.add(r.getLong("s30_60") + "");
                        row.add(r.getLong("s60_90") + "");
                        row.add(r.getLong("s90_120") + "");
                        row.add(r.getLong("s120") + "");

                        row.add(r.getLong("m0_5") + "");
                        row.add(r.getLong("m5_10") + "");
                        row.add(r.getLong("m10_20") + "");
                        row.add(r.getLong("m20_30") + "");
                        row.add(r.getLong("m30_45") + "");
                        row.add(r.getLong("m45_50") + "");
                        row.add(r.getLong("m50") + "");
                        row.add(r.getLong("total") + "");
                        table.add(row);
                    }
                    con.closeConnection();
                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        try {
            PgConnection con = new PgConnection();
            String dateCon = "and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  ";
            String totalSQL = "SELECT  "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=300 and t.call_time is not null  " + dateCon + ") as m0_5, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>300 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=600  and t.call_time is not null  " + dateCon + ") as m5_10, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>600 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=1200 and t.call_time is not null " + dateCon + " ) as m10_20, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>1200 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=1800  and t.call_time is not null  " + dateCon + ") as m20_30, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>1800 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=2700  and t.call_time is not null  " + dateCon + ") as m30_45, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>2700 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=3000  and t.call_time is not null  " + dateCon + ") as m45_50, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>3000  and t.call_time is not null  " + dateCon + ") as m50, "
                    + " "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=15 and t.call_time is not null  " + dateCon + ") as s0_15, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>15 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=30  and t.call_time is not null  " + dateCon + ") as s15_30, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>30 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=60 and t.call_time is not null  " + dateCon + ") as s30_60, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>60 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=90  and t.call_time is not null  " + dateCon + ") as s60_90, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>90 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=120 and t.call_time is not null  " + dateCon + ") as s90_120, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>120 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=300  and t.call_time is not null  " + dateCon + ") as s120, "
                    + " "
                    + "(select count(*) from t_ticket t where  t.call_time is not null " + dateCon + " ) as total "
                    + ";";
            ResultSet r = con.getStatement().executeQuery(totalSQL);
            while (r.next()) {
                ArrayList row = new ArrayList<>();
                row.add("Totale");
                row.add("");
                row.add("Totale");
                row.add("Totale");
                row.add(r.getLong("s0_15") + "");
                row.add(r.getLong("s15_30") + "");
                row.add(r.getLong("s30_60") + "");
                row.add(r.getLong("s60_90") + "");
                row.add(r.getLong("s90_120") + "");
                row.add(r.getLong("s120") + "");

                row.add(r.getLong("m0_5") + "");
                row.add(r.getLong("m5_10") + "");
                row.add(r.getLong("m10_20") + "");
                row.add(r.getLong("m20_30") + "");
                row.add(r.getLong("m30_45") + "");
                row.add(r.getLong("m45_50") + "");
                row.add(r.getLong("m50") + "");
                row.add(r.getLong("total") + "");
                setChartData2(row, 4, row.size() - 1, 10);
                table.add(row);
            }
            setChartLables2(getGlaCols(), 2, getGlaCols().length - 1, 8);
            con.closeConnection();

        } catch (Exception ex) {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return table;
    }

    public List<ArrayList> generateGlaTable(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        List<ArrayList> table = new ArrayList<>();
        AgenceController ac = new AgenceController();
        CibleController cc = new CibleController();
        if (this.dbs.length > 0) {
            for (int i = 0; i < this.dbs.length; i++) {
                try {
                    Agence a = ac.getAgenceById(UUID.fromString(this.dbs[i]));
                    PgConnection con = new PgConnection();

                    String dateCon = "and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  and t.db_id='" + a.getId() + "' ";
                    String SQL = "SELECT  "
                            + "b.name,"
                            + "b.id, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=300 and t.call_time is not null " + dateCon + ") as m0_5, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>300 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=600  and t.call_time is not null  " + dateCon + ") as m5_10, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>600 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=1200 and t.call_time is not null  " + dateCon + ") as m10_20, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>1200 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=1800  and t.call_time is not null  " + dateCon + ") as m20_30, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>1800 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=2700  and t.call_time is not null  " + dateCon + ") as m30_45, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>2700 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=3000  and t.call_time is not null  " + dateCon + ") as m45_50, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>3000  and t.call_time is not null  " + dateCon + ") as m50, "
                            + " "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=15 and t.call_time is not null  " + dateCon + ") as s0_15, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>15 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=30  and t.call_time is not null  " + dateCon + ") as s15_30, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>30 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=60 and t.call_time is not null  " + dateCon + ") as s30_60, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>60 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=90  and t.call_time is not null  " + dateCon + ") as s60_90, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>90 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=120 and t.call_time is not null  " + dateCon + ") as s90_120, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>120 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=300  and t.call_time is not null  " + dateCon + ") as s120, "
                            + " "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and t.call_time is not null  " + dateCon + ") as total "
                            + "FROM t_biz_type b  where b.db_id='" + a.getId() + "' "
                            + ";";
                    String subSQL = "SELECT  "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=300 and t.call_time is not null  " + dateCon + ") as m0_5, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>300 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=600  and t.call_time is not null  " + dateCon + ") as m5_10, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>600 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=1200 and t.call_time is not null " + dateCon + " ) as m10_20, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>1200 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=1800  and t.call_time is not null  " + dateCon + ") as m20_30, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>1800 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=2700  and t.call_time is not null  " + dateCon + ") as m30_45, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>2700 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=3000  and t.call_time is not null  " + dateCon + ") as m45_50, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>3000  and t.call_time is not null  " + dateCon + ") as m50, "
                            + " "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=15 and t.call_time is not null  " + dateCon + ") as s0_15, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>15 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=30  and t.call_time is not null  " + dateCon + ") as s15_30, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>30 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=60 and t.call_time is not null  " + dateCon + ") as s30_60, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>60 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=90  and t.call_time is not null  " + dateCon + ") as s60_90, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>90 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=120 and t.call_time is not null  " + dateCon + ") as s90_120, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>120 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=300  and t.call_time is not null  " + dateCon + ") as s120, "
                            + " "
                            + "(select count(*) from t_ticket t where  t.call_time is not null " + dateCon + " ) as total "
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add(r.getString("id"));
                        row.add(a.getName());
                        row.add(r.getString("name"));
                        row.add(r.getLong("s0_15") + "");
                        row.add(r.getLong("s15_30") + "");
                        row.add(r.getLong("s30_60") + "");
                        row.add(r.getLong("s60_90") + "");
                        row.add(r.getLong("s90_120") + "");
                        row.add(r.getLong("s120") + "");

                        row.add(r.getLong("m0_5") + "");
                        row.add(r.getLong("m5_10") + "");
                        row.add(r.getLong("m10_20") + "");
                        row.add(r.getLong("m20_30") + "");
                        row.add(r.getLong("m30_45") + "");
                        row.add(r.getLong("m45_50") + "");
                        row.add(r.getLong("m50") + "");
                        row.add(r.getLong("total") + "");
                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("Sous-Totale");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getLong("s0_15") + "");
                        row.add(r.getLong("s15_30") + "");
                        row.add(r.getLong("s30_60") + "");
                        row.add(r.getLong("s60_90") + "");
                        row.add(r.getLong("s90_120") + "");
                        row.add(r.getLong("s120") + "");

                        row.add(r.getLong("m0_5") + "");
                        row.add(r.getLong("m5_10") + "");
                        row.add(r.getLong("m10_20") + "");
                        row.add(r.getLong("m20_30") + "");
                        row.add(r.getLong("m30_45") + "");
                        row.add(r.getLong("m45_50") + "");
                        row.add(r.getLong("m50") + "");
                        row.add(r.getLong("total") + "");
                        table.add(row);
                    }
                    con.closeConnection();
                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            try {
                PgConnection con = new PgConnection();
                String dateCon = "and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and (" + getDbsSql("t") + ") ";
                String totalSQL = "SELECT  "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=300 and t.call_time is not null  " + dateCon + ") as m0_5, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>300 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=600  and t.call_time is not null  " + dateCon + ") as m5_10, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>600 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=1200 and t.call_time is not null " + dateCon + " ) as m10_20, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>1200 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=1800  and t.call_time is not null  " + dateCon + ") as m20_30, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>1800 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=2700  and t.call_time is not null  " + dateCon + ") as m30_45, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>2700 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=3000  and t.call_time is not null  " + dateCon + ") as m45_50, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>3000  and t.call_time is not null  " + dateCon + ") as m50, "
                        + " "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=15 and t.call_time is not null  " + dateCon + ") as s0_15, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>15 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=30  and t.call_time is not null  " + dateCon + ") as s15_30, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>30 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=60 and t.call_time is not null  " + dateCon + ") as s30_60, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>60 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=90  and t.call_time is not null  " + dateCon + ") as s60_90, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>90 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=120 and t.call_time is not null  " + dateCon + ") as s90_120, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric>120 and DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric<=300  and t.call_time is not null  " + dateCon + ") as s120, "
                        + " "
                        + "(select count(*) from t_ticket t where  t.call_time is not null " + dateCon + " ) as total "
                        + ";";
                ResultSet r = con.getStatement().executeQuery(totalSQL);
                while (r.next()) {
                    ArrayList row = new ArrayList<>();
                    row.add("Totale");
                    row.add("");
                    row.add("Totale");
                    row.add("Totale");
                    row.add(r.getLong("s0_15") + "");
                    row.add(r.getLong("s15_30") + "");
                    row.add(r.getLong("s30_60") + "");
                    row.add(r.getLong("s60_90") + "");
                    row.add(r.getLong("s90_120") + "");
                    row.add(r.getLong("s120") + "");

                    row.add(r.getLong("m0_5") + "");
                    row.add(r.getLong("m5_10") + "");
                    row.add(r.getLong("m10_20") + "");
                    row.add(r.getLong("m20_30") + "");
                    row.add(r.getLong("m30_45") + "");
                    row.add(r.getLong("m45_50") + "");
                    row.add(r.getLong("m50") + "");
                    row.add(r.getLong("total") + "");
                    setChartData2(row, 4, row.size() - 1, 10);
                    table.add(row);
                }
                setChartLables2(getGlaCols(), 2, getGlaCols().length - 1, 8);
                con.closeConnection();

            } catch (Exception ex) {
                Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return generateGlaTable2(getDate1(), getDate2());
        }
        return table;
    }

    public List<ArrayList> generateGltTable2(String d1, String d2) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        List<ArrayList> table = new ArrayList<>();
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            for (int i = 0; i < agences.size(); i++) {
                try {
                    Agence a = agences.get(i);
                    PgConnection con = new PgConnection();
                    String dateCon = "and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t.db_id='" + a.getId() + "'";
                    String SQL = "SELECT  "
                            + "b.name, "
                            + "b.id,"
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>=300 and t.status=4 " + dateCon + ") as m5, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>300 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=600  and t.status=4  " + dateCon + ") as m5_10, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>600 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=1200 and t.status=4  " + dateCon + ") as m10_20, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>1200 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=1800  and t.status=4  " + dateCon + ") as m20_30, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>1800 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=2700  and t.status=4  " + dateCon + ") as m30_45, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>2700 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=3000  and t.status=4  " + dateCon + ") as m45_50, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>3000  and t.status=4  " + dateCon + ") as m50, "
                            + " "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=15 and t.status=4  " + dateCon + ") as s0_15, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>15 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=30  and t.status=4  " + dateCon + ") as s15_30, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>30 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=60 and t.status=4  " + dateCon + ") as s30_60, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>60 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=90  and t.status=4  " + dateCon + ") as s60_90, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>90 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=120 and t.status=4  " + dateCon + ") as s90_120, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>120 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=300  and t.status=4  " + dateCon + ") as s120, "
                            + " "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and t.status=4  " + dateCon + ") as total "
                            + "FROM t_biz_type b where b.db_id='" + a.getId() + "' "
                            + ";";
                    String subSQL = "SELECT  "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>=300 and t.status=4  " + dateCon + ") as m5, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>300 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=600  and t.status=4  " + dateCon + ") as m5_10, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>600 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=1200 and t.status=4 " + dateCon + " ) as m10_20, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>1200 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=1800  and t.status=4  " + dateCon + ") as m20_30, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>1800 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=2700  and t.status=4  " + dateCon + ") as m30_45, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>2700 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=3000  and t.status=4  " + dateCon + ") as m45_50, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>3000  and t.status=4  " + dateCon + ") as m50, "
                            + " "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=15 and t.status=4  " + dateCon + ") as s0_15, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>15 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=30  and t.status=4  " + dateCon + ") as s15_30, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>30 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=60 and t.status=4  " + dateCon + ") as s30_60, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>60 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=90  and t.status=4  " + dateCon + ") as s60_90, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>90 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=120 and t.status=4  " + dateCon + ") as s90_120, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>120 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=300  and t.status=4  " + dateCon + ") as s120, "
                            + " "
                            + "(select count(*) from t_ticket t where  t.status=4 " + dateCon + " ) as total "
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add(r.getString("id"));
                        row.add(a.getName());
                        row.add(r.getString("name"));
                        row.add(r.getLong("s0_15") + "");
                        row.add(r.getLong("s15_30") + "");
                        row.add(r.getLong("s30_60") + "");
                        row.add(r.getLong("s60_90") + "");
                        row.add(r.getLong("s90_120") + "");
                        row.add(r.getLong("s120") + "");

                        row.add(r.getLong("m5") + "");
                        row.add(r.getLong("m5_10") + "");
                        row.add(r.getLong("m10_20") + "");
                        row.add(r.getLong("m20_30") + "");
                        row.add(r.getLong("m30_45") + "");
                        row.add(r.getLong("m45_50") + "");
                        row.add(r.getLong("m50") + "");
                        row.add(r.getLong("total") + "");

                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getLong("s0_15") + "");
                        row.add(r.getLong("s15_30") + "");
                        row.add(r.getLong("s30_60") + "");
                        row.add(r.getLong("s60_90") + "");
                        row.add(r.getLong("s90_120") + "");
                        row.add(r.getLong("s120") + "");

                        row.add(r.getLong("m5") + "");
                        row.add(r.getLong("m5_10") + "");
                        row.add(r.getLong("m10_20") + "");
                        row.add(r.getLong("m20_30") + "");
                        row.add(r.getLong("m30_45") + "");
                        row.add(r.getLong("m45_50") + "");
                        row.add(r.getLong("m50") + "");
                        row.add(r.getLong("total") + "");
                        table.add(row);
                    }
                    con.closeConnection();
                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        try {
            PgConnection con = new PgConnection();
            String dateCon = "and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD')  ";
            String totalSQL = "SELECT  "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>=300 and t.status=4  " + dateCon + ") as m5, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>300 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=600  and t.status=4  " + dateCon + ") as m5_10, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>600 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=1200 and t.status=4 " + dateCon + " ) as m10_20, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>1200 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=1800  and t.status=4  " + dateCon + ") as m20_30, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>1800 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=2700  and t.status=4  " + dateCon + ") as m30_45, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>2700 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=3000  and t.status=4  " + dateCon + ") as m45_50, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>3000  and t.status=4  " + dateCon + ") as m50, "
                    + " "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=15 and t.status=4  " + dateCon + ") as s0_15, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>15 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=30  and t.status=4  " + dateCon + ") as s15_30, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>30 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=60 and t.status=4  " + dateCon + ") as s30_60, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>60 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=90  and t.status=4  " + dateCon + ") as s60_90, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>90 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=120 and t.status=4  " + dateCon + ") as s90_120, "
                    + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>120 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=300  and t.status=4  " + dateCon + ") as s120, "
                    + " "
                    + "(select count(*) from t_ticket t where  t.status=4 " + dateCon + " ) as total "
                    + ";";
            ResultSet r = con.getStatement().executeQuery(totalSQL);
            while (r.next()) {
                ArrayList row = new ArrayList<>();
                row.add("Totale");
                row.add("");
                row.add("Totale");
                row.add("Totale");
                row.add(r.getLong("s0_15") + "");
                row.add(r.getLong("s15_30") + "");
                row.add(r.getLong("s30_60") + "");
                row.add(r.getLong("s60_90") + "");
                row.add(r.getLong("s90_120") + "");
                row.add(r.getLong("s120") + "");

                row.add(r.getLong("m5") + "");
                row.add(r.getLong("m5_10") + "");
                row.add(r.getLong("m10_20") + "");
                row.add(r.getLong("m20_30") + "");
                row.add(r.getLong("m30_45") + "");
                row.add(r.getLong("m45_50") + "");
                row.add(r.getLong("m50") + "");
                row.add(r.getLong("total") + "");
                setChartData2(row, 4, row.size() - 1, 10);
                table.add(row);
            }
            setChartLables2(getGltCols(), 2, getGltCols().length - 1, 8);
            con.closeConnection();

        } catch (Exception ex) {
            Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }

    public List<ArrayList> generateGltTable(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        List<ArrayList> table = new ArrayList<>();
        AgenceController ac = new AgenceController();
        CibleController cc = new CibleController();
        if (this.dbs.length > 0) {
            for (int i = 0; i < this.dbs.length; i++) {
                try {
                    Agence a = ac.getAgenceById(UUID.fromString(this.dbs[i]));
                    PgConnection con = new PgConnection();
                    String dateCon = "and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t.db_id='" + a.getId() + "'";
                    String SQL = "SELECT  "
                            + "b.name, "
                            + "b.id,"
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>=300 and t.status=4 " + dateCon + ") as m5, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>300 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=600  and t.status=4  " + dateCon + ") as m5_10, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>600 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=1200 and t.status=4  " + dateCon + ") as m10_20, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>1200 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=1800  and t.status=4  " + dateCon + ") as m20_30, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>1800 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=2700  and t.status=4  " + dateCon + ") as m30_45, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>2700 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=3000  and t.status=4  " + dateCon + ") as m45_50, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>3000  and t.status=4  " + dateCon + ") as m50, "
                            + " "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=15 and t.status=4  " + dateCon + ") as s0_15, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>15 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=30  and t.status=4  " + dateCon + ") as s15_30, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>30 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=60 and t.status=4  " + dateCon + ") as s30_60, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>60 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=90  and t.status=4  " + dateCon + ") as s60_90, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>90 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=120 and t.status=4  " + dateCon + ") as s90_120, "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>120 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=300  and t.status=4  " + dateCon + ") as s120, "
                            + " "
                            + "(select count(*) from t_ticket t where t.biz_type_id=b.id and t.status=4  " + dateCon + ") as total "
                            + "FROM t_biz_type b where b.db_id='" + a.getId() + "' "
                            + ";";
                    String subSQL = "SELECT  "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>=300 and t.status=4  " + dateCon + ") as m5, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>300 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=600  and t.status=4  " + dateCon + ") as m5_10, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>600 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=1200 and t.status=4 " + dateCon + " ) as m10_20, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>1200 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=1800  and t.status=4  " + dateCon + ") as m20_30, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>1800 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=2700  and t.status=4  " + dateCon + ") as m30_45, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>2700 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=3000  and t.status=4  " + dateCon + ") as m45_50, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>3000  and t.status=4  " + dateCon + ") as m50, "
                            + " "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=15 and t.status=4  " + dateCon + ") as s0_15, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>15 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=30  and t.status=4  " + dateCon + ") as s15_30, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>30 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=60 and t.status=4  " + dateCon + ") as s30_60, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>60 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=90  and t.status=4  " + dateCon + ") as s60_90, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>90 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=120 and t.status=4  " + dateCon + ") as s90_120, "
                            + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>120 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=300  and t.status=4  " + dateCon + ") as s120, "
                            + " "
                            + "(select count(*) from t_ticket t where  t.status=4 " + dateCon + " ) as total "
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add(r.getString("id"));
                        row.add(a.getName());
                        row.add(r.getString("name"));
                        row.add(r.getLong("s0_15") + "");
                        row.add(r.getLong("s15_30") + "");
                        row.add(r.getLong("s30_60") + "");
                        row.add(r.getLong("s60_90") + "");
                        row.add(r.getLong("s90_120") + "");
                        row.add(r.getLong("s120") + "");

                        row.add(r.getLong("m5") + "");
                        row.add(r.getLong("m5_10") + "");
                        row.add(r.getLong("m10_20") + "");
                        row.add(r.getLong("m20_30") + "");
                        row.add(r.getLong("m30_45") + "");
                        row.add(r.getLong("m45_50") + "");
                        row.add(r.getLong("m50") + "");
                        row.add(r.getLong("total") + "");

                        table.add(row);
                    }
                    r = con.getStatement().executeQuery(subSQL);
                    while (r.next()) {
                        ArrayList row = new ArrayList();
                        row.add(a.getId());
                        row.add("");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add(r.getLong("s0_15") + "");
                        row.add(r.getLong("s15_30") + "");
                        row.add(r.getLong("s30_60") + "");
                        row.add(r.getLong("s60_90") + "");
                        row.add(r.getLong("s90_120") + "");
                        row.add(r.getLong("s120") + "");

                        row.add(r.getLong("m5") + "");
                        row.add(r.getLong("m5_10") + "");
                        row.add(r.getLong("m10_20") + "");
                        row.add(r.getLong("m20_30") + "");
                        row.add(r.getLong("m30_45") + "");
                        row.add(r.getLong("m45_50") + "");
                        row.add(r.getLong("m50") + "");
                        row.add(r.getLong("total") + "");
                        table.add(row);
                    }
                    con.closeConnection();
                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            try {
                PgConnection con = new PgConnection();
                String dateCon = "and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and (" + getDbsSql("t") + ") ";
                String totalSQL = "SELECT  "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>=300 and t.status=4  " + dateCon + ") as m5, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>300 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=600  and t.status=4  " + dateCon + ") as m5_10, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>600 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=1200 and t.status=4 " + dateCon + " ) as m10_20, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>1200 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=1800  and t.status=4  " + dateCon + ") as m20_30, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>1800 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=2700  and t.status=4  " + dateCon + ") as m30_45, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>2700 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=3000  and t.status=4  " + dateCon + ") as m45_50, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>3000  and t.status=4  " + dateCon + ") as m50, "
                        + " "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=15 and t.status=4  " + dateCon + ") as s0_15, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>15 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=30  and t.status=4  " + dateCon + ") as s15_30, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>30 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=60 and t.status=4  " + dateCon + ") as s30_60, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>60 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=90  and t.status=4  " + dateCon + ") as s60_90, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>90 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=120 and t.status=4  " + dateCon + ") as s90_120, "
                        + "(select count(*) from t_ticket t where  DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric>120 and DATE_PART('epoch'::text, t.finish_time-t.start_time)::numeric<=300  and t.status=4  " + dateCon + ") as s120, "
                        + " "
                        + "(select count(*) from t_ticket t where  t.status=4 " + dateCon + " ) as total "
                        + ";";
                ResultSet r = con.getStatement().executeQuery(totalSQL);
                while (r.next()) {
                    ArrayList row = new ArrayList<>();
                    row.add("Totale");
                    row.add("");
                    row.add("Totale");
                    row.add("Totale");
                    row.add(r.getLong("s0_15") + "");
                    row.add(r.getLong("s15_30") + "");
                    row.add(r.getLong("s30_60") + "");
                    row.add(r.getLong("s60_90") + "");
                    row.add(r.getLong("s90_120") + "");
                    row.add(r.getLong("s120") + "");

                    row.add(r.getLong("m5") + "");
                    row.add(r.getLong("m5_10") + "");
                    row.add(r.getLong("m10_20") + "");
                    row.add(r.getLong("m20_30") + "");
                    row.add(r.getLong("m30_45") + "");
                    row.add(r.getLong("m45_50") + "");
                    row.add(r.getLong("m50") + "");
                    row.add(r.getLong("total") + "");
                    setChartData2(row, 4, row.size() - 1, 10);
                    table.add(row);
                }
                setChartLables2(getGltCols(), 2, getGltCols().length - 1, 8);
                con.closeConnection();

            } catch (Exception ex) {
                Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return generateGltTable2(getDate1(), getDate2());
        }
        return table;
    }

    public List<ArrayList<String>> generateAplTable(HttpServletRequest request, String d1, String d2, String db)
            throws SQLException, IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.DB = db;
        this.table.clear();
        String SQL = "SELECT  "
                + "b.name as service, "
                + "t.ticket_id, "
                + "t.ticket_time, "
                + "t.call_time, "
                + "t.start_time, "
                + "t.finish_time, "
                + "w.name guichet, "
                + "u.name username, "
                + "DATE_PART('epoch'::text, t.call_time - t.ticket_time)::numeric as da, "
                + "DATE_PART('epoch'::text, t.finish_time - t.start_time)::numeric as dt, "
                + "t.status "
                + "from t_ticket t "
                + "left join t_biz_type b on t.biz_type_id=b.id "
                + "left join t_window w on t.deal_win=w.id "
                + "left join t_user u on t.deal_user=u.id "
                + "where to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t.call_time is not null"
                + ";";
        PgConnection con = new PgConnection();
        ResultSet r = con.getStatement().executeQuery(SQL);
        while (r.next()) {
            ArrayList row = new ArrayList();
            row.add(r.getString("service"));
            row.add(r.getString("ticket_id"));
            row.add(r.getString("ticket_time"));
            row.add(r.getString("call_time"));
            row.add(r.getString("start_time"));
            row.add(r.getString("finish_time"));
            row.add(r.getString("guichet"));
            row.add(r.getString("username"));
            row.add(getFormatedTime(r.getFloat("da")));
            row.add(getFormatedTime(r.getFloat("dt")));
            String stat = "";
            switch (r.getString("status")) {
                case "0":
                    stat = "<span class='text-center text-white bg-secondary p-1'>En Attente</span>";
                    break;
                case "1":
                    stat = "<span class='text-center text-white bg-primary p-1'>En Appel</span>";
                    break;
                case "2":
                    stat = "<span class='text-center text-white bg-warning text-dark p-1'>Absent</span>";
                    break;
                case "3":
                    stat = "<span class='text-center text-white bg-info p-1'>En traitement</span>";
                    break;
                case "4":
                    stat = "<span class='text-center text-white bg-success p-1'>Traité</span>";
                    break;
                case "5":
                    stat = "<span class='text-center text-white bg-danger p-1'>Interrupt</span>";
                    break;
                case "6":
                    stat = "<span class='text-center text-white bg-danger p-1'>Abnormal ticket</span>";
                    break;
                case "7":
                    stat = "<span class='text-center text-white bg-danger p-1'>No sign of WeChat</span>";
                    break;
                case "8":
                    stat = "<span class='text-center text-white bg-danger p-1'>Failure of verification</span>";
                    break;
            }
            row.add(stat);
            this.table.add(row);
        }

        con.closeConnection();
        return this.table;
    }

    public List<ArrayList> generateTaskTable(String d1, String d2, String[] dbs) {
        this.date1 = (d1 == null) ? format.format(new Date()) : d1;
        this.date2 = (d2 == null) ? format.format(new Date()) : d2;
        this.dbs = (dbs == null) ? new String[0] : filterDbs(dbs);
        List<ArrayList> table = new ArrayList<>();
        AgenceController ac = new AgenceController();
        CibleController cc = new CibleController();
        if (this.dbs.length > 0) {
            for (int i = 0; i < this.dbs.length; i++) {
                try {
                    Agence a = ac.getAgenceById(UUID.fromString(this.dbs[i]));
                    PgConnection con = new PgConnection();

                    String dateCon = " and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t2.db_id='" + a.getId() + "'";

                    String gblSQL2 = "select "
                            + "b.id as biz_id,"
                            + "tt.id_task as id_task,"
                            + "b.name as service,"
                            + "tch.name as task,"
                            + "(SELECT COUNT(*) FROM rougga_ticket_task tt2 , t_ticket t2 WHERE tt2.id_task=tt.id_task and tt2.id_ticket=t2.id " + dateCon + ") AS NB_TT,"
                            + "(SELECT sum(tt2.quantity) FROM rougga_ticket_task tt2 , t_ticket t2 WHERE tt2.id_task=tt.id_task and tt2.id_ticket=t2.id " + dateCon + " ) AS NB_QTT "
                            + "from "
                            + "rougga_task tch, rougga_ticket_task tt, t_biz_type b , t_ticket t "
                            + "where "
                            + "tch.id_service=b.id and tt.id_task=tch.id and tt.id_ticket = t.id and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t.db_id='" + a.getId() + "' "
                            + "group by biz_id, id_task, service , task "
                            + "order by service "
                            + ";";

                    String subTotalSQL = "select "
                            + " count(*) as nb_tsk, "
                            + "(SELECT COUNT(*) FROM rougga_ticket_task tt2 , t_ticket t2 WHERE  tt2.id_ticket=t2.id " + dateCon + ") AS NB_TT,"
                            + "(SELECT sum(tt2.quantity) FROM rougga_ticket_task tt2 , t_ticket t2 WHERE  tt2.id_ticket=t2.id " + dateCon + " ) AS NB_QTT "
                            + "from "
                            + "rougga_task tch, rougga_ticket_task tt, t_biz_type b , t_ticket t "
                            + "where "
                            + "tch.id_service=b.id and tt.id_task=tch.id and tt.id_ticket = t.id and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE('" + date1 + "','YYYY-MM-DD') AND TO_DATE('" + date2 + "','YYYY-MM-DD') and t.db_id='" + a.getId() + "' "
                            + " order by nb_tsk "
                            + ";";
                    ResultSet r = con.getStatement().executeQuery(gblSQL2);
                    table.clear();
                    while (r.next()) {
                        ArrayList row = new ArrayList<>();
                        row.add(a.getId().toString());
                        row.add(r.getString("id_task"));
                        row.add(a.getName());
                        row.add(r.getString("service"));
                        row.add(r.getString("task"));
                        row.add(r.getLong("NB_TT") + "");
                        row.add(r.getLong("NB_QTT") + "");
                        table.add(row);
                    }

                    r = con.getStatement().executeQuery(subTotalSQL);
                    while (r.next()) {
                        ArrayList<String> row = new ArrayList<>();
                        row.add(a.getId().toString());
                        row.add("");
                        row.add(a.getName());
                        row.add("Sous-Totale");
                        row.add("Sous-Totale");
                        row.add(r.getLong("NB_TT") + "");
                        row.add(r.getLong("NB_QTT") + "");
                        table.add(row);
                    }
                    con.closeConnection();

                } catch (Exception ex) {
                    Logger.getLogger(TableGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            return generateGblTable2(getDate1(), getDate2());
        }
        return table;
    }

    public SimpleDateFormat getFormat() {
        return format;
    }

    public Map getTable(HttpServletRequest request, String d1, String d2, String[] dbs, String type)
            throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {
        type = (type == null) ? "gbl" : type.toLowerCase().trim();
        TitleHandler th = new TitleHandler(request);
        Map data = new HashMap();
        List<ArrayList<String>> T = null;
        List<ArrayList> T2 = null;
        switch (type) {
            case "gbl":
                T2 = generateGblTable(request.getParameter("date1"), request.getParameter("date2"), dbs);
                setTitle(th.getGblTitle());
                setCols(getGblCols());
                setType(type);
                setDefaultHTML();
                break;
            case "emp":
                T2 = generateEmpTable(request.getParameter("date1"), request.getParameter("date2"), dbs);
                setTitle(th.getEmpTitle());
                setCols(getEmpCols());
                setType(type);
                setDefaultHTML();
                break;
            case "empser":
                T2 = generateEmpServiceTable(request.getParameter("date1"), request.getParameter("date2"), dbs);
                setTitle(th.getEmpSerTitle());
                setCols(getEmpServiceCols());
                setType(type);
                setDefaultHTML();
                break;
            case "gch":
                T2 = generateGchTable(request.getParameter("date1"), request.getParameter("date2"), dbs);
                setTitle(th.getGchTitle());
                setCols(getGchCols());
                setType(type);
                setDefaultHTML();
                break;
            case "gchserv":
                T2 = generateGchServiceTable(request.getParameter("date1"), request.getParameter("date2"), dbs);
                setTitle(th.getGchServTitle());
                setCols(getGchServiceCols());
                setType(type);
                setDefaultHTML();
                break;
            case "ndt":
                T2 = generateNdtTable(request.getParameter("date1"), request.getParameter("date2"), dbs);
                generateNdtChart2(request.getParameter("date1"), request.getParameter("date2"), dbs);
                setTitle(th.getNdtTitle());
                setCols(getNdtCols());
                setType(type);
                setChartHTML("false");
                break;
            case "ndtt":
                T2 = generateNdttTable(request.getParameter("date1"), request.getParameter("date2"), dbs);
                generateNdttChart2(request.getParameter("date1"), request.getParameter("date2"), dbs);
                setTitle(th.getNdttTitle());
                setCols(getNdtCols());
                setType(type);
                setChartHTML("false");
                break;
            case "ndta":
                T2 = generateNdtaTable(request.getParameter("date1"), request.getParameter("date2"), dbs);
                generateNdtaChart2(request.getParameter("date1"), request.getParameter("date2"), dbs);
                setTitle(th.getNdtaTitle());
                setCols(getNdtCols());
                setType(type);
                setChartHTML("false");
                break;
            case "ndtsa":
                T2 = generateNdtsaTable(request.getParameter("date1"), request.getParameter("date2"), dbs);
                generateNdtsaChart2(request.getParameter("date1"), request.getParameter("date2"), dbs);
                setTitle(th.getNdtsaTitle());
                setCols(getNdtCols());
                setType(type);
                setChartHTML("false");

                break;
            case "cnx":
                T2 = generateCnxTable(request.getParameter("date1"), request.getParameter("date2"), dbs);
                setTitle(th.getCnxTitle());
                setCols(getCnxCols());
                setType(type);
                setDefaultHTML();
                this.bottomHTML = "";
                break;
            case "remp":
                T2 = generateEmpTable(request.getParameter("date1"), request.getParameter("date2"), dbs);
                setTitle(th.getRempTitle());
                setCols(getEmpCols());
                setType(type);
                setDefaultHTML();
                break;
            case "ser":
                T2 = generateSerTable2(null, null);
                setTitle(th.getSerTitle());
                setCols(getSerCols());
                setType(type);
                setTimerHTML();
                break;
            case "sgch":
                T2 = generateSgchTable2();
                setTitle(th.getSgchTitle());
                setCols(getSgchCols());
                setType(type);
                setTimerHTML();
                break;
            case "apl":
                T = generateAplTable(request, request.getParameter("date1"), request.getParameter("date2"), request.getSession().getAttribute("db") + "");
                setTitle(th.getAplTitle());
                setCols(getAplCols());
                setType(type);
                setDefaultHTML();
                break;
            case "gla":
                T2 = generateGlaTable(request.getParameter("date1"), request.getParameter("date2"), dbs);
                setTitle(th.getGlaTitle());
                setCols(getGlaCols());
                setType(type);
                setChartHTML("true");
                break;
            case "glt":
                T2 = generateGltTable(request.getParameter("date1"), request.getParameter("date2"), dbs);
                setTitle(th.getGltTitle());
                setCols(getGltCols());
                setType(type);
                setChartHTML("true");
                break;
            case "tch":
                T2 = generateTaskTable(request.getParameter("date1"), request.getParameter("date2"), dbs);
                setTitle(th.getTaskTitle());
                setCols(getTaskCols());
                setType(type);
                setDateHTML();
                break;
            default:
                T2 = generateGblTable(request.getParameter("date1"), request.getParameter("date2"), dbs);
                setTitle(th.getGblTitle());
                setCols(getGblCols());
                setType(type);
                setDefaultHTML();
        }
        data.put("table", T2);
        data.put("table2", T);
        data.put("title", getTitle());
        data.put("cols", getCols());
        data.put("top", getTopHTML());
        data.put("bottom", getBottomHTML());
        return data;
    }

    public String getDB() {
        return DB;
    }

    public String getDate1() {
        return date1;
    }

    public String getDate2() {
        return date2;
    }

    public List<ArrayList<String>> getTable() {
        return table;
    }

    public ArrayList<String> getSubTotal() {
        return subTotal;
    }

    public String[] getGblCols() {
        return gblCols;
    }

    public String[] getEmpCols() {
        return empCols;
    }

    public String[] getEmpServiceCols() {
        return empServiceCols;
    }

    public String[] getGchCols() {
        return gchCols;
    }

    public String[] getGchServiceCols() {
        return gchServiceCols;
    }

    public String[] getNdtCols() {
        return ndtCols;
    }

    public String[] getCnxCols() {
        return cnxCols;
    }

    public String[] getSerCols() {
        return serCols;
    }

    public String[] getSgchCols() {
        return sgchCols;
    }

    public String[] getAplCols() {
        return aplCols;
    }

    public String[] getGlaCols() {
        return glaCols;
    }

    public String[] getGltCols() {
        return gltCols;
    }

    public String[] getTaskCols() {
        return taskCols;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getTopHTML() {
        return topHTML;
    }

    public String getBottomHTML() {
        return bottomHTML;
    }

    public String[] getCols() {
        return cols;
    }

    public void setCols(String[] cols) {
        this.cols = cols;
    }

    public String getChartLables() {
        return chartLables;
    }

    public String[] getNdtChartCols() {
        return ndtChartCols;
    }

    public String getChartData() {
        return chartData;
    }

    public String getDbsSql(String tablePrefix) {
        String addDbs1 = tablePrefix + ".db_id='" + this.dbs[0] + "' ";
        if (this.dbs.length > 1) {
            for (int i = 1; i < this.dbs.length; i++) {
                addDbs1 += " or " + tablePrefix + ".db_id='" + this.dbs[i] + "' ";
            }
        }
        return addDbs1;
    }

    public SimpleDateFormat getFormat2() {
        return format2;
    }

    public void setChartLables(String[] col, int start, int end) {
        if (col.length <= 0) {
            this.chartLables = "[]";
        } else {
            this.chartLables = "[";
            for (int i = start; i < end; i++) {
                this.chartLables += "'" + col[i] + "',";
            }
            this.chartLables = this.chartLables.substring(0, this.chartLables.lastIndexOf(","));
            this.chartLables += "]";
        }
    }

    public void setChartLables2(String[] col, int start, int end, int jump) {
        if (col.length <= 0) {
            this.chartLables = "[]";
        } else {
            this.chartLables = "[";
            for (int i = start; i < end; i++) {
                if (i == jump) {
                } else {
                    this.chartLables += "'" + col[i] + "',";
                }
            }
            this.chartLables = this.chartLables.substring(0, this.chartLables.lastIndexOf(","));
            this.chartLables += "]";
        }
    }

    public void setChartData(ArrayList row, int start, int end) {
        if (row.isEmpty()) {
            this.chartData = "[]";
        } else {
            this.chartData = "[";
            for (int i = start; i < end; i++) {
                this.chartData += row.get(i) + ",";
            }
            this.chartData = this.chartData.substring(0, this.chartData.lastIndexOf(","));
            this.chartData += "]";
        }

    }

    public void setChartData2(ArrayList row, int start, int end, int jump) {
        if (row.isEmpty()) {
            this.chartData = "[]";
        } else {

            this.chartData = "[";
            for (int i = start; i < end; i++) {
                if (i == jump) {
                } else {
                    this.chartData += row.get(i) + ",";
                }
            }
            this.chartData = this.chartData.substring(0, this.chartData.lastIndexOf(","));
            this.chartData += "]";
        }

    }

    public void setDefaultHTML() {
        this.topHTML = "<div class='div-wrapper d-flex justify-content-center align-items-center'>"
                + "<form class='form-inline' id='filterForm'  action=''>"
                + "<label class='m-1' for='date1'>Du: </label>"
                + "<input type='date' class='form-control mb-2 mr-sm-2' id='date1' name='date1' value='" + getDate1() + "' max='" + format.format(new Date()) + "'>"
                + "<label class='m-1' for='date2'>Au: </label>"
                + "<input type='date' class='form-control mb-2 mr-sm-2' id='date2' name='date2' value='" + getDate2() + "' >"
                + "<input type='hidden' value='" + getType() + "' name='type'>"
                + "<div class='btn-group dropright mb-2 mr-4'>"
                + "  <button type='button' class='btn btn-secondary dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>"
                + "    Intervalle"
                + "  </button>"
                + "  <div class='dropdown-menu '>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='today'>Aujourd'hui</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='yesterday'>Hier</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='cWeek'>Semaine en cours</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='lWeek'>Dernier semaine</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='cMonth'>Mois en cours</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='lMonth'>Mois dernier</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='cYear'>Année en cours</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='lYear'>Année dernier</a>"
                + "  </div>"
                + "</div>"
                + "<div class='btn-group dropright mb-2 mr-4'>"
                + "  <button type='button' class='btn btn-warning font-weight-bold dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>"
                + "    Agences"
                + "  </button>"
                + "  <div class='dropdown-menu ' id='agences'>"
                + "<span class='dropdown-item font-weight-bold  agence'>"
                + "<input type='checkbox'  class='mr-1 form-check-input check' id='selectAll'><span id='textSelect'>Toutes les agences.</span>"
                + ""
                + "</span>";
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null && agences.size() > 0) {
            for (int i = 0; i < agences.size(); i++) {
                this.topHTML += "<span class='dropdown-item font-weight-bold appHover agence'>"
                        + "<input type='checkbox' name='agences' class='mr-1 form-check-input check' value='" + agences.get(i).getId().toString() + "' checked>"
                        + "<span class='ck-text' data-id='" + agences.get(i).getId().toString() + "'>" + agences.get(i).getName() + "</span>"
                        + "</span>";
            }

        }

        this.topHTML += "  </div>"
                + "</div>"
                + "<button type='button' class='btn btn-primary mb-2' id='refresh'><img src='./img/icon/reload.png'/> Actualiser</button>"
                + "<input type='hidden' name='format' value='' id='format'>"
                + "</form>"
                + "<script>"
                + "</script>"
                + "</div>";
        this.bottomHTML = "<div class='div-wrapper d-flex justify-content-center align-items-center p-2'>"
                + "<a type='button' class='btn btn-success m-2' id='excel'><img src='./img/icon/excel.png'/> Excel</a>"
                + "<a type='button' class='btn btn-danger m-2' id='pdf' ><img src='./img/icon/pdf.png'/> PDF</a>"
                + "</div>";
    }

    public void setEmptyHTML() {
        this.topHTML = "<div class='div-wrapper d-flex justify-content-center align-items-center'>"
                + "</div>";
        this.bottomHTML = "<div class='div-wrapper d-flex justify-content-center align-items-center p-2'>"
                + "</div>";
    }

    public void setChartHTML(String bol) throws ParseException {
        this.topHTML = "<div class='div-wrapper d-flex justify-content-center align-items-center'>"
                + "<form class='form-inline' id='filterForm' action=''>"
                + "<label class='m-1' for='date1'>Du: </label>"
                + "<input type='date' class='form-control mb-2 mr-sm-2' id='date1' name='date1' value='" + getDate1() + "' max='" + format.format(new Date()) + "'>"
                + "<label class='m-1' for='date2'>Au: </label>"
                + "<input type='date' class='form-control mb-2 mr-sm-2' id='date2' name='date2' value='" + getDate2() + "' >"
                + "<input type='hidden' value='" + getType() + "' name='type'>"
                + "<div class='btn-group dropright mb-2 mr-3'>"
                + "  <button type='button' class='btn btn-secondary dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>"
                + "    Intervalle"
                + "  </button>"
                + "  <div class='dropdown-menu '>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='today'>Aujourd'hui</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='yesterday'>Hier</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='cWeek'>Semaine en cours</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='lWeek'>Dernier semaine</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='cMonth'>Mois en cours</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='lMonth'>Mois dernier</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='cYear'>Année en cours</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='lYear'>Année dernier</a>"
                + "  </div>"
                + "</div>"
                + "<div class='btn-group dropright mb-2 mr-3'>"
                + "  <button type='button' class='btn btn-warning font-weight-bold dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>"
                + "    Agences"
                + "  </button>"
                + "  <div class='dropdown-menu ' id='agences'>"
                + "<span class='dropdown-item font-weight-bold appHover agence'>"
                + "<input type='checkbox'  class='mr-1 form-check-input check' id='selectAll'>Toutes les agences."
                + "</span>";
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null && agences.size() > 0) {
            for (int i = 0; i < agences.size(); i++) {
                this.topHTML += "<span class='dropdown-item font-weight-bold appHover agence'>"
                        + "<input type='checkbox' name='agences' class='mr-1 form-check-input check' value='" + agences.get(i).getId().toString() + "' checked>"
                        + agences.get(i).getName()
                        + "</span>";
            }

        }

        this.topHTML += "  </div>"
                + "</div>"
                + "<button type='button' class='btn btn-primary mb-2' id='refresh'><img src='./img/icon/reload.png'/> Actualiser</button>"
                + "<div class=''>"
                + "<button type='button' class='btn btn-success mb-2 ml-5  justify-content-end align-items-end' id='excel'><img src='./img/icon/excel.png'/> Excel</button>"
                + "<button type='button' class='btn btn-danger mb-2 ml-2  justify-content-end align-items-end' id='pdf'><img src='./img/icon/pdf.png'/> PDF</button>"
                + "</div>"
                + "<input type='hidden' name='format' value='' id='format'>"
                + "</form>"
                + "<script>"
                + "</script>"
                + "</div>";
        this.bottomHTML
                = "<div id='graphs' class='text-white bg-white ' style='height:400px'></div>"
                + ""
                + "                <script type='text/javascript'>"
                + ""
                + ""
                + "                    var myChart = echarts.init(document.getElementById('graphs'));"
                + "                    var option = {"
                + "                        title: {"
                + "                            text: '" + getTitle().replace("'", "\\'") + " De " + format2.format(format.parse(date1)) + " Au " + format2.format(format.parse(date2)) + "'"
                + "                        },"
                + "                        tooltip: {"
                + "                            trigger: 'axis'"
                + "                        },"
                + "                        legend: {"
                + "                            "
                + "                        },"
                + "                        toolbox: {"
                + "                            show: true,"
                + "                             itemSize: '20',"
                + "                             itemGrap: '20',"
                + "                            feature: {"
                + "                                mark: {show: true},"
                + "                                magicType: {show: true, type: ['line', 'bar']},"
                + "                                saveAsImage: {show: true, title: 'Sauvegarder'}"
                + "                             },"
                + "                              iconStyle: {color: 'black'} "
                + "                        },"
                + "                        calculable: true,"
                + "                        xAxis: ["
                + "                            {"
                + "                                type: 'category',"
                + "                                  interval:0 ,"
                + "                                 nameLocation :'center',"
                + "                                data: " + getChartLables() + ","
                + "                                 axisLabel: {"
                + "                                         show: true,"
                + "                                         interval: 0,"
                + "                                          rotate: 30,"
                + "                                          fontWeight : 'bold'"
                + "                                             },"
                + "                                 axisTick:{"
                + "                                     alignWithLabel:" + bol
                + "                                         }"
                + "                            }"
                + "                        ],"
                + "                        yAxis: ["
                + "                            {"
                + "                                type: 'value',"
                + "                                 name:'Nb. Tickets',"
                + "                                nameTextStyle:{"
                + "                                     fontWeight:'bold'"
                + "                                 },"
                + "                                axisLabel: {"
                + "                                     fontWeight:'bold'"
                + "                                } "
                + "                            }"
                + "                        ],"
                + "                        series: ["
                + "                            {"
                + "                                name: '',"
                + "                                type: 'bar',"
                + "                                data: " + getChartData() + ","
                + "                                itemStyle: {"
                + "                                    normal: {"
                + "                                        color: '#14b6fa'"
                + "                                    }"
                + "                                }"
                + ""
                + "                            }"
                + "                        ]"
                + "                    };"
                + "                    myChart.setOption(option);"
                + "                    $(window).on('resize', function(){"
                + "                         myChart.resize();"
                + "                    });"
                + "                </script>";
    }

    public void setTimerHTML() {
        this.topHTML = "<div class='div-wrapper d-flex justify-content-center align-items-center'>"
                + ""
                + "</div>";
        this.bottomHTML = "<div class='div-wrapper d-flex justify-content-center align-items-center p-2'>"
                + "<h6 id='timer' class='text-danger p-0 m-0'>"
                + "<div class='spinner-border text-danger' role='status'>"
                + "<span class='sr-only'>Loading...</span>"
                + "</div>"
                + " Auto-Refresh 5sec"
                + "</h6> "
                + "<a class='btn btn-light p-1 ml-2' href='#' id='PauseTimer'>STOP</a>"
                + "<script>"
                + "var timer = setTimeout(function () {"
                + " window.location = window.location.href"
                + "}, 5000);"
                + "$('#PauseTimer').on('click',function(){"
                + "clearTimeout(timer);"
                + "$('#timer').hide();"
                + "$(this).hide();"
                + "});"
                + "</script>"
                + "</div>";
    }
    
    public void setDateHTML() {
        this.topHTML = "<div class='div-wrapper d-flex justify-content-center align-items-center'>"
                + "<form class='form-inline' id='filterForm'  action=''>"
                + "<label class='m-1' for='date1'>Du: </label>"
                + "<input type='date' class='form-control mb-2 mr-sm-2' id='date1' name='date1' value='" + getDate1() + "' max='" + format.format(new Date()) + "'>"
                + "<label class='m-1' for='date2'>Au: </label>"
                + "<input type='date' class='form-control mb-2 mr-sm-2' id='date2' name='date2' value='" + getDate2() + "' >"
                + "<input type='hidden' value='" + getType() + "' name='type'>"
                + "<div class='btn-group dropright mb-2 mr-4'>"
                + "  <button type='button' class='btn btn-secondary dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>"
                + "    Intervalle"
                + "  </button>"
                + "  <div class='dropdown-menu '>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='today'>Aujourd'hui</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='yesterday'>Hier</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='cWeek'>Semaine en cours</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='lWeek'>Dernier semaine</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='cMonth'>Mois en cours</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='lMonth'>Mois dernier</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='cYear'>Année en cours</a>"
                + "    <a class='dropdown-item font-weight-bold appHover' href='#' id='lYear'>Année dernier</a>"
                + "  </div>"
                + "</div>"
                + "<div class='btn-group dropright mb-2 mr-4'>"
                + "  <button type='button' class='btn btn-warning font-weight-bold dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>"
                + "    Agences"
                + "  </button>"
                + "  <div class='dropdown-menu ' id='agences'>"
                + "<span class='dropdown-item font-weight-bold  agence'>"
                + "<input type='checkbox'  class='mr-1 form-check-input check' id='selectAll'><span id='textSelect'>Toutes les agences.</span>"
                + ""
                + "</span>";
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null && agences.size() > 0) {
            for (int i = 0; i < agences.size(); i++) {
                this.topHTML += "<span class='dropdown-item font-weight-bold appHover agence'>"
                        + "<input type='checkbox' name='agences' class='mr-1 form-check-input check' value='" + agences.get(i).getId().toString() + "' checked>"
                        + "<span class='ck-text' data-id='" + agences.get(i).getId().toString() + "'>" + agences.get(i).getName() + "</span>"
                        + "</span>";
            }

        }

        this.topHTML += "  </div>"
                + "</div>"
                + "<button type='button' class='btn btn-primary mb-2' id='refresh'><img src='./img/icon/reload.png'/> Actualiser</button>"
                + "<input type='hidden' name='format' value='' id='format'>"
                + "</form>"
                + "<script>"
                + "</script>"
                + "</div>";
        this.bottomHTML = "";
    }

    
    public SimpleDateFormat getFormat3() {
        return format3;
    }

    public String[] filterDbs(String[] dbs) {
        String[] arr = {};

        return dbs;
    }
}
