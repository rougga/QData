package main.controller;

import ma.rougga.nst.controller.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.rougga.nst.modal.central.Task;
import main.PgConnection;
import main.PgMultiConnection;
import main.modal.Agence;

public class TaskController {

    public TaskController() {
    }

    public ArrayList<Task> getTasks(UUID db_id) {
        ArrayList<Task> taches = new ArrayList<>();
        String SQL = "SELECT id,name,id_service FROM rougga_task WHERE db_id=? order by id_service;";
        try {
            PgConnection scon = new PgConnection();
            PreparedStatement ps = scon.getStatement().getConnection().prepareStatement(SQL);
            ps.setString(1, db_id.toString());
            ResultSet r = ps.executeQuery();

            while (r.next()) {
                taches.add(new Task(UUID.fromString(r.getString("id")), r.getString("name"), r.getString("id_service"), UUID.fromString(r.getString("db_id"))));
            }
            return taches;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
            return taches;
        }
    }

    public ArrayList<ma.rougga.nst.modal.Task> getTasks(Statement s) {
        ArrayList<ma.rougga.nst.modal.Task> taches = new ArrayList<>();
        String SQL = "SELECT id,name,id_service FROM rougga_task order by id_service;";
        try {
            PreparedStatement ps = s.getConnection().prepareStatement(SQL);
            ResultSet r = ps.executeQuery();

            while (r.next()) {
                taches.add(new ma.rougga.nst.modal.Task(UUID.fromString(r.getString("id")), r.getString("name"), r.getString("id_service")));
            }
            return taches;
        } catch (SQLException ex) {
            Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
            return taches;
        }

    }

    public boolean add(Task task) {
        try {
            String SQL = "INSERT INTO rougga_task values (?,?,?,?);";
            PgConnection scon = new PgConnection();
            PreparedStatement ps = scon.getStatement().getConnection().prepareStatement(SQL);
            ps.setString(1, task.getId().toString());
            ps.setString(2, task.getName());
            ps.setString(3, task.getId_service());
            ps.setString(4, task.getDb_id().toString());
            return ps.execute();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean addToCentral(ma.rougga.nst.modal.Task task, UUID db_id) {
        try {
            String SQL = "INSERT INTO rougga_task values (?,?,?,?);";
            PgConnection scon = new PgConnection();
            PreparedStatement ps = scon.getStatement().getConnection().prepareStatement(SQL);
            ps.setString(1, task.getId().toString());
            ps.setString(2, task.getName());
            ps.setString(3, task.getId_service());
            ps.setString(4, db_id.toString());
            return ps.execute();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deleteById(UUID id, UUID db_id) {
        try {
            String SQL = "DELETE from rougga_task where id=? AND db_id=?";
            PgConnection scon = new PgConnection();
            PreparedStatement ps = scon.getStatement().getConnection().prepareStatement(SQL);
            ps.setString(1, id.toString());
            ps.setString(2, db_id.toString());
            ps.execute();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ArrayList<Task> getTasksByService(String id_service, UUID db_id) {
        ArrayList<Task> taches = new ArrayList<>();
        try {
            String SQL = "SELECT * from rougga_task where id_service=? AND db_id=?";
            PgConnection scon = new PgConnection();
            PreparedStatement ps = scon.getStatement().getConnection().prepareStatement(SQL);
            ps.setString(1, id_service);
            ps.setString(2, db_id.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                taches.add(
                        new Task(
                                UUID.fromString(rs.getString("id")),
                                rs.getString("name"),
                                rs.getString("id_service"),
                                UUID.fromString(rs.getString("db_id"))
                        )
                );
            }
            return taches;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
            return taches;
        }
    }

    public boolean setTaskToTicket(String id_task, String id_ticket, int qte, UUID db_id) {
        try {
            String SQL = "INSERT INTO rougga_ticket_task values (?,?,?,?)";
            PgConnection scon = new PgConnection();
            PreparedStatement ps = scon.getStatement().getConnection().prepareCall(SQL);
            ps.setString(1, id_ticket);
            ps.setString(2, id_task);
            ps.setInt(3, qte);
            ps.setString(4, db_id.toString());
            ps.execute();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void updateTasksById(UUID db_id) {
        Agence a = new AgenceController().getAgenceById(db_id);
        if (a != null) {
            try {

                PgConnection scon = new PgConnection();
                PgMultiConnection con = new PgMultiConnection(a.getHost(), String.valueOf(a.getPort()), a.getDatabase(), a.getUsername(), a.getPassword());
                List<ma.rougga.nst.modal.Task> tks = getTasks(con.getStatement());

                String SQL = "select * from rougga_ticket_task";
                ResultSet r = con.getStatement().executeQuery(SQL);
                clearTasksFromDb(a.getId());
                for (int index = 0; index < tks.size(); index++) {
                    addToCentral(tks.get(index), db_id);
                }
                while (r.next()) {
                    String SQL2 = "INSERT INTO rougga_ticket_task values (?,?,?,?)";
                    try (PreparedStatement ps = scon.getStatement().getConnection().prepareCall(SQL2)) {
                        ps.setString(1, r.getString("id_ticket"));
                        ps.setString(2, r.getString("id_task"));
                        ps.setInt(3, r.getInt("quantity"));
                        ps.setString(4, db_id.toString());
                        ps.execute();
                    } catch (SQLException ex) {
                        Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
                    }
                }

                con.closeConnection();

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
            }

            System.out.println("-- Tasks updated.");
        } else {
            System.out.println("-- TAsks not updated updated.");
        }
    }

    private boolean clearTasksFromDb(UUID db_id) {
        try {
            String SQL = "delete from rougga_task WHERE db_id=?";
            PgConnection scon = new PgConnection();
            PreparedStatement ps = scon.getStatement().getConnection().prepareCall(SQL);
            ps.setString(1, db_id.toString());
            ps.execute();
            scon.closeConnection();
            System.out.println("-- TAsks table cleared.");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void updateAllTasks() {
        List<Agence> ag = new AgenceController().getAllAgence();
        for (Agence a : ag) {
            try {
                PgConnection scon = new PgConnection();
                PgMultiConnection con = new PgMultiConnection(a.getHost(), String.valueOf(a.getPort()), a.getDatabase(), a.getUsername(), a.getPassword());
                List<ma.rougga.nst.modal.Task> tks = getTasks(con.getStatement());

                String SQL = "select * from rougga_ticket_task";
                ResultSet r = con.getStatement().executeQuery(SQL);
                clearTasksFromDb(a.getId());
                for (int index = 0; index < tks.size(); index++) {
                    addToCentral(tks.get(index), a.getId());
                }
                while (r.next()) {
                    String SQL2 = "INSERT INTO rougga_ticket_task values (?,?,?,?)";
                    try (PreparedStatement ps = scon.getStatement().getConnection().prepareCall(SQL2)) {
                        ps.setString(1, r.getString("id_ticket"));
                        ps.setString(2, r.getString("id_task"));
                        ps.setInt(3, r.getInt("quantity"));
                        ps.setString(4, a.getId().toString());
                        ps.execute();
                    } catch (SQLException ex) {
                        Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
                    }
                }
                System.out.println("-- Tasks for "+a.getName()+" updated.");
                con.closeConnection();

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex.getMessage());
            }

            System.out.println("-- Tasks updated.");
        }
    }
}
