package com.dpi.financial.ftcom.service;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.base.GenericDao;

import java.util.List;

/**
 * Created by h.mohammadi on 10/26/2016.
 */
public abstract class GeneralServiceImpl<T extends EntityBase> implements GeneralServiceApi<T> {

    public GeneralServiceImpl() {
    }

    public abstract GenericDao<T> getGenericDao();

    @Override
    public T create(T entity) {
        return getGenericDao().create(entity);
    }

    @Override
    public List<T> findAll() {
        return getGenericDao().findAll();
    }

    @Override
    public T find(Long id) {
        return getGenericDao().findById(id);
    }

    @Override
    public void update(T t) {
        getGenericDao().update(t);
    }

    @Override
    public void delete(T t) {
        getGenericDao().remove(t);
    }
}
