package com.dpi.financial.ftcom.web.bundle;

import javax.faces.context.FacesContext;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleUtil {
    private ResourceBundle bundle;

    public static final String LABEL_BUNDLE = "com/dpi/financial/ftcom/web/i18n/label/messages";
    
    public static final String MESSAGE_BUNDLE = "com/dpi/financial/ftcom/web/i18n/message/messages";

    public static ResourceBundle getResourceBundle(String bundleName) {
        return ResourceBundle.getBundle(bundleName, new Locale("fa", "IR"));
    }

    /**
     * GAP
     * @param bundleName
     * @return
     */
    public ResourceBundle getBundle(String bundleName) {
        FacesContext context = FacesContext.getCurrentInstance();
        bundle = context.getApplication().getResourceBundle(context, bundleName);
        return bundle;
    }
}
