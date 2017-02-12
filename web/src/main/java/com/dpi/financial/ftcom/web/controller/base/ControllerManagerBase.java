package com.dpi.financial.ftcom.web.controller.base;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.base.EntityBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import java.io.IOException;
import java.util.List;

/**
 *
 */

@Dependent
public abstract class ControllerManagerBase<T extends EntityBase>
        extends AbstractController implements Manager {

    Logger logger = LoggerFactory.getLogger(ControllerManagerBase.class);

    protected List<T> entityList;

    public ControllerManagerBase(Class<T> entityBeanClass) {
    }

    @Override
    @PostConstruct
    public void init() {
        logger.info("init list ...");
        entityList = getGeneralServiceApi().findAll();
    }

    public void showNewEntityView() {
        /*
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        RequestContext.getCurrentInstance().openDialog("insert", options, null);
        */

        try {
            String url = getRequestContextPath() + getFacesContext().getViewRoot().getViewId().replace("index", "insert") + "?faces-redirect=true";
            getExternalContext().redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
            printErrorMessage(e);
        }

        // String url = FacesContext.getCurrentInstance().getViewRoot().getViewId().replace("insert", "index") + "?faces-redirect=true";
        // return url;
    }

    protected abstract void onLoad();

    public abstract GeneralServiceApi<T> getGeneralServiceApi();
}
