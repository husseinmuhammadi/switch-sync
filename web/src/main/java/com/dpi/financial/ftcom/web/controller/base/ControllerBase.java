package com.dpi.financial.ftcom.web.controller.base;

import com.dpi.financial.ftcom.model.base.EntityBase;

public class ControllerBase<T extends EntityBase> {

    protected final T search;

    public ControllerBase(T search) {
        this.search = search;
    }

    public T getSearch() {
        return search;
    }
}
