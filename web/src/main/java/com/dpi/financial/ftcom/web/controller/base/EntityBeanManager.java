package com.dpi.financial.ftcom.web.controller.base;

import java.lang.reflect.ParameterizedType;

/**
 *
 */
public class EntityBeanManager<T> {
    public T instance;

    public EntityBeanManager() throws Exception {
        instance = (T)((Class)((ParameterizedType)this.getClass().
                getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
    }

    private static class EntityContainer<E>
    {
        E createContents(Class<E> e) throws IllegalAccessException, InstantiationException {
            return e.newInstance();
        }
    }
}
