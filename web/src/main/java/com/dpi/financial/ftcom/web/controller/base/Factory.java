package com.dpi.financial.ftcom.web.controller.base;

import com.dpi.financial.ftcom.model.base.EntityBase;

/**
 *
 */
public class Factory<T extends EntityBase> implements IFactory<T> {

    Class<T> entityBeanType;

    public Factory(Class<T> entityBeanType) {
        this.entityBeanType = entityBeanType;
    }

    @Override
    public T createInstance() {
        T t = null;
        try {
            t = entityBeanType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
}
