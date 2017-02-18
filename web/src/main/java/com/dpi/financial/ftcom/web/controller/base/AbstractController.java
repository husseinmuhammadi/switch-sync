package com.dpi.financial.ftcom.web.controller.base;

import com.dpi.financial.ftcom.utility.i18n.MessageUtil;
import com.dpi.financial.ftcom.web.bundle.ResourceManager;

import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public abstract class AbstractController {

    protected FacesContext facesContext = null;

    public FacesContext getFacesContext() {
        if (facesContext != null) {
            // for unit tests
            return facesContext;
        }
        return FacesContext.getCurrentInstance();
    }

    protected ServletContext getServletContext() {
        return (ServletContext) getFacesContext().getExternalContext().getContext();
    }

    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
    }

    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    public String getParameter(String name) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
    }

    public String getRemoteIpAddress() {
        return getRequest().getRemoteAddr();
    }

    protected HttpServletResponse getHttpServletResponse() {
        return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
    }

    public String getRequestContextPath() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    }

    public ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    protected ResourceBundle getMessageBundle() {
        ResourceManager resourceManager = new ResourceManager();
        return resourceManager.getBundle("msg");
    }

    protected ResourceBundle getLabelBundle() {
        ResourceManager resourceManager = new ResourceManager();
        return resourceManager.getBundle("lbl");
    }

    protected String getMessage(String message) {
        ResourceBundle resourceBundle = ResourceManager.getMessageBundle();
        return MessageUtil.getMessage(message, resourceBundle);
    }

    protected String getLabel(String label) {
        ResourceBundle resourceBundle = ResourceManager.getLabelBundle();
        return MessageUtil.getMessage(label, resourceBundle);
    }

    protected String getLabel(Enum anEnum) {
        ResourceBundle resourceBundle = getLabelBundle();
        // ResourceManager.getResourceBundle(ResourceManager.LABEL_BUNDLE);
        return MessageUtil.getMessage(anEnum, resourceBundle);
    }

    protected void printErrorMessage(Throwable e) {
        ResourceBundle resourceBundle = ResourceManager.getMessageBundle();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(resourceBundle.getString("request.error")));

        ProjectStage projectStage = FacesContext.getCurrentInstance().getApplication().getProjectStage();
        if (projectStage != null && projectStage.equals(ProjectStage.Development)) {
            Throwable cause = e.getCause();
            while (cause != null /* && !(cause instanceof SQLException)*/) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(cause.toString()));

                // if (cause instanceof javax.persistence.PersistenceException) entity.setId(null);

                cause = cause.getCause();
            }
        }
    }

    public void getApplicationContext() {

    }
}
