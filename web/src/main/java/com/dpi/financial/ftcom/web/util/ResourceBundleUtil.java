package com.dpi.financial.ftcom.web.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleUtil {
    public static final String LABEL_BUNDLE = "com/dpi/financial/ftcom/web/i18n/label/messages";
    
    public static final String MESSAGE_BUNDLE = "com/dpi/financial/ftcom/web/i18n/message/messages";

    public static ResourceBundle getResourceBundle(String bundleName) {
        return ResourceBundle.getBundle(bundleName, new Locale("fa", "IR"));
    }
}
