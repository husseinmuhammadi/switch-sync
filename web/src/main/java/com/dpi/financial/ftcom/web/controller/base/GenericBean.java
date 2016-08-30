package com.dpi.financial.ftcom.web.controller.base;

import com.dpi.financial.ftcom.model.base.EntityBase;

/**
 *
 */
public interface GenericBean<T extends EntityBase> {

    T getEntity();

    void setEntity(T entity);
}
