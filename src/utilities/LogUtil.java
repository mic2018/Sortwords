package utilities;

import java.io.IOException;
import java.util.logging.*;

public class LogUtil {

    public static void setupLogger(Logger logger, String logFile) {
        try {
            // Remove any existing handlers
            Handler[] handlers = logger.getHandlers();
            for (Handler handler : handlers) {
                logger.removeHandler(handler);
            }

            // Console Handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            logger.addHandler(consoleHandler);

            // File Handler
            FileHandler fileHandler = new FileHandler(logFile, true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            // Set logger level to ALL
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false); // Avoid duplicate log entries

            logger.info("Logger initialized successfully.");
        } catch (IOException e) {
            System.err.println("Failed to setup logger: " + e.getMessage());
        }
    }
}
