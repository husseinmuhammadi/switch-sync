package com.dpi.financial.ftcom.web.controller.base;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.to.TestCase;

import javax.enterprise.context.Dependent;

/**
 *
 */

@Dependent
public abstract class ControllerManagerBase<T extends EntityBase> {
    public ControllerManagerBase(Class<TestCase> entityBeanClass) {
    }

    public abstract GeneralServiceApi<T> getGeneralServiceApi();

}
