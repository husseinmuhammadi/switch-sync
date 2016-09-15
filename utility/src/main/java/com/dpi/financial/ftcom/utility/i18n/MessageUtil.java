package com.dpi.financial.ftcom.utility.i18n;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageUtil {
    public static String getMessage(Enum anEnum, ResourceBundle resourceBundle) {
        String key = anEnum.getClass().getName() + "." + anEnum.name();
        return getMessage(key, resourceBundle);
    }

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
