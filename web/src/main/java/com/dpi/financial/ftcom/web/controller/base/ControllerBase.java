package com.dpi.financial.ftcom.web.controller.base;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.web.bundle.ResourceManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ResourceBundle;

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
        ResourceBundle resourceBundle = ResourceManager.getMessageBundle();
        try {
            // prepare entity
            prepare();

            // validate entity
            validate();

            getGeneralServiceApi().create(entity);
            getFacesContext().addMessage(null, new FacesMessage(resourceBundle.getString("request.success")));
            getExternalContext().getFlash().setKeepMessages(true);
            return afterCreate();

            // TODO: After saving entity we need to create new entity if we stay in insert view
            // entity = factory.createInstance();
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }

        return null;
    }

    protected String afterCreate() {
        return getFacesContext().getViewRoot().getViewId().replace("insert", "index") + "?faces-redirect=true";
    }

    @Override
    public String update() {
        String url = null;
        try {
            getGeneralServiceApi().update(entity);
            url = FacesContext.getCurrentInstance().getViewRoot().getViewId().replace("insert", "index") + "?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
        return url;
    }

    public String cancel() {
        getFacesContext().addMessage(null, new FacesMessage(getMessage("request.cancel")));
        getExternalContext().getFlash().setKeepMessages(true);
        String url = getFacesContext().getViewRoot().getViewId().replace("insert", "index") + "?faces-redirect=true";
        return url;
    }

    @Override
    public String delete() {
        ResourceBundle resourceBundle = ResourceManager.getMessageBundle();

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
