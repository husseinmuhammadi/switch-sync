package com.dpi.financial.ftcom.web.controller.base.product;


import com.dpi.financial.ftcom.api.base.ProductService;
import com.dpi.financial.ftcom.model.to.Product;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ProductController implements Serializable {

    @EJB
    private ProductService productService;

    private Product product;

    public ProductController() {
        // super(new Product());
        this.product = new Product();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String save() {
        Product product = productService.create(this.product);

        return null;
    }
}
