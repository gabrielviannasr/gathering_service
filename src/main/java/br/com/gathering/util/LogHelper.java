package br.com.gathering.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHelper {

    /**
     * Logger é criado uma única vez por classe.
     * 
     * O cache é uma camada de segurança e otimização preventiva — não obrigatória, mas inteligente.
     * 
     * Ele garante:
     * 		- criação única por classe, mesmo em contextos dinâmicos ou concorrentes;
     * 		- acesso thread-safe e constante (O(1) via ConcurrentHashMap);
     * 		- e evita overhead de Class.forName() e LoggerFactory em chamadas repetidas.
     * 
     * Tem ganho real se em dois cenários:
     * 		1. Se Logger for chamado em tempo de execução;
     * 		2. Proteção contra inicialização concorrente (threads).
     * 
     * Se o uso for sempre em campos static final, não teria impacto perceptível.
     * 
     * É apenas uma otimização defensiva.
     */
    private static final Map<String, Logger> CACHE = new ConcurrentHashMap<>();

    public static Logger getLogger() {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        return CACHE.computeIfAbsent(className, name -> {
           try {
               return LoggerFactory.getLogger(Class.forName(name));
           } catch (ClassNotFoundException e) {
               throw new IllegalStateException("Unable to resolve logger class for " + name, e);
           }
        });
    }

    /*// Método getLogger sem uso de cache 
     public static Logger getLogger() {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        try {
            return LoggerFactory.getLogger(Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Unable to resolve logger class for " + className, e);
        }
    }*/

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
