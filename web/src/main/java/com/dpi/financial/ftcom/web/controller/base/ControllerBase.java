package com.dpi.financial.ftcom.web.controller.base;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.web.bundle.ResourceBundleUtil;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.AbstractCollection;
import java.util.ResourceBundle;
import java.util.TreeMap;

@Dependent
public abstract class ControllerBase<T extends EntityBase> extends AbstractController
        implements ControllerActionBase, GenericBean<T> {

    private final IFactory<T> factory;
    protected T entity;

    private Long id;

    public ControllerBase(Class<T> entityBeanType) {
        factory = new Factory<T>(entityBeanType);
        entity = factory.createInstance();
    }

    @Override
    public T getEntity() {
        return entity;
    }

    @Override
    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract GeneralServiceApi<T> getGeneralServiceApi();

    @PostConstruct
    protected void init() {
    }

    @Override
    public void prepare() {
        entity.setId(null);
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public String create() {
        ResourceBundle resourceBundle = ResourceBundleUtil.getResourceBundle(ResourceBundleUtil.MESSAGE_BUNDLE);
        try {
            // prepare entity
            prepare();

            // validate entity
            validate();

            getGeneralServiceApi().create(entity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(resourceBundle.getString("request.success")));
            entity = factory.createInstance();
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
        return null;
    }

    @Override
    public String update() {
        try {
            getGeneralServiceApi().update(entity);
            String url = FacesContext.getCurrentInstance().getViewRoot().getViewId().replace("insert", "index") + "?faces-redirect=true";
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
        return null;
    }

    @Override
    public String delete() {
        ResourceBundle resourceBundle = ResourceBundleUtil.getResourceBundle(ResourceBundleUtil.MESSAGE_BUNDLE);

        try {
            getGeneralServiceApi().delete(entity);
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();

            FacesMessage message = new FacesMessage(resourceBundle.getString("request.success"));
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessage("request.success"), "");
            context.addMessage(null, message);

            externalContext.getFlash().setKeepMessages(true);

            // externalContext.getFlash().put("message", new FacesMessage(FacesMessage.SEVERITY_INFO, getMessage("request.success"), ""));
            // externalContext.getFlash().setKeepMessages(true);
            String url = context.getViewRoot().getViewId() + "?faces-redirect=true";
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
        return null;
    }

    @Override
    public String delete(Long id) {
        try {
            entity = getGeneralServiceApi().find(id);
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
        return delete();
    }

    @Override
    public String refresh() {
        FacesContext context = FacesContext.getCurrentInstance();
        String url = context.getViewRoot().getViewId() + "?faces-redirect=true";
        return url;
    }

    public void onLoad() {
        if (id != null) {
            entity = getGeneralServiceApi().find(id);
            if (entity == null) {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().dispatch("index");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        afterLoad();
    }
}
