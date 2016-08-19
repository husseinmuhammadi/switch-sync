package com.dpi.financial.ftcom.web.controller.base.product;


import com.dpi.financial.ftcom.model.to.Product;
import com.dpi.financial.ftcom.utility.producer.LoggerProvider;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ProductController extends ControllerBase<Product> implements Serializable {

    private String name;
    private static LoggerProvider logger = new LoggerProvider();

    public ProductController() {
        super(new Product());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
