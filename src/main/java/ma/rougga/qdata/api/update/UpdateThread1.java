package ma.rougga.qdata.api.update;

import ma.rougga.qdata.controller.AgenceController;
import ma.rougga.qdata.controller.report.EmpTableController;
import ma.rougga.qdata.controller.report.GblTableController;
import ma.rougga.qdata.controller.report.GlaTableController;
import ma.rougga.qdata.controller.report.GltTableController;
import ma.rougga.qdata.modal.Agence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateThread1 extends Thread {

    private AgenceController ac = new AgenceController();
    private static final Logger logger = LoggerFactory.getLogger(UpdateThread1.class);

    public UpdateThread1() {
    }

    @Override
    public void run() {
        logger.info("Thread 01 STARTED.");
        for (Agence a : ac.getAllAgence()) {
            if (!ac.isOnline(a.getId())) {
                logger.info("Agence: {} is OFFLINE",a.getName());
                continue;
            } 
            new GblTableController().updateAgenceFromJson(null, null, a.getId().toString());
            new EmpTableController().updateAgenceFromJson(null, null, a.getId().toString());
            new GlaTableController().updateAgenceFromJson(null, null, a.getId().toString());
            new GltTableController().updateAgenceFromJson(null, null, a.getId().toString());
        }
        logger.info("Thread 01 DONE.");
    }

}
