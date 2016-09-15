package com.dpi.financial.ftcom.web.controller.base;

import com.dpi.financial.ftcom.utility.i18n.MessageUtil;
import com.dpi.financial.ftcom.web.bundle.ResourceBundleUtil;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public class AbstractController {

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

    protected HttpServletResponse getResponse() {
        return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
    }

    protected ResourceBundle getMessageBundle() {
        ResourceBundleUtil resourceBundleUtil = new ResourceBundleUtil();
        return resourceBundleUtil.getBundle("msg");
    }

    protected ResourceBundle getLabelBundle() {
        ResourceBundleUtil resourceBundleUtil = new ResourceBundleUtil();
        return resourceBundleUtil.getBundle("lbl");
    }

    protected String getMessage(String message) {
        ResourceBundle resourceBundle =
                ResourceBundleUtil.getResourceBundle(ResourceBundleUtil.MESSAGE_BUNDLE);

        return MessageUtil.getMessage(message, resourceBundle);
    }

    protected String getLabel(String label) {
        ResourceBundle resourceBundle =
                ResourceBundleUtil.getResourceBundle(ResourceBundleUtil.LABEL_BUNDLE);
        return MessageUtil.getMessage(label, resourceBundle);
    }

    protected String getLabel(Enum anEnum) {
        ResourceBundle resourceBundle = getLabelBundle();
                // ResourceBundleUtil.getResourceBundle(ResourceBundleUtil.LABEL_BUNDLE);
        return MessageUtil.getMessage(anEnum, resourceBundle);
    }

}
