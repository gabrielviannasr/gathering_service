package br.com.gathering.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHelper {

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static void info(Logger log, String action, Object... details) {
        log.info("[{}] {} | {}", getContext(log), action, formatDetails(details));
    }

    public static void warn(Logger log, String action, Object... details) {
        log.warn("[{}] {} | {}", getContext(log), action, formatDetails(details));
    }

    public static void error(Logger log, String action, Throwable ex, Object... details) {
        log.error("[{}] {} | {} | error={}", getContext(log), action, formatDetails(details), ex.getMessage(), ex);
    }

    private static String getContext(Logger log) {
        String name = log.getName();
        int lastDot = name.lastIndexOf('.');
        return lastDot != -1 ? name.substring(lastDot + 1) : name;
    }

    private static String formatDetails(Object... details) {
        if (details == null || details.length == 0) return "-";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < details.length; i += 2) {
            if (i > 0) sb.append(", ");
            sb.append(details[i]).append("=").append(details[i + 1]);
        }
        return sb.toString();
    }
}
