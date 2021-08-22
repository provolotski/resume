package by.minsk.resume.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);

    public void contextInitialized(ServletContextEvent event) {
        LOGGER.debug("Application started");
    }

    public void contextDestroyed(ServletContextEvent event) {
        LOGGER.info("Application destroyed");
    }
}
