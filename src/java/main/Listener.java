package main;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Listener implements ServletContextListener {

    private volatile ScheduledExecutorService executor;
    final Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("-- OffReport Data Update Starting.....");
            new Updater().updateDatabase();
            System.out.println("-- Last Updated: " + new Date().toString());
        }
    };

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        executor = Executors.newScheduledThreadPool(2);
        executor.scheduleAtFixedRate(myRunnable, 0, 5, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        final ScheduledExecutorService executor = this.executor;

        if (executor != null) {
            executor.shutdown();
            this.executor = null;
        }
    }

}
