package com.dpi.financial.ftcom.utility.regex;

import java.util.Locale;

/**
 * Created by h.mohammadi on 12/19/2016.
 */
public class StringUtils {

    public static final String toLowercaseFirstLetter(final String originalStr) {
        Locale locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
        return toLowercaseFirstLetter(originalStr, locale);
    }
    public static final String toLowercaseFirstLetter(final String originalStr, final Locale locale) {
        final int splitIndex = 1;
        final String result;
        if (originalStr.isEmpty()) {
            result = originalStr;
        } else {
            result = Character.toLowerCase(originalStr.charAt(0)) + originalStr.substring(1);
            /*
            final String first = originalStr.substring(0, splitIndex).toLowerCase(locale);
            final String rest = originalStr.substring(splitIndex);
            final StringBuilder uncapStr = new StringBuilder(first).append(rest);
            result = uncapStr.toString();
            */
        }
        return result;
    }
}
