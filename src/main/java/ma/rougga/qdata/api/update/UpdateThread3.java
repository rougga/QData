package ma.rougga.qdata.api.update;

import ma.rougga.qdata.controller.AgenceController;
import ma.rougga.qdata.controller.report.EmpSerTableController;
import ma.rougga.qdata.controller.report.GchSerTableController;
import ma.rougga.qdata.controller.report.ThSATableController;
import ma.rougga.qdata.controller.report.ThTTableController;
import ma.rougga.qdata.modal.Agence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateThread3 extends Thread {

    private AgenceController ac = new AgenceController();
    private static final Logger logger = LoggerFactory.getLogger(UpdateThread3.class);

    public UpdateThread3() {
    }

    @Override
    public void run() {
        logger.info("Thread 03 STARTED.");
        for (Agence a : ac.getAllAgence()) {
            if (!ac.isOnline(a.getId())) {
                logger.info("Agence: {} is OFFLINE", a.getName());
                continue;
            }
            new GchSerTableController().updateAgenceFromJson(null, null, a.getId().toString());
            new EmpSerTableController().updateAgenceFromJson(null, null, a.getId().toString());
            new ThSATableController().updateAgenceFromJson(null, null, a.getId().toString());
            new ThTTableController().updateAgenceFromJson(null, null, a.getId().toString());
        }
        logger.info("Thread 03 DONE.");
    }

}
