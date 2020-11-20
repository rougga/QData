package main.modal;

import java.util.Date;
import java.util.UUID;

public class Ticket {

    private String id;
    private String biz_type_id;
    private String ticket_id;
    private String evaluation_id;
    private String ticket_type;
    private int status;
    private String deal_win;
    private String transfer_win;
    private String deal_user;
    private String id_card_info_id;
    private Date ticket_time;
    private Date call_time;
    private Date start_time;
    private Date finish_time;
    private String call_type;
    private String branch_id;
    private String id_card_name;
    private UUID db_id;

    public Ticket(String id, String biz_type_id, String ticket_id, String evaluation_id, String ticket_type, int status, String deal_win, String transfer_win, String deal_user, String id_card_info_id, Date ticket_time, Date call_time, Date start_time, Date finish_time, String call_type, String branch_id, String id_card_name, UUID db_id) {
        this.id = id;
        this.biz_type_id = biz_type_id;
        this.ticket_id = ticket_id;
        this.evaluation_id = evaluation_id;
        this.ticket_type = ticket_type;
        this.status = status;
        this.deal_win = deal_win;
        this.transfer_win = transfer_win;
        this.deal_user = deal_user;
        this.id_card_info_id = id_card_info_id;
        this.ticket_time = ticket_time;
        this.call_time = call_time;
        this.start_time = start_time;
        this.finish_time = finish_time;
        this.call_type = call_type;
        this.branch_id = branch_id;
        this.id_card_name = id_card_name;
        this.db_id = db_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBiz_type_id(String biz_type_id) {
        this.biz_type_id = biz_type_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public void setEvaluation_id(String evaluation_id) {
        this.evaluation_id = evaluation_id;
    }

    public void setTicket_type(String ticket_type) {
        this.ticket_type = ticket_type;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDeal_win(String deal_win) {
        this.deal_win = deal_win;
    }

    public void setTransfer_win(String transfer_win) {
        this.transfer_win = transfer_win;
    }

    public void setDeal_user(String deal_user) {
        this.deal_user = deal_user;
    }

    public void setId_card_info_id(String id_card_info_id) {
        this.id_card_info_id = id_card_info_id;
    }

    public void setTicket_time(Date ticket_time) {
        this.ticket_time = ticket_time;
    }

    public void setCall_time(Date call_time) {
        this.call_time = call_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public void setFinish_time(Date finish_time) {
        this.finish_time = finish_time;
    }

    public void setCall_type(String call_type) {
        this.call_type = call_type;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public void setId_card_name(String id_card_name) {
        this.id_card_name = id_card_name;
    }

    public void setDb_id(UUID db_id) {
        this.db_id = db_id;
    }

    public String getId() {
        return id;
    }

    public String getBiz_type_id() {
        return biz_type_id;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public String getEvaluation_id() {
        return evaluation_id;
    }

    public String getTicket_type() {
        return ticket_type;
    }

    public int getStatus() {
        return status;
    }

    public String getDeal_win() {
        return deal_win;
    }

    public String getTransfer_win() {
        return transfer_win;
    }

    public String getDeal_user() {
        return deal_user;
    }

    public String getId_card_info_id() {
        return id_card_info_id;
    }

    public Date getTicket_time() {
        return ticket_time;
    }

    public Date getCall_time() {
        return call_time;
    }

    public Date getStart_time() {
        return start_time;
    }

    public Date getFinish_time() {
        return finish_time;
    }

    public String getCall_type() {
        return call_type;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public String getId_card_name() {
        return id_card_name;
    }

    public UUID getDb_id() {
        return db_id;
    }

}
