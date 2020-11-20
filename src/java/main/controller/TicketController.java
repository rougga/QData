package main.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.PgConnection;
import main.PgMultiConnection;
import main.TableGenerator;
import main.modal.Agence;
import main.modal.Ticket;

public class TicketController {

    public TicketController() {
        
    }

    public int clearTodayTickets(UUID id) {
        try {
            PgConnection con = new PgConnection();
            String SQL = "delete from t_ticket where db_id=? and to_date(to_char(ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  >= TO_DATE(?,'YYYY-MM-DD') ;";
            PreparedStatement p = con.getStatement().getConnection().prepareStatement(SQL);
            p.setString(1, id.toString());
            p.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            p.execute();
            con.closeConnection();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int addTicket(Ticket t) {
        try {
            
            PgConnection con = new PgConnection();
            String SQL = "insert into t_ticket"
                    + "(id,biz_type_id,ticket_id,evaluation_id"
                    + ",ticket_type,status,deal_win,transfer_win,deal_user"
                    + ",id_card_info_id,ticket_time,call_time,start_time"
                    + ",finish_time,call_type,branch_id,id_card_name,db_id)"
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement r = con.getStatement().getConnection().prepareStatement(SQL);
            r.setString(1, t.getId());
            r.setString(2, t.getBiz_type_id());
            r.setString(3, t.getTicket_id());
            r.setString(4, t.getEvaluation_id());
            r.setString(5, t.getTicket_type());
            r.setInt(6, t.getStatus());
            r.setString(7, t.getDeal_win());
            r.setString(8, t.getTransfer_win());
            r.setString(9, t.getDeal_user());
            r.setString(10, t.getId_card_info_id());
            r.setObject(11,t.getTicket_time());
            r.setObject(12,t.getCall_time());
            r.setObject(13,t.getStart_time());
            r.setObject(14,t.getFinish_time());
            r.setString(15, t.getCall_type());
            r.setString(16, t.getBranch_id());
            r.setString(17, t.getId_card_name());
            r.setString(18, t.getDb_id().toString());
            r.execute();
            con.closeConnection();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public void updateTodayTickets() {
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            System.out.println("-- Updating t_ticket ....");
            for (int i = 0; i < agences.size(); i++) {
                try {
                    PgMultiConnection con = new PgMultiConnection(agences.get(i).getHost(), String.valueOf(agences.get(i).getPort()), agences.get(i).getDatabase(), agences.get(i).getUsername(), agences.get(i).getPassword());
                    String SQL = "select * from t_ticket where to_date(to_char(ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  >= TO_DATE(?,'YYYY-MM-DD') ;";
                    clearTodayTickets(agences.get(i).getId());
                    PreparedStatement p = con.getStatement().getConnection().prepareStatement(SQL);
                    p.setString(1, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    ResultSet r = p.executeQuery();
                    while (r.next()) {
                        addTicket(
                                new Ticket(
                                        r.getString("id"),
                                        r.getString("biz_type_id"),
                                        r.getString("ticket_id"),
                                        r.getString("evaluation_id"),
                                        r.getString("ticket_type"),
                                        r.getInt("status"),
                                        r.getString("deal_win"),
                                        r.getString("transfer_win"),
                                        r.getString("deal_user"),
                                        r.getString("id_card_info_id"),
                                        r.getDate("ticket_time"),
                                        r.getDate("call_time"),
                                        r.getDate("start_time"),
                                        r.getDate("finish_time"),
                                        r.getString("call_type"),
                                        r.getString("branch_id"),
                                        r.getString("id_card_name"),
                                        agences.get(i).getId()
                                )
                        );
                    }
                    con.closeConnection();

                } catch (Exception ex) {
                    Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("-- t_ticket updated.");
        }
    }
}
