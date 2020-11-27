package main.modal;

import java.util.Date;

public class LoginLog {

    String id;
    Date login_time;
    String login_type;
    String user_id;
    String account;
    String login_source;
    String logs;
    String login_ip;
    String session_id;
    int successed;
    String db_id;

    public LoginLog(String id, Date login_time, String login_type, String user_id, String account, String login_source, String logs, String login_ip, String session_id, int successed, String db_id) {
        this.id = id;
        this.login_time = login_time;
        this.login_type = login_type;
        this.user_id = user_id;
        this.account = account;
        this.login_source = login_source;
        this.logs = logs;
        this.login_ip = login_ip;
        this.session_id = session_id;
        this.successed = successed;
        this.db_id = db_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLogin_time(Date login_time) {
        this.login_time = login_time;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setLogin_source(String login_source) {
        this.login_source = login_source;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public void setLogin_ip(String login_ip) {
        this.login_ip = login_ip;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public void setSuccessed(int successed) {
        this.successed = successed;
    }

    public void setDb_id(String db_id) {
        this.db_id = db_id;
    }

    public String getId() {
        return id;
    }

    public Date getLogin_time() {
        return login_time;
    }

    public String getLogin_type() {
        return login_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getAccount() {
        return account;
    }

    public String getLogin_source() {
        return login_source;
    }

    public String getLogs() {
        return logs;
    }

    public String getLogin_ip() {
        return login_ip;
    }

    public String getSession_id() {
        return session_id;
    }

    public int getSuccessed() {
        return successed;
    }

    public String getDb_id() {
        return db_id;
    }

}
