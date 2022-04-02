package dev.shph.digapply.controller.listener;

import dev.shph.commandeur.container.AnnotationScanningContainer;
import dev.shph.commandeur.container.CommandContainer;
import dev.shph.commandeur.container.CommandeurException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CommandDispatcherManager implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            CommandContainer container = new AnnotationScanningContainer();
            sce.getServletContext().setAttribute("commandContainer", container);
            logger.info("Commandeur command container initialized.");
        } catch (CommandeurException e) {
            logger.error("Unable to initialize Commandeur command container. {}", e.getMessage());
            throw new RuntimeException("Unable to initialize command dispatcher.", e);
        }
    }
}
