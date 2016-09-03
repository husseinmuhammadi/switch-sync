package com.dpi.financial.ftcom.web.controller.base.template;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

/**
 *
 */

@Named
@SessionScoped
public class TemplateController implements Serializable {

    private String localeValue = "fa_IR";

    public String getLocaleValue() {
        return localeValue;
    }

    @PostConstruct
    private void init() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("fa", "IR"));
    }
}
