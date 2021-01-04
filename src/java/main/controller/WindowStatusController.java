package main.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.PgConnection;
import main.PgMultiConnection;
import main.modal.Agence;
import main.modal.Window;
import main.modal.WindowStatus;

public class WindowStatusController {

    public WindowStatusController() {
    }

    public String getFormatedDateAsString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            return format.format(date);
        } else {
            return null;
        }
    }

    public Date getFormatedDateAsDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            try {
                return format.parse(date);
            } catch (ParseException ex) {
                Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            return null;
        }
    }

    public int clearTable(UUID id) {
        try {
            PgConnection con = new PgConnection();
            String SQL = "delete from t_window_status where db_id=? ;";
            PreparedStatement p = con.getStatement().getConnection().prepareStatement(SQL);
            p.setString(1, id.toString());
            p.execute();
            con.closeConnection();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(WindowStatusController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int addWindowStatus(WindowStatus ws) {
        try {
            PgConnection con = new PgConnection();
            String SQL = "insert into t_window_status"
                    + "(window_id,"
                    + "user_id,"
                    + "status,"
                    + "access_time,"
                    + "pause_count,"
                    + "final_opernate_time,"
                    + "ip,"
                    + "win_puase_time,"
                    + "batch_deal_status,"
                    + "pause_time,"
                    + "current_ticket,"
                    + "db_id) "
                    + " values"
                    + "(?,"
                    + "?,"
                    + "?,"
                    + "to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'),"
                    + "?,"
                    + "to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'),"
                    + "?,"
                    + "to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'),"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?);";
            PreparedStatement r = con.getStatement().getConnection().prepareStatement(SQL);
            r.setString(1, ws.getWindow_id());
            r.setString(2, ws.getUser_id());
            r.setInt(3, ws.getStatus());
            r.setString(4, getFormatedDateAsString(ws.getAccess_time()));
            r.setLong(5, ws.getPause_count());
            r.setString(6, getFormatedDateAsString(ws.getFinal_opernate_time()));
            r.setString(7, ws.getIp());
            r.setString(8, getFormatedDateAsString(ws.getWin_puase_time()));
            r.setInt(9, ws.getBatch_deal_status());
            r.setLong(10, ws.getPause_time());
            r.setString(11, ws.getCurrent_ticket());
            r.setString(12, ws.getDb_id());

            r.execute();
            con.closeConnection();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(WindowStatusController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    public int updateWindowStatus() {
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            System.out.println("-- Updating t_window_status....");
            for (int i = 0; i < agences.size(); i++) {
                try {
                    PgMultiConnection con = new PgMultiConnection(agences.get(i).getHost(), String.valueOf(agences.get(i).getPort()), agences.get(i).getDatabase(), agences.get(i).getUsername(), agences.get(i).getPassword());
                    String SQL = "select * from t_window_status;";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    clearTable(agences.get(i).getId());
                    while (r.next()) {
                        addWindowStatus(
                                new WindowStatus(
                                        r.getString("window_id"),
                                        r.getString("user_id"),
                                        r.getInt("status"),
                                        getFormatedDateAsDate(r.getString("access_time")),
                                        r.getLong("pause_count"),
                                        getFormatedDateAsDate(r.getString("final_opernate_time")),
                                        r.getString("ip"),
                                        getFormatedDateAsDate(r.getString("win_puase_time")),
                                        r.getInt("batch_deal_status"),
                                        r.getLong("pause_time"),
                                        r.getString("current_ticket"),
                                        agences.get(i).getId().toString())
                        );
                    }
                    con.closeConnection();

                } catch (Exception ex) {
                    Logger.getLogger(WindowStatusController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
                }
            }
            System.out.println("-- t_window_status updated.");
            return 1;
        } else {
            return 0;
        }

    }

    public int updateWindowStatusById(UUID id) {
        Agence a = new AgenceController().getAgenceById(id);
        if (a != null) {
            System.out.println("-- Updating t_window_status for " + a.getName() + " ....");
            try {
                PgMultiConnection con = new PgMultiConnection(a.getHost(), String.valueOf(a.getPort()), a.getDatabase(), a.getUsername(), a.getPassword());
                String SQL = "select * from t_window_status;";
                ResultSet r = con.getStatement().executeQuery(SQL);
                clearTable(a.getId());
                while (r.next()) {
                    addWindowStatus(
                            new WindowStatus(
                                    r.getString("window_id"),
                                    r.getString("user_id"),
                                    r.getInt("status"),
                                    getFormatedDateAsDate(r.getString("access_time")),
                                    r.getLong("pause_count"),
                                    getFormatedDateAsDate(r.getString("final_opernate_time")),
                                    r.getString("ip"),
                                    getFormatedDateAsDate(r.getString("win_puase_time")),
                                    r.getInt("batch_deal_status"),
                                    r.getLong("pause_time"),
                                    r.getString("current_ticket"),
                                    a.getId().toString())
                    );
                }
                con.closeConnection();

            } catch (Exception ex) {
                Logger.getLogger(WindowStatusController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
            }

            System.out.println("-- t_window_status updated.");
            return 1;
        } else {
            return 0;
        }

    }
}
