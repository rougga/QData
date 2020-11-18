package main.modal;

import java.util.Date;
import java.util.UUID;

public class User {

    private String id;
    private String account;
    private String passwd;
    private String name;
    private String nike_name;
    private Date limit_time;
    private Date access_time;
    private int status;
    private String dept_id;
    private int usertype;
    private String work_num;
    private String work;
    private UUID db_id;

    public User(String id, String account, String passwd, String name, String nike_name, Date limit_time, Date access_time, int status, String dept_id, int usertype, String work_num, String work, UUID db_id) {
        this.id = id;
        this.account = account;
        this.passwd = passwd;
        this.name = name;
        this.nike_name = nike_name;
        this.limit_time = limit_time;
        this.access_time = access_time;
        this.status = status;
        this.dept_id = dept_id;
        this.usertype = usertype;
        this.work_num = work_num;
        this.work = work;
        this.db_id = db_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNike_name(String nike_name) {
        this.nike_name = nike_name;
    }

    public void setLimit_time(Date limit_time) {
        this.limit_time = limit_time;
    }

    public void setAccess_time(Date access_time) {
        this.access_time = access_time;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public void setWork_num(String work_num) {
        this.work_num = work_num;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public void setDb_id(UUID db_id) {
        this.db_id = db_id;
    }

    public String getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getName() {
        return name;
    }

    public String getNike_name() {
        return nike_name;
    }

    public Date getLimit_time() {
        return limit_time;
    }

    public Date getAccess_time() {
        return access_time;
    }

    public int getStatus() {
        return status;
    }

    public String getDept_id() {
        return dept_id;
    }

    public int getUsertype() {
        return usertype;
    }

    public String getWork_num() {
        return work_num;
    }

    public String getWork() {
        return work;
    }

    public UUID getDb_id() {
        return db_id;
    }

}
