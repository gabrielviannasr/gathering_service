
package br.com.gathering.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHelper {

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static void info(Logger log, String context, String action, Object... details) {
        log.info("[{}] {} | {}", context, action, formatDetails(details));
    }

    public static void warn(Logger log, String context, String action, Object... details) {
        log.warn("[{}] {} | {}", context, action, formatDetails(details));
    }

    public static void error(Logger log, String context, String action, Throwable ex, Object... details) {
        log.error("[{}] {} | {} | error={}", context, action, formatDetails(details), ex.getMessage(), ex);
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
