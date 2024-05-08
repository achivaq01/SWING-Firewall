package com.project.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogLib {

    private LogLib() {}

    /**
     * This method is used to log info.
     *
     * @param tag Tag to identify where is this logging from.
     * @param clazz The class that is being logged from.
     * @param message Log message.
     * @param origin Method of the class the log is from.
     */
    public static <T> void logInfo(String tag, Class<T> clazz, String message, String origin) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.info("{} | {} : {}", tag, origin, message);
    }

    /**
     * This method is used to log warnings.
     *
     * @param tag Tag to identify where is this logging from.
     * @param clazz The class that is being logged from.
     * @param message Log message.
     * @param origin Method of the class the log is from.
     */
    public static <T> void logWarn(String tag, Class<T> clazz, String message, String origin) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.warn("{} | {} : {}", tag, origin, message);
    }

    /**
     * This method is used to log errors.
     *
     * @param tag Tag to identify where is this logging from.
     * @param clazz The class that is being logged from.
     * @param message Log message.
     * @param origin Method of the class the log is from.
     * @param throwable The error to log.
     */
    public static <T> void logError(String tag, Class<T> clazz, String message, String origin, Throwable throwable) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.error("{} | {} : {} | {}", tag, origin, message, throwable.getMessage());
    }

}
