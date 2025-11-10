package br.com.gathering.util;

public class RouteHelper {

	public static String route(String method, String path, String suffix) {
        return method + " " + path + (suffix != null ? suffix : "");
    }

	public static String route(String method, String path) {
        return route(method, path, "");
    }

}
