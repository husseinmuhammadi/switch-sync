package com.dpi.financial.ftcom.web.bundle;

import javax.faces.context.FacesContext;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceManager {
    private ResourceBundle bundle;

    protected static final String LABEL_BUNDLE = "com/dpi/financial/ftcom/web/i18n/label/messages";

    protected static final String MESSAGE_BUNDLE = "com/dpi/financial/ftcom/web/i18n/message/messages";

    /**
     *
     * @param bundleName
     * @return
     */
    protected static ResourceBundle getResourceBundle(String bundleName) {
        return ResourceBundle.getBundle(bundleName, new Locale("fa", "IR"));
    }

    public static ResourceBundle getMessageBundle() {
        return getResourceBundle(MESSAGE_BUNDLE);
    }

    public static ResourceBundle getLabelBundle() {
        return getResourceBundle(LABEL_BUNDLE);
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
