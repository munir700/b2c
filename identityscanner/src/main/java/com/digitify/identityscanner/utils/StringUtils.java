package com.digitify.identityscanner.utils;

import java.util.Arrays;

public class StringUtils {

    public static String convertToCommaSeparated(Object[] strings) {
        return Arrays.toString(strings).replaceAll("[\\[.\\].\\s+]", "");
    }
}
