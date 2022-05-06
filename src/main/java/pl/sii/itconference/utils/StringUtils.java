package pl.sii.itconference.utils;

public class StringUtils {

    public static boolean isNotBlank(String str) {
        return str != null && !str.isBlank();
    }

    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
