package com.dpi.financial.ftcom.web.controller.base.test;


import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.ProductService;
import com.dpi.financial.ftcom.model.to.Product;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class TestController implements Serializable {
    private String name;

    public void onLoad() {
        if (name == null || name.isEmpty())
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("name is empty"));
    }

    public String query() {
        name = "Hossein";
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
