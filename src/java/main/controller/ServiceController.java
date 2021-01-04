package main.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.PgConnection;
import main.PgMultiConnection;
import main.modal.Agence;
import main.modal.Service;

public class ServiceController {

    public ServiceController() {
    }

    public List<Service> getAll() {
        try {
            List<Service> services = new ArrayList();
            PgConnection con = new PgConnection();
            ResultSet r = con.getStatement().executeQuery("select * from t_biz_type;");
            while (r.next()) {
                services.add(
                        new Service(
                                r.getString("id"),
                                r.getString("name"),
                                r.getString("biz_prefix"),
                                r.getInt("status"),
                                r.getString("start_num"),
                                r.getInt("sort"),
                                r.getInt("call_delay"),
                                r.getString("biz_class_id"),
                                r.getInt("deal_time_warning"),
                                r.getInt("hidden"),
                                UUID.fromString(r.getString("db_id")))
                );
            }
            con.closeConnection();
            return services;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Service getById(String id, UUID db_id) {
        try {

            PgConnection con = new PgConnection();
            PreparedStatement p = con.getStatement().getConnection().prepareStatement("select * from t_biz_type where id=? and db_id=? ;");
            p.setString(1, id);
            p.setString(2, db_id.toString());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                con.closeConnection();
                Service s = (new Service(
                        r.getString("id"),
                        r.getString("name"),
                        r.getString("biz_prefix"),
                        r.getInt("status"),
                        r.getString("start_num"),
                        r.getInt("sort"),
                        r.getInt("call_delay"),
                        r.getString("biz_class_id"),
                        r.getInt("deal_time_warning"),
                        r.getInt("hidden"),
                        db_id));
                return s;
            } else {
                con.closeConnection();
                return null;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int clearTable(UUID id) {
        try {
            PgConnection con = new PgConnection();
            String SQL = "delete from t_biz_type where db_id=? ;";
            PreparedStatement p = con.getStatement().getConnection().prepareStatement(SQL);
            p.setString(1, id.toString());
            p.execute();
            con.closeConnection();
            return 1;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int addService(Service s) {
        try {
            PgConnection con = new PgConnection();
            String SQL = "insert into t_biz_type"
                    + "(id,name,biz_prefix,status,start_num,sort,call_delay,biz_class_id,deal_time_warning,hidden,db_id)"
                    + " values (?,?,?,?,?,?,?,?,?,?,?) ;";
            PreparedStatement r = con.getStatement().getConnection().prepareStatement(SQL);
            r.setString(1, s.getId());
            r.setString(2, s.getName());
            r.setString(3, s.getBiz_prefix());
            r.setInt(4, s.getStatus());
            r.setString(5, s.getStart_num());
            r.setInt(6, s.getSort());
            r.setInt(7, s.getCall_delay());
            r.setString(8, s.getBiz_class_id());
            r.setInt(9, s.getDeal_time_warning());
            r.setInt(10, s.getHidden());
            r.setString(11, s.getDb_id().toString());
            r.execute();
            con.closeConnection();
            return 1;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public void updateServices() {
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            System.err.println("-- Updating t_biz_type.....");
            for (int i = 0; i < agences.size(); i++) {
                try {
                    PgMultiConnection con = new PgMultiConnection(agences.get(i).getHost(), String.valueOf(agences.get(i).getPort()), agences.get(i).getDatabase(), agences.get(i).getUsername(), agences.get(i).getPassword());
                    String SQL = "select * from t_biz_type;";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    clearTable(agences.get(i).getId());
                    while (r.next()) {
                        addService(new Service(
                                r.getString("id"),
                                r.getString("name"),
                                r.getString("biz_prefix"),
                                r.getInt("status"),
                                r.getString("start_num"),
                                r.getInt("sort"),
                                r.getInt("call_delay"),
                                r.getString("biz_class_id"),
                                r.getInt("deal_time_warning"),
                                r.getInt("hidden"),
                                agences.get(i).getId()));
                    }
                    con.closeConnection();

                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
                }
            }
            System.err.println("-- t_biz_type updated.");
        }
    }

    public void updateServicesById(UUID id) {
        Agence a = new AgenceController().getAgenceById(id);
        if (a != null) {
            System.err.println("-- Updating t_biz_type for " + a.getName() + " .....");
            try {
                PgMultiConnection con = new PgMultiConnection(a.getHost(), String.valueOf(a.getPort()), a.getDatabase(), a.getUsername(), a.getPassword());
                String SQL = "select * from t_biz_type;";
                ResultSet r = con.getStatement().executeQuery(SQL);
                clearTable(a.getId());
                while (r.next()) {
                    addService(new Service(
                            r.getString("id"),
                            r.getString("name"),
                            r.getString("biz_prefix"),
                            r.getInt("status"),
                            r.getString("start_num"),
                            r.getInt("sort"),
                            r.getInt("call_delay"),
                            r.getString("biz_class_id"),
                            r.getInt("deal_time_warning"),
                            r.getInt("hidden"),
                            a.getId()));
                }
                con.closeConnection();

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
            }

            System.err.println("-- t_biz_type for "+a.getName()+" updated.");
        }
    }
}
