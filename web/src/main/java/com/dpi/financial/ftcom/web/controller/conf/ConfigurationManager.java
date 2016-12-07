package com.dpi.financial.ftcom.web.controller.conf;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;

/**
 * Created by h.mohammadi on 11/27/2016.
 */
@Dependent
public abstract class ConfigurationManager {

    @PostConstruct
    protected abstract void init();

    @PreDestroy
    protected abstract void destroy();
}
