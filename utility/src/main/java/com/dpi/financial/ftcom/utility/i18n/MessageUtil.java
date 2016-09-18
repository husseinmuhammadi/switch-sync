package com.dpi.financial.ftcom.utility.i18n;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageUtil {
    public static String getMessage(Enum anEnum, ResourceBundle resourceBundle) {
        String key = anEnum.getClass().getName() + "." + anEnum.name();
        return getMessage(key, resourceBundle);
    }

    /**
     * http://stackoverflow.com/questions/6451215/how-to-remove-the-surrounding-when-message-is-not-found-in-bundle
     *
     * @param key
     * @param resourceBundle
     * @return
     */
    public static String getMessage(String key, ResourceBundle resourceBundle) {
        // return resourceBundle.getString(name);

        String result = null;
        try {
            result = resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            result = String.format("[%s]", key);
        }
        return result;
    }
}
