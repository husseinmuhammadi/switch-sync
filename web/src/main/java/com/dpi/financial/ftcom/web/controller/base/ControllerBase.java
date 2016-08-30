package com.dpi.financial.ftcom.web.controller.base;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.utility.bundle.ResourceBundleUtil;

import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
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
        // ResourceBundle resourceBundle = ResourceBundleUtil.getResourceBundle(ResourceBundleUtil.MESSAGE_BUNDLE);
        getGeneralServiceApi().create(entity);
        // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(resourceBundle.getString("request.success")));
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
