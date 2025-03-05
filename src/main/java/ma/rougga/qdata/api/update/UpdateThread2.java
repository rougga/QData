package ma.rougga.qdata.api.update;

import ma.rougga.qdata.controller.CibleController;
import ma.rougga.qdata.controller.report.GchSerTableController;
import ma.rougga.qdata.controller.report.GchTableController;
import ma.rougga.qdata.controller.report.ThATableController;
import ma.rougga.qdata.controller.report.ThSATableController;
import ma.rougga.qdata.controller.report.ThTTTableController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateThread2 extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(UpdateThread2.class);

    public UpdateThread2() {
    }

    @Override
    public void run() {
        logger.info("Thread 02 STARTED.");
        new GchTableController().updateFromJson(null, null);
        new GchSerTableController().updateFromJson(null, null);
        new ThTTTableController().updateFromJson(null, null);
        new ThATableController().updateFromJson(null, null);
        new ThSATableController().updateFromJson(null, null);
        new CibleController().updateFromJson();
        logger.info("Thread 02 DONE.");
    }

}
