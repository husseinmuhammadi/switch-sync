package com.dpi.financial.ftcom.web.controller.handler;

import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

/**
 * Created by h.mohammadi on 9/3/2016.
 */
public class DevelopmentFacesErrorHandler extends UnitTestFacesErrorHandler implements IFacesErrorHandler {
    @Override
    public void addMessage(String clientId, FacesMessage message) {
        super.addMessage(clientId, message);
    }

    @Override
    public void addErrorMessage(String clientId, FacesMessage message, Throwable throwable) {
        super.addErrorMessage(clientId, message, throwable);

        Throwable cause = throwable.getCause();
        while (cause != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(cause.toString()));
            cause = cause.getCause();
        }

    }
}
