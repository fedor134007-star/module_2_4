package utils;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        IO.println("=== START application ===");
        DatabaseMigration.migrate();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        IO.println("=== STOP application ===");
        HibernateUtil.shutdown();
    }
}
