package com.ihordev.core.util;

public final class StringUtils {

    private StringUtils() {
        throw new AssertionError("StringUtils cannot be instantiated.");
    }

    /**
     * Decapitilizes first letter in specified string.
     *
     * @param str  the string where first letter must be decapitilized
     * @return string with decapitilized first letter.
     */
    public static String decapitalize(String str) {
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}
