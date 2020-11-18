package main;

import main.controller.AgenceController;
import main.controller.ServiceController;
import main.controller.TicketController;
import main.controller.UserController;
import main.controller.WindowController;

public class Updater {

    public Updater() {
    }

    public void updateDatabase() {
        new AgenceController().updateAllAgenceName();
        new ServiceController().updateServices();
        new WindowController().updateWindows();
        new UserController().updateUsers();
        new TicketController().updateTodayTickets();
        //updateCible();
    }

}
