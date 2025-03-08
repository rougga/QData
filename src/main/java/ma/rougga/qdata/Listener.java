package ma.rougga.qdata;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import ma.rougga.qdata.api.update.UpdateThread1;
import ma.rougga.qdata.api.update.UpdateThread2;
import ma.rougga.qdata.api.update.UpdateThread3;

import org.slf4j.LoggerFactory;

public class Listener implements ServletContextListener {
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Listener.class);
    
    private static volatile ScheduledExecutorService executor;
    
    
    static final Runnable myRunnable = () -> {
        logger.info("-- "+CfgHandler.APP+" v"+CfgHandler.VERSION+" Today Data Update Starting.....");
        new UpdateThread1().start();
        new UpdateThread2().start();
        new UpdateThread3().start();
        logger.info("-- Last Updated: {}", new Date().toString());
    };

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        executor = Executors.newScheduledThreadPool(2);
        executor.scheduleAtFixedRate(myRunnable, 0, CfgHandler.AUTOUPDATE_REFRESHTIME, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        final ScheduledExecutorService executor = this.executor;

        if (executor != null) {
            executor.shutdown();
            this.executor = null;
        }
    }

    public static void changeRefreshTime(long time){
        executor.shutdown();
        executor = Executors.newScheduledThreadPool(2);
        executor.scheduleAtFixedRate(myRunnable, 0, time, TimeUnit.MINUTES);
    }
}
