package com.dpi.financial.ftcom.model.dao;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.Product;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ProductDao extends GenericDao<Product> {
    public ProductDao() {
        super(Product.class);
    }

    public List<Product> findAll() {
        return null;
    }
}
