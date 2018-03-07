package com.redsponge.platformer.utils;

public class StringUtils {

    public static String arrayToString(Object[] array) {
        String result = "";
        for(Object o : array) {
            result += o.getClass().getSimpleName();
            result += ",";
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

}
