package com.dpi.financial.ftcom.web.controller.base;

import com.dpi.financial.ftcom.model.base.EntityBase;

/**
 *
 */
public interface IFactory<T extends EntityBase> {
    T createInstance();
}
