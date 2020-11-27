package main.modal;

import java.util.Date;

public class WindowStatus {

    String window_id;
    String user_id;
    int status;
    Date access_time;
    long pause_count;
    Date final_opernate_time;
    String ip;
    Date win_puase_time;
    int batch_deal_status;
    long pause_time;
    String current_ticket;
    String db_id;

    public WindowStatus(String window_id, String user_id, int status, Date access_time, long pause_count, Date final_opernate_time, String ip, Date win_puase_time, int batch_deal_status, long pause_time, String current_ticket, String db_id) {
        this.window_id = window_id;
        this.user_id = user_id;
        this.status = status;
        this.access_time = access_time;
        this.pause_count = pause_count;
        this.final_opernate_time = final_opernate_time;
        this.ip = ip;
        this.win_puase_time = win_puase_time;
        this.batch_deal_status = batch_deal_status;
        this.pause_time = pause_time;
        this.current_ticket = current_ticket;
        this.db_id = db_id;
    }

    public void setWindow_id(String window_id) {
        this.window_id = window_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAccess_time(Date access_time) {
        this.access_time = access_time;
    }

    public void setPause_count(long pause_count) {
        this.pause_count = pause_count;
    }

    public void setFinal_opernate_time(Date final_opernate_time) {
        this.final_opernate_time = final_opernate_time;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setWin_puase_time(Date win_puase_time) {
        this.win_puase_time = win_puase_time;
    }

    public void setBatch_deal_status(int batch_deal_status) {
        this.batch_deal_status = batch_deal_status;
    }

    public void setPause_time(long pause_time) {
        this.pause_time = pause_time;
    }

    public void setCurrent_ticket(String current_ticket) {
        this.current_ticket = current_ticket;
    }

    public void setDb_id(String db_id) {
        this.db_id = db_id;
    }

    public String getWindow_id() {
        return window_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public int getStatus() {
        return status;
    }

    public Date getAccess_time() {
        return access_time;
    }

    public long getPause_count() {
        return pause_count;
    }

    public Date getFinal_opernate_time() {
        return final_opernate_time;
    }

    public String getIp() {
        return ip;
    }

    public Date getWin_puase_time() {
        return win_puase_time;
    }

    public int getBatch_deal_status() {
        return batch_deal_status;
    }

    public long getPause_time() {
        return pause_time;
    }

    public String getCurrent_ticket() {
        return current_ticket;
    }

    public String getDb_id() {
        return db_id;
    }
    
}
