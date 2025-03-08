package ma.rougga.qdata.api.update;

import ma.rougga.qdata.controller.report.EmpTableController;
import ma.rougga.qdata.controller.report.GblTableController;
import ma.rougga.qdata.controller.report.GlaTableController;
import ma.rougga.qdata.controller.report.GltTableController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateThread1 extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(UpdateThread1.class);
    public UpdateThread1() {
    }
    
    @Override
    public void run() {
        logger.info("Thread 01 STARTED.");
        new GblTableController().updateFromJson(null, null);
        new EmpTableController().updateFromJson(null, null);
        new GlaTableController().updateFromJson(null, null);
        new GltTableController().updateFromJson(null, null);
        logger.info("Thread 01 DONE.");
    }
    
}
