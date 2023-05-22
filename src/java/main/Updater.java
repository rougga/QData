package main;

import java.util.UUID;
import main.controller.AgenceController;
import main.controller.LoginLogController;
import main.controller.ServiceController;
import main.controller.TaskController;
import main.controller.TicketController;
import main.controller.UserController;
import main.controller.WindowController;
import main.controller.WindowStatusController;

public class Updater {

    public Updater() {
    }

    public void updateDatabaseById(UUID id) {
        new AgenceController().updateAgenceNameById(id);
        new ServiceController().updateServicesById(id);
        new WindowController().updateWindowsById(id);
        new UserController().updateUsersById(id);
        new TicketController().updateTodayTicketsById(id);
        new WindowStatusController().updateWindowStatusById(id);
        new LoginLogController().updateTodayLoginLogsById(id);
        new TaskController().updateTasksById(id);
        //update goal
    }

    public void updateDatabase() {
        new AgenceController().updateAllAgenceName();
        new ServiceController().updateServices();
        new WindowController().updateWindows();
        new UserController().updateUsers();
        new TicketController().updateTodayTickets();
        new WindowStatusController().updateWindowStatus();
        new LoginLogController().updateTodayLoginLogs();
        new TaskController().updateAllTasks();
        //update goal
    }

    public void updateDatabaseAllDataById(UUID id) {
        new AgenceController().updateAgenceNameById(id);
        new ServiceController().updateServicesById(id);
        new WindowController().updateWindowsById(id);
        new UserController().updateUsersById(id);
        new TicketController().updateAllTicketsById(id);
        new WindowStatusController().updateWindowStatusById(id);
        new LoginLogController().updateAllLoginLogsById(id);
        new TaskController().updateTasksById(id);
        //update goal
    }

    public void updateDatabaseAllData() {
        new AgenceController().updateAllAgenceName();
        new ServiceController().updateServices();
        new WindowController().updateWindows();
        new UserController().updateUsers();
        new TicketController().updateAllTickets();
        new WindowStatusController().updateWindowStatus();
        new LoginLogController().updateAllLoginLogs();
        new TaskController().updateAllTasks();
        //update goal
    }

}
