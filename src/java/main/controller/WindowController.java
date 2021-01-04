package main.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.PgConnection;
import main.PgMultiConnection;
import main.modal.Agence;
import main.modal.Window;

public class WindowController {

    public int clearTable(UUID id) {
        try {
            PgConnection con = new PgConnection();
            String SQL = "delete from t_window where db_id=? ;";
            PreparedStatement p = con.getStatement().getConnection().prepareStatement(SQL);
            p.setString(1, id.toString());
            p.execute();
            con.closeConnection();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(WindowController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int addWindow(Window w) {
        try {
            PgConnection con = new PgConnection();
            String SQL = "insert into t_window"
                    + "(id,win_number,name,status,win_group_id,default_user,branch_id,db_id,screen_type) "
                    + " values"
                    + "(?,?,?,?,?,?,?,?,?);";
            PreparedStatement r = con.getStatement().getConnection().prepareStatement(SQL);
            r.setString(1, w.getId());
            r.setInt(2, w.getWin_number());
            r.setString(3, w.getName());
            r.setInt(4, w.getStatus());
            r.setString(5, w.getWin_group_id());
            r.setString(6, w.getDefault_user());
            r.setString(7, w.getBranch_id());
            r.setString(8, w.getDb_id().toString());
            r.setString(9, w.getScreen_type());
            r.execute();
            con.closeConnection();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(WindowController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int updateWindows() {
        List<Agence> agences = new AgenceController().getAllAgence();
        if (agences != null) {
            System.out.println("-- Updating t_window....");
            for (int i = 0; i < agences.size(); i++) {
                try {
                    PgMultiConnection con = new PgMultiConnection(agences.get(i).getHost(), String.valueOf(agences.get(i).getPort()), agences.get(i).getDatabase(), agences.get(i).getUsername(), agences.get(i).getPassword());
                    String SQL = "select * from t_window;";
                    ResultSet r = con.getStatement().executeQuery(SQL);
                    clearTable(agences.get(i).getId());
                    while (r.next()) {
                        addWindow(
                                new Window(
                                        r.getString("id"),
                                        r.getInt("win_number"),
                                        r.getString("name"),
                                        r.getInt("status"),
                                        r.getString("win_group_id"),
                                        r.getString("default_user"),
                                        r.getString("branch_id"),
                                        agences.get(i).getId(),
                                        r.getString("screen_type")
                                )
                        );
                    }
                    con.closeConnection();

                } catch (Exception ex) {
                    Logger.getLogger(WindowController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
                }
            }
            System.out.println("-- t_window updated.");
            return 1;
        } else {
            return 0;
        }

    }

    public int updateWindowsById(UUID id) {
        Agence a = new AgenceController().getAgenceById(id);
        if (a != null) {
            System.out.println("-- Updating t_window for " + a.getName() + "....");
            try {
                PgMultiConnection con = new PgMultiConnection(a.getHost(), String.valueOf(a.getPort()), a.getDatabase(), a.getUsername(), a.getPassword());
                String SQL = "select * from t_window;";
                ResultSet r = con.getStatement().executeQuery(SQL);
                clearTable(a.getId());
                while (r.next()) {
                    addWindow(
                            new Window(
                                    r.getString("id"),
                                    r.getInt("win_number"),
                                    r.getString("name"),
                                    r.getInt("status"),
                                    r.getString("win_group_id"),
                                    r.getString("default_user"),
                                    r.getString("branch_id"),
                                    a.getId(),
                                    r.getString("screen_type")
                            )
                    );
                }
                con.closeConnection();

            } catch (Exception ex) {
                Logger.getLogger(WindowController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
            }

            System.out.println("-- t_window for " + a.getName() + " updated.");
            return 1;
        } else {
            return 0;
        }

    }
}
