package ma.rougga.qdata.api.update;

import ma.rougga.qdata.controller.report.EmpSerTableController;
import ma.rougga.qdata.controller.report.GchSerTableController;
import ma.rougga.qdata.controller.report.ThSATableController;
import ma.rougga.qdata.controller.report.ThTTableController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateThread3 extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(UpdateThread3.class);

    public UpdateThread3() {
    }

    @Override
    public void run() {
        logger.info("Thread 03 STARTED.");
        new GchSerTableController().updateFromJson(null, null);
        new EmpSerTableController().updateFromJson(null, null);
        new ThSATableController().updateFromJson(null, null);
        new ThTTableController().updateFromJson(null, null);
        logger.info("Thread 03 DONE.");
    }

}
