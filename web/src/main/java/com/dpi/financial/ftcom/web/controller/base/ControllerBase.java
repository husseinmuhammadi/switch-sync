package com.dpi.financial.ftcom.web.controller.base;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.web.bundle.ResourceBundleUtil;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.util.AbstractCollection;
import java.util.ResourceBundle;
import java.util.TreeMap;

@Dependent
public abstract class ControllerBase<T extends EntityBase> extends AbstractController
        implements ControllerActionBase, GenericBean<T> {

    private final IFactory<T> factory;
    protected T entity;

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
        return null;
    }

    @Override
    public String delete() {
        ResourceBundle resourceBundle = ResourceBundleUtil.getResourceBundle(ResourceBundleUtil.MESSAGE_BUNDLE);

        try {
            getGeneralServiceApi().delete(entity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(resourceBundle.getString("request.success")));
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

    private void printErrorMessage(Throwable e) {
        ResourceBundle resourceBundle = ResourceBundleUtil.getResourceBundle(ResourceBundleUtil.MESSAGE_BUNDLE);
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
}
