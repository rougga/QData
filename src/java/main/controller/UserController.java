package main.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.PgConnection;
import main.PgMultiConnection;
import main.modal.Agence;
import main.modal.User;

public class UserController {

    public UserController() {
    }

    public int clearTable(UUID id) {
        try {
            PgConnection con = new PgConnection();
            String SQL = "delete from t_user where db_id=? ;";
            PreparedStatement p = con.getStatement().getConnection().prepareStatement(SQL);
            p.setString(1, id.toString());
            p.execute();
            con.closeConnection();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int addUser(User u) {
        try {
            PgConnection con = new PgConnection();
            String SQL = "insert into t_user"
                    + "(id,account,name,passwd,nike_name,limit_time,access_time,status,dept_id,usertype,work_num,work,db_id)"
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement r = con.getStatement().getConnection().prepareStatement(SQL);

            r.setString(1, u.getId());
            r.setString(2, u.getAccount());
            r.setString(3, u.getName());
            r.setString(4, u.getPasswd());
            r.setString(5, u.getNike_name());
            r.setDate(6, null);
            r.setDate(7, null);
            r.setInt(8, u.getStatus());
            r.setString(9, u.getDept_id());
            r.setInt(10, u.getUsertype());
            r.setString(11, u.getWork_num());
            r.setString(12, u.getWork());
            r.setString(13, u.getDb_id().toString());
            r.execute();
            con.closeConnection();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int updateUsers() {

        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            System.out.println("-- Updating t_user....");
            for (int i = 0; i < agences.size(); i++) {
                try {
                    PgMultiConnection con = new PgMultiConnection(agences.get(i).getHost(), String.valueOf(agences.get(i).getPort()), agences.get(i).getDatabase(), agences.get(i).getUsername(), agences.get(i).getPassword());
                    String SQL = "select * from t_user;";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    clearTable(agences.get(i).getId());
                    while (r.next()) {
                        addUser(
                                new User(
                                        r.getString("id"),
                                        r.getString("account"),
                                        r.getString("passwd"),
                                        r.getString("name"),
                                        r.getString("nike_name"),
                                        r.getDate("limit_time"),
                                        r.getDate("access_time"),
                                        r.getInt("status"),
                                        r.getString("dept_id"),
                                        r.getInt("usertype"),
                                        r.getString("work_num"),
                                        r.getString("work"),
                                        agences.get(i).getId()
                                )
                        );
                    }
                    con.closeConnection();

                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
                }
            }
            System.out.println("-- t_user updated.");
            return 1;
        } else {
            return 0;
        }
    }

    public int updateUsersById(UUID id) {

        Agence a = new AgenceController().getAgenceById(id);
        if (a != null) {
            System.out.println("-- Updating t_user for " + a.getName() + " ....");
            try {
                PgMultiConnection con = new PgMultiConnection(a.getHost(), String.valueOf(a.getPort()), a.getDatabase(), a.getUsername(), a.getPassword());
                String SQL = "select * from t_user;";
                ResultSet r = con.getStatement().executeQuery(SQL);
                clearTable(a.getId());
                while (r.next()) {
                    addUser(
                            new User(
                                    r.getString("id"),
                                    r.getString("account"),
                                    r.getString("passwd"),
                                    r.getString("name"),
                                    r.getString("nike_name"),
                                    r.getDate("limit_time"),
                                    r.getDate("access_time"),
                                    r.getInt("status"),
                                    r.getString("dept_id"),
                                    r.getInt("usertype"),
                                    r.getString("work_num"),
                                    r.getString("work"),
                                    a.getId()
                            )
                    );
                }
                con.closeConnection();

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
            }

            System.out.println("-- t_user updated.");
            return 1;
        } else {
            return 0;
        }
    }
}
