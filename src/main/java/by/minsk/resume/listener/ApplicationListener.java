package by.minsk.resume.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


@Component
public class ApplicationListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);

    @Value("${application.production}")
    private boolean production;

    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("production",production);
        LOGGER.debug("Application started");
    }

    public void contextDestroyed(ServletContextEvent event) {
        LOGGER.info("Application destroyed");
    }
}
