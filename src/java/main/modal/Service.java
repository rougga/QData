
package main.modal;

import java.util.UUID;

public class Service {
        private String id;
        private String name;
        private String biz_prefix;
        private int status;
        private String start_num;
        private int sort;
        private int call_delay;
        private String biz_class_id;
        private int deal_time_warning;
        private int hidden;
        private UUID db_id;

    public Service(String id, String name, String biz_prefix, int status, String start_num, int sort, int call_delay, String biz_class_id, int deal_time_warning, int hidden, UUID db_id) {
        this.id = id;
        this.name = name;
        this.biz_prefix = biz_prefix;
        this.status = status;
        this.start_num = start_num;
        this.sort = sort;
        this.call_delay = call_delay;
        this.biz_class_id = biz_class_id;
        this.deal_time_warning = deal_time_warning;
        this.hidden = hidden;
        this.db_id = db_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBiz_prefix(String biz_prefix) {
        this.biz_prefix = biz_prefix;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStart_num(String start_num) {
        this.start_num = start_num;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void setCall_delay(int call_delay) {
        this.call_delay = call_delay;
    }

    public void setBiz_class_id(String biz_class_id) {
        this.biz_class_id = biz_class_id;
    }

    public void setDeal_time_warning(int deal_time_warning) {
        this.deal_time_warning = deal_time_warning;
    }

    public void setHidden(int hidden) {
        this.hidden = hidden;
    }

    public void setDb_id(UUID db_id) {
        this.db_id = db_id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBiz_prefix() {
        return biz_prefix;
    }

    public int getStatus() {
        return status;
    }

    public String getStart_num() {
        return start_num;
    }

    public int getSort() {
        return sort;
    }

    public int getCall_delay() {
        return call_delay;
    }

    public String getBiz_class_id() {
        return biz_class_id;
    }

    public int getDeal_time_warning() {
        return deal_time_warning;
    }

    public int getHidden() {
        return hidden;
    }

    public UUID getDb_id() {
        return db_id;
    }
        
        
}