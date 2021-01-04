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
import main.modal.LoginLog;

public class LoginLogController {

    public String getFormatedDateAsString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            return format.format(date);
        } else {
            return null;
        }
    }
    public Date getFormatedDateAsDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(date!=null){
            try {
                return format.parse(date);
            } catch (ParseException ex) {
                Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }else{
            return null;
        }
    }

    public int clearTodayLoginLog(UUID id) {
        try {
            PgConnection con = new PgConnection();
            String SQL = "delete from t_login_log where db_id=? and to_date(to_char(login_time,'YYYY-MM-DD'),'YYYY-MM-DD')  >= TO_DATE(?,'YYYY-MM-DD') ;";
            PreparedStatement p = con.getStatement().getConnection().prepareStatement(SQL);
            p.setString(1, id.toString());
            p.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            p.execute();
            con.closeConnection();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(LoginLogController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int clearTable(UUID id) {
        try {
            PgConnection con = new PgConnection();
            String SQL = "delete from t_login_log where db_id=? ;";
            PreparedStatement p = con.getStatement().getConnection().prepareStatement(SQL);
            p.setString(1, id.toString());
            p.execute();
            con.closeConnection();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(LoginLogController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int addLoginLog(LoginLog l) {
        try {
            PgConnection con = new PgConnection();
            String SQL = "insert into t_login_log"
                    + "("
                    + "id,"
                    + "login_time,"
                    + "login_type,"
                    + "user_id,"
                    + "account,"
                    + "login_ip,"
                    + "successed,"
                    + "db_id"
                    + ") "
                    + " values"
                    + "("
                    + "?,"
                    + "to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'),"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?"
                    + ");";
            PreparedStatement r = con.getStatement().getConnection().prepareStatement(SQL);
            r.setString(1, l.getId());
            r.setString(2, getFormatedDateAsString(l.getLogin_time()));
            r.setString(3, l.getLogin_type());
            r.setString(4, l.getUser_id());
            r.setString(5, l.getAccount());
            r.setString(6, l.getLogin_ip());
            r.setInt(7, l.getSuccessed());
            r.setString(8, l.getDb_id());

            r.execute();
            con.closeConnection();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(LoginLogController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int updateTodayLoginLogs() {
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            System.out.println("-- Updating Today t_login_log....");
            for (int i = 0; i < agences.size(); i++) {
                try {
                    PgMultiConnection con = new PgMultiConnection(agences.get(i).getHost(), String.valueOf(agences.get(i).getPort()), agences.get(i).getDatabase(), agences.get(i).getUsername(), agences.get(i).getPassword());
                    String SQL = "select * from t_login_log where to_date(to_char(login_time,'YYYY-MM-DD'),'YYYY-MM-DD')  >= TO_DATE(?,'YYYY-MM-DD') ;";
                    PreparedStatement p = con.getStatement().getConnection().prepareStatement(SQL);
                    p.setString(1, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    ResultSet r = p.executeQuery();
                    clearTodayLoginLog(agences.get(i).getId());
                    while (r.next()) {
                        addLoginLog(
                                new LoginLog(r.getString("id"),
                                        getFormatedDateAsDate(r.getString("login_time")),
                                        r.getString("login_type"),
                                        r.getString("user_id"),
                                        r.getString("account"),
                                        null,
                                        null,
                                        r.getString("login_ip"),
                                        null,
                                        r.getInt("successed"),
                                        agences.get(i).getId().toString())
                        );
                    }
                    con.closeConnection();

                } catch (Exception ex) {
                    Logger.getLogger(LoginLogController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
                }
            }
            System.out.println("-- Today t_login_log updated.");
            return 1;
        } else {
            return 0;
        }

    }

    public int updateAllLoginLogs() {
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            System.out.println("-- Updating t_login_log....");
            for (int i = 0; i < agences.size(); i++) {
                try {
                    PgMultiConnection con = new PgMultiConnection(agences.get(i).getHost(), String.valueOf(agences.get(i).getPort()), agences.get(i).getDatabase(), agences.get(i).getUsername(), agences.get(i).getPassword());
                    String SQL = "select * from t_login_log;";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    clearTable(agences.get(i).getId());
                    while (r.next()) {
                        addLoginLog(
                                new LoginLog(r.getString("id"),
                                        getFormatedDateAsDate(r.getString("login_time")),
                                        r.getString("login_type"),
                                        r.getString("user_id"),
                                        r.getString("account"),
                                        null,
                                        null,
                                        r.getString("login_ip"),
                                        null,
                                        r.getInt("successed"),
                                        agences.get(i).getId().toString())
                        );
                    }
                    con.closeConnection();

                } catch (Exception ex) {
                    Logger.getLogger(LoginLogController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("-- t_login_log updated.");
            return 1;
        } else {
            return 0;
        }

    }

    public int updateTodayLoginLogsById(UUID id) {
        Agence a = new AgenceController().getAgenceById(id);
        if (a != null) {
            System.out.println("-- Updating Today t_login_log for "+a.getName()+" ....");
                try {
                    PgMultiConnection con = new PgMultiConnection(a.getHost(), String.valueOf(a.getPort()), a.getDatabase(), a.getUsername(), a.getPassword());
                    String SQL = "select * from t_login_log where to_date(to_char(login_time,'YYYY-MM-DD'),'YYYY-MM-DD')  >= TO_DATE(?,'YYYY-MM-DD') ;";
                    PreparedStatement p = con.getStatement().getConnection().prepareStatement(SQL);
                    p.setString(1, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    ResultSet r = p.executeQuery();
                    clearTodayLoginLog(a.getId());
                    while (r.next()) {
                        addLoginLog(
                                new LoginLog(r.getString("id"),
                                        getFormatedDateAsDate(r.getString("login_time")),
                                        r.getString("login_type"),
                                        r.getString("user_id"),
                                        r.getString("account"),
                                        null,
                                        null,
                                        r.getString("login_ip"),
                                        null,
                                        r.getInt("successed"),
                                        a.getId().toString())
                        );
                    }
                    con.closeConnection();

                } catch (Exception ex) {
                    Logger.getLogger(LoginLogController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
                }
            
            System.out.println("-- Today t_login_log updated.");
            return 1;
        } else {
            return 0;
        }

    }

    public int updateAllLoginLogsById(UUID id) {
         Agence a = new AgenceController().getAgenceById(id);
        if (a != null) {
            System.out.println("-- Updating t_login_log (All) for "+a.getName()+" ....");
                try {
                    PgMultiConnection con = new PgMultiConnection(a.getHost(), String.valueOf(a.getPort()), a.getDatabase(), a.getUsername(), a.getPassword());
                    String SQL = "select * from t_login_log;";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    clearTable(a.getId());
                    while (r.next()) {
                        addLoginLog(
                                new LoginLog(r.getString("id"),
                                        getFormatedDateAsDate(r.getString("login_time")),
                                        r.getString("login_type"),
                                        r.getString("user_id"),
                                        r.getString("account"),
                                        null,
                                        null,
                                        r.getString("login_ip"),
                                        null,
                                        r.getInt("successed"),
                                        a.getId().toString())
                        );
                    }
                    con.closeConnection();

                } catch (Exception ex) {
                    Logger.getLogger(LoginLogController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            System.out.println("-- t_login_log updated (All) for "+a.getName()+".");
            return 1;
        } else {
            return 0;
        }

    }
}
