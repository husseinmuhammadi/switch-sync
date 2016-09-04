package com.dpi.financial.ftcom.web.controller.handler.error;

import javax.faces.application.FacesMessage;

/**
 * Created by h.mohammadi on 9/3/2016.
 */
public class UnitTestFacesErrorHandler extends SystemTestFacesErrorHandler implements IFacesErrorHandler {
    @Override
    public void addMessage(String clientId, FacesMessage message) {
        super.addMessage(clientId, message);
    }

    @Override
    public void addErrorMessage(String clientId, FacesMessage message, Throwable throwable) {
        super.addErrorMessage(clientId, message, throwable);
    }
}
