package com.dpi.financial.ftcom.web.controller.handler;

import javax.faces.application.FacesMessage;

/**
 * Created by h.mohammadi on 9/3/2016.
 */
public interface IFacesErrorHandler {
    void addMessage(String clientId, FacesMessage message);
    void addErrorMessage(String clientId, FacesMessage message, Throwable throwable);
}
