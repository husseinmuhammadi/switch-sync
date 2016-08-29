package com.dpi.financial.ftcom.service.base;

import com.dpi.financial.ftcom.api.base.ProductService;
import com.dpi.financial.ftcom.model.dao.ProductDao;
import com.dpi.financial.ftcom.model.to.Product;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(ProductService.class)
public class ProductServiceImpl implements ProductService {

    @EJB
    private ProductDao productDao;

    @Override
    public Product create(Product product) {
        return productDao.create(product);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Product find(Long id) {
        return productDao.findById(id);
    }

    @Override
    public void update(Product product) {
        productDao.update(product);
    }

    @Override
    public void delete(Product product) {
        productDao.remove(product);
    }
}
