package com.example.server.utils;

import org.slf4j.LoggerFactory;

public class LoggerUtil {
    private LoggerUtil() {}
    public static <T> void info(Class<T> clazz, String msg) {
        LoggerFactory.getLogger(clazz).info(msg);
    }

    public static <T> void error(Class<T> clazz, String msg) {
        LoggerFactory.getLogger(clazz).error(msg);
    }

    public static <T> void error(Class<T> clazz, String msg, Throwable t) {
        LoggerFactory.getLogger(clazz).error(msg, t);
    }

}
