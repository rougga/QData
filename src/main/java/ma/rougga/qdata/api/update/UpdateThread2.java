package ma.rougga.qdata.api.update;

import ma.rougga.qdata.controller.AgenceController;
import ma.rougga.qdata.controller.CibleController;
import ma.rougga.qdata.controller.report.GchTableController;
import ma.rougga.qdata.controller.report.ThATableController;
import ma.rougga.qdata.controller.report.ThTTTableController;
import ma.rougga.qdata.modal.Agence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateThread2 extends Thread {

    private AgenceController ac = new AgenceController();
    private static final Logger logger = LoggerFactory.getLogger(UpdateThread2.class);

    public UpdateThread2() {
    }

    @Override
    public void run() {
        logger.info("Thread 02 STARTED.");
        for (Agence a : ac.getAllAgence()) {
            if (!ac.isOnline(a.getId())) {
                logger.info("Agence: {} is OFFLINE", a.getName());
                continue;
            }
            new GchTableController().updateAgenceFromJson(null, null, a.getId().toString());
            new ThTTTableController().updateAgenceFromJson(null, null, a.getId().toString());
            new ThATableController().updateAgenceFromJson(null, null, a.getId().toString());
            new CibleController().updateAgenceFromJson(a.getId().toString());
        }
        logger.info("Thread 02 DONE.");
    }

}
