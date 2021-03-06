package by.minsk.resume.component.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Component
public class ApplicationListener implements ServletContextListener {
    public static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);

    @Value("${application.production}")
    private boolean production;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
      servletContextEvent.getServletContext().setAttribute("production",production);
      LOGGER.info("Application started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("Application destroyed");

    }
}
