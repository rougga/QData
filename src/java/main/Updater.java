package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.controller.AgenceController;
import main.controller.ServiceController;
import main.modal.Agence;
import main.modal.Service;

public class Updater {

    public Updater() {
    }

    public void updateDatabase() {
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            for (int i = 0; i < agences.size(); i++) {
                UUID id_db = agences.get(i).getId();
                updateServices(id_db);
                updateUsers(id_db);
                updateTickets(id_db);
                updateWindows(id_db);
                updateAgenceName(id_db);
            }
        }
    }

    private void updateServices(UUID id_db) {
        try {
            AgenceController ac = new AgenceController();
            Agence a = ac.getAgenceById(id_db);
            ServiceController sc = new ServiceController();
            PgMultiConnection con = new PgMultiConnection(a.getHost(), String.valueOf(a.getPort()), a.getDatabase(), a.getUsername(), a.getPassword());
            String SQL = "select * from t_biz_type";
            ResultSet r = con.getStatement().executeQuery(SQL);
            sc.clearTable(a.getId());
            while(r.next()){
                sc.addService(new Service(
                        r.getString("id"),
                        r.getString("name"), 
                        r.getString("prefix"),
                        r.getInt("status") , 
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
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateUsers(UUID id_db) {
    }

    private void updateTickets(UUID id_db) {
    }

    private void updateWindows(UUID id_db) {
    }

    private void updateAgenceName(UUID id_db) {
        try {
            AgenceController ac = new AgenceController();
            Agence a = ac.getAgenceById(id_db);
            PgMultiConnection con = new PgMultiConnection(a.getHost(), String.valueOf(a.getPort()), a.getDatabase(), a.getUsername(), a.getPassword());
            String SQL = "SELECT value FROM t_basic_par where name='BRANCH_NAME' ;";
            ResultSet r = con.getStatement().executeQuery(SQL);
            if (r.next()) {
                ac.updateName(a.getId(), r.getString("value"));
            }
            con.closeConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
