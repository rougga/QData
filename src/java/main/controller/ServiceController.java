package main.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.PgConnection;
import main.modal.Service;

public class ServiceController {

    public ServiceController() {
    }

    public Service getServiceById(UUID id) {

        return null;
    }

    public int clearTable(UUID id) {
         try {
            PgConnection con = new PgConnection();
            String SQL = "delete from service where id_db=?";
            PreparedStatement p = con.getStatement().getConnection().prepareStatement(SQL);
            p.setString(1, id.toString());
            p.execute();
            return 1;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int addService(Service s) {
        try {
            PgConnection con = new PgConnection();
            String SQL = "insert into service values (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement r = con.getStatement().getConnection().prepareStatement(SQL);
            r.setString(1, s.getId().toString());
            r.setString(2,s.getBiz_type_id());
            r.setString(3,s.getName());
            r.setString(4,s.getBiz_prefix());
            r.setInt(5,s.getStatus());
            r.setString(6,s.getStart_num());
            r.setInt(7,s.getSort());
            r.setInt(8,s.getCall_delay());
            r.setString(9, s.getBiz_class_id());
            r.setInt(10, s.getDeal_time_warning());
            r.setInt(11, s.getHidden());
            r.setString(12, s.getId_db().toString());
            r.execute();
            return 1;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

}
