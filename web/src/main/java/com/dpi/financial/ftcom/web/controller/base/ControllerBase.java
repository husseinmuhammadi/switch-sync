package com.dpi.financial.ftcom.web.controller.base;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.web.util.ResourceBundleUtil;

import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

@Dependent
public abstract class ControllerBase<T extends EntityBase> implements ControllerActionBase, GenericBean<T> {

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

    @Override
    public String create() {
        ResourceBundle resourceBundle = ResourceBundleUtil.getResourceBundle(ResourceBundleUtil.MESSAGE_BUNDLE);

        try {
            getGeneralServiceApi().create(entity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(resourceBundle.getString("request.success")));
            entity = factory.createInstance();
        } catch (Exception e) {
            e.printStackTrace();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(resourceBundle.getString("request.error")));

            ProjectStage projectStage = FacesContext.getCurrentInstance().getApplication().getProjectStage();

            if (projectStage != null && projectStage.equals(ProjectStage.Development)) {
                Throwable cause = e.getCause();
                while (cause != null /* && !(cause instanceof SQLException)*/) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(cause.toString()));
                    cause = cause.getCause();
                }
            }
        }
        return null;
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }
}
