package com.dpi.financial.ftcom.web.controller.base.product;


import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.ProductService;
import com.dpi.financial.ftcom.model.to.Product;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ProductController extends ControllerBase<Product> implements Serializable {

    @EJB
    private ProductService productService;

    public ProductController() {
        super(Product.class);
    }

    public Product getProduct() {
        return super.getEntity();
    }

    public void setProduct(Product product) {
        super.setEntity(product);
    }

    @Override
    public GeneralServiceApi<Product> getGeneralServiceApi() {
        return productService;
    }

}
