package com.dpi.financial.ftcom.web.controller.handler;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Created by h.mohammadi on 9/3/2016.
 */
public abstract class FacesErrorHandler implements IFacesErrorHandler {

    @Override
    public void addMessage(String clientId, FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(clientId, message);
    }

    @Override
    public void addErrorMessage(String clientId, FacesMessage message, Throwable throwable) {
        addMessage(clientId, message);
    }

}
