package com.dpi.financial.ftcom.service.base;

import com.dpi.financial.ftcom.api.base.ProductService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.ProductDao;
import com.dpi.financial.ftcom.model.to.Product;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;
import com.dpi.financial.ftcom.utility.helper.DateHelper;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(ProductService.class)
public class ProductServiceImpl extends GeneralServiceImpl<Product> 
        implements ProductService {

    @EJB
    private ProductDao dao;

    @Override
    public GenericDao<Product> getGenericDao() {
        return dao;
    }

    /*
    @Override
    public Product create(Product product) {
        return dao.create(product);
    }

    @Override
    public List<Product> findAll() {
        return dao.findAll();
    }

    @Override
    public Product find(Long id) {
        return dao.findById(id);
    }

    @Override
    public void update(Product product) {
        dao.update(product);
    }

    @Override
    public void delete(Product product) {
        dao.remove(product);
    }
    */
}
